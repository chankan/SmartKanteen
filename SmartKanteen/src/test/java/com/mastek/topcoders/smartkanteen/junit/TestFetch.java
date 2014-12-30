package com.mastek.topcoders.smartkanteen.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.Tag;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

public class TestFetch
{
	@Test
	public void testGetCaterers()
	{
		MenuService service = new MenuServiceImpl();
		List<Caterer> catererList = service.getCaterers();
		Assert.assertEquals(2, catererList.size());
	}

	@Test
	public void testGetDailyMenu()
	{
		/*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String dateInString = "18-12-2014 0:0:0";
		Date date = null;
		try
		{
			date = sdf.parse(dateInString);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}*/

		MenuService service = new MenuServiceImpl();
		List<Menu> menuList = service.getDailyMenu(null, 1);

		Assert.assertEquals(4, menuList.size());
	}

	@Test
	public void testGetMenuByCaterer()
	{
		MenuService service = new MenuServiceImpl();
		List<Menu> menuList = service.getMenuMasterByCaterer(2);
		Assert.assertEquals(5, menuList.size());
	}

	@Test
	public void testGetMenuMaster()
	{
		MenuService service = new MenuServiceImpl();
		List<Menu> menuList = service.getMenuMaster();
		Assert.assertEquals(10, menuList.size());
	}

	@Test
	public void testGetTags()
	{
		MenuService service = new MenuServiceImpl();
		List<Tag> tags = service.getTags();
		Assert.assertEquals(11, tags.size());
	}
}