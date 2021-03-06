package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.ObjectNotFoundException;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.MenuTagsMapping;
import com.mastek.topcoders.smartkanteen.bean.OrderDetails;
import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.bean.Tag;
import com.mastek.topcoders.smartkanteen.bean.Role;
import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.bean.UserDetails;
import com.mastek.topcoders.smartkanteen.common.util.IncorrectPasswordException;
import com.mastek.topcoders.smartkanteen.common.util.OrderStatus;
import com.mastek.topcoders.smartkanteen.common.util.UserExistException;

public class Test
{
	public static void main(String[] args) throws Exception
	{
		/*testingMenuTable();

		testingCatererTable();

		testingDailyMenuTable();

		testingTagsTable();*/
		
		displayTags();
	}

	public static void testingMenuTable() throws ObjectNotFoundException, Exception
	{
		System.out.println("Initial DB");
		displayMenuItems();

		testAddMenu();
		System.out.println("After Adding Items");
		displayMenuItems();

		testAddItemInMenuMaster();
		System.out.println("After Adding Items with tags");
		displayMenuItems();

		testAddMenuWithCaterer();
		System.out.println("After Adding Menu Items With Caterer");
		displayMenuItems();

		testUpdateMenuItems();
		System.out.println("After Updating Items");
		displayMenuItems();

		testDeleteItems();
		System.out.println("After Deleting Items");
		displayMenuItems();

		System.out.println("Get Menu By Name");
		testGetMenuByName();
	}

