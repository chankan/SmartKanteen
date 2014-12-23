package com.mastek.topcoders.smartkanteen.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mastek.topcoders.smartkanteen.bean.OrderDetails;
import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;

public class OrderDAO
{
	public void createOrder(OrderMaster order, OrderDetails orderDetails)
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.save(order);
			session.save(orderDetails);
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();

		}
		DatabaseUtil.closeSession(session);
	}
}
