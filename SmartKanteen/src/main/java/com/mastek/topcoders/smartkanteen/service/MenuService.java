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
	
	List<Menu> getMenuByName(String itemName);

	Integer addItemInMenuMaster(Menu menuMaster);

	void addItemInMenuMaster(Menu menuMaster, Caterer caterer);

	/*
	 * Added by @vaibhav testing Post Operation on RS
	 */
	void addItemMenuMaster(String itemName, String description, BigDecimal price, Integer prepTime, Integer category);

	void updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price, Integer prepTime);

	void deleteItemFromMenuMaster(Integer itemId);

	void deleteAllItemsFromMenuMaster();

	Caterer getCaterer(Integer catererId);

	List<Caterer> getCaterers();

	Integer addCaterer(Caterer caterer);

	Boolean updateCaterer(Integer catererId, String catererName);

	Boolean deleteCaterer(Integer catererId);

	List<DailyMenu> getDailyMenu(Date menuDate, Integer catererId);

	void addDailyMenu(Integer catererId, Date menuDate, List<Menu> menu);

	void updateDailyMenu(Integer dailyMenuId, List<Menu> menuList);
	
	void appendDailyMenu(Integer dailyMenuId, Menu menu);

	void appendDailyMenu(Integer dailyMenuId, List<Menu> menuList);
}
