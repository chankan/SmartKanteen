package com.mastek.topcoders.smartkanteen.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mastek.topcoders.smartkanteen.bean.Role;
import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.bean.UserRoleMapping;
import com.mastek.topcoders.smartkanteen.bean.UserSession;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;
import com.mastek.topcoders.smartkanteen.dao.UserDAO;

public class UserServiceImpl implements UserService
{
	private static HashMap<String, UserSession> userSessionMap=new HashMap<String, UserSession>();
	private static Random rnd=new Random(System.currentTimeMillis());
	
	public User getUserById(int userId)
	{
		UserDAO dao = new UserDAO();
		User user = dao.getUserById(userId);
		return user;
	}

	public Boolean authenicateUser(String loginId, String password)
	{
		UserDAO dao = new UserDAO();
		return dao.authenticateUser(loginId, password);

	}

	public User createUser(User user) throws UserExistException, Exception
	{
		UserDAO dao = new UserDAO();

		user.setAccountCreationDate(new Date());

		Role role = new Role();
		role.setRoleId(3);

		UserRoleMapping userRoleMapping = new UserRoleMapping();
		userRoleMapping.setRole(role);
		userRoleMapping.setUser(user);

		UserDetails userDetails = new UserDetails();
		userDetails.setUser(user);

		return dao.createUser(user, userDetails, userRoleMapping);
	}

	public User updateUser(Integer userId, String loginId, String firstName, String lastName, String emailId,
			String gender, Date dateOfBirth, Long contactNo, Integer extensionNo, Integer employeeId) throws Exception
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

	/*	public User updateUser(User user) throws Exception
		{
			User userDb;
			UserDetails userDetails, userDetailsDb;

			if (user.getUserId() == null || user.getUserId() == 0)
			{
				System.out.println("User does not exist...");
				return null;
			}
			userDetails = user.getUserDetails();

			userDb = getUserById(user.getUserId());
			userDetailsDb = userDb.getUserDetails();

			if (user != null)
			{
				userDb.setUserId(user.getUserId());
				if (user.getLoginId() != null)
				{
					userDb.setLoginId(user.getLoginId());
				}

				if (user.getEmailId() != null)
				{
					userDb.setEmailId(user.getEmailId());
				}

				if (userDetails.getFirstName() != null)
				{
					userDetailsDb.setFirstName(userDetails.getFirstName());
				}

				if (userDetails.getLastName() != null)
				{
					userDetailsDb.setLastName(userDetails.getLastName());
				}

				if (userDetails.getGender() != null)
				{
					userDetailsDb.setGender(userDetails.getGender());
				}

				if (userDetails.getDateOfBirth() != null)
				{
					userDetailsDb.setDateOfBirth(userDetails.getDateOfBirth());
				}

				if (userDetails.getContactNo() != null && userDetails.getContactNo() != 0)
				{
					userDetailsDb.setContactNo(userDetails.getContactNo());
				}

				if (userDetails.getExtensionNo() != null && userDetails.getExtensionNo() != 0)
				{
					userDetailsDb.setExtensionNo(userDetails.getExtensionNo());
				}

				if (userDetails.getEmployeeId() != null && userDetails.getEmployeeId() != 0)
				{
					userDetailsDb.setEmployeeId(userDetails.getEmployeeId());
				}
				System.out.println(user.toString() + "\n" + userDetailsDb.toString());

				UserDAO dao = new UserDAO();

				return dao.updateUser(userDb, userDetailsDb);
			}
			return null;
		}
		*/

	public User updateUser(User user) throws Exception
	{
		User userDb;
		UserDetails userDetails, userDetailsDb;
		UserDAO dao = new UserDAO();

		if (user.getLoginId() == null)
		{
			System.out.println("Login Id does not exist...");
			return null;
		}
		userDetails = user.getUserDetails();

		userDb = dao.getUserByLoginId(user.getLoginId());
		userDetailsDb = userDb.getUserDetails();

		if (user != null)
		{

			if (user.getEmailId() != null)
			{
				userDb.setEmailId(user.getEmailId());
			}

			if (userDetails != null)
			{
				if (userDetails.getFirstName() != null)
				{
					userDetailsDb.setFirstName(userDetails.getFirstName());
				}

				if (userDetails.getLastName() != null)
				{
					userDetailsDb.setLastName(userDetails.getLastName());
				}

				if (userDetails.getGender() != null)
				{
					userDetailsDb.setGender(userDetails.getGender());
				}

				if (userDetails.getDateOfBirth() != null)
				{
					userDetailsDb.setDateOfBirth(userDetails.getDateOfBirth());
				}

				if (userDetails.getContactNo() != null && userDetails.getContactNo() != 0)
				{
					userDetailsDb.setContactNo(userDetails.getContactNo());
				}

				if (userDetails.getExtensionNo() != null && userDetails.getExtensionNo() != 0)
				{
					userDetailsDb.setExtensionNo(userDetails.getExtensionNo());
				}

				if (userDetails.getEmployeeId() != null && userDetails.getEmployeeId() != 0)
				{
					userDetailsDb.setEmployeeId(userDetails.getEmployeeId());
				}
			}
			System.out.println(user.toString() + "\n" + userDetailsDb.toString());

			return dao.updateUser(userDb, userDetailsDb);
		}
		return null;
	}

	public Boolean deleteUser(User user) throws Exception
	{
		UserDAO dao = new UserDAO();
		return dao.deleteUser(user);
	}

	public User updateUserRole(User user, List<Role> roleList) throws Exception
	{
		UserDAO dao = new UserDAO();
		return dao.updateUserRole(user, roleList);
	}

	public User changePassword(String loginId, String oldPassword, String newPassword)
			throws IncorrectPasswordException, Exception
	{
		UserDAO dao = new UserDAO();
		return dao.changePassword(loginId, oldPassword, newPassword);
	}
	
	public UserSession loginUser(User user) throws Exception {
		UserDAO dao = new UserDAO();
		User dbUser = dao.getUserByLoginId(user.getLoginId());
		UserSession userSession;
		if (dbUser != null) {
			String currPass = passwordEncryption(user.getPassword());
			if (currPass != null && dbUser.getPassword().equals(currPass)
					&& dbUser.getLoginId().equals(user.getLoginId())) {
				String sessionId = getRandomSession();
				if (userSessionMap.containsKey(sessionId)) {
					sessionId = getRandomSession();
					if (userSessionMap.containsKey(sessionId)) {
						throw new Exception("Session Id generation failed.");
					}
				}
				user.setPassword("");
				user.setUserId(dbUser.getUserId());
				userSession = new UserSession();
				userSession.setSessionId(sessionId);
				userSession.setUser(user);
				userSession.setRole(3);
				userSessionMap.put(sessionId, userSession);
			} else {
				throw new Exception("Failed to authenticate.");
			}
		} else {
			throw new Exception("Failed to login.");
		}
		return userSession;
	}

	private String passwordEncryption(String input) {

		String md5 = null;

		if (null == input || "".equalsIgnoreCase(input.trim()))
			return null;

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes(), 0, input.length());
			md5 = new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}

	private String getRandomSession() {
		String sessionId;
		sessionId = "" + System.currentTimeMillis();
		sessionId += rnd.nextLong();
		return sessionId;
	}
}