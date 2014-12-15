package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;
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

	// Fetching DailyMenu

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

	//Adding Menu using MenuMaster object

	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Integer addItemInMenuMaster(Menu menuMaster) {
		menuService.addItemInMenuMaster(menuMaster);
		return null;
	}

	//Adding Menu using Form Fields

	

	//Updating Menu in Menu Master table

	@Path("/update")
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

	@Path("caterer/delete/{catererId}")
	@GET
	//@Produces({MediaType.TEXT_PLAIN,MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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

	
	
	/*@Path("/menu/caterer/add/")
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void addDailyMenu(Integer catererId, Date menuDate, List<Menu> menu) {
		menuService.addDailyMenu(catererId, menuDate, menu);
	}

	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void updateDailyMenu(Integer dailyMenuId, List<Menu> menuList) {
		menuService.updateDailyMenu(dailyMenuId, menuList);

	}

	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void appendDailyMenu(Integer dailyMenuId, Menu menu) {
		menuService.appendDailyMenu(dailyMenuId, menu);

	}
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void appendDailyMenu(Integer dailyMenuId, List<Menu> menuList) {
		menuService.appendDailyMenu(dailyMenuId, menuList);

	}
*/

	public static void main(String[] args) {
		MenuResource resource= new MenuResource();
		/*Menu menu= new Menu();
		menu.setItemName("testAdd");
		menu.setDescription("testDesc");
		menu.setPrepTime(10);
		menu.setPrice(new BigDecimal(20.0));

		resource.addItemInMenuMaster(menu);*/
		
		Caterer caterer = new Caterer();
		//caterer.setCatererId(4);
		caterer.setCatererName("mdp");
		resource.addCaterer(caterer);
	}


	
}
