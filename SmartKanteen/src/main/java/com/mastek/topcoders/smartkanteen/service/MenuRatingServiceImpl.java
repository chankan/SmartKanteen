package com.mastek.topcoders.smartkanteen.service;

import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.MenuRating;
import com.mastek.topcoders.smartkanteen.dao.MenuRatingDAO;
import com.mastek.topcoders.smartkanteen.dao.OrderDAO;

public class MenuRatingServiceImpl implements MenuRatingService{

	@Override
	public MenuRating getMenuAverageRating(Integer itemId) throws Exception {
		MenuRatingDAO dao = new MenuRatingDAO();
		return dao.getMenuAverageRating(itemId);
	}

	@Override
	public List<MenuRating> getMenuRatings() throws Exception {
		MenuRatingDAO dao = new MenuRatingDAO();
		return dao.getMenuRatings();
	}

	
}
