package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.dao.MenuDAO;

public class MenuServiceImpl implements MenuService
{
	public List<Menu> getMenuMaster()
	{
		MenuDAO dao = new MenuDAO();
		return dao.getMenuMaster();
	}

	public List<Menu> getMenuMasterByCaterer(Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		List<Menu> menuList= dao.getMenuMaster(catererId);
		return   menuList;
	}

	public List<Menu> getMenuByName(String itemName)
	{
		MenuDAO dao = new MenuDAO();
		List<Menu> menuList = dao.getMenuByName(itemName);
		return menuList;
	}

	public Integer addItemInMenuMaster(Menu menuMaster)
	{
		MenuDAO dao = new MenuDAO();
		return dao.addItem(menuMaster);
	}

	public void addItemInMenuMaster(Menu menuMaster, Caterer caterer)
	{
		MenuDAO dao = new MenuDAO();
		dao.addItemInMenuMaster(menuMaster, caterer);
	}

	/*
	 * Added by @vaibhav testing Post Operation on RS
	 */
	@Override
	public void addItemMenuMaster(String itemName, String description, BigDecimal price, Integer prepTime,
			Integer category)
	{
		// TODO Auto-generated method stub
		MenuDAO dao = new MenuDAO();
		// dao.addItemInMenuMaster(itemName, description,
		// price, prepTime,category);

		// MenuDAO dao = new MenuDAO();

		// Menu menuMaster = dao.getItem(itemId);

		Menu menuMaster = new Menu();

		if (itemName != null)
		{
			menuMaster.setItemName(itemName);
		}

		if (description != null)
		{
			menuMaster.setDescription(description);
		}

		if (price != null)
		{
			menuMaster.setPrice(price);
		}

		if (prepTime != null)
		{
			menuMaster.setPrepTime(prepTime);
		}

		if (category != null)
		{
			// menuMaster.se(prepTime);
		}

		dao.addItem(menuMaster);
	}

	public void updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price,
			Integer prepTime)
	{
		if (itemId == null || itemId == 0)
		{
			System.out.println("Item not found");
		}

		MenuDAO dao = new MenuDAO();

		Menu menuMaster = dao.getItem(itemId);

		if (menuMaster != null)
		{
			if (itemName != null)
			{
				menuMaster.setItemName(itemName);
			}

			if (description != null)
			{
				menuMaster.setDescription(description);
			}

			if (price != null)
			{
				menuMaster.setPrice(price);
			}

			if (prepTime != null)
			{
				menuMaster.setPrepTime(prepTime);
			}

			dao.updateItem(menuMaster);
		}
		else
		{
			System.out.println("Menu item not found!!");
		}
	}

	public void deleteItemFromMenuMaster(Integer itemId)
	{
		MenuDAO dao = new MenuDAO();
		dao.deleteItem(itemId);
	}

	public void deleteAllItemsFromMenuMaster()
	{
		MenuDAO dao = new MenuDAO();
		dao.deleteAllItemsFromMenuMaster();
	}

	public Caterer getCaterer(Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		Caterer caterer = dao.getCaterer(catererId);
		return caterer;
	}

	public List<Caterer> getCaterers()
	{
		MenuDAO dao = new MenuDAO();
		List<Caterer> catererList = dao.getCaterers();
		return catererList;
	}

	public Integer addCaterer(Caterer caterer)
	{
		MenuDAO dao = new MenuDAO();
		Integer result = dao.addCaterer(caterer);
		return result;
	}

	public Boolean updateCaterer(Integer catererId, String catererName)
	{

		boolean result = false;

		if (catererId == 0 || catererId == null)
		{
			System.out.println("CatererId not found");
		}
		MenuDAO dao = new MenuDAO();
		Caterer caterer = dao.getCaterer(catererId);

		if (caterer != null)
		{
			if (catererName != null)
			{
				caterer.setCatererName(catererName);
			}
			result = dao.updateCaterer(caterer);
		}
		return result;
	}

	public Boolean deleteCaterer(Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		boolean result = dao.deleteCaterer(catererId);
		return result;
	}

	public void deleteAllCaterers()
	{
		MenuDAO dao = new MenuDAO();
		dao.deleteAllCaterers();
	}

	public List<DailyMenu> getDailyMenu(Date menuDate, Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		List<DailyMenu> dailyMenuList = dao.getDailyMenu(menuDate, catererId);
		return dailyMenuList;
	}

	public void addDailyMenu(Integer catererId, Date menuDate, List<Menu> menuList)
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setCatererId(catererId);
		dailyMenu.setMenuDate(menuDate);
		dailyMenu.setMenuList(menuList);

		dao.addDailyMenuItem(dailyMenu);
	}

	public void updateDailyMenu(Integer dailyMenuId, List<Menu> menuList)
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setDailyMenuId(dailyMenuId);
		dailyMenu.setMenuList(menuList);

		dao.updateDailyMenu(dailyMenu);
	}
	
	public void appendDailyMenu(Integer dailyMenuId, Menu menu)
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setDailyMenuId(dailyMenuId);

		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		dailyMenu.setMenuList(menuList);

		dao.appendDailyMenu(dailyMenu);
	}

	public void appendDailyMenu(Integer dailyMenuId, List<Menu> menuList)
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setDailyMenuId(dailyMenuId);
		dailyMenu.setMenuList(menuList);

		dao.appendDailyMenu(dailyMenu);
	}
}
