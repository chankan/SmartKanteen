package com.mastek.topcoders.smartkanteen.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mastek.topcoders.smartkanteen.bean.Role;
import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.bean.UserRoleMapping;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;

public class UserDAO
{
	public User getUserByLoginId(String loginId)
	{
		User user = null;
		Session session = DatabaseUtil.getSession();
		try
		{
			Query query = session.createQuery("FROM User WHERE loginId= :login_id");
			query.setParameter("login_id", loginId);
			List<User> userList = query.list();

			if (userList != null && userList.size() == 1)
			{
				user = userList.get(0);
				System.out.println(user);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		DatabaseUtil.closeSession(session);
		return user;
	}

	public User getUserById(int userId)
	{
		Session session = DatabaseUtil.getSession();
		User user = null;
		try
		{
			user = (User) session.get(User.class, userId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		DatabaseUtil.closeSession(session);
		return user;
	}

	private String passwordEncryption(String input)
	{

		String md5 = null;

		if (null == input)
			return null;

		try
		{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes(), 0, input.length());
			md5 = new BigInteger(1, digest.digest()).toString(16);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return md5;
	}

	public User updateUser(User user, UserDetails userDetails) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;

	
		User userDB = getUserByLoginId(user.getLoginId());

		if (userDB != null)
		{
			try
			{
				tx = session.beginTransaction();
				session.update(userDetails);
				session.update(user);
				tx.commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				tx.rollback();
				DatabaseUtil.closeSession(session);
				throw e;
			}
		}
		DatabaseUtil.closeSession(session);
		return user;
	}

	public Boolean authenticateUser(String loginId, String password)
	{
		boolean result = false;
		Session session = DatabaseUtil.getSession();
		try
		{
			Query query = session.createQuery("FROM User WHERE loginId= :loginId AND password= :pwd");
			query.setParameter("loginId", loginId);
			query.setParameter("pwd", passwordEncryption(password));
			List<User> userList = query.list();

			if (userList != null && userList.size() == 1)
			{
				result = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public Boolean deleteUser(User user) throws Exception
	{
		boolean result = false;
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		User userDB = getUserById(user.getUserId());

		if (userDB != null)
		{
			try
			{
				tx = session.beginTransaction();
				UserDetails userDetails = userDB.getUserDetails();
				Set<UserRoleMapping> userRoleMappingSetDB = userDB.getUserRoleMappingSet();

				for (UserRoleMapping userRoleMapping : userRoleMappingSetDB)
				{
					session.delete(userRoleMapping);
				}

				session.delete(userDetails);
				session.delete(userDB);
				tx.commit();
				result = true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				tx.rollback();
				result = false;
				DatabaseUtil.closeSession(session);
				throw e;
			}
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public User createUser(User user, UserDetails userDetails, UserRoleMapping userRoleMapping)
			throws UserExistException, Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx;
		String encryptedPassword = null;
		if (user != null)
		{
			if (getUserByLoginId(user.getLoginId()) == null)
			{
				tx = session.beginTransaction();
				try
				{
					encryptedPassword = passwordEncryption(user.getPassword());
					user.setPassword(encryptedPassword);
					session.save(user);
					session.save(userDetails);
					session.save(userRoleMapping);
					tx.commit();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					tx.rollback();
					DatabaseUtil.closeSession(session);
					throw e;
				}
			}
			else
			{
				throw new UserExistException("Login id entered already exist...");
			}
		}
		DatabaseUtil.closeSession(session);
		return user;
	}

	public Boolean deleteUserRole(User user)
	{
		boolean result;
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();

			Set<UserRoleMapping> userRoleMappingSet = user.getUserRoleMappingSet();

			String userRoleIds = "";

			for (UserRoleMapping userRoleMapping : userRoleMappingSet)
			{
				userRoleIds = userRoleIds + ", " + userRoleMapping.getUserRoleMappingId();
			}
			if (userRoleIds.length() > 1)
			{
				userRoleIds = userRoleIds.substring(1);
			}

			Query query = session.createSQLQuery("DELETE FROM 	User_Role_Mapping WHERE user_role_mapping_id IN ( "
					+ userRoleIds + ")");
			query.executeUpdate();
			tx.commit();
			result = true;
		}
		catch (Exception e)
		{
			result = false;
			tx.rollback();
			e.printStackTrace();
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public User updateUserRole(User user, List<Role> roleList) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;

		User userDb = getUserById(user.getUserId());

		if (user != null)
		{
			try
			{
				deleteUserRole(userDb);

				tx = session.beginTransaction();

				if (roleList != null && roleList.size() > 0)
				{
					for (Role role : roleList)
					{
						UserRoleMapping userRoleMapping = new UserRoleMapping();
						userRoleMapping.setUser(userDb);
						userRoleMapping.setRole(role);
						session.save(userRoleMapping);
					}
				}
				session.flush();
				tx.commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				DatabaseUtil.closeSession(session);
				throw e;
			}
			DatabaseUtil.closeSession(session);
			return user;
		}
		return null;
	}

	public User changePassword(String loginId, String oldPassword, String newPassword)
			throws IncorrectPasswordException, Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		User user = getUserByLoginId(loginId);
		if (user != null)
		{

			if (user.getPassword().equals(passwordEncryption(oldPassword)))
			{
				try
				{
					user.setPassword(passwordEncryption(newPassword));
					tx = session.beginTransaction();
					session.update(user);
					tx.commit();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					tx.rollback();
					DatabaseUtil.closeSession(session);
					throw e;
				}
			}
			else
			{
				throw new IncorrectPasswordException("Invalid Password...");
			}
		}
		DatabaseUtil.closeSession(session);
		return user;
	}

}
