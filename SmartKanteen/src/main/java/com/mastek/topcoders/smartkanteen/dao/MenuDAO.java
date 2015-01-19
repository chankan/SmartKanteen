package com.mastek.topcoders.smartkanteen.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.CatererMenuMapping;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.DailyMenuMapping;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.MenuTagsMapping;
import com.mastek.topcoders.smartkanteen.bean.Tag;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;

public class MenuDAO
{
	public List<Menu> getMenuMaster()
	{
		Session session = DatabaseUtil.getSession();
		List<Menu> menuMasterList = session.createCriteria(Menu.class).list();
		DatabaseUtil.closeSession(session);

		return menuMasterList;
	}

	public List<Menu> getMenuMaster(Integer catererId)
	{
		Session session = DatabaseUtil.getSession();
		Criteria criteria = session.createCriteria(Caterer.class);
		criteria.add(Restrictions.eq("catererId", catererId));

		/*Query query = session.createQuery("FROM Caterer WHERE catererId= :catererID");
		query.setParameter("catererID", catererId);
		List<Caterer> catererList = query.list();*/

		List<Caterer> catererList = criteria.list();

		List<Menu> menuList = new ArrayList<Menu>();

		if (catererList != null && catererList.size() == 1)
		{
			Caterer caterer = catererList.get(0);

			for (CatererMenuMapping catererMenuMapping : caterer.getCatererMenuMapping())
			{
				menuList.add(catererMenuMapping.getMenu());
			}
		}

		return menuList;
	}

	public List<Menu> getMenuByName(String itemName)
	{
		Session session = DatabaseUtil.getSession();
		Criteria criteria = session.createCriteria(Menu.class);
		criteria.add(Restrictions.like("itemName", "%" + itemName + "%"));
		List<Menu> menuList = criteria.list();
		return menuList;
	}

	public Menu addItem(Menu menuMaster) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();
		try
		{
			session.save(menuMaster);

			MenuTagsMapping menuTagsMapping = menuMaster.getMenuTagsMapping();

			if (menuTagsMapping != null)
			{
				menuTagsMapping.setItemId(menuMaster.getItemId());
				session.save(menuMaster.getMenuTagsMapping());
			}

			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}
		DatabaseUtil.closeSession(session);

