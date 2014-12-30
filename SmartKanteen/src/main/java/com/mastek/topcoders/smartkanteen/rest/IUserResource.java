package com.mastek.topcoders.smartkanteen.rest;

import java.util.Date;

import javax.ws.rs.core.Response;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;



public interface IUserResource {
	

	User getUserById(int userId);

	

	User createUser(User user);

	User updateUser(User user);

	Response deleteUser(User user) throws Exception;

	Response login(String loginId, String password);
	
	User changePassword(String loginId, String oldPassword, String newPassword)  throws  IncorrectPasswordException,Exception;

	User updateUserRole(int userId, int roleId) throws Exception;



	Response loginUser(User user);
}
