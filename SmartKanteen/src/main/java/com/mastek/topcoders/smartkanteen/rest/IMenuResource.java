package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.Menu;


public interface IMenuResource {
	
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Menu> getMenuMaster ();
	
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<DailyMenu> getDailyMenu(DateParam menuDate, Integer catererId);
	
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Menu> getMenuMasterByCaterer(Integer catererId);
	
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Caterer> getCaterers();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Caterer getCaterer(Integer catererId);
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	Integer addItemInMenuMaster(Menu menuMaster);
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	void addItemMenuMaster(String itemName, String description,
			BigDecimal price, Integer prepTime,Integer category);
	
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	void updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price,
			Integer prepTime);

//	@POST
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	Integer addCaterer(Caterer caterer);
//	
//	@POST
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	void addDailyMenu(Integer catererId, Date menuDate, List<Menu> menu);
//	
//	@POST
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	void updateDailyMenu(Integer dailyMenuId, List<Menu> menuList);
//	
//	@POST
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	void appendDailyMenu(Integer dailyMenuId, Menu menu);
//
//	@POST
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	void appendDailyMenu(Integer dailyMenuId, List<Menu> menuList);
	
	
	/*
	
	
	
	
	
	
	
	
	
	
	
	
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GET
	
	

	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	Integer addItemInMenuMaster(Menu menuMaster, Caterer caterer);

	@POST
	@Produces(MediaType.APPLICATION_XML)
	void deleteItemFromMenuMaster(Integer itemId);

	@POST
	@Produces(MediaType.APPLICATION_XML)
	void deleteAllItemsFromMenuMaster();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	Caterer getCaterer(Integer catererId);
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	List<Caterer> getCaterers();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	Integer addCaterer(Caterer caterer);
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	Boolean updateCaterer(Integer catererId, String catererName);
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	Boolean deleteCaterer(Integer catererId);
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	DailyMenu getDailyMenu(Date menuDate, Integer catererId);

	
*/	
	
}
