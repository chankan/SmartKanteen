package com.mastek.topcoders.smartkanteen.service;

import com.mastek.topcoders.smartkanteen.bean.MenuUserRatingMapping;

public interface MenuUserRatingMappingService {

	MenuUserRatingMapping addMenuRating(MenuUserRatingMapping murMapping) throws Exception;
	
	MenuUserRatingMapping updateMenuUserRating(Integer itemId, Integer userId, Integer newRating) throws Exception;
}
