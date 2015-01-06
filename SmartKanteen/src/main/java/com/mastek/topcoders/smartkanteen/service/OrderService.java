package com.mastek.topcoders.smartkanteen.service;

import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.common.util.OrderStatus;

public interface OrderService
{
	OrderMaster createOrder(OrderMaster order) throws Exception;

	OrderMaster updateOrderStatus(Integer orderId, OrderStatus orderStatus, String remarks) throws Exception;

	OrderMaster cancelOrder(Integer orderId, String remarks) throws Exception;

	List<OrderMaster> getOrdersByCaterer(Integer catererId) throws Exception;

	List<OrderMaster> getOrdersByUser(Integer userId) throws Exception;
	
	List<OrderMaster> getOrdersByUserByCaterer(Integer userId, Integer catererId) throws Exception;
	
	List<OrderMaster> getOrders() throws Exception;
}
