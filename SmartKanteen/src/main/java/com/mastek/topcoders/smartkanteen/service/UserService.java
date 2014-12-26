package com.mastek.topcoders.smartkanteen.service;

import java.util.Date;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.bean.UserRoleMapping;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;

public interface UserService
{
	User getUserById(int userId);

	User authenicateUser(String loginId, String password);

	User createUser(User user, UserDetails userDetails, UserRoleMapping userRoleMapping) throws UserExistException,Exception;

	User updateUser(Integer userId, String loginId, String firstName, String lastName, String emailId, String gender,
			Date dateOfBirth, Integer contactNo, Integer extensionNo, Integer employeeId) throws Exception;

	Boolean deleteUser(User user) throws Exception;

	User changePassword(String loginId, String oldPassword, String newPassword)  throws  IncorrectPasswordException,Exception;

	User updateUserRole(int userId, int roleId) throws Exception;
}
