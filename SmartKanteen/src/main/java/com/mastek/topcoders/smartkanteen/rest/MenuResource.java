package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.ObjectNotFoundException;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.rest.exception.NotAuthorizedException;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;


@Path("/service")
public class MenuResource implements IMenuResource {

	private final MenuServiceImpl menuService;
	private String role="Admin";

	public MenuResource() {
		menuService = new MenuServiceImpl();
	}


	@Path("/caterer")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public 	List<Caterer> getCaterers() {
		List<Caterer> caterer;
		caterer= menuService.getCaterers();
		return caterer;
	}

	@Path("/caterer")
	@POST
	@Produces  ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override 
	public Response addCaterer(Caterer caterer) {
		// TODO Auto-generated method stub
		if(role.equalsIgnoreCase("superAdmin"))
		{
			menuService.addCaterer(caterer);
			return Response   
					.status(200)   
					.entity("New Caterer with id "+ caterer.getCatererId()+ " is deleted!!").build();
		}
		else
		{
			throw new NotAuthorizedException("You Don't Have Permission to Add Caterer!!!");
		}
	}

	@Path("/caterer/{catererId}")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public Caterer getCaterer(@PathParam("catererId")Integer catererId) {
		Caterer caterer =null;
		if(role.equalsIgnoreCase("superAdmin") || role.equalsIgnoreCase("user") )
		{
			caterer=menuService.getCaterer(catererId);
		}
		else
		{
			throw new NotAuthorizedException("You Don't Have Permission to Access other Caterer's Data!!!");
		}
		return caterer;
	}

	@Path("/caterer/{catererId}/")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateCaterer(@PathParam("catererId")Integer catererId, //@FormParam("catererName")
			String catererName){
		//create dummy date for catererName
		catererName="xyz";
		if(role.equalsIgnoreCase("superAdmin"))
		{

			if(menuService.updateCaterer(catererId, catererName))
			{
				return Response   
						.status(200)   
						.entity("Caterer with id "+ catererId+ " is updated!!").build();

			}
			else
			{
				return Response.status(404).entity("No such Caterer").build();
			}

		}
		else
		{
			throw new NotAuthorizedException("You Don't Have Permission to Add Caterer!!!");
		}

	}

