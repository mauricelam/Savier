package com.mauricelam.Savier;

import com.amazon.advertising.api.sample.SignedRequestsHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

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

	public static AmazonGoal fromId(String amazonId) {
		// Query the amazon API here, and set the correct target
		
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
			itemTitle = (response.getElementsByTagName("Title").item(0)).getTextContent();
			
			/* Item Price */
			Element bestOffer = (Element)(response.getElementsByTagName("Price").item(0));
			itemPriceFormatted = bestOffer.getElementsByTagName("FormattedPrice").item(0).getTextContent();
			String itemPriceAmountString = bestOffer.getElementsByTagName("Amount").item(0).getTextContent();
			int itemPriceAmountInt = Integer.parseInt(itemPriceAmountString);
			itemPrice = itemPriceAmountInt / 100.;
			
			/* Item ImageURL */			
			itemImageURL = ((Element)response.getElementsByTagName("MediumImage").item(0))
					.getElementsByTagName("URL").item(0).getTextContent();
			
			/* Item URL */
			itemURL = (response.getElementsByTagName("DetailPageURL").item(0)).getTextContent();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new AmazonGoal(itemTitle, itemPrice, amazonId, itemImageURL, itemURL);
	}
	
}
