package com.mauricelam.Savier;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.os.AsyncTask;
import android.util.Log;

import com.amazon.advertising.api.sample.SignedRequestsHelper;


public class AmazonProductLookup {
	
	private final String SECRET_KEY = "9MBo0ETT15LZ9wJOcPI2dI0iH+sVauLPSgTZIhMT";
	private final String AWS_KEY = "AKIAIZABATABRL7L45MQ";
	private final String ASSOCIATE_TAG = "savier05-20";
	
	private String productID;
	
	public AmazonProductLookup(String productID) {
		this.productID = productID;
	}
	
	
	private class LookupTask extends AsyncTask<String, Void, Map<String, String>> {

		@Override
		protected Map<String, String> doInBackground(String... params) {
			String productID = params[0];
			Map<String, String> result = new HashMap<String, String>();
			
			String itemTitle = null;
			double itemPrice = 0.0;
			String itemPriceFormatted = null; // in String format, e.g. "$199.99"
			String itemImageURL = null;
			String itemURL = null;
			String itemDescription = null;

			SignedRequestsHelper helper = null;
			try {
				helper = SignedRequestsHelper.getInstance("ecs.amazonaws.com", AWS_KEY, SECRET_KEY);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("Service", "AWSECommerceService");
			parameters.put("Operation", "ItemLookup");
			parameters.put("ItemId", productID);
			parameters.put("ResponseGroup", "Large");
			parameters.put("AssociateTag", ASSOCIATE_TAG);

			String url = helper.sign(parameters);
			Log.d("queryURL", url);
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
				
				/* Item Description */
				Element editorialReviews = (Element)(response.getElementsByTagName("EditorialReviews").item(0));
				Element editorialReview = (Element)(editorialReviews.getElementsByTagName("EditorialReview").item(0));
				itemDescription = (editorialReview.getElementsByTagName("Content").item(0)).getTextContent();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			result.put("Title", itemTitle);
			result.put("Price", itemPriceFormatted);
			result.put("ImageURL", itemImageURL);
			result.put("ItemURL", itemURL);
			result.put("Description", itemDescription);
			
			return result;
			
		}
		
	}
	/**
	 * 
	 * @param amazonId
	 * @return 
	 */
	public Map<String, String> lookup() {
		
		Map<String, String> result; // = new HashMap<String, String>();
		// Query the amazon API here, and set the correct target
		LookupTask lookup = new LookupTask();
		try {
			result = lookup.execute(productID).get();
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
	
}