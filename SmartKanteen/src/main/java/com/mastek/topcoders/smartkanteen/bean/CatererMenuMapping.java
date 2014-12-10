package com.mastek.topcoders.smartkanteen.bean;


public class CatererMenuMapping
{
	private int caterer_menu_mapping_id;
	private Menu menu;
	private int catererId;

	public CatererMenuMapping()
	{
		// TODO Auto-generated constructor stub
	}

	public int getCaterer_menu_mapping_id()
	{
		return caterer_menu_mapping_id;
	}

	public void setCaterer_menu_mapping_id(int caterer_menu_mapping_id)
	{
		this.caterer_menu_mapping_id = caterer_menu_mapping_id;
	}

	public Menu getMenu()
	{
		return menu;
	}

	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}

	public int getCatererId()
	{
		return catererId;
	}

	public void setCatererId(int catererId)
	{
		this.catererId = catererId;
	}

	@Override
	public String toString()
	{
		return "CatererMenuMapping [caterer_menu_mapping_id=" + caterer_menu_mapping_id + ", menu=" + menu
				+ ", catererId=" + catererId + "]";
	}

}
