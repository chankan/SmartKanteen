package com.mastek.topcoders.smartkanteen.service;

import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.common.util.OrderStatus;
import com.mastek.topcoders.smartkanteen.dao.OrderDAO;

public class OrderServiceImpl implements OrderService
{
	@Override
	public OrderMaster createOrder(OrderMaster order) throws Exception
	{
		OrderDAO dao = new OrderDAO();
		return dao.createOrder(order);
	}

	@Override
	public OrderMaster updateOrderStatus(Integer orderId, OrderStatus orderStatus, String remarks) throws Exception
	{
		OrderDAO dao = new OrderDAO();
		return dao.updateOrderStatus(orderId, orderStatus.getOrderStatus(), remarks);
	}

	@Override
	public OrderMaster cancelOrder(Integer orderId,Integer userId,  String remarks) throws Exception
	{
		OrderDAO dao = new OrderDAO();
		return dao.cancelOrder(orderId,userId,remarks);
	}

	@Override
	public List<OrderMaster> getOrdersByCaterer(Integer catererId) throws Exception
	{
		OrderDAO dao = new OrderDAO();
		return dao.getOrderByCaterer(catererId);
	}

	@Override
	public List<OrderMaster> getOrdersByUser(Integer userId) throws Exception
	{
		OrderDAO dao = new OrderDAO();
		return dao.getOrderByUser(userId);
	}

	@Override
	public List<OrderMaster> getOrdersByUserByCaterer(Integer userId, Integer catererId) throws Exception
	{
		OrderDAO dao = new OrderDAO();
		return dao.getOrdersByUserByCaterer(userId, catererId);
	}

	@Override
	public List<OrderMaster> getOrders() throws Exception
	{
		OrderDAO dao = new OrderDAO();
		return dao.getOrders();
	}
	
	@Override
	public OrderMaster getOrdersById(Integer orderId) throws Exception
	{
		OrderDAO dao = new OrderDAO();
		return dao.getOrderById(orderId);
	}


}
