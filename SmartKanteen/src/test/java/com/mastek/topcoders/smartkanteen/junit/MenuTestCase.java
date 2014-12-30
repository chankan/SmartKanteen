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
		menu.setItemName("Veg Paneer Fried Rice");
		menu.setDescription("Veg Paneer Fried Rice is one of the most famous item in India");
		menu.setPrepTime(25);
		menu.setPrice(BigDecimal.valueOf(150.0));

		Caterer caterer = new Caterer();
		caterer.setCatererId(2);

		MenuService service = new MenuServiceImpl();
		CatererMenuMapping catererMenuMapping = service.addItemInMenuMaster(menu, caterer);

		DatabaseUtil.closeSession(session);
		
		Assert.assertEquals(menu.getItemName(), catererMenuMapping.getMenu().getItemName());
		Assert.assertEquals(menu.getDescription(), catererMenuMapping.getMenu().getDescription());
		Assert.assertEquals(menu.getPrepTime(), catererMenuMapping.getMenu().getPrepTime());
		Assert.assertEquals(menu.getPrice(), catererMenuMapping.getMenu().getPrice());

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
		
		DatabaseUtil.closeSession(session);
		Assert.assertEquals(result2, result1);
	}

	@Test
	public void testUpdateMenuItemInMenuMaster() throws Exception
	{

		MenuService service = new MenuServiceImpl();
		Menu menu1 = new Menu();
		menu1.setItemId(1);
		menu1.setItemName("Veg Biryani");
		menu1.setDescription("Veg Biryani is one of the most famous items");
		menu1.setPrepTime(20);
		service.updateItemInMenuMaster(menu1.getItemId(), menu1.getItemName(), menu1.getDescription(),
				menu1.getPrice(), menu1.getPrepTime());

		Session session = DatabaseUtil.getSession();
		Menu menu2 = (Menu) session.get(Menu.class, 1);
		DatabaseUtil.closeSession(session);
		
		Assert.assertEquals(menu1.getItemName(), menu2.getItemName());
		Assert.assertEquals(menu1.getDescription(), menu2.getDescription());
		Assert.assertEquals(menu1.getPrepTime(), menu2.getPrepTime());
	}
}
