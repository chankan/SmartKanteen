package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.Tag;

public interface MenuService
{
	List<Menu> getMenuMaster();

	List<Menu> getMenuMasterByCaterer(Integer catererId);

	List<Menu> getMenuByName(String itemName);

	Integer addItemInMenuMaster(Menu menuMaster);

	Integer addItemInMenuMaster(Menu menu, String tags);

	void addItemInMenuMaster(Menu menuMaster, Caterer caterer);

	void addItemInMenuMaster(Menu menuMaster, Caterer caterer, String tags);

	void updateItemInMenuMaster(Menu menu);

	void updateItemInMenuMaster(Menu menuMaster, String tags);

	void updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price, Integer prepTime);

	Boolean deleteItemFromMenuMaster(Integer itemId);

	void addMenuTags(Menu menu, String tags);

	void updateMenuTags(Menu menu, String tags);

	void deleteMenuTags(Menu menu);

	Caterer getCaterer(Integer catererId);

	List<Caterer> getCaterers();

	Integer addCaterer(Caterer caterer);

	Boolean updateCaterer(Integer catererId, String catererName);

	Boolean deleteCaterer(Integer catererId);

	List<Menu> getDailyMenu(Date menuDate, Integer catererId);

	void addDailyMenu(Integer catererId, Date menuDate, List<Menu> menu);

	Boolean deleteDailyMenu(Integer dailyMenuId);

	void updateDailyMenuItems(Integer dailyMenuId, List<Menu> menuList);

	void appendDailyMenuItems(Integer dailyMenuId, Menu menu);

	void appendDailyMenuItems(Integer dailyMenuId, List<Menu> menuList);

	Boolean removeDailyMenuItems(Integer dailyMenuId, List<Menu> menuList);

	List<Tag> getTags();

	Tag getTag(Integer tagId);

	void addTag(Tag tag);

	void updateTag(Tag tag);

	void deleteTag(Tag tag);
}
