package com.mastek.topcoders.smartkanteen.junit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;
import com.mastek.topcoders.smartkanteen.service.UserService;
import com.mastek.topcoders.smartkanteen.service.UserServiceImpl;

public class UserTestCase
{

	@Test
	public void testCreateUser()
	{
		User userDB = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "24-12-2014 0:0:0";
		Date date = null;
		try
		{
			date = sdf.parse(dateInString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		User user = new User();
		user.setLoginId("Sahil");
		user.setPassword("123456");
		user.setEmailId("Sahil.yadav@mastek.com");
		user.setAccountCreationDate(date);

		UserServiceImpl service = new UserServiceImpl();
		try
		{
			userDB = service.createUser(user);
		}
		catch (UserExistException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		User fetchedUser = (User) service.getUserById(22);
		Assert.assertEquals(userDB.getLoginId(), fetchedUser.getLoginId());
		Assert.assertEquals(userDB.getPassword(), fetchedUser.getPassword());
		Assert.assertEquals(userDB.getEmailId(), fetchedUser.getEmailId());
		Assert.assertEquals(userDB.getAccountCreationDate(), fetchedUser.getAccountCreationDate());
	}

	@Test
	public void testUpdateUser()
	{

		User user = new User();
		user.setUserId(1);

		UserDetails userDetails = new UserDetails();
		userDetails.setFirstName("Rohit");
		userDetails.setLastName("Sharma");
		userDetails.setGender("M");
		user.setUserDetails(userDetails);
		UserService service = new UserServiceImpl();
		try
		{
			/*User user1 = service.updateUser(user.getUserId(), user.getLoginId(), userDetails.getFirstName(),
					userDetails.getLastName(), user.getEmailId(), userDetails.getGender(),
					userDetails.getDateOfBirth(), userDetails.getContactNo(), userDetails.getExtensionNo(),
					userDetails.getEmployeeId());*/
			User updatedUser = service.updateUser(user);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Session session = DatabaseUtil.getSession();
		User userDb = (User) session.get(User.class, user.getUserId());

		Assert.assertEquals(userDetails.getFirstName(), userDb.getUserDetails().getFirstName());
		Assert.assertEquals(userDetails.getLastName(), userDb.getUserDetails().getLastName());
		Assert.assertEquals(userDetails.getGender(), userDb.getUserDetails().getGender());
		DatabaseUtil.closeSession(session);
	}

	@Test
	public void testDeleteUser()
	{
		boolean result2 = false;
		User user1 = new User();
		user1.setUserId(23);
		UserService service = new UserServiceImpl();
		boolean result1 = false;
		try
		{
			result1 = service.deleteUser(user1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Session session = DatabaseUtil.getSession();
		User user2 = (User) session.get(User.class, 17);

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
		Boolean result = service.authenicateUser("Kiran12741", "123456");
		Assert.assertEquals(true, result);
	}

	@Test
	public void testGetUserById()
	{
		boolean result = false;
		UserService service = new UserServiceImpl();
		User user = service.getUserById(12);
		if (user != null)
		{
			result = true;
		}
		Assert.assertEquals(true, result);
	}

	@Test
	public void testChangePassword() throws Exception
	{
		UserService service = new UserServiceImpl();
		User user = null;
		try
		{
			user = service.changePassword("Kiran12741", "123456", "Kiran@123");
		}
		catch (IncorrectPasswordException e)
		{
			e.printStackTrace();
		}
		Assert.assertEquals("Kiran@123", user.getPassword());
	}
}
