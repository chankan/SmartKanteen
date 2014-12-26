package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
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
	public Integer addItemInMenuMaster(Menu menuMaster)
	{
		MenuDAO dao = new MenuDAO();
		return dao.addItem(menuMaster);
	}

	@Override
	public Integer addItemInMenuMaster(Menu menu, String tags)
	{
		MenuDAO dao = new MenuDAO();

		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setMenu(menu);
		menuTagsMapping.setTags(tags);

		menu.setMenuTagsMapping(menuTagsMapping);

		System.out.println(menu);
		return dao.addItem(menu);
	}

	@Override
	public void addItemInMenuMaster(Menu menuMaster, Caterer caterer)
	{
		MenuDAO dao = new MenuDAO();
		dao.addItemInMenuMaster(menuMaster, caterer);
	}

	@Override
	public void addItemInMenuMaster(Menu menuMaster, Caterer caterer, String tags)
	{
		MenuDAO dao = new MenuDAO();

		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setMenu(menuMaster);
		menuTagsMapping.setTags(tags);

		menuMaster.setMenuTagsMapping(menuTagsMapping);

		dao.addItemInMenuMaster(menuMaster, caterer);
	}

	@Override
	public void updateItemInMenuMaster(Menu menu)
	{
		MenuDAO dao = new MenuDAO();
		dao.updateItem(menu);
	}

	@Override
	public void updateItemInMenuMaster(Menu menuMaster, String tags)
	{
		MenuDAO dao = new MenuDAO();

		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setMenu(menuMaster);
		menuTagsMapping.setTags(tags);

		menuMaster.setMenuTagsMapping(menuTagsMapping);

		dao.updateItem(menuMaster);
	}

	@Override
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

	@Override
	public Boolean deleteItemFromMenuMaster(Integer itemId)
	{
		MenuDAO dao = new MenuDAO();
		boolean result = dao.deleteItem(itemId);
		return result;
	}

	@Override
	public void addMenuTags(Menu menu, String tags)
	{
		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setMenu(menu);
		menuTagsMapping.setTags(tags);

		MenuDAO dao = new MenuDAO();
		dao.addMenuTags(menuTagsMapping);
	};

	@Override
	public void updateMenuTags(Menu menu, String tags)
	{
		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setMenu(menu);
		menuTagsMapping.setTags(tags);

		MenuDAO dao = new MenuDAO();
		dao.updateMenuTags(menuTagsMapping);
	}

	@Override
	public void deleteMenuTags(Menu menu)
	{
		MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
		menuTagsMapping.setMenu(menu);

		MenuDAO dao = new MenuDAO();
		dao.deleteMenuTags(menuTagsMapping);
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
	public Integer addCaterer(Caterer caterer)
	{
		MenuDAO dao = new MenuDAO();
		Integer result = dao.addCaterer(caterer);
		return result;
	}

	@Override
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

	@Override
	public Boolean deleteCaterer(Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		boolean result = dao.deleteCaterer(catererId);
		return result;
	}

	@Override
	public List<Menu> getDailyMenu(Date menuDate, Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		List<Menu> menuList = dao.getDailyMenu(menuDate, catererId);
		return menuList;
	}

	@Override
	public void addDailyMenu(Integer catererId, Date menuDate, List<Menu> menuList)
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setCatererId(catererId);
		dailyMenu.setMenuDate(menuDate);
		dailyMenu.setMenuList(menuList);

		dao.addDailyMenuItem(dailyMenu);
	}
	
	@Override
	public void updateDailyMenu(Integer catererId, Date menuDate,
			List<Menu> menu) {
		MenuDAO dao = new MenuDAO();
		dao.updateDailyMenuItem(catererId,menuDate,menu);
	}
	
	

	@Override
	public Boolean deleteDailyMenu(Integer dailyMenuId)
	{
		MenuDAO dao = new MenuDAO();
		boolean result = dao.deleteDailyMenu(dailyMenuId);
		return result;
	}

	@Override
	public Boolean deleteDailyMenu(Integer catererId, Date menuDate) {
		MenuDAO dao = new MenuDAO();
		boolean result = dao.deleteDailyMenu(catererId,menuDate);
		return result;
	}
	
	@Override
	public void updateDailyMenuItems(Integer dailyMenuId, List<Menu> menuList)
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setDailyMenuId(dailyMenuId);
		dailyMenu.setMenuList(menuList);

		dao.updateDailyMenu(dailyMenu);
	}

	@Override
	public void appendDailyMenuItems(Integer dailyMenuId, Menu menu)
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setDailyMenuId(dailyMenuId);

		List<Menu> menuList = new ArrayList<Menu>();
		menuList.add(menu);
		dailyMenu.setMenuList(menuList);

		dao.appendDailyMenu(dailyMenu);
	}

	@Override
	public void appendDailyMenuItems(Integer dailyMenuId, List<Menu> menuList)
	{
		MenuDAO dao = new MenuDAO();

		DailyMenu dailyMenu = new DailyMenu();
		dailyMenu.setDailyMenuId(dailyMenuId);
		dailyMenu.setMenuList(menuList);

		dao.appendDailyMenu(dailyMenu);
	}

	@Override
	public Boolean removeDailyMenuItems(Integer dailyMenuId, List<Menu> menuList)
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
	public void addTag(Tag tag)
	{
		MenuDAO dao = new MenuDAO();
		dao.addTag(tag);
	}

	@Override
	public void updateTag(Tag tag)
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
			dao.updateTag(tagDB);
		}
	}

	@Override
	public void deleteTag(Tag tag)
	{
		MenuDAO dao = new MenuDAO();
		dao.deleteTag(tag);
	}
}
