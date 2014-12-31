package com.mastek.topcoders.smartkanteen.rest;

import java.util.Date;

import javax.ws.rs.core.Response;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;



public interface IUserResource {
	

	User getUserById(int userId);

	User createUser(User user);

	User updateUser(String loginId,User user);

	Response deleteUser(User user) throws Exception;

	User changePassword(String loginId, String oldPassword, String newPassword)  ;

	Response loginUser(User user);

	Response logoutUser(User user);
}
