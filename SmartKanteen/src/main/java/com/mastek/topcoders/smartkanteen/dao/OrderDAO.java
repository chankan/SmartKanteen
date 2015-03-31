package com.mastek.topcoders.smartkanteen.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.common.util.OrderStatus;

public class OrderDAO
{
	public OrderMaster createOrder(OrderMaster order) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.save(order);
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);

			throw e;
		}

		DatabaseUtil.closeSession(session);
		return order;
	}

	public OrderMaster updateOrderStatus(int orderId, String orderStatus, String remarks) throws Exception
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
				order.setRemark(remarks);

				session.update(order);
				tx.commit();
			}
			catch (Exception e)
			{
				tx.rollback();
				e.printStackTrace();
				DatabaseUtil.closeSession(session);
				throw e;
			}
		}

		DatabaseUtil.closeSession(session);
		return order;
	}
	
	public OrderMaster cancelOrder(Integer orderId, Integer userId, String remarks) throws ObjectNotFoundException, Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		OrderMaster order = getOrderById(orderId);
		User user = getUserById(userId);

		if (order != null && user!=null)
		{
			try
			{
				tx = session.beginTransaction();
				order.setRemark(remarks);
				order.setStatus(OrderStatus.CANCELLED.getOrderStatus());
				order.setUserCancellingTheOrder(user);
				
				System.out.println("In CANCEL ORDER DAO" + order);
				System.out.println("Order cancelled is :"+order.getOrderId()+" , Cancelled by User : "+user.getEmailId());
				session.update(order);

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
			throw new ObjectNotFoundException(order, "Order or User not found");
		}

		DatabaseUtil.closeSession(session);
		return order;
	}

	public OrderMaster getOrderById(Integer orderId)
	{
		Session session = DatabaseUtil.getSession();
		OrderMaster order = (OrderMaster) session.get(OrderMaster.class, orderId);
		DatabaseUtil.closeSession(session);
		return order;
	}
	
	public User getUserById(Integer userId)
	{
		Session session = DatabaseUtil.getSession();
		User user = (User) session.get(User.class, userId);
		DatabaseUtil.closeSession(session);
		return user;
	}

	public List<OrderMaster> getOrderByCaterer(int catererId) throws Exception
	{
		List<OrderMaster> orderMasterList = null;
		Session session = DatabaseUtil.getSession();

		try
		{
			Criteria criteria = session.createCriteria(OrderMaster.class);
			criteria.add(Restrictions.eq("caterer_id", catererId));

			orderMasterList = criteria.list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return orderMasterList;
	}

	public List<OrderMaster> getOrderByUser(Integer userId) throws Exception
	{
		List<OrderMaster> orderMasterList = null;
		Session session = DatabaseUtil.getSession();

		try
		{
			Criteria criteria = session.createCriteria(OrderMaster.class);
			criteria.add(Restrictions.eq("user_id", userId));

			orderMasterList = criteria.list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return orderMasterList;
	}

	public List<OrderMaster> getOrdersByUserByCaterer(Integer userId, Integer catererId) throws Exception
	{
		List<OrderMaster> orderMasterList = null;
		Session session = DatabaseUtil.getSession();

		try
		{
			Criteria criteria = session.createCriteria(OrderMaster.class);
			criteria.add(Restrictions.eq("user_id", userId));
			criteria.add(Restrictions.eq("caterer_Id", catererId));

			orderMasterList = criteria.list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return orderMasterList;
	}

	public List<OrderMaster> getOrders() throws Exception
	{
		List<OrderMaster> orderMasterList = null;
		Session session = DatabaseUtil.getSession();

		try
		{
			Criteria criteria = session.createCriteria(OrderMaster.class);
			orderMasterList = criteria.list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return orderMasterList;
	}
}
