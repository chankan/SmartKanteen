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

import org.hibernate.ObjectNotFoundException;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.Tag;
import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.bean.UserRoleMapping;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;
import com.mastek.topcoders.smartkanteen.rest.exception.GenericException;
import com.mastek.topcoders.smartkanteen.rest.exception.NotAuthorizedException;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;


public class UserResource implements IUserResource 
{

	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User authenicateUser(String loginId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createUser(User user, UserDetails userDetails,
			UserRoleMapping userRoleMapping) throws UserExistException,
			Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(Integer userId, String loginId, String firstName,
			String lastName, String emailId, String gender, Date dateOfBirth,
			Integer contactNo, Integer extensionNo, Integer employeeId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User changePassword(String loginId, String oldPassword,
			String newPassword) throws IncorrectPasswordException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUserRole(int userId, int roleId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

		
}
