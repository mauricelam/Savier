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

    private final String SECRET_KEY = "SECRET_KEY";
    private final String AWS_KEY = "AWS_KEY";
    private final String ASSOCIATE_TAG = "ASSOCIATE_TAG";

    private String productID;
    private String itemTitle = null;
    private double itemPrice = 0.0;
    private String itemPriceFormatted = null; // in String format, e.g. "$199.99"
    private String itemImageURL = null;
    private String itemURL = null;
    private String itemDescription = null;

    public AmazonProductLookup(String productID) {
        this.productID = productID;
    }

    /**
     * Return only the string values
     *
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

    public double getPrice() {
        return itemPrice;
    }

    private class LookupTask extends AsyncTask<String, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(String... params) {
            String productID = params[0];
            Map<String, String> result = new HashMap<String, String>();

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
            Log.d("Savier queryURL", url);
            try {
				/* Get XML Object */
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document response = builder.parse(url);
                response.getDocumentElement().normalize();

                try {
                    /* Item Title */
                    itemTitle = (response.getElementsByTagName("Title").item(0)).getTextContent();
                } catch (Exception e) {
                }
				
				/* Item Price */
                try {
                    Element bestOffer = (Element)(response.getElementsByTagName("Price").item(0));
                    itemPriceFormatted = bestOffer.getElementsByTagName("FormattedPrice").item(0).getTextContent();
                    String itemPriceAmountString = bestOffer.getElementsByTagName("Amount").item(0).getTextContent();
                    int itemPriceAmountInt = Integer.parseInt(itemPriceAmountString);
                    itemPrice = itemPriceAmountInt / 100.;
                } catch (Exception e) {
                    try {
                        Element listPrice = (Element) response.getElementsByTagName("ListPrice").item(0);
                        itemPriceFormatted = listPrice.getElementsByTagName("FormattedPrice").item(0).getTextContent();
                        String itemPriceAmountString = listPrice.getElementsByTagName("Amount").item(0).getTextContent();
                        int itemPriceAmountInt = Integer.parseInt(itemPriceAmountString);
                        itemPrice = itemPriceAmountInt / 100.;
                    } catch (Exception e2) {
                    }
                }

                try {
                    /* Item ImageURL */
                    itemImageURL = ((Element)response.getElementsByTagName("MediumImage").item(0))
                            .getElementsByTagName("URL").item(0).getTextContent();
                } catch (Exception e) {
                }

                try {
                    /* Item URL */
                    itemURL = (response.getElementsByTagName("DetailPageURL").item(0)).getTextContent();
                } catch (Exception e) {
                }

                try {
                /* Item Description */
                    Element editorialReviews = (Element)(response.getElementsByTagName("EditorialReviews").item(0));
                    Element editorialReview = (Element)(editorialReviews.getElementsByTagName("EditorialReview").item(0));
                    itemDescription = (editorialReview.getElementsByTagName("Content").item(0)).getTextContent();
                } catch (Exception e) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            result.put("ProductID", productID);
            result.put("Title", itemTitle);
            result.put("Price", itemPriceFormatted);
            result.put("ImageURL", itemImageURL);
            result.put("ItemURL", itemURL);
            result.put("Description", itemDescription);

            return result;

        }

    }


}