	public static void displayMenuItems()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		System.out.println(service.getMenuMaster());
	}

	public static void testAddMenu() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menuMaster = new Menu();
		menuMaster.setItemId(1);
		menuMaster.setItemName("Medu Wada");
		menuMaster.setDescription("South Indian");
		menuMaster.setPrepTime(5);
		menuMaster.setPrice(new BigDecimal(40));
		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setMenuTagsMappingId(1);
		//menuTagsMapping.setMenu(menuMaster);
		menuTagsMapping.setTags("3,4,13");
		menuMaster.setMenuTagsMapping(menuTagsMapping);
		service.addItemInMenuMaster(menuMaster);

		Menu menuMaster1 = new Menu();
		menuMaster1.setItemId(2);
		menuMaster1.setItemName("Chole Bhature");
		menuMaster1.setDescription("Punjabi");
		menuMaster1.setPrepTime(15);
		menuMaster1.setPrice(new BigDecimal(40));
		MenuTagsMapping menuTagsMapping1 = new MenuTagsMapping();
		menuTagsMapping1.setMenuTagsMappingId(2);
		menuTagsMapping1.setTags("1,7");
		menuMaster1.setMenuTagsMapping(menuTagsMapping1);
		service.addItemInMenuMaster(menuMaster1);

		Menu menuMaster2 = new Menu();
		menuMaster2.setItemId(3);
		menuMaster2.setItemName("Dum Briyani");
		menuMaster2.setDescription("Mughlai");
		menuMaster2.setPrepTime(25);
		menuMaster2.setPrice(new BigDecimal(40));
		service.addItemInMenuMaster(menuMaster2);

		Menu menuMaster3 = new Menu();
		menuMaster3.setItemId(4);
		menuMaster3.setItemName("Palak Paneer");
		menuMaster3.setDescription("North Indian");
		menuMaster3.setPrepTime(10);
		menuMaster3.setPrice(new BigDecimal(40));
		service.addItemInMenuMaster(menuMaster3);

		Menu menuMaster4 = new Menu();
		menuMaster4.setItemId(5);
		menuMaster4.setItemName("Thali");
		menuMaster4.setDescription("Malwani");
		menuMaster4.setPrepTime(20);
		menuMaster4.setPrice(new BigDecimal(40));
		service.addItemInMenuMaster(menuMaster4);
	}

	public static void testAddItemInMenuMaster() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menu = new Menu();
		menu.setDescription("Test Data");
		menu.setItemId(13);
		menu.setItemName("Test Item");
		menu.setPrepTime(15);
		menu.setPrice(new BigDecimal(50));

		String tags = "1,2,3";
		service.addItemInMenuMaster(menu, tags);
	}

	public static void testAddMenuWithCaterer() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menuMaster = new Menu();
		menuMaster.setItemId(11);
		menuMaster.setItemName("Fruit Custord");
		menuMaster.setDescription("Dessert");
		menuMaster.setPrepTime(15);
		menuMaster.setPrice(new BigDecimal(65));

		Menu menuMaster1 = new Menu();
		menuMaster1.setItemId(12);
		menuMaster1.setItemName("Falooda");
		menuMaster1.setDescription("Dessert");
		menuMaster1.setPrepTime(5);
		menuMaster1.setPrice(new BigDecimal(20));

		Caterer caterer = new Caterer();
		caterer.setCatererId(1);

		Caterer caterer1 = new Caterer();
		caterer1.setCatererId(2);

		service.addItemInMenuMaster(menuMaster, caterer);
		service.addItemInMenuMaster(menuMaster1, caterer1);
	}

	public static void testUpdateMenuItems() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		service.updateItemInMenuMaster(4, "Five Rice", "Chinese", null, null);

		Menu menu = new Menu();
		menu.setItemId(1);
		menu.setPrice(new BigDecimal(25));

		service.updateItemInMenuMaster(menu);
	}

	public static Boolean testDeleteItems() throws ObjectNotFoundException, Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();
		boolean result = true;
		result = service.deleteItemFromMenuMaster(1);
		return result;

		//service.deleteItemFromMenuMaster(3);
	}

	public static void testGetMenuByName()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		List<Menu> menuList = service.getMenuByName("Biryani");

		System.out.println(menuList);
	}

	public static void testingCatererTable() throws ObjectNotFoundException, Exception
	{
		System.out.println("Initial DB");
		displayCaterers();

		testAddCaterer();
		System.out.println("After Adding Caterer");
		displayCaterers();

		testUpdateCaterer();
		System.out.println("After Updating Caterer");
		displayCaterers();

		System.out.println("After Getting Caterer by ID");
		testGetCatererById();

		testDeleteCaterer();
		System.out.println("After Deleting Caterer");
		displayCaterers();

		System.out.println("Getting Menu List by Caterer");
		testGetMenuMasterByCaterer(1);
	}

	public static void displayCaterers()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		System.out.println(service.getCaterers());
	}

	public static void testAddCaterer() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Caterer caterer = new Caterer();
		caterer.setCatererId(3);
		caterer.setCatererName("Tadka Caterer");
		//caterer.setCatererDetails("Tadka");

		Caterer caterer1 = new Caterer();
		caterer1.setCatererId(4);
		caterer1.setCatererName("Nitya Caterer");
		//caterer1.setCatererDetails("Nitya");

		service.addCaterer(caterer);
		service.addCaterer(caterer1);
	}

	public static void testUpdateCaterer() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Caterer caterer = new Caterer();
		caterer.setCatererId(1);
		caterer.setCatererName("Royal Kings");
		//caterer.setCatererDetails("Royal Kings");

		//service.updateCaterer(caterer.getCatererId(), caterer.getCatererName(), caterer.getCatererDetails());
	}

	public static void testGetCatererById()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		System.out.println(service.getCaterer(1));
	}

	public static void testDeleteCaterer() throws ObjectNotFoundException, Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();
		service.deleteCaterer(4);
	}

	public static void testGetMenuMasterByCaterer(Integer catererId)
	{
		MenuServiceImpl service = new MenuServiceImpl();
		List<Menu> menuList = service.getMenuMasterByCaterer(catererId);
		System.out.println(menuList);
	}

	public static void testingDailyMenuTable() throws ObjectNotFoundException, Exception
	{
		System.out.println("Initial");
		displayDailyMenu();

		testAddDailyMenu();
		System.out.println("After Adding");
		displayDailyMenu();

		testUpdateDailyMenuItems();
		System.out.println("After Updating");
		displayDailyMenu();

		testAppendDailyMenuItems();
		System.out.println("After Appending");
		displayDailyMenu();

		testRemoveDailyMenuItems();
		System.out.println("After Removing");
		displayDailyMenu();

		System.out.println("Getting Todays Menu");
		testGetDailyMenu();

		testDeleteDailyMenu();
		System.out.println("After Deleting");
		displayDailyMenu();
	}

	public static void displayDailyMenu()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		List<Menu> menuList = service.getDailyMenu(null, null);
		System.out.println(menuList);
	}

	public static void testAddDailyMenu() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menu = new Menu();
		menu.setItemId(8);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		service.addDailyMenu(2, new Date(), menuList);
	}

	public static void testUpdateDailyMenuItems() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menu = new Menu();
		menu.setItemId(3);
		menu.setPrepTime(10);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		service.updateDailyMenuItems(1, menuList);
	}

	public static void testAppendDailyMenuItems() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();
		Menu menu = new Menu();
		menu.setItemId(5);
		service.appendDailyMenuItems(1, menu);

		Menu menu1 = new Menu();
		menu1.setItemId(4);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu1);
		service.appendDailyMenuItems(1, menuList);
	}

	public static Boolean testRemoveDailyMenuItems() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		List<Menu> menuList = new ArrayList<Menu>();
		Menu menu = new Menu();
		menu.setItemId(5);
		menuList.add(menu);

		boolean result = service.removeDailyMenuItems(1, menuList);
		return result;
	}

	public static void testGetDailyMenu()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "15-12-2014 0:0:0";
		Date date = null;
		try
		{
			date = sdf.parse(dateInString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		List<Menu> menuList = service.getDailyMenu(date, 2);

		System.out.println(menuList);
	}

	public static Boolean testDeleteDailyMenu() throws ObjectNotFoundException, Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();
		boolean result = service.deleteDailyMenu(5);
		return result;
	}

	public static void testingTagsTable() throws ObjectNotFoundException, Exception
	{
		System.out.println("Initial");
		displayTags();

		testAddTag();
		System.out.println("After Adding");
		displayTags();

		testUpdateTag();
		System.out.println("After Updating");
		displayTags();

		System.out.println("Get Tag by Id");
		testGetTagById();

		testDeleteTag();
		System.out.println("After Deleting");
		displayTags();
	}

	public static void displayTags()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		List<Tag> tagList = service.getTags();

		System.out.println(tagList);
	}

	public static void testAddTag() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Tag tag = new Tag();
		tag.setTagName("Shush");

		service.addTag(tag);
	}

	public static void testUpdateTag() throws Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Tag tag = new Tag();
		tag.setTagId(1);
		tag.setTagName("Lunch");

		service.updateTag(tag);
	}

	public static void testDeleteTag() throws ObjectNotFoundException, Exception
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Tag tag = new Tag();
		tag.setTagId(4);

		service.deleteTag(tag);
	}

	public static void testGetTagById()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		Tag tag = service.getTag(1);

		System.out.println(tag);
	}

	public static void testingOrderTable() throws Exception
	{
		testCreateOrder();
		
		testCancelOrder();
	}
	
	public static void testCreateOrder() throws Exception
	{
		OrderMaster order = new OrderMaster();
		OrderDetails orderDetails1 = new OrderDetails();
		Menu menu1 = new Menu();
		menu1.setItemId(1);
		menu1.setPrice(new BigDecimal(100));
		orderDetails1.setMenu(menu1);
		orderDetails1.setQuantity(1);
		
		OrderDetails orderDetails2 = new OrderDetails();
		Menu menu2 = new Menu();
		menu2.setItemId(2);
		menu2.setPrice(new BigDecimal(80));
		orderDetails2.setMenu(menu2);
		orderDetails2.setQuantity(2);
		
		OrderDetails orderDetails3 = new OrderDetails();
		Menu menu3 = new Menu();
		menu3.setItemId(3);
		menu3.setPrice(new BigDecimal(40));
		orderDetails3.setMenu(menu3);
		orderDetails3.setQuantity(3);
		
		Set<OrderDetails> orderDetailsSet = new HashSet<OrderDetails>();
		orderDetailsSet.add(orderDetails1);
		orderDetailsSet.add(orderDetails2);
		orderDetailsSet.add(orderDetails3);
		
		order.setOrderDetails(orderDetailsSet);
		order.setTotalCost(null);
		order.setOrderDate(new Date());
		Caterer caterer = new Caterer();
		caterer.setCatererId(2);
		order.setCaterer(caterer);
		order.setOrderCreatedDate(new Date());
		order.setStatus(OrderStatus.PENDING.getOrderStatus());
		User user = new User();
		user.setUserId(1);
		order.setUser(user);
		
		orderDetails1.setOrderMaster(order);
		orderDetails2.setOrderMaster(order);
		orderDetails3.setOrderMaster(order);
		OrderService service = new OrderServiceImpl();
		service.createOrder(order);
	}
	
	public static void testCancelOrder() throws Exception
	{
		OrderService service = new OrderServiceImpl();
		service.cancelOrder(2, "Item not available");
	}
	
	/*public static void  testCreateOrder()
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "15-12-2014 0:0:0";
		Date date = null;
		try
		{
			date = sdf.parse(dateInString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		
		DailyMenuMapping dailyMenuMapping= new DailyMenuMapping();
		dailyMenuMapping.setDailyMenuMappingId(4);;
		
		
		Set<OrderDetails> orderDetailsSet= new  HashSet<OrderDetails>(); 
		 
		
		
		OrderDetails orderDetails= new  OrderDetails();
		orderDetails.setDailyMenuMapping(dailyMenuMapping);
	
		
		User user= new User();
		user.setUserId(11);
		
		
		//Order Master  
		OrderMaster order= new OrderMaster();
		order.setTotalCost(new BigDecimal(120));
	    order.setOrderDate(date);
	    order.setOrderDetails(orderDetailsSet);
	    order.setUser(user);
		order.setRemark("Order is pending...");
		order.setStatus("PENDING");
		System.out.println(order.toString());
		
	    orderDetails.setOrderMaster(order);
		
		
		OrderServiceImpl service= new OrderServiceImpl();
		service.createOrder(order,orderDetails);
		
	}*/

	/*	public static void getUserRole()
		{
			UserDAO dao = new UserDAO();
			User user = dao.getRole(11);
			System.out.println(user.toString());
		}*/

	public static void testCreateUser()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "24-12-2014 0:0:0";
		Date date = null;
		try
		{
			date = sdf.parse(dateInString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		User user = new User();
		user.setLoginId("Kiran");
		user.setPassword("Nayak");
		user.setEmailId("kiran.nayak@mastek.com");

		UserServiceImpl service = new UserServiceImpl();
		try
		{
			service.createUser(user);
		}
		catch (UserExistException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void testDeleteUser()
	{
		UserServiceImpl service = new UserServiceImpl();
		User user = new User();
		user.setUserId(23);
		try
		{
			service.deleteUser(user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void testUpdateUser()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "24-12-2014 0:0:0";
		Date date = null;
		try
		{
			date = sdf.parse(dateInString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		User user = new User();
		user.setLoginId("Kiran");

		UserDetails userDetails = new UserDetails();
		userDetails.setFirstName("Kiran");
		userDetails.setLastName("Nayak");

		UserService service = new UserServiceImpl();
		try
		{
			//user.setUserDetails(userDetails);
			service.updateUser(user);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void testAuthenticateUser() throws Exception
	{
		UserServiceImpl service = new UserServiceImpl();
		Boolean result = service.authenicateUser("Radha", "123456");
		System.out.println("Result" + result);
	}

	public static void testGetUserById()
	{
		UserServiceImpl service = new UserServiceImpl();
		User user = service.getUserById(2);
		System.out.println(user);
	}

	public static void testUpdateUserRole()
	{
		UserServiceImpl service = new UserServiceImpl();
		try
		{
			User user = new User();
			user.setUserId(6);
			Role admin = new Role();
			admin.setRoleId(1);
			Role customer = new Role();
			customer.setRoleId(2);

			List<Role> roleList = new ArrayList<Role>();
			roleList.add(admin);
			roleList.add(customer);

			User user1 = service.updateUserRole(user, roleList);
			System.out.println(user);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testChangePassword()
	{
		UserServiceImpl service = new UserServiceImpl();
		try
		{
			User user = service.changePassword("Radha", "Radha123", "123456");
			System.out.println(user.toString());
		}
		catch (IncorrectPasswordException ipe)
		{
			ipe.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
