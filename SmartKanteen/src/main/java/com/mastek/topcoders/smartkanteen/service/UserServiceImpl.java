package com.mastek.topcoders.smartkanteen.service;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.dao.UserDAO;



public class UserServiceImpl implements UserService
{
	public User getUserById(int userId)
	{
		UserDAO dao = new UserDAO();
		User user  = dao.getUserById(userId);
		return user;
	}

	public Boolean registerUser(User user)
	{
		UserDAO dao = new UserDAO();
		boolean result = dao.registerUser(user);
		return result;
	}

	public Boolean authenicateUser(String loginId, String password)
	{
		UserDAO dao = new UserDAO();
		boolean result = dao.authenticateUser(loginId, password);
		return result;

	}

	public Boolean updateUser(Integer userId, String loginId, String firstName, String lastName, String password,
			String emailId)
	{

	   boolean result1=false;
		User user;

		if (userId == null || userId == 0)
		{
			System.out.println("User does not exist...");
			return false;
		}
		    user = getUserById(userId);

		if (user!=null)
		{
			user.setUserId(userId);
			if (loginId != null)
			{
				user.setLoginId(loginId);
			}
			if (firstName != null)
			{
				user.setFirstName(firstName);
			}
			if (lastName != null)
			{
				user.setLastName(lastName);
			}
			if (password != null)
			{
				user.setPassword(password);
			}
			if (emailId != null)
			{
				user.setEmailId(emailId);
			}
			UserDAO dao = new UserDAO();

			boolean result2 = dao.updateUser(user);
			if (result2 == true)
			{
				result1 = true;
			}
		}
		return result1;

	}

	public Boolean deleteUser(User user)
	{
		UserDAO dao = new UserDAO();
		boolean result = dao.deleteUser(user);
		return result;
	}

}