package com.mastek.topcoders.smartkanteen.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.DailyMenuMapping;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;

public class MenuDAO
{
	public List<Menu> getMenuMaster()
	{
		Session session = DatabaseUtil.getSession();
		List<Menu> menuMasterList = (List<Menu>) session.createQuery("FROM Menu").list();
		DatabaseUtil.closeSession(session);

		return menuMasterList;
	}

	public List<Menu> getMenuMaster(Integer catererId)
	{
		return null;
	}

	public List<Menu> getMenuByName(String itemName)
	{
		Session session = DatabaseUtil.getSession();
		Criteria criteria = session.createCriteria(Menu.class);
		criteria.add(Restrictions.like("itemName", "%" + itemName + "%"));
		List<Menu> menuList = criteria.list();
		return menuList;
	}

	public Menu getItem(Integer itemId)
	{
		Session session = DatabaseUtil.getSession();

		Query query = session.createQuery("FROM Menu WHERE itemId= :itemId");
		query.setParameter("itemId", itemId);

		List<Menu> menuMasterList = (List<Menu>) query.list();
		DatabaseUtil.closeSession(session);

		if (menuMasterList != null && menuMasterList.size() == 1)
		{
			return menuMasterList.get(0);
		}

		return null;
	}

	public Integer addItem(Menu menuMaster)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();
		Integer result = (Integer) session.save(menuMaster);
		tx.commit();

		DatabaseUtil.closeSession(session);
		return result;
	}

	public void deleteItem(Integer itemId)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		Menu menuMaster = new Menu();
		menuMaster.setItemId(itemId);

		session.delete(menuMaster);
		tx.commit();
		DatabaseUtil.closeSession(session);
	}

	public void deleteAllItemsFromMenuMaster()
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		session.createQuery("DELETE FROM Menu").executeUpdate();
		tx.commit();
		DatabaseUtil.closeSession(session);
	}

	public void updateItem(Menu menuMaster)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		session.update(menuMaster);
		tx.commit();
		DatabaseUtil.closeSession(session);
	}

	public void addItemInMenuMaster(Menu menuMaster, Caterer caterer)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		Integer menuId = (Integer) addItem(menuMaster);

		String sql = "INSERT INTO CATERER_MENU_MAPPING VALUES (:menuId, :catererId)";
		Query query = session.createSQLQuery(sql);
		query.setParameter("catererId", caterer.getCatererId());
		query.setParameter("menuId", menuId);

		System.out.println("menuId" + menuId);
		int result = query.executeUpdate();
		session.update(menuMaster);
		tx.commit();
		DatabaseUtil.closeSession(session);
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
		Integer result = (Integer) session.save(caterer);
		tx.commit();
		DatabaseUtil.closeSession(session);
		return result;
	}

	public boolean updateCaterer(Caterer caterer)
	{

		Session session = DatabaseUtil.getSession();
		if (caterer != null)
		{
			Transaction tx = session.beginTransaction();
			session.update(caterer);
			tx.commit();
			session.close();
			return true;
		}
		return false;
	}

	public boolean deleteCaterer(Integer catererId)
	{
		Session session = DatabaseUtil.getSession();
		Caterer caterer = (Caterer) session.load(Caterer.class, catererId);
		if (caterer != null)
		{
			Transaction tx = session.beginTransaction();
			session.delete(caterer);
			tx.commit();
			session.close();
			return true;
		}
		return false;
	}

	public void deleteAllCaterers()
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		session.createQuery("DELETE FROM Caterer").executeUpdate();
		tx.commit();

		DatabaseUtil.closeSession(session);
	}

	public List<DailyMenu> getDailyMenu(Date menuDate, Integer catererId)
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

		List<DailyMenu> dailyMenuList = (List<DailyMenu>) query.list();
		DatabaseUtil.closeSession(session);

		for (DailyMenu dailyMenu : dailyMenuList)
		{
			List<Menu> menuList = new ArrayList<Menu>();

			for (DailyMenuMapping dailyMenuMapping : dailyMenu.getDailyMenuMapping())
			{
				Menu menu = dailyMenuMapping.getMenu();
				menuList.add(menu);
			}

			dailyMenu.setMenuList(menuList);
		}

		return dailyMenuList;
	}

	public void addDailyMenuItem(DailyMenu dailyMenu)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

		Query insertDailyMenuQuery = session
				.createSQLQuery("INSERT INTO DAILY_MENU (CATERER_ID, MENU_DATE) VALUES (:catererId, :menuDate)");
		insertDailyMenuQuery.setParameter("catererId", dailyMenu.getCatererId());
		insertDailyMenuQuery.setParameter("menuDate", dailyMenu.getMenuDate());

		insertDailyMenuQuery.executeUpdate();

		Query selectDailyMenuQuery = session.createQuery("SELECT MAX(dailyMenuId) FROM DailyMenu");

		List<Integer> dailyMenuList = (List<Integer>) selectDailyMenuQuery.list();

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

		DatabaseUtil.closeSession(session);
	}

	public void updateDailyMenu(DailyMenu dailyMenu)
	{
		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

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

		DatabaseUtil.closeSession(session);

	}

	public void appendDailyMenu(DailyMenu dailyMenu)
	{

		Session session = DatabaseUtil.getSession();

		Transaction tx = session.beginTransaction();

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

		DatabaseUtil.closeSession(session);

	}
}
