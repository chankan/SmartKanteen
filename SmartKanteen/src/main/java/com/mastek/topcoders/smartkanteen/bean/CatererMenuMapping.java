package com.mastek.topcoders.smartkanteen.bean;

public class CatererMenuMapping
{
	private Integer catererMenuMappingId;
	private Menu menu;
	private Integer catererId;

	public Integer getCatererMenuMappingId()
	{
		return catererMenuMappingId;
	}

	public void setCatererMenuMappingId(Integer catererMenuMappingId)
	{
		this.catererMenuMappingId = catererMenuMappingId;
	}

	public Menu getMenu()
	{
		return menu;
	}

	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}

	public Integer getCatererId()
	{
		return catererId;
	}

	public void setCatererId(Integer catererId)
	{
		this.catererId = catererId;
	}

	@Override
	public String toString()
	{
		return "CatererMenuMapping [catererMenuMappingId=" + catererMenuMappingId + ", menu=" + menu + ", catererId="
				+ catererId + "]";
	}
}
