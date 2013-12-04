package com.mauricelam.Savier;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.amazon.advertising.api.sample.SignedRequestsHelper;

/**
 * User: mauricelam Date: 15/11/13 Time: 12:50 PM
 */
public class AmazonGoal extends Goal {

	private String imageUrl;
	private String amazonId;
	
	private static final String SECRET_KEY = "9MBo0ETT15LZ9wJOcPI2dI0iH+sVauLPSgTZIhMT";
	private static final String AWS_KEY = "AKIAIZABATABRL7L45MQ";
	private static final String ASSOCIATE_TAG = "savier05-20";

	protected AmazonGoal() {
		// no-arg constructor for GSON
		super();
	}

	public AmazonGoal(String name, double target, String amazonId,
			String imageUrl, String url) {
		super(name, target, url);
		this.amazonId = amazonId;
		this.imageUrl = imageUrl;
	}

	@Override
	public String getImageURL() {
		return this.imageUrl;
	}

	private static Document getResponse(String url)
			throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = builder.parse(url);
		return doc;
	}

	public static AmazonGoal fromId(String amazonId) {
		// Query the amazon API here, and set the correct target

		// FIXME ---------------
		//int target = 10000;
		//String imageUrl = "http://www.blogcdn.com/www.joystiq.com/media/2011/10/amazon-logo.png";
		// String url =
		// "http://www.amazon.com/gp/product/0743264738/ref=s9_qpp_gw_p14_d99_i2?pf_rd_m=ATVPDKIKX0DER&pf_rd_s=center-4&pf_rd_r=16SQ7XB1AD4VC2NG0ERF&pf_rd_t=101&pf_rd_p=1630083682&pf_rd_i=507846";
		// FIXME ---------------
		
		String itemTitle = null;
		double itemPrice = 0.0;
		String itemPriceFormatted = null; // in String format, e.g. "$199.99"
		String itemImageURL = null;
		String itemURL = null;

		SignedRequestsHelper helper = null;
		try {
			helper = SignedRequestsHelper.getInstance("ecs.amazonaws.com", AWS_KEY, SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", amazonId);
		params.put("ResponseGroup", "Large");
		params.put("AssociateTag", ASSOCIATE_TAG);

		String url = helper.sign(params);
		try {
			/* Get XML Object */
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document response = builder.parse(url);
			response.getDocumentElement().normalize();
			
			/* Item Title */
			itemTitle = extractFromDoc("Title", response);
			
			/* Item Price */
			itemPriceFormatted = extractFromDoc("FormattedPrice", response);
			String itemPriceAmountString = extractFromDoc("Amount", response);
			int itemPriceAmountInt = Integer.parseInt(itemPriceAmountString);
			itemPrice = itemPriceAmountInt / 100.;
			
			/* Item ImageURL */			
			NodeList nodeList = response.getElementsByTagName("MediumImage");
			Node node = nodeList.item(0);
			Element element = (Element) node;
			itemImageURL = element.getElementsByTagName("URL").item(0).getTextContent();
			
			/* Item URL */
			itemURL = extractFromDoc("DetailPageURL", response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new AmazonGoal(itemTitle, itemPrice, "B00CU0NSCU", itemImageURL, itemURL);
	}

	private static String extractFromDoc(String tag, Document doc) {
		NodeList nodeList = doc.getElementsByTagName(tag);
		Node node = nodeList.item(0);
		return ((Element)node).getTextContent();
	}
	
}
