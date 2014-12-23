package com.mastek.topcoders.smartkanteen.service;

public interface OrderService
{
	void createOrder();

	void cancelOrder();

	void updateOrderStatus();

	void getOrdersByCaterer(Integer catererId);

	void getOrdersByUser(Integer userId);
}
