package com.mastek.topcoders.smartkanteen.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mastek.topcoders.smartkanteen.bean.Role;
import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserCatererMapping;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.bean.UserRoleMapping;
import com.mastek.topcoders.smartkanteen.bean.UserSession;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.PasswordEncryption;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;
import com.mastek.topcoders.smartkanteen.dao.UserDAO;

public class UserServiceImpl implements UserService
{
	private static HashMap<String, UserSession> userSessionMap = new HashMap<String, UserSession>();
	private static Random rnd = new Random(System.currentTimeMillis());
	private static final HashMap<String, Integer> roleNameIdMap = new HashMap<String, Integer>(3);
	static
	{
		roleNameIdMap.put(UserService.ROLE_SUPERADMIN, 1);
		roleNameIdMap.put(UserService.ROLE_ADMIN, 2);
		roleNameIdMap.put(UserService.ROLE_USER, 3);
	}

	public User getUserById(int userId)
	{
		UserDAO dao = new UserDAO();
		User user = dao.getUserById(userId);
		return user;
	}

	public List<User> getUsers() throws Exception
	{
		UserDAO dao = new UserDAO();
		return dao.getUsers();
	}

	public Boolean authenicateUser(String sessionId, String roleName) throws Exception
	{
		if (userSessionMap.containsKey(sessionId) && roleName != null)
		{
			UserSession userSession = userSessionMap.get(sessionId);
			roleName = roleName.trim().toLowerCase();
			if (roleNameIdMap.containsKey(roleName))
			{
				int roleId = roleNameIdMap.get(roleName);
				if (roleId == userSession.getRole())
				{
					return true;
				}
			}

		}
		return false;
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
			setUserDetails(user, userDetails, userId, loginId, firstName, lastName, emailId, gender, dateOfBirth,
					contactNo, extensionNo, employeeId);

			UserDAO dao = new UserDAO();

			return dao.updateUser(user, userDetails);
		}
		return null;
	}

	private void setUserDetails(User user, UserDetails userDetails, Integer userId, String loginId, String firstName,
			String lastName, String emailId, String gender, Date dateOfBirth, Long contactNo, Integer extensionNo,
			Integer employeeId)
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
				setUserDetails(userDb, userDetailsDb, userDb.getUserId(), userDb.getLoginId(),
						userDetails.getFirstName(), userDetails.getLastName(), user.getEmailId(),
						userDetails.getGender(), userDetails.getDateOfBirth(), userDetails.getContactNo(),
						userDetails.getExtensionNo(), userDetails.getEmployeeId());
			}
			System.out.println(user + "\n" + userDetailsDb);

			return dao.updateUser(userDb, userDetailsDb);
		}

		return null;
	}

	public Boolean deleteUser(User user) throws Exception
	{
		UserDAO dao = new UserDAO();
		return dao.deleteUser(user);
	}

	public User changePassword(String loginId, String oldPassword, String newPassword)
			throws IncorrectPasswordException, Exception
	{
		UserDAO dao = new UserDAO();
		return dao.changePassword(loginId, oldPassword, newPassword);
	}

	public void logoutUser(UserSession userSession)
	{
		if (userSessionMap.containsKey(userSession.getSessionId()))
		{
			userSessionMap.remove(userSession.getSessionId());
		}
	}

	public User updateUserRole(User user, List<Role> roleList) throws Exception
	{
		UserDAO dao = new UserDAO();
		return dao.updateUserRole(user, roleList);
	}

	public UserSession loginUser(User user) throws Exception
	{
		UserDAO dao = new UserDAO();
		User dbUser = dao.getUserByLoginId(user.getLoginId());
		UserSession userSession;
		System.out.println(dbUser);
		if (dbUser != null)
		{
			String currPass = PasswordEncryption.encryptPassword(user.getPassword());

			if (currPass != null && dbUser.getPassword().equals(currPass)
					&& dbUser.getLoginId().equals(user.getLoginId()))
			{
				String sessionId = getRandomSession();

				if (userSessionMap.containsKey(sessionId))
				{
					sessionId = getRandomSession();
					if (userSessionMap.containsKey(sessionId))
					{
						throw new Exception("Session Id generation failed.");
					}
				}

				user.setUserId(dbUser.getUserId());
				userSession = new UserSession();
				userSession.setSessionId(sessionId);
				userSession.setUser(user);
				
				if (dbUser.getUserRoleMappingSet() != null && dbUser.getUserRoleMappingSet().size() > 0)
				{
					UserRoleMapping rolMap = dbUser.getUserRoleMappingSet().iterator().next();
					userSession.setRole(rolMap.getRole().getRoleId());
				}
				else
				{
					userSession.setRole(3);
				}
				
				//Fetch User Caterer Mapping
				if(userSession.getRole() == roleNameIdMap.get(UserService.ROLE_ADMIN))
				{
					System.out.println(true);
					UserCatererMapping userCatererMapping = dao.getUserCatererMapping(userSession.getUser().getUserId());
					System.out.println(userCatererMapping);
					userSession.setUserCatererMapping(userCatererMapping);
				}
				
				System.out.println("Logged in user ============>> "+userSession);
				userSessionMap.put(sessionId, userSession);
			}
			else
			{
				throw new Exception("Failed to authenticate.");
			}
		}
		else
		{
			throw new Exception("Failed to login.");
		}
		return userSession;
	}

	private String getRandomSession()
	{
		String sessionId;
		sessionId = "" + System.currentTimeMillis();
		sessionId += rnd.nextLong();
		return sessionId;
	}
}