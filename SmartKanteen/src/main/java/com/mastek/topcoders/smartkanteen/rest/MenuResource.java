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
import com.mastek.topcoders.smartkanteen.common.util.Constants;
import com.mastek.topcoders.smartkanteen.rest.exception.GenericException;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;
import com.mastek.topcoders.smartkanteen.service.UserService;
import com.mastek.topcoders.smartkanteen.service.UserServiceImpl;

@Path("/service")
public class MenuResource implements IMenuResource
{
	private final MenuService menuService;
	private final UserService userService;

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
		if (!userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN)
				|| !userService.authenicateUser(userSession, UserService.ROLE_USER))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		List<Caterer> caterer;
		caterer = menuService.getCaterers();

		if (!caterer.isEmpty())
		{
			return caterer;
		}
		else
		{
			throw new GenericException(Constants.NO_DATA_PRESENT_MSG);
		}
	}

	@Path("/caterer")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Caterer addCaterer(@HeaderParam("userSession") String userSession, Caterer caterer) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			Caterer addedcaterer = menuService.addCaterer(caterer);
			return addedcaterer;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
		}
	}

	@Path("/caterer/{catererId}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Caterer getCaterer(@HeaderParam("userSession") String userSession, @PathParam("catererId") Integer catererId)
			throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN)
				|| !userService.authenicateUser(userSession, UserService.ROLE_USER))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			Caterer caterer = menuService.getCaterer(catererId);
			return caterer;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
		}
	}

	@Path("/caterer/{catererId}/")
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Caterer updateCaterer(@HeaderParam("userSession") String userSession,
			@PathParam("catererId") Integer catererId, Caterer caterer) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			if (catererId == -1 && caterer.getCatererId() == -1)
			{
				Caterer addedcaterer = menuService.addCaterer(caterer);
				
				if (!addedcaterer.equals(null))
				{
					return addedcaterer;
				}
				else
				{
					throw new GenericException(Constants.INTERNAL_ERROR_MSG);
				}
			}
			else if (catererId != -1 && caterer.getCatererId() != -1)
			{
				Caterer updatedcaterer = menuService.updateCaterer(caterer);
				
				if (!updatedcaterer.equals(null))
				{
					return updatedcaterer;
				}
				else
				{
					throw new GenericException(Constants.NOTHING_TO_UPDATE_MSG);
				}
			}
			else
			{
				throw new GenericException(Constants.INTERNAL_ERROR_MSG);
			}
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
		}
	}

	@Path("caterer/{catererId}")
	@DELETE
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response deleteCaterer(@HeaderParam("userSession") String userSession,
			@PathParam("catererId") Integer catererId) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			if (menuService.deleteCaterer(catererId))
			{
				return Response.status(200).entity("Caterer with Id " + catererId + " is deleted!!").build();
			}
			else
			{
				return Response.status(404).entity(Constants.CATERER_NOT_FOUND_MSG).build();
			}
		}
		catch (Exception e)
		{
			return Response.status(404).entity(Constants.CATERER_NOT_FOUND_MSG).build();
		}
	}

	@Path("/caterer/{catererId}/menu")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<Menu> getMenuMasterByCaterer(@HeaderParam("userSession") String userSession,
			@PathParam("catererId") Integer catererId) throws Exception
	{
		List<Menu> menu;
		menu = menuService.getMenuMasterByCaterer(catererId);
		if (!menu.isEmpty())
		{
			return menu;
		}
		else
		{
			throw new GenericException(Constants.NO_DATA_PRESENT_MSG);
		}
	}

	@Path("/caterer/{catererId}/menu/")
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Menu addUpdateItemInMenuMaster(@HeaderParam("userSession") String userSession, Menu menuMaster,
			@PathParam("catererId") Integer catererId) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		if (menuMaster != null)
		{
			if (MenuValidation.validate(menuMaster))
			{
				if (menuMaster.getItemId() == null)
				{
					Caterer caterer = new Caterer();
					caterer.setCatererId(catererId);
					try
					{
						menuService.addItemInMenuMaster(menuMaster, caterer);
						return menuMaster;
					}
					catch (Exception e)
					{
						throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
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
						throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
					}
				}
			}
			else
			{
				throw new GenericException(Constants.CONSTRAINT_VOILATION_MSG);
			}
		}
		else
		{
			throw new GenericException(Constants.ENTER_REQUIRED_FIELDS_MSG);
		}
	}

	@Path("/menu/{itemId}")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response deleteItemFromMenuMaster(@HeaderParam("userSession") String userSession,
			@PathParam("itemId") Integer itemId) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			if (menuService.deleteItemFromMenuMaster(itemId))
			{
				return Response.status(200).entity("Menu with Id " + itemId + " is deleted!!").build();
			}
			else
			{
				return Response.status(402).entity(Constants.MENU_NOT_FOUND_MSG).build();
			}
		}
		catch (Exception e)
		{
			return Response.status(402).entity(Constants.MENU_NOT_FOUND_MSG).build();
		}
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<Menu> getDailyMenu(@HeaderParam("userSession") String userSession,
			@PathParam("date") DateParam menuDate, @PathParam("catererId") Integer catererId) throws Exception
	{
		List<Menu> dailymenu;
		Date date = menuDate.getDate();
		dailymenu = menuService.getDailyMenu(date, catererId);
		if (!dailymenu.isEmpty())
		{
			return dailymenu;
		}
		else
		{
			throw new GenericException(Constants.NO_DATA_PRESENT_MSG);
		}
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public DailyMenu addDailyMenu(@HeaderParam("userSession") String userSession, List<Menu> menu,
			@PathParam("catererId") Integer catererId, @PathParam("date") DateParam menuDate) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		DailyMenu dailyMenu = null;
		Date date = menuDate.getDate();
		try
		{
			dailyMenu = menuService.addDailyMenu(catererId, date, menu);
			return dailyMenu;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
		}
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@PUT
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public DailyMenu updateDailyMenu(@HeaderParam("userSession") String userSession, List<Menu> menuList,
			@PathParam("catererId") Integer catererId, @PathParam("date") DateParam menuDate) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		DailyMenu dailyMenu = null;
		Date date = menuDate.getDate();
		try
		{
			dailyMenu = menuService.updateDailyMenu(catererId, date, menuList);
			return dailyMenu;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
		}
	}

	@Path("/caterer/{catererId}/menu/{date}")
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Response deleteDailyMenu(@HeaderParam("userSession") String userSession,
			@PathParam("catererId") Integer catererId, @PathParam("date") DateParam menuDate) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		Date date = menuDate.getDate();
		try
		{
			if (menuService.deleteDailyMenu(catererId, date))
			{
				return Response.status(200).entity("Daily Menu for 23 december is deleted!!").build();
			}
			else
			{
				return Response.status(200).entity("No Menu for 20 december").build();
			}
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
		}
	}

	@Path("/tag")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<Tag> getTags(@HeaderParam("userSession") String userSession) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN)
				|| !userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		List<Tag> taglist = null;
		taglist = menuService.getTags();
		return taglist;
	}

	@Path("/tag")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Tag addTag(@HeaderParam("userSession") String userSession, Tag tag) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN)
				|| !userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		Tag addedtag = null;
		try
		{
			addedtag = menuService.addTag(tag);
			return addedtag;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
		}
	}

	@Path("/tag")
	@PUT
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Tag updateTag(@HeaderParam("userSession") String userSession, Tag tag) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN)
				|| !userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		Tag updatedtag = null;
		try
		{
			updatedtag = menuService.updateTag(tag);
			return updatedtag;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
		}
	}

	@Path("/tag")
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Response deleteTag(@HeaderParam("userSession") String userSession, Tag tag) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN)
				|| !userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			if (menuService.deleteTag(tag))
			{
				return Response.status(200).entity("Tag " + tag.getTagName() + " is deleted").build();
			}
			else
			{
				throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
			}
		}
		catch (ObjectNotFoundException hib)
		{
			throw new GenericException(Constants.TAG_NOT_FOUND_MSG);
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.SOMETHING_WENT_WRONG_MSG);
		}
	}

	public static void main(String[] args) throws Exception
	{
		MenuResource resource = new MenuResource();

		Menu menu = new Menu();
		menu.setItemId(3);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		// Date date =new DateParam("20141218").getDate();
		resource.addDailyMenu("", menuList, 10, new DateParam("20141218"));
	}
}
