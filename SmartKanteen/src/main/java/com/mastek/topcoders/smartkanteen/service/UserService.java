package com.mastek.topcoders.smartkanteen.service;

import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.Role;
import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;

public interface UserService
{
	User getUserById(int userId);

	Boolean authenicateUser(String loginId, String password);

	User createUser(User user) throws UserExistException, Exception;

	User updateUser(User user) throws Exception;

	Boolean deleteUser(User user) throws Exception;

	User changePassword(String loginId, String oldPassword, String newPassword) throws IncorrectPasswordException,
			Exception;

	User updateUserRole(User user, List<Role> roleList) throws Exception;
}
