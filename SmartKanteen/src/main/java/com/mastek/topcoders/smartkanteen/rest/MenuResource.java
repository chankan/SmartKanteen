package com.mastek.topcoders.smartkanteen.rest;

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




	public static void main(String[] args) {
		MenuResource resource= new MenuResource();

		Menu menu = new Menu();
		menu.setItemId(3);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		//Date date =new DateParam("20141218").getDate();
		resource.addDailyMenu(menuList,10, new DateParam("20141218"));
	}







}
