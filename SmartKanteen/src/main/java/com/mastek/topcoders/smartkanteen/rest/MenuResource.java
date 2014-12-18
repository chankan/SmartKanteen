package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;


@Path("/service")
public class MenuResource implements IMenuResource {

	private final MenuServiceImpl menuService;

	public MenuResource() {
		menuService = new MenuServiceImpl();
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

	@Path("/caterer")
	@POST
	@Produces  ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override 
	public Integer addCaterer(Caterer caterer) {
		// TODO Auto-generated method stub
		menuService.addCaterer(caterer);
		return null;
	}

	//Updating Caterer Objects
	@Path("/caterer/{catererId}/")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void updateCaterer(@PathParam("catererId")Integer catererId, //@FormParam("catererName")
			String catererName){
		//create dummy date for catererName
		catererName="xyz";
		menuService.updateCaterer(catererId, catererName);
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



	//Fetching Menu by CatererId

	@Path("/caterer/{catererId}/menu")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public List<Menu> getMenuMasterByCaterer(@PathParam("catererId")Integer catererId) {
		List<Menu> menu;
		menu= menuService.getMenuMasterByCaterer(catererId);
		return menu;
	}

	//Adding Menu for Specific Caterer
	@Path("/caterer/{catererId}/menu/")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Integer addItemInMenuMaster(Menu menuMaster,@PathParam("catererId")int catererId){
		if(MenuValidation.validate(menuMaster))
		{
			if(true)//menuMaster.getItemId()<1 || menuMaster.getItemId()==null)
			{
				Caterer caterer= new Caterer();
				caterer.setCatererId(catererId);
				menuService.addItemInMenuMaster(menuMaster,caterer);
			}
			else
			{
				//updating Menu for particular customer
				menuService.updateItemInMenuMaster(menuMaster);
			}
		}
		else
		{
			System.out.println("Menu Constraints Not Followed!!");
		}
		return null;
	}


	//Fetching DailyMenu

	@Path("/caterer/{catererId}/menu/date/{date}")
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
	@Path("/caterer/{catererId}/menu/date/{date}")
	@POST
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void addDailyMenu(@PathParam("catererId")Integer catererId, @PathParam("date")DateParam menuDate, List<Menu> menu) {
		System.out.println("iN add daily");
		Date date=menuDate.getDate();
		menuService.addDailyMenu(catererId, date, menu);
	}

	//		//updating Daily Menu
	//		@Path("/caterer/{catererId}/menu/date/{date}")
	//		//@POST
	//		@PUT
	//		@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	//		@Override
	//		public void updateDailyMenu(Integer dailyMenuId, List<Menu> menuList) {
	//			// TODO Auto-generated method stub
	//			menuService.updateDailyMenuItems(dailyMenuId, menuList);
	//		}


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

	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Integer addItemInMenuMaster(Menu menuMaster) {
		//		MenuValidation validateService = new MenuValidation();
		if(MenuValidation.validate(menuMaster))
		{
			menuService.addItemInMenuMaster(menuMaster);
		}
		else
		{
			System.out.println("Menu Constraints Not Followed!!");
		}

		return null;
	}








	//Updating MenuMaster Objects
	@Path("/menu/update")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void updateItemInMenuMaster(Menu menu){
		menuService.updateItemInMenuMaster(menu);
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
	@DELETE
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
		Menu menu= new Menu();
		menu.setItemName("testAddN1");
		menu.setDescription("testDescNew");
		menu.setPrepTime(20000);
		menu.setPrice(new BigDecimal(20.0));

		//				Menu menu= new Menu();
		//				menu.setItemName("testAddN2");
		//				menu.setDescription("testDescNew");
		//				menu.setPrepTime(20000);
		//				menu.setPrice(new BigDecimal(20.0));

		List<Menu> menuList = new ArrayList<Menu>();
		resource.addItemInMenuMaster(menu,1);
		/*
		Caterer caterer = new Caterer();
		//caterer.setCatererId(4);
		caterer.setCatererName("mdp");
		resource.addCaterer(caterer);*/

		/*	Menu menu1=new Menu();
		menu1.setItemId(8);
		List<Menu> menuList= new ArrayList<Menu>();
		menuList.add(menu1);

		resource.addDailyMenu(2, new DateParam("2014-12-16"), menuList);
		List<Menu> menuList= new ArrayList<Menu>();
		menuList=resource.getMenuMaster();
		System.out.println(menuList);*/
	}







}
