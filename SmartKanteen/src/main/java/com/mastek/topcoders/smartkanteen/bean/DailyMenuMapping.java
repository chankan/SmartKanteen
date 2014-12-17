package com.mastek.topcoders.smartkanteen.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DailyMenuMapping
{
	private Integer dailyMenuMappingId;
	private Menu menu;
	private Integer dailyMenuId;

	public Integer getDailyMenuMappingId()
	{
		return dailyMenuMappingId;
	}

	public void setDailyMenuMappingId(Integer dailyMenuMappingId)
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

	public Integer getDailyMenuId()
	{
		return dailyMenuId;
	}

	public void setDailyMenuId(Integer dailyMenuId)
	{
		this.dailyMenuId = dailyMenuId;
	}

	@Override
	public String toString()
	{
		return "DailyMenuMapping [dailyMenuMappingId=" + dailyMenuMappingId + ", menu=" + menu + ", dailyMenuId="
				+ dailyMenuId + "]";
	}
}
