package com.mauricelam.Savier;

import android.util.Log;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

/**
 * User: mauricelam Date: 15/11/13 Time: 12:50 PM
 */
public class AmazonGoal extends Goal {

	private String productID;
	private String imageURL;

	protected AmazonGoal() {
		// no-arg constructor for GSON
		super();
	}

    public AmazonGoal(String title, int target, String url, String description, String productID, String imageURL) {
        super(title, target, url, description);
        this.productID = productID;
        this.imageURL = imageURL;
    }

	public static AmazonGoal fromMap(Map<String, String> info) {
        double target = 0;
        String price = null;
        try {
            target = NumberFormat.getCurrencyInstance().parse(info.get("Price")).doubleValue();
        } catch (ParseException e) {
            Log.w("Savier Amazon parsing", "Number format exception: " + price);
        }
		AmazonGoal goal = new AmazonGoal(
                info.containsKey("Title") ? info.get("Title") : "Unknown title",
                (int) (target * 100),
				info.containsKey("URL") ? info.get("URL") : "https://www.amazon.com",
                info.containsKey("Description") ? info.get("Description") : "",
                info.containsKey("ProductID") ? info.get("ProductID") : "Unknown ID",
                info.get("ImageURL")
        );
        return goal;
	}

    public static AmazonGoal fromMap(Map<String, String> info, String customName) {
        info.put("Title", customName);
        return AmazonGoal.fromMap(info);
    }

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}
	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getDesc() {
		return super.getDesc();
	}
	
}
