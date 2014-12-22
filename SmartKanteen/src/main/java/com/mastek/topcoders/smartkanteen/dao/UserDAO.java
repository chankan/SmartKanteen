package com.mastek.topcoders.smartkanteen.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;



public class UserDAO
{

	public Boolean getUserByLoginId(String loginId)
	{
		boolean result = false;
		Session session = DatabaseUtil.getSession();
		Query query = session.createQuery("FROM User WHERE loginId= :login_id");
		query.setParameter("login_id", loginId);
		List<User> userList = query.list();

		if (userList.size() <= 0)
		{
			result = true;
			System.out.println("ENTER THE NULL CONDITION... ");
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public User getUserById(int userId)
	{
		Session session = DatabaseUtil.getSession();
		User user = (User) session.get(User.class, userId);
		DatabaseUtil.closeSession(session);
		return user;
	}

	public Boolean registerUser(User user)
	{
		boolean result = false;
		Session session = DatabaseUtil.getSession();
		Transaction tx;
		if (user != null)
		{
			if (getUserByLoginId(user.getLoginId()))
			{
				tx = session.beginTransaction();
				try
				{

					session.save(user);
					tx.commit();

				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
					tx.rollback();
				}
				result = true;
			}
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public boolean updateUser(User user)
	{
		boolean result1 = false;
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		System.out.println(user.getUserId());
		User user1 = getUserById(user.getUserId());

		if (user!=null)
		{
			try
			{
				tx = session.beginTransaction();
				session.update(user);
				tx.commit();
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				tx.rollback();
				result1 = false;
			}
			result1 = true;
		}
		DatabaseUtil.closeSession(session);
		return result1;
	}

	public Boolean authenticateUser(String loginId, String password)
	{
		boolean result = false;
		Session session = DatabaseUtil.getSession();
		Query query = session.createQuery("FROM User WHERE loginId= :loginId AND password= :pwd");
		query.setParameter("loginId", loginId);
		query.setParameter("pwd", password);
		List<User> userList = query.list();

		if (userList.size() > 0)
		{
			result = true;
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public Boolean deleteUser(User user)
	{
		boolean result = false;
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		if (getUserById(user.getUserId())!=null)
		{
			try
			{

				tx = session.beginTransaction();
				session.delete(user);
				tx.commit();

			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				tx.rollback();
				result = false;
			}
			result = true;
		}
		DatabaseUtil.closeSession(session);
		return result;
	}
}
