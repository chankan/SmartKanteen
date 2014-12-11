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

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;


@Path("/kanteen")
public class MenuResource implements IMenuResource {

	/*
	 * (non-Javadoc)
	 * @see com.mastek.topcoders.smartkanteen.rest.IMenuResource#getMenuMaster()
	 * 
	 * Fetching Master Menu List
	 */
	@Path("/menu")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public List<Menu> getMenuMaster() {
		// TODO Auto-generated method stub
		List<Menu> menu;
		MenuService menuService= new MenuServiceImpl();
		menu= menuService.getMenuMaster();
		return menu;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mastek.topcoders.smartkanteen.rest.IMenuResource#getDailyMenu(com.mastek.topcoders.smartkanteen.rest.DateParam, java.lang.Integer)
	 * Fetchin DailyMenu
	 */
	@Path("/menu/caterer/{catererId}/date/{date}")
	@GET
	@Produces  ({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public List<Menu> getDailyMenu(@PathParam("date")DateParam menuDate,
			@PathParam("catererId")Integer catererId) {
		// TODO Auto-generated method stub
		System.out.println("in dailymenu");
		MenuService menuService = new MenuServiceImpl();
		List<Menu> dailymenu;
		Date date=menuDate.getDate();
		dailymenu = menuService.getDailyMenu(date, catererId);
		return dailymenu;
	}



	/*
	 * (non-Javadoc)
	 * @see com.mastek.topcoders.smartkanteen.rest.IMenuResource#getMenuMasterByCaterer(java.lang.Integer)
	 * Ftetching Menu by CatererId
	 */
	@Path("/menu/caterer/{catererId}")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public List<Menu> getMenuMasterByCaterer(@PathParam("catererId")Integer catererId) {
		// TODO Auto-generated method stub
		List<Menu> menu;
		MenuService menuService= new MenuServiceImpl();
		menu= menuService.getMenuMasterByCaterer(catererId);
		return menu;
	}

	/* (non-Javadoc)
	 * @see com.mastek.topcoders.smartkanteen.rest.IMenuResource#getCaterers()
	 *
	 *Fetching All caterers
	 */

	@Path("/caterer")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public 	List<Caterer> getCaterers() {
		// TODO Auto-generated method stub
		List<Caterer> caterer;
		MenuService menuService= new MenuServiceImpl();
		//GenericEntity<List<Menu>> genMenu;
		//ArrayList<Menu> m= new ArrayList<Menu>();
		caterer= menuService.getCaterers();
		//genMenu= new GenericEntity<List<Menu>>(menu){};
		//return Response.ok(genMenu).build();

		return caterer;
	}


	/* (non-Javadoc)
	 * @see com.mastek.topcoders.smartkanteen.rest.IMenuResource#getCaterer(java.lang.Integer)
	 *
	 *Fetching Caterer using CatererID
	 */

	@Path("/caterer/{catererId}")
	@GET
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public Caterer getCaterer(@PathParam("catererId")Integer catererId) {
		// TODO Auto-generated method stub
		MenuService menuService= new MenuServiceImpl();
		Caterer caterer;
		caterer=menuService.getCaterer(catererId);
		return caterer;
	}

	/* (non-Javadoc)
	 * @see com.mastek.topcoders.smartkanteen.rest.IMenuResource#addItemInMenuMaster(com.mastek.topcoders.smartkanteen.bean.Menu)
	 *
	 *Adding Menu using MenuMaster object
	 */

	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Integer addItemInMenuMaster(Menu menuMaster) {
		// TODO Auto-generated method stub
		MenuService menuService= new MenuServiceImpl();
		menuService.addItemInMenuMaster(menuMaster);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mastek.topcoders.smartkanteen.rest.IMenuResource#addItemMenuMaster(java.lang.String, java.lang.String, java.math.BigDecimal, java.lang.Integer, java.lang.Integer)
	 *
	 *Adding Menu using Form Fields
	 */ 

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
				MenuService menuService= new MenuServiceImpl();
				menuService.addItemMenuMaster(itemName, description, price, prepTime, category);
			}

		}
	}


	/* (non-Javadoc)
	 * @see com.mastek.topcoders.smartkanteen.rest.IMenuResource#updateItemInMenuMaster(java.lang.Integer, java.lang.String, java.lang.String, java.math.BigDecimal, java.lang.Integer)
	 *
	 *Updating Menu in Menu Master table
	 */

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
		MenuService menuService= new MenuServiceImpl();
		//itemId=1;
		itemName="test1";
		description="update test";
		price=new BigDecimal(20);
		prepTime= 10;

		menuService.updateItemInMenuMaster(itemId, itemName, description, price, prepTime);

	}

	/* (non-Javadoc)
	 * @see com.mastek.topcoders.smartkanteen.rest.IMenuResource#addCaterer(com.mastek.topcoders.smartkanteen.bean.Caterer)
	 *
	 *Adding Caterer using Caterer Object
	 */

	
	@POST
	@Produces  ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override 
	public Integer addCaterer(Caterer caterer) {
		// TODO Auto-generated method stub

		MenuService menuService = new MenuServiceImpl();
		menuService.addCaterer(caterer);
		return null;
	}

	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void addDailyMenu(Integer catererId, Date menuDate, List<Menu> menu) {
		// TODO Auto-generated method stub
		MenuService menuService=new MenuServiceImpl();
		menuService.addDailyMenu(catererId, menuDate, menu);
	}

	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void updateDailyMenu(Integer dailyMenuId, List<Menu> menuList) {
		// TODO Auto-generated method stub
		MenuService menuService=new MenuServiceImpl();
		menuService.updateDailyMenu(dailyMenuId, menuList);

	}
	
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
	public void appendDailyMenu(Integer dailyMenuId, Menu menu) {
		// TODO Auto-generated method stub
		MenuService menuService=new MenuServiceImpl();
		menuService.appendDailyMenu(dailyMenuId, menu);

	}
	@Produces ({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Override
public void appendDailyMenu(Integer dailyMenuId, List<Menu> menuList) {
		// TODO Auto-generated method stub
		MenuService menuService=new MenuServiceImpl();
		menuService.appendDailyMenu(dailyMenuId, menuList);

	}


	/*public static void main(String[] args) {
		MenuResource resource= new MenuResource();
		Menu menu= new Menu();
		menu.setItemName("testAdd");
		menu.setDescription("testDesc");
		menu.setPrepTime(10);
		menu.setPrice(new BigDecimal(20.0));
		
		resource.addItemInMenuMaster(menu);
	}*/

}
