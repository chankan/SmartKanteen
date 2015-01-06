package com.mastek.topcoders.smartkanteen.rest;

import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.common.util.OrderStatus;

public interface IOrderResource
{
	OrderMaster createOrder(String userSession, OrderMaster order) throws Exception;

	OrderMaster updateOrderStatus(String userSession, Integer orderId, OrderStatus orderStatus, String remarks)
			throws Exception;

	OrderMaster cancelOrder(String userSession, Integer orderId, String remarks) throws Exception;

	List<OrderMaster> getOrdersByCaterer(String userSession, Integer catererId) throws Exception;

	List<OrderMaster> getOrdersByUser(String userSession, Integer userId) throws Exception;

	List<OrderMaster> getOrdersByUserByCaterer(String userSession, Integer userId, Integer catererId) throws Exception;

	List<OrderMaster> getOrders(String userSession) throws Exception;
}
