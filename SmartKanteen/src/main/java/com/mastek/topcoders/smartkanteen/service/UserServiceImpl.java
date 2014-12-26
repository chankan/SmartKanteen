package com.mastek.topcoders.smartkanteen.service;

import java.util.Date;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.bean.UserRoleMapping;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;
import com.mastek.topcoders.smartkanteen.dao.UserDAO;

public class UserServiceImpl implements UserService
{
	public User getUserById(int userId)
	{
		UserDAO dao = new UserDAO();
		User user = dao.getUserById(userId);
		return user;
	}

	public User authenicateUser(String loginId, String password)
	{
		UserDAO dao = new UserDAO();
		User user = dao.authenticateUser(loginId, password);
		return user;
	}

	public User createUser(User user, UserDetails userDetails, UserRoleMapping userRoleMapping) throws UserExistException,Exception
	{
		UserDAO dao = new UserDAO();
		return dao.createUser(user, userDetails, userRoleMapping);
	}

	public User updateUser(Integer userId, String loginId, String firstName, String lastName, String emailId,
			String gender, Date dateOfBirth, Integer contactNo, Integer extensionNo, Integer employeeId)
			throws Exception
	{
		User user;
		UserDetails userDetails;

		if (userId == null || userId == 0)
		{
			System.out.println("User does not exist...");
			return null;
		}

		user = getUserById(userId);
		userDetails = user.getUserDetails();

		if (user != null)
		{
			user.setUserId(userId);
			if (loginId != null)
			{
				user.setLoginId(loginId);
			}

			if (emailId != null)
			{
				user.setEmailId(emailId);
			}

			if (emailId != null)
			{
				user.setEmailId(emailId);
			}

			if (emailId != null)
			{
				user.setEmailId(emailId);
			}

			if (firstName != null)
			{
				userDetails.setFirstName(firstName);
			}

			if (lastName != null)
			{
				userDetails.setLastName(lastName);
			}

			if (gender != null)
			{
				userDetails.setGender(gender);
			}

			if (lastName != null)
			{
				userDetails.setLastName(lastName);
			}

			if (dateOfBirth != null)
			{
				userDetails.setDateOfBirth(dateOfBirth);
			}

			if (contactNo != null && contactNo != 0)
			{
				userDetails.setContactNo(contactNo);
			}

			if (extensionNo != null && extensionNo != 0)
			{
				userDetails.setExtensionNo(extensionNo);
			}

			if (employeeId != null && employeeId != 0)
			{
				userDetails.setEmployeeId(employeeId);
			}

			UserDAO dao = new UserDAO();

			return dao.updateUser(user, userDetails);
		}

		return null;
	}

	public Boolean deleteUser(User user) throws Exception
	{
		UserDAO dao = new UserDAO();
		return dao.deleteUser(user);
	}

	public User updateUserRole(int userId, int roleId) throws Exception
	{
		UserDAO dao = new UserDAO();
		return dao.updateUserRole(userId, roleId);
	}

	public User changePassword(String loginId, String oldPassword, String newPassword) throws  IncorrectPasswordException,Exception
	{
		UserDAO dao = new UserDAO();
		return dao.changePassword(loginId, oldPassword, newPassword);
	}
}