		return menuMaster;
	}

	public CatererMenuMapping addItemInMenuMaster(Menu menuMaster, Caterer caterer) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		CatererMenuMapping catererMenuMapping = null;

		if (menuMaster != null && caterer != null)
		{
			try
			{
				addItem(menuMaster);

				catererMenuMapping = new CatererMenuMapping();
				catererMenuMapping.setCatererId(caterer.getCatererId());
				catererMenuMapping.setMenu(menuMaster);

				session.save(catererMenuMapping);
				tx.commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				tx.rollback();
				DatabaseUtil.closeSession(session);
				throw e;
			}
		}
		DatabaseUtil.closeSession(session);

		return catererMenuMapping;
	}

	public Menu getItem(Integer itemId)
	{
		Session session = DatabaseUtil.getSession();

		Query query = session.createQuery("FROM Menu WHERE itemId= :itemId");
		query.setParameter("itemId", itemId);

		List<Menu> menuMasterList = query.list();
		DatabaseUtil.closeSession(session);

		if (menuMasterList != null && menuMasterList.size() == 1)
		{
			return menuMasterList.get(0);
		}

		return null;
	}

	public Menu updateItem(Menu menuMaster) throws Exception
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();
		try
		{
			MenuTagsMapping menuTagsMapping = menuMaster.getMenuTagsMapping();

			if(menuMaster!=null && menuMaster.getItemId()!=null)
			{
				Menu menuDB = (Menu)session.get(Menu.class, menuMaster.getItemId());
				
				if(menuMaster.getItemName()!=null)
				{
					menuDB.setItemName(menuMaster.getItemName());
				}
				
				if(menuMaster.getDescription()!=null)
				{
					menuDB.setDescription(menuMaster.getDescription());
				}
				
				if(menuMaster.getPrepTime()!=null)
				{
					menuDB.setPrepTime(menuMaster.getPrepTime());
				}
				
				if(menuMaster.getPrice()!=null)
				{
					menuDB.setPrice(menuMaster.getPrice());
				}
				
				session.update(menuDB);
			}

			if (menuTagsMapping != null)
			{
				Criteria criteria = session.createCriteria(MenuTagsMapping.class);
				criteria.add(Restrictions.eq("menu", menuMaster));

				List<MenuTagsMapping> menuTagsMappingList = criteria.list();

				if (menuTagsMappingList != null && menuTagsMappingList.size() == 1)
				{
					MenuTagsMapping menuTagsMappingDB = menuTagsMappingList.get(0);

					String tags = menuTagsMappingDB.getTags();

					if (!tags.equalsIgnoreCase(""))
					{
						tags = tags + "," + menuMaster.getMenuTagsMapping().getTags();
					}
					else
					{
						tags = menuMaster.getMenuTagsMapping().getTags();
					}

					menuTagsMappingDB.setTags(tags);

					session.update(menuTagsMappingDB);
				}
				else if (menuTagsMappingList == null || menuTagsMappingList.size() == 0)
				{
					menuTagsMapping.setItemId(menuMaster.getItemId());
					session.saveOrUpdate(menuTagsMapping);
				}
			}

			tx.commit();
			DatabaseUtil.closeSession(session);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		return menuMaster;
	}

	public Boolean deleteItem(Integer itemId) throws ObjectNotFoundException, Exception
	{
		boolean result = true;
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		try
		{
			Menu menuMaster = new Menu();
			menuMaster.setItemId(itemId);

			MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
			menuTagsMapping.setItemId(itemId);

			Criteria criteria = session.createCriteria(MenuTagsMapping.class);
			criteria.add(Restrictions.eq("menu", menuMaster));

			List<MenuTagsMapping> menuTagsMappingList = criteria.list();

			if (menuTagsMappingList != null && menuTagsMappingList.size() == 1)
			{
				MenuTagsMapping menuTagsMappingDB = menuTagsMappingList.get(0);
				session.delete(menuTagsMappingDB);
			}

			session.delete(menuMaster);
			tx.commit();
		}
		catch (ObjectNotFoundException hib)
		{
			hib.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw hib;
		}
		catch (Exception e)
		{
			result = false;
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public Caterer getCaterer(Integer catererId)
	{
		Session session = DatabaseUtil.getSession();
		Query query = session.createQuery("FROM Caterer WHERE caterer_id= :catererId");
		query.setParameter("catererId", catererId);
		List<Caterer> catererList = query.list();
		DatabaseUtil.closeSession(session);

		if (catererList != null && catererList.size() == 1)
		{
			return catererList.get(0);
		}
		return null;
	}

	public List<Caterer> getCaterers()
	{
		Session session = DatabaseUtil.getSession();
		List<Caterer> catererList = session.createCriteria(Caterer.class).list();
		DatabaseUtil.closeSession(session);
		return catererList;
	}

	public Caterer addCaterer(Caterer caterer) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		try
		{
			session.save(caterer);
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}
		DatabaseUtil.closeSession(session);
		return caterer;
	}

	public Caterer updateCaterer(Caterer caterer) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		if (caterer != null)
		{
			Transaction tx = session.beginTransaction();
			try
			{
				session.update(caterer);
				tx.commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				tx.rollback();
				DatabaseUtil.closeSession(session);
				throw e;
			}
			DatabaseUtil.closeSession(session);
		}
		return caterer;
	}

	public Boolean deleteCaterer(Integer catererId) throws ObjectNotFoundException, Exception
	{
		Boolean result = false;
		Session session = DatabaseUtil.getSession();
		Caterer caterer = (Caterer) session.load(Caterer.class, catererId);
		if (caterer != null)
		{
			Transaction tx = session.beginTransaction();
			try
			{
				session.delete(caterer);
				tx.commit();
				result = true;
			}
			catch (ObjectNotFoundException hib)
			{
				hib.printStackTrace();
				tx.rollback();
				DatabaseUtil.closeSession(session);
				throw hib;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				tx.rollback();
				DatabaseUtil.closeSession(session);
				throw e;
			}
			DatabaseUtil.closeSession(session);
		}
		return result;
	}

	public List<Menu> getDailyMenu(Date menuDate, Integer catererId)
	{
		Session session = DatabaseUtil.getSession();

		String sqlString = " FROM DailyMenu ";

		if (menuDate != null || catererId != null)
		{
			sqlString = sqlString + " WHERE ";

			if (menuDate != null && catererId != null)
			{
				sqlString = sqlString + " catererId= :catererId AND menuDate= :menuDate ";
			}
			else if (menuDate != null)
			{
				sqlString = sqlString + " menuDate= :menuDate ";
			}
			else if (catererId != null)
			{
				sqlString = sqlString + " catererId= :catererId ";
			}
		}

		Query query = session.createQuery(sqlString);

		if (menuDate != null)
		{
			query.setParameter("menuDate", menuDate);
		}
		if (catererId != null)
		{
			query.setParameter("catererId", catererId);
		}

		List<DailyMenu> dailyMenuList = query.list();
		DatabaseUtil.closeSession(session);
		Menu menu;
		List<Menu> menuList = null;

		for (DailyMenu dailyMenu : dailyMenuList)
		{
			menuList = new ArrayList<Menu>();

			for (DailyMenuMapping dailyMenuMapping : dailyMenu.getDailyMenuMapping())
			{
				menu = dailyMenuMapping.getMenu();
				menuList.add(menu);
			}

			dailyMenu.setMenuList(menuList);
		}

		return menuList;
	}

	public DailyMenu addDailyMenuItem(DailyMenu dailyMenu) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		DailyMenu dailyMenuResult = null;
		try
		{
			Query insertDailyMenuQuery = session
					.createSQLQuery("INSERT INTO DAILY_MENU (CATERER_ID, MENU_DATE) VALUES (:catererId, :menuDate)");
			insertDailyMenuQuery.setParameter("catererId", dailyMenu.getCatererId());
			insertDailyMenuQuery.setParameter("menuDate", dailyMenu.getMenuDate());

			insertDailyMenuQuery.executeUpdate();

			Query selectDailyMenuQuery = session.createQuery("SELECT MAX(dailyMenuId) FROM DailyMenu");

			List<Integer> dailyMenuList = selectDailyMenuQuery.list();

			Integer dailyMenuId = null;

			if (dailyMenuList != null && dailyMenuList.size() == 1)
			{
				dailyMenuId = dailyMenuList.get(0);
			}

			if (dailyMenuId != null)
			{
				List<Menu> menuList = dailyMenu.getMenuList();

				Set<DailyMenuMapping> dailyMenuMappings = new HashSet<DailyMenuMapping>();
				for (Menu menu : menuList)
				{
					DailyMenuMapping dailyMenuMapping = new DailyMenuMapping();
					dailyMenuMapping.setMenu(menu);
					dailyMenuMapping.setDailyMenuId(dailyMenuId);

					session.save(dailyMenuMapping);
					dailyMenuMappings.add(dailyMenuMapping);
				}

				dailyMenuResult = new DailyMenu();
				dailyMenuResult.setDailyMenuMapping(dailyMenuMappings);
				dailyMenuResult.setCatererId(dailyMenu.getCatererId());
				dailyMenuResult.setDailyMenuId(dailyMenuId);
				dailyMenuResult.setMenuDate(dailyMenu.getMenuDate());
				dailyMenuResult.setMenuList(menuList);
			}

			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}
		DatabaseUtil.closeSession(session);
		return dailyMenuResult;
	}

	public DailyMenu updateDailyMenu(DailyMenu dailyMenu) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		try
		{
			Integer dailyMenuId = dailyMenu.getDailyMenuId();

			if (dailyMenuId != null)
			{
				Query query = session.createQuery("DELETE FROM DailyMenuMapping WHERE dailyMenuId = :dailyMenuId ");
				query.setParameter("dailyMenuId", dailyMenuId);

				query.executeUpdate();

				List<Menu> menuList = dailyMenu.getMenuList();

				Set<DailyMenuMapping> dailyMenuMappings = new HashSet<DailyMenuMapping>();
				for (Menu menu : menuList)
				{
					DailyMenuMapping dailyMenuMapping = new DailyMenuMapping();
					dailyMenuMapping.setMenu(menu);
					dailyMenuMapping.setDailyMenuId(dailyMenuId);

					session.saveOrUpdate(dailyMenuMapping);
					dailyMenuMappings.add(dailyMenuMapping);
				}

				dailyMenu.setDailyMenuMapping(dailyMenuMappings);
			}

			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return dailyMenu;
	}

	public DailyMenu updateDailyMenuItem(Integer catererId, Date menuDate, List<Menu> menuList) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		DailyMenu dailyMenu = null;
		try
		{

			Query selectQuery = session
					.createQuery("Select dailyMenuId FROM DailyMenu WHERE catererId= :catererId AND menuDate= :menuDate");
			selectQuery.setParameter("catererId", catererId);
			selectQuery.setParameter("menuDate", menuDate);
			List<Integer> dailyMenuList = selectQuery.list();

			Integer dailyMenuId = null;

			if (dailyMenuList != null && dailyMenuList.size() == 1)
			{
				dailyMenuId = dailyMenuList.get(0);
			}

			if (dailyMenuId != null)
			{
				Query query = session.createQuery("DELETE FROM DailyMenuMapping WHERE dailyMenuId = :dailyMenuId ");
				query.setParameter("dailyMenuId", dailyMenuId);

				query.executeUpdate();

				Set<DailyMenuMapping> dailyMenuMappings = new HashSet<DailyMenuMapping>();
				for (Menu menu : menuList)
				{
					DailyMenuMapping dailyMenuMapping = new DailyMenuMapping();
					dailyMenuMapping.setMenu(menu);
					dailyMenuMapping.setDailyMenuId(dailyMenuId);

					session.saveOrUpdate(dailyMenuMapping);
					dailyMenuMappings.add(dailyMenuMapping);
				}

				dailyMenu = new DailyMenu();
				dailyMenu.setCatererId(catererId);
				dailyMenu.setDailyMenuId(dailyMenuId);
				dailyMenu.setDailyMenuMapping(dailyMenuMappings);
				dailyMenu.setMenuDate(menuDate);
				dailyMenu.setMenuList(menuList);
			}

			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return dailyMenu;
	}

	public Boolean appendDailyMenu(DailyMenu dailyMenu) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();
		Boolean result = false;
		try
		{
			Integer dailyMenuId = dailyMenu.getDailyMenuId();

			if (dailyMenuId != null)
			{
				List<Menu> menuList = dailyMenu.getMenuList();

				for (Menu menu : menuList)
				{
					DailyMenuMapping dailyMenuMapping = new DailyMenuMapping();
					dailyMenuMapping.setMenu(menu);
					dailyMenuMapping.setDailyMenuId(dailyMenuId);

					session.saveOrUpdate(dailyMenuMapping);
				}
			}

			tx.commit();
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}
		DatabaseUtil.closeSession(session);

		return result;
	}

	public Boolean deleteDailyMenu(Integer dailyMenuId) throws ObjectNotFoundException, Exception
	{
		boolean result = false;
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();
		try
		{
			DailyMenu dailyMenu = (DailyMenu) session.load(DailyMenu.class, dailyMenuId);
			if (dailyMenu != null)
			{
				session.delete(dailyMenu);
				tx.commit();
				result = true;
			}
		}
		catch (ObjectNotFoundException hib)
		{
			System.out.println("Item does not exist...");
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw hib;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return result;
	}

	public Boolean deleteDailyMenu(Integer catererId, Date menuDate) throws ObjectNotFoundException, Exception
	{
		Boolean result = false;
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();
		try
		{
			Query selectQuery = session
					.createQuery("Select dailyMenuId FROM DailyMenu WHERE catererId= :catererId AND menuDate= :menuDate");
			selectQuery.setParameter("catererId", catererId);
			selectQuery.setParameter("menuDate", menuDate);
			List<Integer> dailyMenuList = selectQuery.list();

			Integer dailyMenuId = null;

			if (dailyMenuList != null && dailyMenuList.size() == 1)
			{
				dailyMenuId = dailyMenuList.get(0);
			}

			DailyMenu dailyMenu = (DailyMenu) session.load(DailyMenu.class, dailyMenuId);
			if (dailyMenu != null)
			{
				session.delete(dailyMenu);
				tx.commit();
				result = true;
			}
		}
		catch (ObjectNotFoundException hib)
		{
			System.out.println("Item does not exist...");
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw hib;
		}
		catch (Exception e)
		{
			result = false;
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return result;
	}

	public Boolean removeDailyMenuItems(Integer dailyMenuId, List<Menu> menuList) throws Exception
	{
		Boolean result = false;
		Session session = null;
		Transaction tx = null;

		if (menuList != null && menuList.size() >= 1)
		{
			try
			{
				session = DatabaseUtil.getSession();
				tx = session.beginTransaction();
				String itemIds = "";
				for (Menu menu : menuList)
				{
					itemIds = itemIds + ", " + menu.getItemId();
				}

				if (itemIds.length() > 1)
				{
					itemIds = itemIds.substring(1);
				}
				System.out.println(dailyMenuId);
				System.out.println(itemIds);
				Query query = session
						.createSQLQuery(" DELETE FROM DAILY_MENU_MAPPING WHERE DAILY_MENU_ID = :dailyMenuId AND ITEM_ID IN ("
								+ itemIds + ")");
				query.setParameter("dailyMenuId", dailyMenuId);

				query.executeUpdate();

				tx.commit();
				result = true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				tx.rollback();
				DatabaseUtil.closeSession(session);
				throw e;
			}
		}

		DatabaseUtil.closeSession(session);
		return result;
	}

	public DailyMenu dailyMenuExists(DailyMenu dailyMenu) throws ObjectNotFoundException, Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();
		DailyMenu dailyMenuDB;

		try
		{
			Query selectQuery = session
					.createQuery("FROM DailyMenu WHERE catererId= :catererId AND menuDate= :menuDate");
			selectQuery.setParameter("catererId", dailyMenu.getCatererId());
			selectQuery.setParameter("menuDate", dailyMenu.getMenuDate());
			List<DailyMenu> dailyMenuList = selectQuery.list();

			if (dailyMenuList != null && dailyMenuList.size() >= 1)
			{
				dailyMenuDB = dailyMenuList.get(0);
			}
			else
			{
				dailyMenuDB = null;
			}
		}
		catch (ObjectNotFoundException hib)
		{
			System.out.println("No Daily Menu Found...");
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw hib;
		}
		catch (Exception e)
		{
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return dailyMenuDB;
	}

	public List<Tag> getTags()
	{
		Session session = DatabaseUtil.getSession();
		List<Tag> tagList = session.createCriteria(Tag.class).list();
		DatabaseUtil.closeSession(session);

		return tagList;
	}

	public Tag getTagById(Integer tagId)
	{
		Session session = DatabaseUtil.getSession();

		Query query = session.createQuery("FROM Tag WHERE tagId= :tagId");
		query.setParameter("tagId", tagId);

		List<Tag> tagList = query.list();
		DatabaseUtil.closeSession(session);

		if (tagList != null && tagList.size() == 1)
		{
			return tagList.get(0);
		}

		System.out.println(tagList);

		return null;
	}

	public Tag addTag(Tag tag) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		try
		{
			session.save(tag);
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return tag;
	}

	public Tag updateTag(Tag tag) throws Exception
	{
		Session session = DatabaseUtil.getSession();
		if (tag != null)
		{
			Transaction tx = session.beginTransaction();
			try
			{
				session.update(tag);
				tx.commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				tx.rollback();
				DatabaseUtil.closeSession(session);
				throw e;
			}

			DatabaseUtil.closeSession(session);
		}

		return tag;
	}

	public Boolean deleteTag(Tag tag) throws ObjectNotFoundException, Exception
	{
		Boolean result = false;
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		try
		{
			Tag tagDB = (Tag) session.load(Tag.class, tag.getTagId());
			if (tagDB != null)
			{
				Criteria criteria = session.createCriteria(MenuTagsMapping.class);
				criteria.add(Restrictions.like("tags", "%" + tag.getTagId() + "%"));
				List<MenuTagsMapping> menuTagsMappingList = criteria.list();

				List<MenuTagsMapping> updateMenuTagsMappings = new ArrayList<MenuTagsMapping>();
				List<MenuTagsMapping> deleteMenuTagsMappings = new ArrayList<MenuTagsMapping>();

				for (MenuTagsMapping menuTagsMapping : menuTagsMappingList)
				{
					String tags = menuTagsMapping.getTags();
					String[] tagArr = tags.split(",");
					boolean isPresent = false;

					for (String tagStr : tagArr)
					{
						Integer tagId = Integer.parseInt(tagStr.trim());

						if (tagId == tag.getTagId())
						{
							isPresent = true;
							break;
						}
					}

					if (isPresent)
					{
						String updatedTagStr = "";

						for (String tagStr : tagArr)
						{
							Integer tagId = Integer.parseInt(tagStr.trim());

							if (tagId != tag.getTagId())
							{
								updatedTagStr = updatedTagStr + "," + tagId;
							}
						}

						if (updatedTagStr.trim().length() > 0)
						{
							updatedTagStr = updatedTagStr.substring(1);

							menuTagsMapping.setTags(updatedTagStr);
							updateMenuTagsMappings.add(menuTagsMapping);
						}
						else
						{
							deleteMenuTagsMappings.add(menuTagsMapping);
						}
					}
				}

				for (MenuTagsMapping menuTagsMapping : updateMenuTagsMappings)
				{
					session.update(menuTagsMapping);
				}

				for (MenuTagsMapping menuTagsMapping : deleteMenuTagsMappings)
				{
					session.delete(menuTagsMapping);
				}

				session.delete(tagDB);
				tx.commit();
				result = true;
			}
		}
		catch (ObjectNotFoundException hib)
		{
			hib.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw hib;
		}
		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return result;
	}

	public MenuTagsMapping addMenuTags(MenuTagsMapping menuTagsMapping) throws HibernateException, Exception
	{
		Integer itemId = menuTagsMapping.getItemId();
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		MenuTagsMapping menuTagsMappingResult = null;

		try
		{
			Menu menuDB = (Menu) session.load(Menu.class, itemId);

			if (menuDB != null)
			{
				Criteria criteria = session.createCriteria(MenuTagsMapping.class);
				criteria.add(Restrictions.eq("menu", menuDB));

				List<MenuTagsMapping> menuTagsMappingList = criteria.list();

				if (menuTagsMappingList != null && menuTagsMappingList.size() == 1)
				{
					MenuTagsMapping menuTagsMappingDB = menuTagsMappingList.get(0);

					String tags = menuTagsMappingDB.getTags();

					if (!tags.equalsIgnoreCase(""))
					{
						tags = tags + "," + menuTagsMapping.getTags();
					}
					else
					{
						tags = menuTagsMapping.getTags();
					}

					menuTagsMappingDB.setTags(tags);

					session.update(menuTagsMappingDB);
					menuTagsMappingResult = menuTagsMappingDB;
				}
				else if (menuTagsMappingList == null || menuTagsMappingList.size() == 0)
				{
					session.saveOrUpdate(menuTagsMapping);
					menuTagsMappingResult = menuTagsMapping;
				}
			}
			else
			{
				System.out.println("Menu Item not found!!");
			}

			tx.commit();
		}
		catch (HibernateException he)
		{
			he.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw he;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return menuTagsMappingResult;
	}

	public MenuTagsMapping updateMenuTags(MenuTagsMapping menuTagsMapping) throws HibernateException, Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();
		MenuTagsMapping menuTagsMappingDB = null;
		try
		{
			menuTagsMappingDB = (MenuTagsMapping) session.get(MenuTagsMapping.class, menuTagsMapping);

			String tagsDB = menuTagsMappingDB.getTags();
			String[] tagArrDB = tagsDB.split(",");

			Set<Integer> tagIds = new HashSet<Integer>();

			for (String tagStrDB : tagArrDB)
			{
				Integer tagId = Integer.parseInt(tagStrDB.trim());

				tagIds.add(tagId);
			}

			String tags = menuTagsMapping.getTags();
			String[] tagArr = tags.split(",");

			for (String tagStr : tagArr)
			{
				Integer tagId = Integer.parseInt(tagStr.trim());

				tagIds.add(tagId);
			}

			String updatedTagStr = "";

			for (Integer tagId : tagIds)
			{
				updatedTagStr = updatedTagStr + "," + tagId;
			}

			if (updatedTagStr.trim().length() > 0)
			{
				updatedTagStr = updatedTagStr.substring(1);
			}

			menuTagsMappingDB.setTags(updatedTagStr);

			session.update(menuTagsMappingDB);
			tx.commit();
		}
		catch (HibernateException he)
		{
			he.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw he;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return menuTagsMappingDB;
	}

	public Boolean deleteMenuTags(MenuTagsMapping menuTagsMapping) throws HibernateException, Exception
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		Boolean result = false;

		try
		{
			MenuTagsMapping menuTagsMappingDB = (MenuTagsMapping) session.get(MenuTagsMapping.class, menuTagsMapping);

			if (menuTagsMappingDB != null)
			{
				session.delete(menuTagsMappingDB);
				result = true;
			}

			tx.commit();
		}
		catch (HibernateException he)
		{
			he.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw he;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return result;
	}
}
