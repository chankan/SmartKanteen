package com.mastek.topcoders.smartkanteen.dao;

import java.util.List;

import org.hibernate.Query;
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

	public OrderMaster getOrderById(int orderId)
	{
		Session session = DatabaseUtil.getSession();
		OrderMaster order = (OrderMaster) session.get(OrderMaster.class, orderId);
		DatabaseUtil.closeSession(session);
		return order;
	}

	public List<OrderMaster> getOrderByUser(int userId)
	{
		List<OrderMaster> orderMasterList = null;
		try
		{
			Session session = DatabaseUtil.getSession();
			Query query = session.createQuery("FROM OrderMaster WHERE user_id= :userId");
			query.setParameter("userId", userId);
			orderMasterList = query.list();
			DatabaseUtil.closeSession(session);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return orderMasterList;
	}
	
	public List<OrderMaster> getOrderByCaterer(int catererId)
	{
		List<OrderMaster> orderMasterList = null;
		try
		{
			Session session = DatabaseUtil.getSession();
			Query query = session.createQuery("FROM OrderMaster WHERE caterer_id = :caterer_id");
			query.setParameter("caterer_id", catererId);
			orderMasterList = query.list();
			DatabaseUtil.closeSession(session);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return orderMasterList;
	}

	
	
	public OrderMaster updateOrderStatus(int orderId, String orderStatus)
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;

		OrderMaster order = getOrderById(orderId);
		if (order != null)
		{
			try
			{
				tx = session.beginTransaction();
				order.setStatus(orderStatus);
				session.update(order);
				tx.commit();
			}
			catch (Exception e)
			{
				tx.rollback();
				e.printStackTrace();
			}
		}
		DatabaseUtil.closeSession(session);
		return order;
	}
}
