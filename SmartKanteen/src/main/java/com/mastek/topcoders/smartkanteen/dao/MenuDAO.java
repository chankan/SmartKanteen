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
		List<Menu> menuMasterList = session.createQuery(" FROM Menu ").list();
		DatabaseUtil.closeSession(session);

		return menuMasterList;
	}

	public List<Menu> getMenuMaster(Integer catererId)
	{
		Session session = DatabaseUtil.getSession();
		Query query = session.createQuery("FROM Caterer WHERE catererId= :catererID");
		query.setParameter("catererID", catererId);
		List<Caterer> catererList = query.list();

		List<Menu> menuList = null;

		if (catererList != null && catererList.size() == 1)
		{
			Caterer caterer = catererList.get(0);

			menuList = new ArrayList<Menu>();

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

	public Integer addItem(Menu menuMaster)
	{
		Integer result = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();
		try
		{
			result = (Integer) session.save(menuMaster);

			menuMaster.getMenuTagsMapping().setMenu(menuMaster);

			result = (Integer) session.save(menuMaster.getMenuTagsMapping());
			tx.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tx.rollback();
		}
		DatabaseUtil.closeSession(session);

		return result;
	}

	public void addItemInMenuMaster(Menu menuMaster, Caterer caterer)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		if (menuMaster != null && caterer != null)
		{
			try
			{
				Integer itemId = addItem(menuMaster);

				System.out.println(menuMaster);
				menuMaster.setItemId(itemId);

				CatererMenuMapping catererMenuMapping = new CatererMenuMapping();
				catererMenuMapping.setCatererId(caterer.getCatererId());
				catererMenuMapping.setMenu(menuMaster);

				session.save(catererMenuMapping);
				tx.commit();
			}
			catch (Exception e)
			{
				tx.rollback();
			}
		}
		DatabaseUtil.closeSession(session);
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

	public void updateItem(Menu menuMaster)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();
		try
		{
			MenuTagsMapping menuTagsMapping = menuMaster.getMenuTagsMapping();

			session.update(menuMaster);

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
					menuTagsMapping.setMenu(menuMaster);
					session.saveOrUpdate(menuTagsMapping);
				}
			}

			tx.commit();
			DatabaseUtil.closeSession(session);
		}
		catch (Exception e)
		{
			tx.rollback();
		}
	}

	public Boolean deleteItem(Integer itemId)
	{
		boolean result = true;
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();

		try
		{
			Menu menuMaster = new Menu();
			menuMaster.setItemId(itemId);

			MenuTagsMapping menuTagsMapping = new MenuTagsMapping();
			menuTagsMapping.setMenu(menuMaster);

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
		catch (Exception e)
		{
			result = false;
			tx.rollback();
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
		Query query = session.createQuery("FROM Caterer");
		List<Caterer> catererList = query.list();
		DatabaseUtil.closeSession(session);
		return catererList;
	}

	public Integer addCaterer(Caterer caterer)
	{
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();
		Integer result = 0;
		try
		{
			result = (Integer) session.save(caterer);
			tx.commit();
		}
		catch (Exception e)
		{
			tx.rollback();
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public boolean updateCaterer(Caterer caterer)
	{
		boolean result = false;
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
				tx.rollback();
			}
			DatabaseUtil.closeSession(session);
			result = true;
		}
		return result;
	}

	public boolean deleteCaterer(Integer catererId)
	{
		boolean result = false;
		Session session = DatabaseUtil.getSession();
		Caterer caterer = (Caterer) session.load(Caterer.class, catererId);
		if (caterer != null)
		{
			Transaction tx = session.beginTransaction();
			try
			{
				session.delete(caterer);
				tx.commit();
			}
			catch (ObjectNotFoundException hib)
			{
				System.out.println("Caterer does not exist...");
				tx.rollback();
			}
			catch (Exception e)
			{
				tx.rollback();
			}
			DatabaseUtil.closeSession(session);
			result = true;
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

	public void addDailyMenuItem(DailyMenu dailyMenu)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();
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

				for (Menu menu : menuList)
				{
					DailyMenuMapping dailyMenuMapping = new DailyMenuMapping();
					dailyMenuMapping.setMenu(menu);
					dailyMenuMapping.setDailyMenuId(dailyMenuId);

					session.save(dailyMenuMapping);
				}
			}

			tx.commit();
		}
		catch (Exception e)
		{
			tx.rollback();
		}
		DatabaseUtil.closeSession(session);
	}

	public void updateDailyMenu(DailyMenu dailyMenu)
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

				for (Menu menu : menuList)
				{
					DailyMenuMapping dailyMenuMapping = new DailyMenuMapping();
					dailyMenuMapping.setMenu(menu);
					dailyMenuMapping.setDailyMenuId(dailyMenuId);

					session.saveOrUpdate(dailyMenuMapping);
				}
			}

			tx.commit();
		}
		catch (Exception e)
		{
			tx.rollback();
		}

		DatabaseUtil.closeSession(session);
	}

	public void appendDailyMenu(DailyMenu dailyMenu)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();
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
		}
		catch (Exception e)
		{
			tx.rollback();
		}
		DatabaseUtil.closeSession(session);
	}

	public Boolean deleteDailyMenu(Integer dailyMenuId)
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
		}
		catch (Exception e)
		{
			result = false;
			tx.rollback();
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public Boolean removeDailyMenuItems(Integer dailyMenuId, List<Menu> menuList)
	{
		boolean result = false;
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

			}
			catch (Exception e)
			{
				tx.rollback();
			}
			result = true;
		}
		DatabaseUtil.closeSession(session);
		return result;
	}

	public List<Tag> getTags()
	{
		Session session = DatabaseUtil.getSession();
		List<Tag> tagList = session.createQuery(" FROM Tag ").list();
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

		return null;
	}

	public Integer addTag(Tag tag)
	{
		Integer result = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tx = session.beginTransaction();
		try
		{
			result = (Integer) session.save(tag);
			tx.commit();
		}
		catch (Exception e)
		{
			tx.rollback();
		}
		DatabaseUtil.closeSession(session);

		return result;
	}

	public void updateTag(Tag tag)
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
				tx.rollback();
			}
			DatabaseUtil.closeSession(session);
		}
	}

	public void deleteTag(Tag tag)
	{
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
			}
		}
		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();
		}

		DatabaseUtil.closeSession(session);
	}

	public void addMenuTags(MenuTagsMapping menuTagsMapping)
	{
		Menu menu = menuTagsMapping.getMenu();

		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		try
		{
			Menu menuDB = (Menu) session.load(Menu.class, menu.getItemId());

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
				}
				else if (menuTagsMappingList == null || menuTagsMappingList.size() == 0)
				{
					session.saveOrUpdate(menuTagsMapping);
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
			tx.rollback();
		}

		DatabaseUtil.closeSession(session);
	}

	public void updateMenuTags(MenuTagsMapping menuTagsMapping)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		try
		{
			MenuTagsMapping menuTagsMappingDB = (MenuTagsMapping) session.get(MenuTagsMapping.class, menuTagsMapping);

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
			tx.rollback();
		}

		DatabaseUtil.closeSession(session);
	}

	public void deleteMenuTags(MenuTagsMapping menuTagsMapping)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		try
		{
			MenuTagsMapping menuTagsMappingDB = (MenuTagsMapping) session.get(MenuTagsMapping.class, menuTagsMapping);

			if (menuTagsMappingDB != null)
			{
				session.delete(menuTagsMappingDB);
			}

			tx.commit();
		}
		catch (HibernateException he)
		{
			tx.rollback();
		}

		DatabaseUtil.closeSession(session);
	}
}
