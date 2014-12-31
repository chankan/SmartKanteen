package com.mastek.topcoders.smartkanteen.common.util;

public enum OrderStatus
{
	PENDING("PENDING"), CANCELLED("CANCELLED"), WRONG("WRONG"), NOT_DELIVERABLE("NOT DELIVERABLE");

	private String orderStatus;

	private OrderStatus(String orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus()
	{
		return orderStatus;
	}
}
