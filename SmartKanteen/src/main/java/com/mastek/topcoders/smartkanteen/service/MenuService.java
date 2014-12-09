package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;

public interface MenuService
{
	List<Menu> getMenuMaster();

	List<Menu> getMenuMasterByCaterer(Integer catererId);

	Integer addItemInMenuMaster(Menu menuMaster, Caterer caterer);

	void updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price,
			Integer prepTime);
	
	/* Added by @vaibhav
	 * testing Post Operation on RS
	 */
	void addItemMenuMaster(String itemName, String description, BigDecimal price,
			Integer prepTime,Integer category);

	void deleteItemFromMenuMaster(Integer itemId);

	void deleteAllItems();
	
	Caterer getCaterer(Integer catererId);
	
	List<Caterer> getCaterers();
	
	DailyMenu getDailyMenu(Date menuDate, Integer catererId);

}