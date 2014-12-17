package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;


@Path("/kanteen")
public class MenuResource implements IMenuResource {

	private final MenuServiceImpl menuService;

	public MenuResource() {
		menuService = new MenuServiceImpl();
	}


	// Fetching Master Menu List

	@Path("/menu")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public List<Menu> getMenuMaster() {
		List<Menu> menu;
		menu= menuService.getMenuMaster();
		return menu;
	}

	//Fetching Menu by CatererId

	@Path("/menu/caterer/{catererId}")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public List<Menu> getMenuMasterByCaterer(@PathParam("catererId")Integer catererId) {
		List<Menu> menu;
		menu= menuService.getMenuMasterByCaterer(catererId);
		return menu;
	}

	//Fetching Menu By Name
	@Path("/menu/{itemName}")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public List<Menu> getMenuByName(@PathParam("itemName")String itemName) {
		List<Menu> menu;
		menu= menuService.getMenuByName(itemName);
		return menu;
	}

	//Adding Menu using MenuMaster object

	@Path("/menu/add")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Integer addItemInMenuMaster(Menu menuMaster) {
		menuService.addItemInMenuMaster(menuMaster);
		return null;
	}

	//Adding Menu for Specific Caterer
	@Path("/caterer/add/menu")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Integer addItemInMenuMaster(Menu menuMaster, Caterer caterer){
		menuService.addItemInMenuMaster(menuMaster,caterer);
		return null;
	}


	//Updating MenuMaster Objects
	@Path("/menu/update")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void updateItemInMenuMaster(Menu menu){
		menuService.addItemInMenuMaster(menu);
	}


	//Updating Menu in Menu Master table


	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public void updateItemInMenuMaster(@FormParam("itemid")Integer itemId, 
			@FormParam("itemname") String itemName,
			@FormParam("desc") String description,
			@FormParam("price") BigDecimal price,
			@FormParam("time") Integer prepTime) {
		// TODO Auto-generated method stub

		//itemId=1;
		itemName="test1";
		description="update test";
		price=new BigDecimal(20);
		prepTime= 10;

		menuService.updateItemInMenuMaster(itemId, itemName, description, price, prepTime);

	}


	//Deleting Menu item 
	@Path("/menu/delete/{itemId}")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Response deleteItemFromMenuMaster(@PathParam("itemId")Integer itemId) {
		// TODO Auto-generated method stub

		try{
			if (menuService.deleteItemFromMenuMaster(itemId)) {
				return Response   
						.status(200)   
						.entity("Menu with Id "+itemId+" is deleted!!").build();

			}
			else{
				return Response   
						.status(200)   
						.entity("No Such Menu Exist!!").build();
			}

		}
		catch (Exception e) {
			// TODO: handle exception
			return Response   
					.status(200)   
					.entity("No Such Menu Exist!!").build();
		}


	}


	//Fetching All caterers

	@Path("/caterer")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public 	List<Caterer> getCaterers() {
		List<Caterer> caterer;
		caterer= menuService.getCaterers();
		return caterer;
	}

	//Fetching Caterer using CatererID

	@Path("/caterer/{catererId}")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public Caterer getCaterer(@PathParam("catererId")Integer catererId) {
		Caterer caterer;
		caterer=menuService.getCaterer(catererId);
		return caterer;
	}

	//Adding Caterer using Caterer Object

	@Path("caterer/add")
	@POST
	@Produces  ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override 
	public Integer addCaterer(Caterer caterer) {
		// TODO Auto-generated method stub
		menuService.addCaterer(caterer);
		return null;
	}

	//Updating Caterer Objects
	@Path("caterer/{catererId}/update/catererName/{catererName}")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Boolean updateCaterer(@PathParam("catererId")Integer catererId, @PathParam("catererName")String catererName){
		return(menuService.updateCaterer(catererId, catererName));
	}


