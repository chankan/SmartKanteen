package com.mastek.topcoders.smartkanteen.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mastek.topcoders.smartkanteen.bean.MenuRating;
import com.mastek.topcoders.smartkanteen.bean.MenuUserRatingMapping;
import com.mastek.topcoders.smartkanteen.bean.MenuRating;
import com.mastek.topcoders.smartkanteen.common.util.Constants;
import com.mastek.topcoders.smartkanteen.common.util.OrderStatus;
import com.mastek.topcoders.smartkanteen.rest.exception.GenericException;
import com.mastek.topcoders.smartkanteen.service.MenuRatingService;
import com.mastek.topcoders.smartkanteen.service.MenuRatingServiceImpl;
import com.mastek.topcoders.smartkanteen.service.MenuUserRatingMappingService;
import com.mastek.topcoders.smartkanteen.service.MenuUserRatingMappingServiceImpl;
import com.mastek.topcoders.smartkanteen.service.MenuRatingService;
import com.mastek.topcoders.smartkanteen.service.MenuRatingServiceImpl;
import com.mastek.topcoders.smartkanteen.service.UserService;
import com.mastek.topcoders.smartkanteen.service.UserServiceImpl;

@Path("/rating")
public class RatingResource implements IRatingResource
{
	private final MenuRatingService menuRatingService;
	private final MenuUserRatingMappingService menuUsrRatgMapngService;
	private final UserService userService;

	public RatingResource()
	{
		menuRatingService = new MenuRatingServiceImpl();
		menuUsrRatgMapngService = new MenuUserRatingMappingServiceImpl();
		userService = new UserServiceImpl();
	}

	
	
	@Path("/user/{userId}Id/item/{itemId}/rating")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public MenuUserRatingMapping addMenuRating(@HeaderParam("userSession") String userSession, MenuUserRatingMapping murMapping) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_USER))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			MenuUserRatingMapping menuUserRatingMapping = menuUsrRatgMapngService.addMenuRating(murMapping);
			return menuUserRatingMapping;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}	
	
	
	@Path("/user/{userId}Id/item/{itemId}/newRating")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public MenuUserRatingMapping updateMenuUserRating(@HeaderParam("userSession") String userSession,
			@PathParam("itemId") Integer itemId, @PathParam("userId") Integer userId, Integer newRating) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			MenuUserRatingMapping menuUserRatingMapping = menuUsrRatgMapngService.updateMenuUserRating(itemId, userId, newRating);
			return menuUserRatingMapping;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}
	

	
	@Path("/item/{itemId}/avgRating")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public MenuRating getMenuAverageRating(@HeaderParam("userSession") String userSession,
			@PathParam("itemId") Integer itemId)
			throws Exception {
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN)
				|| !userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			MenuRating menuRating = menuRatingService.getMenuAverageRating(itemId);
			return menuRating;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}
	
	
	@Path("/averageMenuRatings")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public List<MenuRating> getMenuRatings(@HeaderParam("userSession") String userSession) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			List<MenuRating> menuRatingList = menuRatingService.getMenuRatings();
			return menuRatingList;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}

	

}
