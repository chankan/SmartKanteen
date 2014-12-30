package com.mastek.topcoders.smartkanteen.bean;

// Generated Dec 30, 2014 11:55:11 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.Set;

/**
 * OrderMaster generated by hbm2java
 */
public class OrderMaster implements java.io.Serializable
{


	private Integer orderId;
	private User user;
	private Caterer caterer;
	private double totalCost;
	private Date orderDate;
	private Date orderCreatedDate;
	private String status;
	private String remark;
	private Set orderDetails;
	public Integer getOrderId()
	{
		return orderId;
	}
	public void setOrderId(Integer orderId)
	{
		this.orderId = orderId;
	}
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public Caterer getCaterer()
	{
		return caterer;
	}
	public void setCaterer(Caterer caterer)
	{
		this.caterer = caterer;
	}
	public double getTotalCost()
	{
		return totalCost;
	}
	public void setTotalCost(double totalCost)
	{
		this.totalCost = totalCost;
	}
	public Date getOrderDate()
	{
		return orderDate;
	}
	public void setOrderDate(Date orderDate)
	{
		this.orderDate = orderDate;
	}
	public Date getOrderCreatedDate()
	{
		return orderCreatedDate;
	}
	public void setOrderCreatedDate(Date orderCreatedDate)
	{
		this.orderCreatedDate = orderCreatedDate;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public Set getOrderDetails()
	{
		return orderDetails;
	}
	public void setOrderDetails(Set orderDetails)
	{
		this.orderDetails = orderDetails;
	}
	@Override
	public String toString()
	{
		return "OrderMaster [orderId=" + orderId + ", user=" + user + ", caterer=" + caterer + ", totalCost="
				+ totalCost + ", orderDate=" + orderDate + ", orderCreatedDate=" + orderCreatedDate + ", status="
				+ status + ", remark=" + remark + ", orderDetails=" + orderDetails + "]";
	}
	
}
