package com.mastek.topcoders.smartkanteen.service;

import com.mastek.topcoders.smartkanteen.bean.MenuUserRatingMapping;
import com.mastek.topcoders.smartkanteen.dao.MenuRatingDAO;
import com.mastek.topcoders.smartkanteen.dao.OrderDAO;

public class MenuUserRatingMappingServiceImpl implements MenuUserRatingMappingService{

	@Override
	public MenuUserRatingMapping addMenuRating(MenuUserRatingMapping murMapping) throws Exception {
		MenuRatingDAO dao = new MenuRatingDAO();
		return dao.addMenuRating(murMapping);
	}

	@Override
	public MenuUserRatingMapping updateMenuUserRating(Integer itemId,
			Integer userId, Integer newRating) throws Exception {
		MenuRatingDAO dao = new MenuRatingDAO();
		return dao.updateMenuUserRating(itemId,userId, newRating);
	}

}
