package com.mastek.topcoders.smartkanteen.service;

import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.MenuRating;
import com.mastek.topcoders.smartkanteen.bean.MenuRating;
import com.mastek.topcoders.smartkanteen.common.util.OrderStatus;

public interface MenuRatingService {
	
	
	MenuRating getMenuAverageRating(Integer itemId) throws Exception;
	
	
	List<MenuRating> getMenuRatings() throws Exception;

}
