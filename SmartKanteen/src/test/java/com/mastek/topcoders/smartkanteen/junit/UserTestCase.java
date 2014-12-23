package com.mastek.topcoders.smartkanteen.junit;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.service.UserService;
import com.mastek.topcoders.smartkanteen.service.UserServiceImpl;

public class UserTestCase
{

	@Test
	public void testRegisterUser()
	{

		User user1 = new User();
		user1.setFirstName("Jinal");
		user1.setLastName("Bhagadia");
		user1.setLoginId("Jinal12781");
		user1.setPassword("Jinal123");
		user1.setEmailId("Jinal.bhagadia@mastek.com");

		UserService service = new UserServiceImpl();
		boolean result = service.registerUser(user1);
		if (result == false)
		{
			System.out.println("User already exist...");
		}
		Session session = DatabaseUtil.getSession();
		User user2 = (User) session.get(User.class, 10);

		if (user2 != null)
		{
			Assert.assertEquals(user1.getFirstName(), user2.getFirstName());
			Assert.assertEquals(user1.getLastName(), user2.getLastName());
			Assert.assertEquals(user1.getLoginId(), user2.getLoginId());
			Assert.assertEquals(user1.getPassword(), user2.getPassword());
			Assert.assertEquals(user1.getEmailId(), user2.getEmailId());
			System.out.println("Enter the if else condition...");
		}
		DatabaseUtil.closeSession(session);
	}

	@Test
	public void testUpdateUser()
	{
		User user1 = new User();
		user1.setUserId(8);
		user1.setPassword("Dipesh123");
		user1.setEmailId("dipesh.patel@mastek.com");
		UserService service = new UserServiceImpl();

		boolean result = service.updateUser(user1.getUserId(), user1.getLoginId(), user1.getFirstName(),
				user1.getLastName(), user1.getPassword(), user1.getEmailId());
		if (result)
		{
			System.out.println("User is updated successfully...");
		}

		Session session = DatabaseUtil.getSession();
		User fetchedUser = (User) session.get(User.class, 8);
		Assert.assertEquals(user1.getPassword(), fetchedUser.getPassword());

		DatabaseUtil.closeSession(session);
	}

	@Test
	public void testDeleteUser()
	{
		boolean result2 = false;
		User user1 = new User();
		user1.setUserId(9);
		UserService service = new UserServiceImpl();
		boolean result1 = service.deleteUser(user1);

		Session session = DatabaseUtil.getSession();
		User user2 = (User) session.get(User.class, 9);

		if (user2 == null)
		{
			result2 = true;
		}
		Assert.assertEquals(result1, result2);
	}

	@Test
	public void testAuthenicateUser()
	{
		UserService service = new UserServiceImpl();
		boolean result = service.authenicateUser("Jekin12740", "jekin123");
		Assert.assertEquals(true, result);

	}

	@Test
	public void testGetUserById()
	{
		boolean result = false;
		UserService service = new UserServiceImpl();
		User user = service.getUserById(5);
		if (user != null)
		{
			result = true;
		}

		Assert.assertEquals(true, result);
	}

}
