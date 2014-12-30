package com.mastek.topcoders.smartkanteen.service;

import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.OrderDetails;
import com.mastek.topcoders.smartkanteen.bean.OrderMaster;

public interface OrderService
{
	void createOrder(OrderMaster order, OrderDetails orderDetails);

	void cancelOrder();

	 OrderMaster updateOrderStatus(int orderId,String orderStatus);

	 List<OrderMaster> getOrdersByCaterer(Integer catererId);

		List<OrderMaster> getOrdersByUser(Integer userId);
}
