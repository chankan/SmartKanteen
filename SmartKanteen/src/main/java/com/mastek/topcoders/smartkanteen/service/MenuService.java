package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.CatererMenuMapping;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.MenuTagsMapping;
import com.mastek.topcoders.smartkanteen.bean.Tag;

public interface MenuService
{
	List<Menu> getMenuMaster();

	List<Menu> getMenuMasterByCaterer(Integer catererId);

	List<Menu> getMenuByName(String itemName);

	Menu addItemInMenuMaster(Menu menuMaster) throws Exception;

	Menu addItemInMenuMaster(Menu menu, String tags) throws Exception;

	CatererMenuMapping addItemInMenuMaster(Menu menuMaster, Caterer caterer) throws Exception;

	CatererMenuMapping addItemInMenuMaster(Menu menuMaster, Caterer caterer, String tags) throws Exception;

	Menu updateItemInMenuMaster(Menu menu) throws Exception;

	Menu updateItemInMenuMaster(Menu menuMaster, String tags) throws Exception;

	Menu updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price, Integer prepTime) throws Exception;

	Boolean deleteItemFromMenuMaster(Integer itemId) throws ObjectNotFoundException, Exception;

	MenuTagsMapping addMenuTags(Menu menu, String tags) throws HibernateException, Exception;

	MenuTagsMapping updateMenuTags(Menu menu, String tags) throws HibernateException, Exception;

	Boolean deleteMenuTags(Menu menu) throws HibernateException, Exception;

	Caterer getCaterer(Integer catererId);

	List<Caterer> getCaterers();

	Caterer addCaterer(Caterer caterer) throws Exception;

	Caterer updateCaterer(Integer catererId, String catererName) throws Exception;

	Boolean deleteCaterer(Integer catererId) throws ObjectNotFoundException, Exception;

	List<Menu> getDailyMenu(Date menuDate, Integer catererId);

	DailyMenu addDailyMenu(Integer catererId, Date menuDate, List<Menu> menu) throws Exception;

	Boolean deleteDailyMenu(Integer dailyMenuId) throws ObjectNotFoundException, Exception;
	
	Boolean deleteDailyMenu(Integer catererId, Date menuDate) throws ObjectNotFoundException, Exception;
	
	DailyMenu updateDailyMenuItems(Integer dailyMenuId, List<Menu> menuList) throws Exception;
	
	DailyMenu updateDailyMenu(Integer catererId, Date menuDate, List<Menu> menu) throws Exception;

	Boolean appendDailyMenuItems(Integer dailyMenuId, Menu menu) throws Exception;

	Boolean appendDailyMenuItems(Integer dailyMenuId, List<Menu> menuList) throws Exception;

	Boolean removeDailyMenuItems(Integer dailyMenuId, List<Menu> menuList) throws Exception;

	List<Tag> getTags();

	Tag getTag(Integer tagId);

	Tag addTag(Tag tag) throws Exception;

	Tag updateTag(Tag tag) throws Exception;

	Boolean deleteTag(Tag tag) throws ObjectNotFoundException, Exception;
}
