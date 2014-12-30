package com.mastek.topcoders.smartkanteen.service;

import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.OrderDetails;
import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.dao.OrderDAO;

public class OrderServiceImpl implements OrderService
{

	@Override
	public void createOrder(OrderMaster order, OrderDetails orderDetails)
	{
		OrderDAO dao = new OrderDAO();
		dao.createOrder(order, orderDetails);
	}

	@Override
	public void cancelOrder()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOrderStatus()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void getOrdersByCaterer(Integer catererId)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<OrderMaster> getOrdersByUser(Integer userId)
	{
		OrderDAO dao = new OrderDAO();
		return dao.getOrderByUser(userId);
	}

}
