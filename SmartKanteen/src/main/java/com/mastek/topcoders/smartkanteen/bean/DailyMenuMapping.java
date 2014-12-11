package com.mastek.topcoders.smartkanteen.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DailyMenuMapping
{
	private int dailyMenuMappingId;
	private Menu menu;
	private int dailyMenuId;

	public int getDailyMenuMappingId()
	{
		return dailyMenuMappingId;
	}

	public void setDailyMenuMappingId(int dailyMenuMappingId)
	{
		this.dailyMenuMappingId = dailyMenuMappingId;
	}

	public Menu getMenu()
	{
		return menu;
	}

	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}

	public int getDailyMenuId()
	{
		return dailyMenuId;
	}

	public void setDailyMenuId(int dailyMenuId)
	{
		this.dailyMenuId = dailyMenuId;
	}

	@Override
	public String toString()
	{
		return "DailyMenuMapping [menu=" + menu + ", dailyMenuId=" + dailyMenuId + "]";
	}

}
