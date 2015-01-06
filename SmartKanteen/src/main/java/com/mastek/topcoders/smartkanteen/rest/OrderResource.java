package com.mastek.topcoders.smartkanteen.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.common.util.Constants;
import com.mastek.topcoders.smartkanteen.common.util.OrderStatus;
import com.mastek.topcoders.smartkanteen.rest.exception.GenericException;
import com.mastek.topcoders.smartkanteen.service.OrderService;
import com.mastek.topcoders.smartkanteen.service.OrderServiceImpl;
import com.mastek.topcoders.smartkanteen.service.UserService;
import com.mastek.topcoders.smartkanteen.service.UserServiceImpl;

@Path("/service")
public class OrderResource implements IOrderResource
{
	private final OrderService orderService;
	private final UserService userService;

	public OrderResource()
	{
		orderService = new OrderServiceImpl();
		userService = new UserServiceImpl();
	}

	@Path("/order/user/{userId}/caterer/{catererId}")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public OrderMaster createOrder(@HeaderParam("userSession") String userSession, OrderMaster order) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_USER))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			OrderMaster orderMaster = orderService.createOrder(order);
			return orderMaster;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}

	@Path("/order/caterer/{catererId}/orderId/{orderId}")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public OrderMaster updateOrderStatus(@HeaderParam("userSession") String userSession,
			@PathParam("orderId") Integer orderId, OrderStatus orderStatus, String remarks) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			OrderMaster orderMaster = orderService.updateOrderStatus(orderId, orderStatus, remarks);
			return orderMaster;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}

	@Path("/order/user/{userId}/orderId/{orderId}")
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public OrderMaster cancelOrder(@HeaderParam("userSession") String userSession,
			@PathParam("orderId") Integer orderId, String remarks) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN)
				|| !userService.authenicateUser(userSession, UserService.ROLE_USER))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			OrderMaster orderMaster = orderService.cancelOrder(orderId, remarks);
			return orderMaster;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}

	@Path("/order/caterer/{catererId}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<OrderMaster> getOrdersByCaterer(@HeaderParam("userSession") String userSession,
			@PathParam("catererId") Integer catererId) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_ADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			List<OrderMaster> orderList = orderService.getOrdersByCaterer(catererId);
			return orderList;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}

	@Path("/order/user/{userId}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<OrderMaster> getOrdersByUser(@HeaderParam("userSession") String userSession,
			@PathParam("userId") Integer userId) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_USER))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			List<OrderMaster> orderList = orderService.getOrdersByUser(userId);
			return orderList;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}

	@Path("/order/user/{userId}/caterer/{catererId}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Override
	public List<OrderMaster> getOrdersByUserByCaterer(@HeaderParam("userSession") String userSession,
			@PathParam("userId") Integer userId, @PathParam("catererId") Integer catererId) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_USER))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			List<OrderMaster> orderList = orderService.getOrdersByUserByCaterer(userId, catererId);
			return orderList;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}

	@Path("/order")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public List<OrderMaster> getOrders(@HeaderParam("userSession") String userSession) throws Exception
	{
		if (!userService.authenicateUser(userSession, UserService.ROLE_SUPERADMIN))
		{
			throw new GenericException(Constants.NOT_AUTHORIZED_MSG);
		}

		try
		{
			List<OrderMaster> orderList = orderService.getOrders();
			return orderList;
		}
		catch (Exception e)
		{
			throw new GenericException(Constants.INTERNAL_ERROR_MSG);
		}
	}

}
