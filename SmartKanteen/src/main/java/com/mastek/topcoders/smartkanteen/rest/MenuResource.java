package com.mastek.topcoders.smartkanteen.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.ObjectNotFoundException;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.Tag;
import com.mastek.topcoders.smartkanteen.rest.exception.GenericException;
import com.mastek.topcoders.smartkanteen.rest.exception.NotAuthorizedException;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;
import com.mastek.topcoders.smartkanteen.service.UserService;
import com.mastek.topcoders.smartkanteen.service.UserServiceImpl;

@Path("/service")
public class MenuResource implements IMenuResource 
{

	private final MenuServiceImpl menuService;
	private final UserService userService;
	private String role = "admin";

	public MenuResource() 
	{
		menuService = new MenuServiceImpl();
		userService = new UserServiceImpl();
	}

	@Path("/caterer")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<Caterer> getCaterers(@HeaderParam("userSession") String userSession) throws Exception
	{
		if(!userService.authenicateUser(userSession, UserService.ROLE_USER)){
			throw new GenericException("Not authorized");
		}
		List<Caterer> caterer;
		caterer = menuService.getCaterers();
		if(!caterer.isEmpty())
		{
			return caterer;
		}
		else
		{	
			
			throw new GenericException("No Data present");
		}

	}

	@Path("/caterer")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Caterer addCaterer(Caterer caterer)
	{
		role = "superadmin";
		if (role.equalsIgnoreCase("superAdmin"))
		{
			try
			{	Caterer addedcaterer = menuService.addCaterer(caterer);
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

		if (role.equalsIgnoreCase("superAdmin")
				|| role.equalsIgnoreCase("user"))
		{
			Caterer caterer = menuService.getCaterer(catererId);
			return caterer;
		} else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to Access other Caterer's Data!!!");
		}

	}

	@Path("/caterer/{catererId}/")
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Caterer updateCaterer(@PathParam("catererId") Integer catererId, 
			Caterer caterer) 
	{

		if (role.equalsIgnoreCase("superAdmin"))
		{
			try
			{
				Caterer updatedcaterer=menuService.updateCaterer(caterer);
				if (!updatedcaterer.equals(null))
				{
					return updatedcaterer;
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
		if(!menu.isEmpty())
		{
			return menu;
		}	
		else
		{	
			
			throw new GenericException("No Data present");
		}

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
		if(!dailymenu.isEmpty())
		{
			return dailymenu;
		}
		else
		{
			throw new GenericException("No data Present!");
		}
		
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

	@Path("/tag")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<Tag> getTags() {
		List<Tag> taglist=null;
		taglist=menuService.getTags();
		return taglist;
	}

	@Path("/tag")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Tag addTag(Tag tag) {
		Tag addedtag = null;
		if (role.equalsIgnoreCase("Admin"))
		{
			try
			{
				addedtag = menuService.addTag(tag);
				return addedtag;
			} 
			catch (Exception e)
			{
				throw new GenericException("Something Went Wrong!!");
			}
		}
		else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to AddTag!!!");
		}
	}

	@Path("/tag")
	@PUT
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Tag updateTag(Tag tag) {
		Tag updatedtag = null;
		if (role.equalsIgnoreCase("Admin"))
		{
			try
			{
				updatedtag = menuService.updateTag(tag);
				return updatedtag;
			} 
			catch (Exception e)
			{
				throw new GenericException("Something Went Wrong!!");
			}
		}
		else
		{
			throw new NotAuthorizedException(
					"You Don't Have Permission to AddTag!!!");
		} 
	}

	@Path("/tag")
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Response deleteTag(Tag tag) {
		if (role.equalsIgnoreCase("Admin"))
		{
			try
			{
				if(menuService.deleteTag(tag))
				{
					return Response.status(200)
							.entity("Tag "+tag.getTagName()+" is deleted")
							.build();
				}
				else
				{
					throw new GenericException("Something went wrong!!");
				}
			}
			catch(ObjectNotFoundException hib )
			{
				throw new GenericException("This Tag is not Present!");
			}
			catch (Exception e) {
				throw new GenericException("Something went wrong!!");
			}

		}
		else
		{
			throw new NotAuthorizedException("You Don't Have Permission to delete a tag");
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
