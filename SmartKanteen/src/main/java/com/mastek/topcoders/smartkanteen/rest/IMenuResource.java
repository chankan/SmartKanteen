package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.core.Response;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;


public interface IMenuResource {

	//Methods related to Master Menu

	List<Menu> getMenuMaster ();
		
	List<Menu> getMenuMasterByCaterer(Integer catererId);
	
	List<Menu> getMenuByName(String itemName);
	
	Integer addItemInMenuMaster(Menu menuMaster);
		
	Integer addItemInMenuMaster(Menu menuMaster, Caterer caterer);
		
	void updateItemInMenuMaster(Menu menu);
		
	void updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price,
			Integer prepTime);
	
	Response deleteItemFromMenuMaster(Integer itemId);
	
	//Methods Ends
	
	
	
	
	//Methods related to caterer
	
	List<Caterer> getCaterers();
	
	Caterer getCaterer(Integer catererId);
	
	Integer addCaterer(Caterer caterer);
	
	Boolean updateCaterer(Integer catererId, String catererName);
	
	Response deleteCaterer(Integer catererId);
	
	//Methods Ends
	
	
	
	
	//Methods related to DailyMenu
	
	List<Menu> getDailyMenu(DateParam menuDate, Integer catererId);
	
	void addDailyMenu(Integer catererId, DateParam menuDate, List<Menu> menu);
	
	void updateDailyMenu(Integer dailyMenuId, List<Menu> menuList);
	
	void appendDailyMenu(Integer dailyMenuId, List<Menu> menuList);
	
	Boolean deleteDailyMenu(Integer dailyMenuId);
	
	Boolean removeDailyMenuItems(Integer dailyMenuId, List<Menu> menuList);
	
	//Method Ends

}
