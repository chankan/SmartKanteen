package com.mastek.topcoders.smartkanteen.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Menu {
	private int  itemID;
	private String itemName;
	private String description;
	private float price;
	private int prepTime;
	
	
	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Menu(int itemID, String itemName, String description, float price,
			int prepTime) {
		super();
		this.itemID = itemID;
		this.itemName = itemName;
		this.description = description;
		this.price = price;
		this.prepTime = prepTime;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getPrepTime() {
		return prepTime;
	}
	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}
	@Override
	public String toString() {
		return "Menu [itemID=" + itemID + ", itemName=" + itemName
				+ ", description=" + description + ", price=" + price
				+ ", prepTime=" + prepTime + "]";
	}
	
}
