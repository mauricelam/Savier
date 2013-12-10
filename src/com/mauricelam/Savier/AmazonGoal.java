package com.mauricelam.Savier;

import java.util.Map;

/**
 * User: mauricelam Date: 15/11/13 Time: 12:50 PM
 */
public class AmazonGoal extends Goal {

	private String productID;
	private String title;
	private String imageURL;
	private String priceFormatted;
	
	protected AmazonGoal() {
		// no-arg constructor for GSON
		super();
	}

	public AmazonGoal(Map<String, String> productInfo) {
		super(productInfo.get("Title"), Double.parseDouble(productInfo.get("Price").substring(1)), 
				productInfo.get("URL"), productInfo.get("Description"));
		this.setProductID(new String(productInfo.get("ProductID")));
		this.setTitle(new String(productInfo.get("Title")));
		this.setImageURL(new String(productInfo.get("ImageURL")));
		this.priceFormatted = new String(productInfo.get("Price"));
	}
	
	/**
	 * Constructor with the user customized name
	 * @param productInfo
	 */
	public AmazonGoal(Map<String, String> productInfo, String goalName) {
		super(goalName, Double.parseDouble(productInfo.get("Price")), 
				productInfo.get("URL"), productInfo.get("Description"));
		this.setProductID(new String(productInfo.get("ProductID")));
		this.setTitle(new String(productInfo.get("Title")));
		this.setImageURL(new String(productInfo.get("ImageURL")));
		this.priceFormatted = new String(productInfo.get("Price"));
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getURL() {
		return super.getUrl();
	}
	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public double getPrice() {
		return super.getTarget();
	}

	public String getPriceFormatted() {
		return imageURL;
	}

	public String getDescription() {
		return super.getDescription();
	}
	
}
