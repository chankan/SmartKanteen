package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;

public class Test
{
	public static void main(String[] args)
	{
		try
		{
			testingMenuTable();

			testingCatererTable();

			testingDailyMenuTable();
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
		}
	}

	public static void testingMenuTable()
	{
		System.out.println("Initial DB");
		displayMenuItems();

		testAddMenu();
		System.out.println("After Adding Items");
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

	public static void testAddMenu()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menuMaster = new Menu();
		menuMaster.setItemId(1);
		menuMaster.setItemName("Medu Wada");
		menuMaster.setDescription("South Indian");
		menuMaster.setPrepTime(5);
		menuMaster.setPrice(new BigDecimal(40));
		service.addItemInMenuMaster(menuMaster);

		Menu menuMaster1 = new Menu();
		menuMaster1.setItemId(2);
		menuMaster1.setItemName("Chole Bhature");
		menuMaster1.setDescription("Punjabi");
		menuMaster1.setPrepTime(15);
		menuMaster1.setPrice(new BigDecimal(40));
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

	public static void testAddMenuWithCaterer()
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

	public static void testUpdateMenuItems()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		service.updateItemInMenuMaster(4, "Five Rice", "Chinese", null, null);

		Menu menu = new Menu();
		menu.setItemId(1);
		menu.setPrice(new BigDecimal(25));

		service.updateItemInMenuMaster(menu);
	}

	public static void testDeleteItems()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		service.deleteItemFromMenuMaster(1);
		service.deleteItemFromMenuMaster(3);
	}

	public static void testGetMenuByName()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		List<Menu> menuList = service.getMenuByName("Biryani");

		System.out.println(menuList);
	}

	public static void testingCatererTable()
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

	public static void testAddCaterer()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Caterer caterer = new Caterer();
		caterer.setCatererId(3);
		caterer.setCatererName("Tadka Caterer");

		Caterer caterer1 = new Caterer();
		caterer1.setCatererId(4);
		caterer1.setCatererName("Nitya Caterer");

		service.addCaterer(caterer);
		service.addCaterer(caterer1);
	}

	public static void testUpdateCaterer()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Caterer caterer = new Caterer();
		caterer.setCatererId(1);
		caterer.setCatererName("Royal Kings");

		service.updateCaterer(caterer.getCatererId(), caterer.getCatererName());
	}

	public static void testGetCatererById()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		System.out.println(service.getCaterer(1));
	}

	public static void testDeleteCaterer()
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

	public static void testingDailyMenuTable()
	{
		System.out.println("Initial");
		displayDailyMenu();

		testaddDailyMenu();
		System.out.println("After Adding");
		displayDailyMenu();

		testupdateDailyMenuItems();
		System.out.println("After Updating");
		displayDailyMenu();

		testappendDailyMenuItems();
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

	public static void testaddDailyMenu()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menu = new Menu();
		menu.setItemId(8);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		service.addDailyMenu(2, new Date(), menuList);
	}

	public static void testupdateDailyMenuItems()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menu = new Menu();
		menu.setItemId(3);
		menu.setPrepTime(10);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		service.updateDailyMenuItems(1, menuList);
	}

	public static void testappendDailyMenuItems()
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

	public static void testRemoveDailyMenuItems()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		List<Menu> menuList = new ArrayList<Menu>();
		Menu menu = new Menu();
		menu.setItemId(5);
		menuList.add(menu);

		service.removeDailyMenuItems(1, menuList);
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

	public static void testDeleteDailyMenu()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		service.deleteDailyMenu(1);
	}
}
