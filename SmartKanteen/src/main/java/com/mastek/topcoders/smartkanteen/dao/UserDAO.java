package com.mastek.topcoders.smartkanteen.dao;

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
		Query query = session.createQuery("FROM User WHERE loginId= :login_id");
		query.setParameter("login_id", loginId);
		List<User> userList = query.list();
		
		if (userList != null && userList.size() == 1)
		{
			user = userList.get(0);
			System.out.println(user);
		}
		
		DatabaseUtil.closeSession(session);
		return user;
	}

	public User getUserById(int userId)
	{
		Session session = DatabaseUtil.getSession();
		User user = (User) session.get(User.class, userId);
		DatabaseUtil.closeSession(session);
		return user;
	}

	public User updateUser(User user, UserDetails userDetails) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		System.out.println(user.getUserId());
		User userDB = getUserById(user.getUserId());

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

	public User authenticateUser(String loginId, String password)
	{
		User user = null;

		Session session = DatabaseUtil.getSession();
		Query query = session.createQuery("FROM User WHERE loginId= :loginId AND password= :pwd");
		query.setParameter("loginId", loginId);
		query.setParameter("pwd", password);
		List<User> userList = query.list();

		if (userList != null && userList.size() == 1)
		{
			user = userList.get(0);
		}
		
		DatabaseUtil.closeSession(session);
		return user;
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

	public User createUser(User user, UserDetails userDetails, UserRoleMapping userRoleMapping) throws UserExistException,Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx;
		if (user != null)
		{
			if (getUserByLoginId(user.getLoginId()) == null)
			{
				tx = session.beginTransaction();
				try
				{
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

	public User updateUserRole(int userId, int roleId) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		Role role;
		User user = getUserById(userId);

		if (user != null)
		{
			try
			{
				tx = session.beginTransaction();
				Set<UserRoleMapping> userRoleMappingSet = user.getUserRoleMappingSet();
    
				if (userRoleMappingSet !=null && userRoleMappingSet.size() > 0)
				{
					for (UserRoleMapping userRoleMapping : userRoleMappingSet)
					{
						role = new Role();
						role.setRoleId(roleId);
						userRoleMapping.setRole(role);
						session.save(userRoleMapping);
					}
					
					tx.commit();
				}
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

	public User changePassword(String loginId, String oldPassword, String newPassword) throws IncorrectPasswordException,Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		User user = getUserByLoginId(loginId);
		if (user != null)
		{
			if (user.getPassword().equals(oldPassword))
			{
				try
				{
					user.setPassword(newPassword);
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
