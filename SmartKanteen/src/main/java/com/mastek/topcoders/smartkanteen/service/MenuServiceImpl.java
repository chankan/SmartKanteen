package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.CatererMenuMapping;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.MenuTagsMapping;
import com.mastek.topcoders.smartkanteen.bean.Tag;
import com.mastek.topcoders.smartkanteen.dao.MenuDAO;

public class MenuServiceImpl implements MenuService
{
	@Override
	public List<Menu> getMenuMaster()
	{
		MenuDAO dao = new MenuDAO();
		return dao.getMenuMaster();
	}

	@Override
	public List<Menu> getMenuMasterByCaterer(Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		List<Menu> menuList = dao.getMenuMaster(catererId);
		return menuList;
	}

	@Override
	public List<Menu> getMenuByName(String itemName)
	{
		MenuDAO dao = new MenuDAO();
		List<Menu> menuList = dao.getMenuByName(itemName);
		return menuList;
	}

	@Override
	public Menu addItemInMenuMaster(Menu menuMaster) throws Exception
	{
		MenuDAO dao = new MenuDAO();
		return dao.addItem(menuMaster);
	}

	@Override
	public Menu addItemInMenuMaster(Menu menu, String tags) throws Exception
	{
		MenuDAO dao = new MenuDAO();

		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setItemId(menu.getItemId());
		menuTagsMapping.setTags(tags);

		menu.setMenuTagsMapping(menuTagsMapping);

		System.out.println(menu);
		return dao.addItem(menu);
	}

	@Override
	public CatererMenuMapping addItemInMenuMaster(Menu menuMaster, Caterer caterer) throws Exception
	{
		MenuDAO dao = new MenuDAO();
		return dao.addItemInMenuMaster(menuMaster, caterer);
	}

	@Override
	public CatererMenuMapping addItemInMenuMaster(Menu menuMaster, Caterer caterer, String tags) throws Exception
	{
		MenuDAO dao = new MenuDAO();

		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setItemId(menuMaster.getItemId());
		menuTagsMapping.setTags(tags);

		menuMaster.setMenuTagsMapping(menuTagsMapping);

		return dao.addItemInMenuMaster(menuMaster, caterer);
	}

	@Override
	public Menu updateItemInMenuMaster(Menu menu) throws Exception
	{
		MenuDAO dao = new MenuDAO();
		return dao.updateItem(menu);
	}

	@Override
	public Menu updateItemInMenuMaster(Menu menuMaster, String tags) throws Exception
	{
		MenuDAO dao = new MenuDAO();

		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setItemId(menuMaster.getItemId());
		menuTagsMapping.setTags(tags);

		menuMaster.setMenuTagsMapping(menuTagsMapping);

		return dao.updateItem(menuMaster);
	}

