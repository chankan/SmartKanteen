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
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.rest.exception.GenericException;
import com.mastek.topcoders.smartkanteen.rest.exception.NotAuthorizedException;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

@Path("/service")
public class MenuResource implements IMenuResource 
{

	private final MenuServiceImpl menuService;
	private String role = "Admin";

	public MenuResource() 
	{
		menuService = new MenuServiceImpl();
	}

	@Path("/caterer")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<Caterer> getCaterers()
	{
		List<Caterer> caterer;
		caterer = menuService.getCaterers();
		return caterer;
	}

	@Path("/caterer")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Caterer addCaterer(Caterer caterer)
	{
		Caterer addedcaterer = null;
		if (role.equalsIgnoreCase("superAdmin"))
		{
			try
			{
				addedcaterer = menuService.addCaterer(caterer);
				return addedcaterer;
			} 
			catch (Exception e)
			{
				throw new GenericException("Something Went Wrong!!");
			}
		}
		else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to Add Caterer!!!");
		}
	}

	@Path("/caterer/{catererId}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Caterer getCaterer(@PathParam("catererId") Integer catererId)
	{
		Caterer caterer = null;
		if (role.equalsIgnoreCase("superAdmin")
				|| role.equalsIgnoreCase("user"))
		{
			caterer = menuService.getCaterer(catererId);
		} else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to Access other Caterer's Data!!!");
		}
		return caterer;
	}

	@Path("/caterer/{catererId}/")
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Caterer updateCaterer(@PathParam("catererId") Integer catererId, // @FormParam("catererName")
			String catererName) 
	{
		Caterer caterer = new Caterer();
		if (role.equalsIgnoreCase("superAdmin"))
		{
			try
			{
				caterer = menuService.updateCaterer(catererId, catererName);
				if (!caterer.equals(null))
				{
					return caterer;
				} 
				else
				{
					throw new GenericException("Nothing to Update!");
				}
			} 
			catch (Exception e)
			{
				throw new GenericException("Something Went Wrong!!");
			}
		}
		else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to Add Caterer!!!");
		}
	}

	@Path("caterer/{catererId}")
	@DELETE
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML })
	@Override
	public Response deleteCaterer(@PathParam("catererId") Integer catererId)
	{
		if (role.equalsIgnoreCase("superAdmin"))
		{
			try
			{
				if (menuService.deleteCaterer(catererId))
				{
					return Response
							.status(200)
							.entity("Caterer with Id " + catererId
									+ " is deleted!!").build();
				} else
				{
					return Response.status(404)
							.entity("No Such Caterer Exist!!").build();
				}
			}
			catch (Exception e)
			{
				return Response.status(404).entity("No Such Caterer Exist!!")
						.build();
			}
		} 
		else 
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to Add Caterer!!!");
		}
	}

	@Path("/caterer/{catererId}/menu")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<Menu> getMenuMasterByCaterer(@PathParam("catererId") Integer catererId)
	{
		List<Menu> menu;
		menu = menuService.getMenuMasterByCaterer(catererId);
		return menu;
	}

	@Path("/caterer/{catererId}/menu/")
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Menu addUpdateItemInMenuMaster(Menu menuMaster,
			@PathParam("catererId") int catererId) 
	{
		if (!menuMaster.equals(null))
		{
			if (role.equalsIgnoreCase("admin"))
			{
				if (MenuValidation.validate(menuMaster)) 
				{
					if (menuMaster.getItemId() == null) 
					{
						Caterer caterer = new Caterer();
						caterer.setCatererId(catererId);
						try 
						{
							menuService
							.addItemInMenuMaster(menuMaster, caterer);
							return menuMaster;
						} 
						catch (Exception e)
						{
							throw new GenericException("Something Went Wrong!!");
						}
					}
					else 
					{
						try
						{
							menuService.updateItemInMenuMaster(menuMaster);
							return menuMaster;
						} 
						catch (Exception e)
						{
							throw new GenericException("Something Went Wrong!!");
						}
					}
				} 
				else
				{
					throw new GenericException(
							"Menu Constraints Not Followed!!");
				}
			}
			else
			{
				throw new NotAuthorizedException("You Don't Have Permission!!!");
			}
		} 
		else
		{
			throw new GenericException("Please Enter Required fields!");
		}
	}

	@Path("/menu/{itemId}")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response deleteItemFromMenuMaster(@PathParam("itemId") Integer itemId)
	{
		if (role.equalsIgnoreCase("Admin"))
		{
			try
			{
				if (menuService.deleteItemFromMenuMaster(itemId)) 
				{
					return Response.status(200)
							.entity("Menu with Id " + itemId + " is deleted!!")
							.build();
				}
				else 
				{
					return Response.status(402).entity("No Such Menu Exist!!")
							.build();
				}
			}
			catch (Exception e)
			{

				return Response.status(402).entity("No Such Menu Exist!!")
						.build();
			}
		} 
		else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to delete item!!!");
		}
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<Menu> getDailyMenu(@PathParam("date") DateParam menuDate,
			@PathParam("catererId") Integer catererId) 
	{
		List<Menu> dailymenu;
		Date date = menuDate.getDate();
		dailymenu = menuService.getDailyMenu(date, catererId);
		return dailymenu;
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public DailyMenu addDailyMenu(List<Menu> menu,
			@PathParam("catererId") Integer catererId,
			@PathParam("date") DateParam menuDate) 
	{
		DailyMenu dailyMenu = null;
		Date date = menuDate.getDate();
		if (role.equalsIgnoreCase("Admin")) 
		{
			try 
			{
				dailyMenu = menuService.addDailyMenu(catererId, date, menu);
				return dailyMenu;
			}
			catch (Exception e)
			{
				throw new GenericException("Something Went Wrong!!");
			}
		}
		else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to add daily Menu!!!");
		}
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@PUT
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_JSON })
	@Override
	public DailyMenu updateDailyMenu(List<Menu> menuList,
			@PathParam("catererId") Integer catererId,
			@PathParam("date") DateParam menuDate) 
	{
		DailyMenu dailyMenu = null;
		Date date = menuDate.getDate();
		if (role.equalsIgnoreCase("Admin"))
		{
			try 
			{
				dailyMenu = menuService.updateDailyMenu(catererId, date,
						menuList);
				return dailyMenu;
			} 
			catch (Exception e)
			{
				throw new GenericException("Something Went Wrong!!");
			}
		} 
		else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to update daily Menu!!!");
		}
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Response deleteDailyMenu(@PathParam("catererId") Integer catererId,
			@PathParam("date") DateParam menuDate)
	{

		Date date = menuDate.getDate();
		if (role.equalsIgnoreCase("Admin"))
		{
			try
			{
				if (menuService.deleteDailyMenu(catererId, date))
				{
					return Response.status(200)
							.entity("Daily Menu for 23 december is deleted!!")
							.build();
				} 
				else
				{
					return Response.status(200)
							.entity("No Menu for 20 december").build();
				}

			}
			catch (Exception e) 
			{
				throw new GenericException("Something Went Wrong!!");
			}
		}
		else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to delete daily Menu!!!");
		}
	}

	public static void main(String[] args) {
		MenuResource resource = new MenuResource();

		Menu menu = new Menu();
		menu.setItemId(3);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		// Date date =new DateParam("20141218").getDate();
		resource.addDailyMenu(menuList, 10, new DateParam("20141218"));
	}

}