	@Path("caterer/{catererId}")
	@DELETE
	@Produces({MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Response deleteCaterer(@PathParam("catererId")Integer catererId) {
		// TODO Auto-generated method stub
		if(role.equalsIgnoreCase("superAdmin"))
		{
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
							.status(404)   
							.entity("No Such Caterer Exist!!").build();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Response   
						.status(404)			   
						.entity("No Such Caterer Exist!!").build();
			}
		}
		else
		{
			throw new NotAuthorizedException("You Don't Have Permission to Add Caterer!!!");
		}
	}

	@Path("/caterer/{catererId}/menu")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public List<Menu> getMenuMasterByCaterer(@PathParam("catererId")Integer catererId) {
		List<Menu> menu;
		menu= menuService.getMenuMasterByCaterer(catererId);
		return menu;
	}

	@Path("/caterer/{catererId}/menu/")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response addUpdateItemInMenuMaster(Menu menuMaster,@PathParam("catererId")int catererId){
		if(role.equalsIgnoreCase("admin"))
		{

			if(MenuValidation.validate(menuMaster))
			{
				if(menuMaster.getItemId()==null)
				{
					Caterer caterer= new Caterer();
					caterer.setCatererId(catererId);
					menuService.addItemInMenuMaster(menuMaster,caterer);
					return Response.status(200).entity("Menu Added!!").build();

				}
				else	
				{
					//updating Menu for particular customer
					menuService.updateItemInMenuMaster(menuMaster);
					return Response.status(200).entity("Menu Updated!!").build();
				}
			}
			else
			{

				return Response.status(200).entity("Menu Constraints Not Followed!!").build();
			}
		}
		else	
		{
			throw new NotAuthorizedException("You Don't Have Permission!!!");
			//throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}


	}

	@Path("/menu/{itemId}")
	@DELETE
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Response deleteItemFromMenuMaster(@PathParam("itemId")Integer itemId) {
		// TODO Auto-generated method stub
		if(role.equalsIgnoreCase("Admin"))
		{
			try{
				if (menuService.deleteItemFromMenuMaster(itemId)) {
					return Response   
							.status(200)   
							.entity("Menu with Id "+itemId+" is deleted!!").build();

				}
				else{
					return Response   
							.status(402)   
							.entity("No Such Menu Exist!!").build();
				}

			}
			catch (Exception e) {
				// TODO: handle exception
				return Response   
						.status(402)   
						.entity("No Such Menu Exist!!").build();
			}
		}
		else
		{
			throw new NotAuthorizedException("You Don't Have Permission to delete item!!!");
		}


	}

	@Path("/caterer/{catererId}/menu/{date}")
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

	@Path("/caterer/{catererId}/menu/{date}")
	@POST
	@Consumes ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void addDailyMenu(List<Menu> menu, @PathParam("catererId")Integer catererId, @PathParam("date")DateParam menuDate) {
		System.out.println("iN add daily");
		Date date=menuDate.getDate();
		if(role.equalsIgnoreCase("Admin"))
		{
			menuService.addDailyMenu(catererId, date, menu);
		}
		else
		{
			throw new NotAuthorizedException("You Don't Have Permission to add daily Menu!!!");
		}
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@PUT
	@Produces ({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void updateDailyMenu(List<Menu> menuList,@PathParam("catererId")Integer catererId, @PathParam("date")DateParam menuDate) {
		
		Date date=menuDate.getDate();
		if(role.equalsIgnoreCase("Admin"))
		{
			menuService.updateDailyMenu(catererId, date, menuList);
		}
		else
		{
			throw new NotAuthorizedException("You Don't Have Permission to update daily Menu!!!");
		}
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@DELETE
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public Response deleteDailyMenu(@PathParam("catererId")Integer catererId, @PathParam("date")DateParam menuDate) {
		// TODO Auto-generated method stub
		Date date = menuDate.getDate();
		if(role.equalsIgnoreCase("Admin"))
		{
			if (menuService.deleteDailyMenu(catererId, date))
			{
				return Response   
						.status(200)   
						.entity("Daily Menu for 23 december is deleted!!").build();

			}
			else
			{
				return Response   
						.status(200)   
						.entity("No Menu for 20 december").build();
			}
		}
		else
		{
			throw new NotAuthorizedException("You Don't Have Permission to delete daily Menu!!!");
		}
	}


	/*	Not Needed AS OF NOW
	 * 
	 * // Fetching Master Menu List

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











	//appending Daily Menu
	@Path("/menu/caterer/append/{dailyMenuId}/")
	@POST
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void appendDailyMenu(Integer dailyMenuId, List<Menu> menuList) {
		// TODO Auto-generated method stub
		menuService.appendDailyMenuItems(dailyMenuId, menuList);

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

	@Path("/add")
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
		//		Menu menu= new Menu();
		//		menu.setItemName("");
		//		menu.setDescription("testDescNew");
		//		menu.setPrepTime(20000);
		//		menu.setPrice(new BigDecimal(20.0));
		//
		//		System.out.println(MenuValidation.validate(menu));

		//				Menu menu= new Menu();
		//				menu.setItemName("testAddN2");
		//				menu.setDescription("testDescNew");
		//				menu.setPrepTime(20000);
		//				menu.setPrice(new BigDecimal(20.0));
		//
		//		List<Menu> menuList = new ArrayList<Menu>();
		//		resource.addUpdateItemInMenuMaster(menu,10);
		//		/*
		//				Caterer caterer = new Caterer();
		//				caterer.setCatererId(14);
		//				caterer.setCatererName("mdp");
		//				resource.updateCaterer(14, "");
		//
		//		Menu menu1=new Menu();
		//		menu1.setItemId(8);
		//		List<Menu> menuList= new ArrayList<Menu>();
		//		menuList.add(menu1);

		//resource.addDailyMenu(10, new DateParam("2014-12-18"), menuList);
		//		List<Menu> menuList= new ArrayList<Menu>();
		//		menuList=resource.getMenuMaster();
		//		System.out.println(menuList);
		
		Menu menu = new Menu();
		menu.setItemId(3);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		//Date date =new DateParam("20141218").getDate();
		resource.addDailyMenu(menuList,10, new DateParam("20141218"));
	}







}