	@Override
	public Menu updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price,
			Integer prepTime) throws Exception
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

			return dao.updateItem(menuMaster);
		}
		else
		{
			System.out.println("Menu item not found!!");
		}

		return null;
	}

	@Override
	public Boolean deleteItemFromMenuMaster(Integer itemId) throws ObjectNotFoundException, Exception
	{
		MenuDAO dao = new MenuDAO();
		boolean result = dao.deleteItem(itemId);
		return result;
	}

	@Override
	public MenuTagsMapping addMenuTags(Menu menu, String tags) throws HibernateException, Exception
	{
		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setItemId(menu.getItemId());
		menuTagsMapping.setTags(tags);

		MenuDAO dao = new MenuDAO();
		return dao.addMenuTags(menuTagsMapping);
	};

	@Override
	public MenuTagsMapping updateMenuTags(Menu menu, String tags) throws HibernateException, Exception
	{
		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setItemId(menu.getItemId());
		menuTagsMapping.setTags(tags);

		MenuDAO dao = new MenuDAO();
		return dao.updateMenuTags(menuTagsMapping);
	}

	@Override
	public Boolean deleteMenuTags(Menu menu) throws HibernateException, Exception
	{
		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setItemId(menu.getItemId());

		MenuDAO dao = new MenuDAO();
		return dao.deleteMenuTags(menuTagsMapping);
	}

	@Override
	public Caterer getCaterer(Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		Caterer caterer = dao.getCaterer(catererId);
		return caterer;
	}

	@Override
	public List<Caterer> getCaterers()
	{
		MenuDAO dao = new MenuDAO();
		List<Caterer> catererList = dao.getCaterers();
		return catererList;
	}

	@Override
	public Caterer addCaterer(Caterer caterer) throws Exception
	{
		MenuDAO dao = new MenuDAO();
		return dao.addCaterer(caterer);
	}

	@Override
	public Caterer updateCaterer(Caterer caterer) throws Exception
	{
		if (caterer.getCatererId() == 0 || caterer.getCatererId() == null)
		{
			System.out.println("CatererId not found");
		}

		MenuDAO dao = new MenuDAO();
		Caterer catererDB = dao.getCaterer(caterer.getCatererId());

		if (catererDB != null)
		{
			if (caterer.getCatererName() != null)
			{
				catererDB.setCatererName(caterer.getCatererName());
			}
			
			if (caterer.getCatererDetails() != null)
			{
				catererDB.setCatererDetails(caterer.getCatererDetails());
			}
			
			return dao.updateCaterer(catererDB);
		}

		return null;
	}
	
	@Override
	public Caterer updateCaterer(Integer catererId, String catererName, String catererDetails) throws Exception
	{
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
			
			if (catererDetails != null)
			{
				caterer.setCatererDetails(catererDetails);
			}
			
			return dao.updateCaterer(caterer);
		}

		return null;
	}

	@Override
	public Boolean deleteCaterer(Integer catererId) throws ObjectNotFoundException, Exception
	{
		MenuDAO dao = new MenuDAO();
		return dao.deleteCaterer(catererId);
	}

	@Override
	public List<Menu> getDailyMenu(Date menuDate, Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		List<Menu> menuList = dao.getDailyMenu(menuDate, catererId);
		return menuList;
	}

	@Override
	public DailyMenu addDailyMenu(Integer catererId, Date menuDate, List<Menu> menuList) throws Exception
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setCatererId(catererId);
		dailyMenu.setMenuDate(menuDate);
		dailyMenu.setMenuList(menuList);
		
		DailyMenu dailyMenuDB = dao.dailyMenuExists(dailyMenu);

		if(dailyMenuDB == null)
		{
			return dao.addDailyMenuItem(dailyMenu);
		}
		else
		{
			Exception e = new Exception("Daily Menu already Exists!!");
			throw e;
		}
	}

	@Override
	public DailyMenu updateDailyMenu(Integer catererId, Date menuDate, List<Menu> menu) throws Exception
	{
		MenuDAO dao = new MenuDAO();
		return dao.updateDailyMenuItem(catererId, menuDate, menu);
	}

	@Override
	public Boolean deleteDailyMenu(Integer dailyMenuId) throws ObjectNotFoundException, Exception
	{
		MenuDAO dao = new MenuDAO();
		boolean result = dao.deleteDailyMenu(dailyMenuId);
		return result;
	}

	@Override
	public Boolean deleteDailyMenu(Integer catererId, Date menuDate) throws ObjectNotFoundException, Exception
	{
		MenuDAO dao = new MenuDAO();
		return dao.deleteDailyMenu(catererId, menuDate);
	}

	@Override
	public DailyMenu updateDailyMenuItems(Integer dailyMenuId, List<Menu> menuList) throws Exception
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setDailyMenuId(dailyMenuId);
		dailyMenu.setMenuList(menuList);

		return dao.updateDailyMenu(dailyMenu);
	}

	@Override
	public Boolean appendDailyMenuItems(Integer dailyMenuId, Menu menu) throws Exception
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setDailyMenuId(dailyMenuId);

		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		dailyMenu.setMenuList(menuList);

		return dao.appendDailyMenu(dailyMenu);
	}

	@Override
	public Boolean appendDailyMenuItems(Integer dailyMenuId, List<Menu> menuList) throws Exception
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setDailyMenuId(dailyMenuId);
		dailyMenu.setMenuList(menuList);

		return dao.appendDailyMenu(dailyMenu);
	}

	@Override
	public Boolean removeDailyMenuItems(Integer dailyMenuId, List<Menu> menuList) throws Exception
	{
		MenuDAO dao = new MenuDAO();
		boolean result = dao.removeDailyMenuItems(dailyMenuId, menuList);
		return result;
	}

	@Override
	public List<Tag> getTags()
	{
		MenuDAO dao = new MenuDAO();
		List<Tag> tags = dao.getTags();
		return tags;
	}

	@Override
	public Tag getTag(Integer tagId)
	{
		MenuDAO dao = new MenuDAO();
		Tag tag = dao.getTagById(tagId);
		return tag;
	}

	@Override
	public Tag addTag(Tag tag) throws Exception
	{
		MenuDAO dao = new MenuDAO();
		return dao.addTag(tag);
	}

	@Override
	public Tag updateTag(Tag tag) throws Exception
	{
		if (tag.getTagId() == 0 || tag.getTagId() == null)
		{
			System.out.println("Tag not found");
		}

		MenuDAO dao = new MenuDAO();
		Tag tagDB = dao.getTagById(tag.getTagId());

		if (tagDB != null)
		{
			if (tag.getTagName() != null)
			{
				tagDB.setTagName(tag.getTagName());
			}
			return dao.updateTag(tagDB);
		}

		return null;
	}

	@Override
	public Boolean deleteTag(Tag tag) throws ObjectNotFoundException, Exception
	{
		MenuDAO dao = new MenuDAO();
		return dao.deleteTag(tag);
	}
}
