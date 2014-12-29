package com.mastek.topcoders.smartkanteen.common.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseUtil
{
	private static SessionFactory factory;

	public static SessionFactory getSessionFactory() throws HibernateException
	{
		if (factory == null)
		{
			try
			{
				factory = new Configuration().configure("/hibernate/hibernate.cfg.xml").buildSessionFactory();
			}
			catch (HibernateException e)
			{
				System.out.println("Failed to create Session Factory object");
				e.printStackTrace();
				throw e;
			}
		}

		return factory;
	}

	public static Session getSession() throws HibernateException
	{
		Session session = null;
		try
		{
			factory = getSessionFactory();
			session = factory.openSession();
		}
		catch (HibernateException e)
		{
			System.out.println("Failed to open Session");
			e.printStackTrace();
			throw e;
		}

		return session;
	}

	public static void closeSession(Session session) throws HibernateException
	{
		if (session != null)
		{
			try
			{
				session.close();
			}
			catch (HibernateException e)
			{
				System.out.println("Failed to close Session");
				e.printStackTrace();
				throw e;
			}
		}
	}

}
