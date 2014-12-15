package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;

public interface MenuService
{
	List<Menu> getMenuMaster();

	List<Menu> getMenuMasterByCaterer(Integer catererId);

	List<Menu> getMenuByName(String itemName);

	Integer addItemInMenuMaster(Menu menuMaster);

	void addItemInMenuMaster(Menu menuMaster, Caterer caterer);

	void updateItemInMenuMaster(Menu menu);

	void updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price, Integer prepTime);

	void deleteItemFromMenuMaster(Integer itemId);

	Caterer getCaterer(Integer catererId);

	List<Caterer> getCaterers();

	Integer addCaterer(Caterer caterer);

	Boolean updateCaterer(Integer catererId, String catererName);

	Boolean deleteCaterer(Integer catererId);

	List<Menu> getDailyMenu(Date menuDate, Integer catererId);

	void addDailyMenu(Integer catererId, Date menuDate, List<Menu> menu);

	void deleteDailyMenu(Integer dailyMenuId);

	void updateDailyMenuItems(Integer dailyMenuId, List<Menu> menuList);

	void appendDailyMenuItems(Integer dailyMenuId, Menu menu);

	void appendDailyMenuItems(Integer dailyMenuId, List<Menu> menuList);

	void removeDailyMenuItems(Integer dailyMenuId, List<Menu> menuList);
}
