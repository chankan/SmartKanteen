package com.mastek.topcoders.smartkanteen.junit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.DailyMenuMapping;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

public class DailyMenuTestCase
{

	//TODO Needs refactoring

	@Test
	public void testAddDailyMenu() throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "16-12-2014 0:0:0";
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
		Menu menu = new Menu();
		menu.setItemId(6);

		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);

		DailyMenu dailyMenu = service.addDailyMenu(1, date, menuList);
	}

	@Test
	public void testDeleteDailyMenu() throws ObjectNotFoundException, Exception
	{
		Session session = DatabaseUtil.getSession();
		boolean isDeleted = false;

		MenuService service = new MenuServiceImpl();
		boolean resultDB = service.deleteDailyMenu(10);

		DailyMenu dailyMenu = (DailyMenu) session.get(DailyMenu.class, 11);

		if (dailyMenu == null)
		{
			isDeleted = true;
		}

		Assert.assertEquals(isDeleted, resultDB);
	}

	@Test
	public void testRemoveDailyMenuItems() throws Exception
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

	@Test
	public void TestAppendDailyMenuItems() throws Exception
	{
		Menu menu1 = new Menu();
		menu1.setItemId(6);
		Menu menu2 = new Menu();
		menu2.setItemId(5);

		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu1);
		menuList.add(menu2);

		MenuService service = new MenuServiceImpl();
		service.appendDailyMenuItems(3, menuList);

		Session session = DatabaseUtil.getSession();
		Query query = session.createQuery("FROM DailyMenuMapping WHERE dailyMenuId= :daily_menu_id");
		query.setParameter("daily_menu_id", 3);

		List<DailyMenuMapping> dailyMenuMappingList = query.list();

		Assert.assertEquals(3, dailyMenuMappingList.size());

	}

	@Test
	public void TestAppendDailyMenuItem() throws Exception
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
