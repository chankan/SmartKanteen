package com.mastek.topcoders.smartkanteen.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;
import com.mastek.topcoders.smartkanteen.rest.exception.GenericException;
import com.mastek.topcoders.smartkanteen.service.UserServiceImpl;

@Path("/user")
public class UserResource implements IUserResource {
	private final UserServiceImpl userService;

	public UserResource() {

		userService = new UserServiceImpl();
	}

	@Override
	public User getUserById(int userId) {

		User user = userService.getUserById(userId);
		return user;
	}

	@Path("/")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public User createUser(User user) {
		try {
			return userService.createUser(user);
		} catch (UserExistException uxe) {
			throw new GenericException("User Already Exist");
		} catch (Exception e) {
			throw new GenericException("Something went Wrong");
		}

	}

	@Path("/{loginId}")
	@PUT
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public User updateUser(@PathParam("loginId")String loginId,User user) {
		try {
			User updateduser = userService.updateUser(user);
			if (updateduser != null) {
				return updateduser;
			} else {
				throw new GenericException("User Not Exist");
			}
		} catch (Exception e) {
			throw new GenericException("Something Went Wrong!!");
		}
	}

	@Path("/")
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Response deleteUser(User user) {

		try {
			if (userService.deleteUser(user)) {
				return Response.status(200).entity("User Deleted!!").build();
			} else {
				return Response.status(400).entity("Something Went wrong!!")
						.build();
			}
		} catch (Exception e) {
			throw new GenericException("Something Went Wrong!");
		}
	}

	@Path("/{loginId}/changepass")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public User changePassword(@PathParam("loginId") String loginId,
			@FormParam("oldPassword") String oldPassword, @FormParam("newPassword") String newPassword) {
		try 
		{
			return userService
					.changePassword(loginId, oldPassword, newPassword);
		} catch (IncorrectPasswordException e)
		{
			System.out.println("Incorrect password");
			throw new GenericException("Incorrect Password!");
		} catch (Exception e) 
		{
			throw new GenericException("Something Went Wrong");
		}
	}

	

	@Path("/login")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Response loginUser(User user) {
		if (userService.authenicateUser(user.getLoginId(), user.getPassword())) {
			System.out.println("Login Successful");
			return Response.status(200).entity("Login successful").build();

		} else {
			System.out.println("Login Failed");
			throw new GenericException("Login attempt failed. Try again");
		}
	}

	@Path("/logout")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public Response logoutUser(User user) {
		return null;
	}
	
	public static void main(String[] args) {
		UserResource userResource = new UserResource();
		User user = new User();
		user.setLoginId("vaibhav13099");
		user.setPassword("1234");
		user.setEmailId("vaibhavk123@gmail.com");
		
		// userResource.createUser(user);
//		userResource.login(user.getLoginId(), user.getPassword());
//		userResource.changePassword(user.getLoginId(), "xyz1","abc");
		userResource.updateUser(user.getLoginId(),user);
	}

}
