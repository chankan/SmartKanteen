package com.mastek.topcoders.smartkanteen.service;

import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.OrderDetails;
import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.dao.OrderDAO;
import com.mastek.topcoders.smartkanteen.dao.UserDAO;

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
	public OrderMaster updateOrderStatus(int orderId,String orderStatus)
	{
		OrderDAO dao= new OrderDAO();
		return dao.updateOrderStatus(orderId, orderStatus);
    }

	@Override
	public List<OrderMaster> getOrdersByCaterer(Integer catererId)
	{
      OrderDAO dao= new OrderDAO();
       return  dao.getOrderByCaterer(catererId);
	}


	@Override
	public List<OrderMaster> getOrdersByUser(Integer userId)
	{
		OrderDAO dao = new OrderDAO();
		return dao.getOrderByUser(userId);
	}

}
