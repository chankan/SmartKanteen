package com.mastek.topcoders.smartkanteen.junit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Assert;

import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.DailyMenuMapping;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

public class DailyMenuTestCase
{

	/**
	 * There is a bug in this method  so 
	 * pls  solve as soon as possible...
	 * (Bug: The method is allow the  
	 *  user to add duplicate entries 
	 *  in the dailyMenu table which 
	 *  should be avioded  )
	 * 
	 */

	@Test
	public void testAddDailyMenu()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "15-12-2014 0:0:0";
		Date date = null;
		try
		{
			date = sdf.parse(dateInString);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		MenuService service = new MenuServiceImpl();
		Menu menu1 = new Menu();
		menu1.setItemId(6);
		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu1);
		service.addDailyMenu(1, date, menuList);
	}

											/**
											* There is bug in the 
											* getDailyMenu() method
											* (Bug : If there  are multiple entries
											*  related to the specified date and
											*  caterer_id then it shows 
											*  only the last entry. )
											*  
											*/

	@Test
	public void testGetDailyMenu()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String dateInString = "18-12-2014 0:0:0";
		Date date = null;
		try
		{
			date = sdf.parse(dateInString);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		MenuService service = new MenuServiceImpl();
		List<Menu> menuList = service.getDailyMenu(date, 1);

		System.out.println(menuList);

		Assert.assertEquals(2, menuList.size());
	}

	@Test
	public void testDeleteDailyMenu()
	{
		Session session = DatabaseUtil.getSession();
		boolean result1 = false;
		MenuService service = new MenuServiceImpl();
		boolean result2 = service.deleteDailyMenu(10);
		DailyMenu dailyMenu = (DailyMenu) session.get(DailyMenu.class, 11);
		if (dailyMenu == null)
		{
			result1 = true;
		}
		Assert.assertEquals(result2, result1);
	}

															/**
															 * Bug :- The Items were not 
															 * getting deleted but  
															 *   it has been solved. 
															 * 
															 */

	@Test
	public void testRemoveDailyMenuItems()
	{
		Session session = DatabaseUtil.getSession();

		MenuServiceImpl service = new MenuServiceImpl();
		Menu menu1 = new Menu();
		menu1.setItemId(4);
		Menu menu2 = new Menu();
		menu2.setItemId(3);

		List<Menu> menuList1 = new ArrayList<Menu>();
		menuList1.add(menu1);
		menuList1.add(menu2);
		boolean result2 = service.removeDailyMenuItems(1, menuList1);

		List<Menu> menuList = session.createQuery("FROM DailyMenuMapping").list();
		System.out.println(menuList.size());
		Assert.assertEquals(3, menuList.size());
	}

																    /**
																        * Bug:-
																        *      This method     
																        *      is allowing 
																        *      duplicate 
																        *      entries in the  
																        *      daily_menu_mapping 
																        *      tab;e  
																        */

		@Test
	public  void TestAppendDailyMenuItems()
	{
	Menu menu1= new Menu();
	menu1.setItemId(6);
	Menu menu2= new Menu(); 
	menu2.setItemId(5);

	List<Menu> menuList=new ArrayList<Menu>();
	menuList.add(menu1);
	menuList.add(menu2);

	MenuService service= new MenuServiceImpl();
	service.appendDailyMenuItems(3, menuList);

	Session session= DatabaseUtil.getSession();
	Query query= session.createQuery("FROM DailyMenuMapping WHERE dailyMenuId= :daily_menu_id");
	query.setParameter("daily_menu_id",3);

	List<DailyMenuMapping> dailyMenuMappingList =  query.list();

	Assert.assertEquals(3,dailyMenuMappingList.size());

	}

																			/**
																			* Bug:-
																			*      This method     
																			*      is allowing 
																			*      duplicate 
																			*      entries in the  
																			*      daily_menu_mapping 
																			*      tab;e  
																			*/

	@Test
	public void TestAppendDailyMenuItem()
	{
		Menu menu = new Menu();
		menu.setItemId(6);

		MenuService service = new MenuServiceImpl();
		service.appendDailyMenuItems(3, menu);

		Session session = DatabaseUtil.getSession();
		Query query = session.createQuery("FROM DailyMenuMapping WHERE dailyMenuId= :daily_menu_id");
		query.setParameter("daily_menu_id", 3);

		List<DailyMenuMapping> dailyMenuMappingList = query.list();

		Assert.assertEquals(7, dailyMenuMappingList.size());

	}

}
