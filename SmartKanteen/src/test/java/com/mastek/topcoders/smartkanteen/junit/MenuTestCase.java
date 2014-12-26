package com.mastek.topcoders.smartkanteen.junit;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.CatererMenuMapping;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

public class MenuTestCase
{

	//TODO Needs refactoring

	@Test
	public void testAddItemInMenuMasterByCaterer() throws Exception
	{
		Session session = DatabaseUtil.getSession();

		Menu menu = new Menu();
		menu.setItemName("Veg Paneer7 Fried Rice");
		menu.setDescription("Veg Paneer7 Fried Rice is one of the most famous item in India");
		menu.setPrepTime(25);
		menu.setPrice(BigDecimal.valueOf(150.0));

		Caterer caterer = new Caterer();
		caterer.setCatererId(1);

		MenuService service = new MenuServiceImpl();
		service.addItemInMenuMaster(menu, caterer);

		Menu menuDB = (Menu) session.get(Menu.class, 31);

		Query query = session.createQuery("FROM CatererMenuMapping WHERE  caterer_id= :caterer_id");
		query.setParameter("caterer_id", 1);
		List<CatererMenuMapping> catererMenuMappingList = (List<CatererMenuMapping>) query.list();

		session.load(CatererMenuMapping.class, 23);
		CatererMenuMapping catererMenuMapping = catererMenuMappingList.get(0);

		Assert.assertEquals(menu.getItemName(), menuDB.getItemName());
		Assert.assertEquals(menu.getDescription(), menuDB.getDescription());
		Assert.assertEquals(menu.getPrepTime(), menuDB.getPrepTime());
		Assert.assertEquals(menu.getPrice(), menuDB.getPrice());

		Assert.assertEquals(caterer.getCatererId(), catererMenuMapping.getCatererId());
	}

	@Test
	public void testAddItemInMenuMaster() throws Exception
	{
		Menu menu = new Menu();
		menu.setItemName("Veg Paneer Fried Rice1");
		menu.setDescription("Veg Paneer Fried Rice1 is one of the most famous item in India");
		menu.setPrepTime(25);
		menu.setPrice(BigDecimal.valueOf(150));
		
		MenuService service = new MenuServiceImpl();
		service.addItemInMenuMaster(menu);
	}

	@Test
	public void testGetMenuByName()
	{
		MenuService service = new MenuServiceImpl();
		List<Menu> menuList = service.getMenuByName("x");
		Assert.assertEquals(0, menuList.size());
	}

	@Test
	public void testGetMenuByCaterer()
	{
		MenuService service = new MenuServiceImpl();
		List<Menu> menuList = service.getMenuMasterByCaterer(2);
		Assert.assertEquals(4, menuList.size());
	}

	@Test
	public void getMenuMaster()
	{
		MenuService service = new MenuServiceImpl();
		List<Menu> menuList = service.getMenuMaster();
		Assert.assertEquals(10, menuList.size());
	}

	@Test
	public void testDeleteItemFromMenuMaster() throws ObjectNotFoundException, Exception
	{
		boolean result1 = false;
		MenuServiceImpl service = new MenuServiceImpl();
		boolean result2 = service.deleteItemFromMenuMaster(11);
		Session session = DatabaseUtil.getSession();
		Menu menu = (Menu) session.get(Menu.class, 11);

		if (menu == null)
		{
			result1 = true;
		}
		Assert.assertEquals(result2, result1);
	}

	@Test
	public void testUpdateMenuItemInMenuMaster() throws Exception
	{

		MenuService service = new MenuServiceImpl();
		Menu menu1 = new Menu();
		menu1.setItemId(36);
		menu1.setItemName("Veg Biryani");
		menu1.setDescription("Veg Biryani is one of the most famous items");
		menu1.setPrepTime(20);
		service.updateItemInMenuMaster(menu1.getItemId(), menu1.getItemName(), menu1.getDescription(),
				menu1.getPrice(), menu1.getPrepTime());
		Session session = DatabaseUtil.getSession();
		Menu menu2 = (Menu) session.get(Menu.class, 36);
		Assert.assertEquals(menu1.getItemName(), menu2.getItemName());
		Assert.assertEquals(menu1.getDescription(), menu2.getDescription());
		Assert.assertEquals(menu1.getPrepTime(), menu2.getPrepTime());
		session.close();
	}

	@Test
	public void testGetInsertedMenuItem()
	{
		Session session = DatabaseUtil.getSession();
		Query query = session.createQuery("FROM Menu");
		List<Menu> menuList = query.list();

		Menu menu1 = (Menu) menuList.get(0);
		Menu menu2 = new Menu();
		menu2.setItemName("Veg Paneer Fried Rice");
		menu2.setDescription("Veg Paneer Fried Rice is one of the most famous item in India");
		menu2.setPrepTime(25);

		menu2.setPrice(BigDecimal.valueOf(150));

		Assert.assertEquals(menu2.getItemName(), menu1.getItemName());
		Assert.assertEquals(menu2.getDescription(), menu1.getDescription());
		Assert.assertEquals(menu2.getPrepTime(), menu1.getPrepTime());
		Assert.assertEquals(menu2.getPrice(), menu1.getPrice());
	}
}
