package com.mastek.topcoders.smartkanteen.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.mastek.topcoders.smartkanteen.bean.MenuRating;
import com.mastek.topcoders.smartkanteen.bean.MenuUserRatingMapping;
import com.mastek.topcoders.smartkanteen.bean.OrderMaster;
import com.mastek.topcoders.smartkanteen.bean.User;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.common.util.OrderStatus;

public class MenuRatingDAO {

	public MenuUserRatingMapping addMenuRating(MenuUserRatingMapping murMapping) {
		
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.save(murMapping);
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
		return murMapping;
	}

	
	
	public MenuUserRatingMapping updateMenuUserRating(Integer itemId,
			Integer userId, Integer newRating) {
		
		Session session = DatabaseUtil.getSession();
		Transaction tx = null;

		MenuUserRatingMapping murMapping = getMenuItemById(itemId);
		if (murMapping != null)
		{
			try
			{
				tx = session.beginTransaction();
				murMapping.setRating(newRating);

				session.update(murMapping);
				tx.commit();
			}
			catch (Exception e)
			{
				tx.rollback();
				e.printStackTrace();
				DatabaseUtil.closeSession(session);
				throw e;
			}
		}

		DatabaseUtil.closeSession(session);
		return murMapping;
	}

	
	public MenuRating getMenuAverageRating(Integer itemId) {
		
		Session session = DatabaseUtil.getSession();
		MenuRating menuRating = (MenuRating) session.get(MenuRating.class, itemId);
		DatabaseUtil.closeSession(session);
		return menuRating;
		
	}

	
	public List<MenuRating> getMenuRatings() {
		
		List<MenuRating> menuRatingList = null;
		Session session = DatabaseUtil.getSession();

		try
		{
			Criteria criteria = session.createCriteria(MenuRating.class);
			menuRatingList = criteria.list();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			DatabaseUtil.closeSession(session);
			throw e;
		}

		DatabaseUtil.closeSession(session);
		return menuRatingList;
	}
	
	
	public MenuUserRatingMapping getMenuItemById(Integer itemId)
	{
		Session session = DatabaseUtil.getSession();
		MenuUserRatingMapping murMapping = (MenuUserRatingMapping) session.get(MenuUserRatingMapping.class, itemId);
		DatabaseUtil.closeSession(session);
		return murMapping;
	}

	
}
