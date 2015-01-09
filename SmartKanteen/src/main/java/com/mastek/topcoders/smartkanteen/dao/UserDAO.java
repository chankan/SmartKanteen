package com.mastek.topcoders.smartkanteen.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.mastek.topcoders.smartkanteen.bean.Role;
import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserCatererMapping;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.bean.UserRoleMapping;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.PasswordEncryption;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;

public class UserDAO
{
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

	public List<User> getUsers() throws Exception
	{
		Session session = DatabaseUtil.getSession();
		List<User> userList = null;

		try
		{
			Criteria criteria = session.createCriteria(User.class);
			userList = (List<User>) criteria.list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return userList;
	}

	public Boolean authenticateUser(String loginId, String password) throws Exception
	{
		boolean result = false;
		Session session = DatabaseUtil.getSession();
		try
		{
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("loginId", loginId));
			criteria.add(Restrictions.eq("password", PasswordEncryption.encryptPassword(password)));

			/*Query query = session.createQuery("FROM User WHERE loginId= :loginId AND password= :pwd");
			query.setParameter("loginId", loginId);
			query.setParameter("pwd", passwordEncryption(password));
			List<User> userList = query.list();*/

			List<User> userList = criteria.list();

			if (userList != null && userList.size() == 1)
			{
				result = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			DatabaseUtil.closeSession(session);
			throw e;
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
					encryptedPassword = PasswordEncryption.encryptPassword(user.getPassword());
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

	public User changePassword(String loginId, String oldPassword, String newPassword)
			throws IncorrectPasswordException, Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		User user = getUserByLoginId(loginId);
		if (user != null)
		{

			if (user.getPassword().equals(PasswordEncryption.encryptPassword(oldPassword)))
			{
				try
				{
					user.setPassword(PasswordEncryption.encryptPassword(newPassword));
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

	public User getUserByLoginId(String loginId)
	{
		User user = null;
		Session session = DatabaseUtil.getSession();
		try
		{
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("loginId", loginId));

			/*Query query = session.createQuery("FROM User WHERE loginId= :login_id");
			query.setParameter("login_id", loginId);
			List<User> userList = query.list();*/

			List<User> userList = criteria.list();

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

	public List<Role> getRoles(User user)
	{
		Session session = DatabaseUtil.getSession();
		Query query = session.createQuery("FROM  UserRoleMapping WHERE USER_ID=  :user_Id ");

		query.setParameter("user_Id", user.getUserId());
		List<UserRoleMapping> userRoleList = query.list();
		DatabaseUtil.closeSession(session);
		Role role;
		List<Role> roleList = new ArrayList<Role>();
		for (UserRoleMapping userRoleMapping : userRoleList)
		{
			role = userRoleMapping.getRole();
			roleList.add(role);
		}

		return roleList;
	}

	public UserCatererMapping getUserCatererMapping(Integer userId) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Criteria criteria = session.createCriteria(UserCatererMapping.class);
		criteria.add(Restrictions.eq("userId", userId));

		List<UserCatererMapping> userCatererMappingList = criteria.list();
		DatabaseUtil.closeSession(session);

		if (userCatererMappingList != null && userCatererMappingList.size() == 1)
		{
			return userCatererMappingList.get(0);
		}

		return null;
	}
}
