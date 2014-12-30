package com.mastek.topcoders.smartkanteen.junit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;
import com.mastek.topcoders.smartkanteen.dao.UserDAO;
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
		user.setLoginId("Kiran");
		user.setPassword("123456");
		user.setEmailId("kiran.nayak@mastek.com");
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

		User fetchedUser = service.getUserById(4);
		Assert.assertEquals(userDB.getLoginId(), fetchedUser.getLoginId());
		Assert.assertEquals(userDB.getPassword(), fetchedUser.getPassword());
		Assert.assertEquals(userDB.getEmailId(), fetchedUser.getEmailId());
	}

	@Test
	public void testUpdateUser()
	{

		User user = new User();
		user.setLoginId("rahul");
		user.setEmailId("Rahul.Panchal@mastek.com");

		UserDetails userDetails = new UserDetails();
		userDetails.setExtensionNo(3535);
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

		//Session session = DatabaseUtil.getSession();
		UserDAO dao = new UserDAO();
		User userDb = dao.getUserByLoginId("rahul");

		Assert.assertEquals(userDetails.getExtensionNo(), userDb.getUserDetails().getExtensionNo());
		Assert.assertEquals(user.getEmailId(), userDb.getEmailId());
	}

	@Test
	public void testDeleteUser()
	{
		User user1 = new User();
		user1.setUserId(3);
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

		Assert.assertEquals(true, result1);
	}

	@Test
	public void testAuthenicateUser()
	{
		UserService service = new UserServiceImpl();
		Boolean result = service.authenicateUser("tarul", "123456");
		Assert.assertEquals(true, result);
	}

	@Test
	public void testGetUserById()
	{
		boolean result = false;
		UserService service = new UserServiceImpl();
		User user = service.getUserById(1);
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
			user = service.changePassword("rahul", "123456", "rahul123");
			System.out.println("----------------------------------------------\n" + user);
		}
		catch (IncorrectPasswordException e)
		{
			e.printStackTrace();
		}
		UserDAO dao = new UserDAO();
		String password = dao.passwordEncryption("rahul123");
		Assert.assertEquals(password, user.getPassword());
	}
}
