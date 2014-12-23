package com.mastek.topcoders.smartkanteen.bean;

// Generated Dec 4, 2014 12:33:20 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * MenuMaster generated by hbm2java
 */
@XmlRootElement
public class Menu implements java.io.Serializable
{
	private Integer itemId;
	private String itemName;
	private String description;
	private BigDecimal price;
	private Integer prepTime;
	private MenuTagsMapping menuTagsMapping;

	public Integer getItemId()
	{
		return itemId;
	}

	public void setItemId(Integer itemId)
	{
		this.itemId = itemId;
	}

	public String getItemName()
	{
		return itemName;
	}

	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	public Integer getPrepTime()
	{
		return prepTime;
	}

	public void setPrepTime(Integer prepTime)
	{
		this.prepTime = prepTime;
	}

	public MenuTagsMapping getMenuTagsMapping()
	{
		return menuTagsMapping;
	}

	public void setMenuTagsMapping(MenuTagsMapping menuTagsMapping)
	{
		this.menuTagsMapping = menuTagsMapping;
	}

	@Override
	public String toString()
	{
		return "Menu [itemId=" + itemId + ", itemName=" + itemName + ", description=" + description + ", price="
				+ price + ", prepTime=" + prepTime + ", menuTagsMapping=" + menuTagsMapping + "]";
	}
}