	//Deleting Caterer
	@Path("caterer/delete/{catererId}")
	@GET
	@Produces({MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Response deleteCaterer(@PathParam("catererId")Integer catererId) {
		// TODO Auto-generated method stub

		try {
			if(menuService.deleteCaterer(catererId))
			{
				return Response   
						.status(200)   
						.entity("Caterer with Id "+catererId+" is deleted!!").build();
			}
			else
			{
				return Response   
						.status(200)   
						.entity("No Such Caterer Exist!!").build();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response   
					.status(404)			   
					.entity("No Such Caterer Exist!!").build();
		}
	}



	//Fetching DailyMenu

	@Path("/menu/caterer/{catererId}/date/{date}")
	@GET
	@Produces  ({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public List<Menu> getDailyMenu(@PathParam("date")DateParam menuDate,
			@PathParam("catererId")Integer catererId) {
		List<Menu> dailymenu;
		Date date=menuDate.getDate();
		dailymenu = menuService.getDailyMenu(date, catererId);
		return dailymenu;
	}

	//Adding Daily Menu
	@Path("/menu/caterer/{catererId}/date/{date}/add/")
	@POST
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void addDailyMenu(@PathParam("catererId")Integer catererId, @PathParam("date")DateParam menuDate, List<Menu> menu) {
		Date date=menuDate.getDate();
		menuService.addDailyMenu(catererId, date, menu);
	}

	//updating Daily Menu
	@Path("/menu/caterer/update/{dailyMenuId}/")
	@POST
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void updateDailyMenu(Integer dailyMenuId, List<Menu> menuList) {
		// TODO Auto-generated method stub
		menuService.updateDailyMenuItems(dailyMenuId, menuList);
	}


	//appending Daily Menu
	@Path("/menu/caterer/append/{dailyMenuId}/")
	@POST
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void appendDailyMenu(Integer dailyMenuId, List<Menu> menuList) {
		// TODO Auto-generated method stub
		menuService.appendDailyMenuItems(dailyMenuId, menuList);

	}

	//deleting Daily Menu
	@Path("/menu/caterer/delete/{dailyMenuId}/")
	@POST
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public Boolean deleteDailyMenu(@PathParam("dailyMenuId")Integer dailyMenuId) {
		// TODO Auto-generated method stub
		return (menuService.deleteDailyMenu(dailyMenuId));
	}
	
	
	// Removing daily Menu Items

	@Path("/menu/caterer/remove/{dailyMenuId}/")
	@POST
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public Boolean removeDailyMenuItems(Integer dailyMenuId, List<Menu> menuList) {
		// TODO Auto-generated method stub
		return (menuService.removeDailyMenuItems(dailyMenuId, menuList));
	}


	//Adding Menu using Form Fields

	/*@Path("/add")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public void addItemMenuMaster(
			@FormParam("itemname") String itemName,
			@FormParam("desc") String description,
			@FormParam("price") BigDecimal price,
			@FormParam("time") Integer prepTime,
			@FormParam("category") Integer category){
		if(itemName !=null && description !=null && price !=null)// && prepTime !=null && category !=null)
		{
			if(itemName.length()>50)
			{
				System.out.println("Name should not be greater than 50 characters!!");	
			}
			else if(description.length()>250)
			{
				System.out.println("Desc should not be greater than 250 characters!!");
			}
			else
			{
				menuService.addItemMenuMaster(itemName, description, price, prepTime, category);
			}

		}
	}



	 *
	 *
	 *
	 *
	 *
	 */



	public static void main(String[] args) {
		MenuResource resource= new MenuResource();
		/*Menu menu= new Menu();
		menu.setItemName("testAdd");
		menu.setDescription("testDesc");
		menu.setPrepTime(10);
		menu.setPrice(new BigDecimal(20.0));

		resource.addItemInMenuMaster(menu);*/
		/*
		Caterer caterer = new Caterer();
		//caterer.setCatererId(4);
		caterer.setCatererName("mdp");
		resource.addCaterer(caterer);*/

		/*	Menu menu1=new Menu();
		menu1.setItemId(8);
		List<Menu> menuList= new ArrayList<Menu>();
		menuList.add(menu1);

		resource.addDailyMenu(2, new DateParam("2014-12-16"), menuList);*/
		List<Menu> menuList= new ArrayList<Menu>();
		menuList=resource.getMenuMaster();
		System.out.println(menuList);
	}


	




}
