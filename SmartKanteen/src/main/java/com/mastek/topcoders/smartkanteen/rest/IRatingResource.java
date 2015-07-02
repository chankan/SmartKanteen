package com.mastek.topcoders.smartkanteen.rest;

import java.util.List;
import com.mastek.topcoders.smartkanteen.bean.MenuRating;
import com.mastek.topcoders.smartkanteen.bean.MenuUserRatingMapping;

public interface IRatingResource
{
	
	//MenuUserRatingMapping addMenuRating(String userSession, Integer itemId, Integer userID, Integer rating) throws Exception;
	
	MenuUserRatingMapping addMenuRating(String userSession, MenuUserRatingMapping murMapping) throws Exception;
	
	MenuUserRatingMapping updateMenuUserRating(String userSession, Integer itemId, Integer userId, Integer newRating) throws Exception;
	
	MenuRating getMenuAverageRating(String userSession, Integer itemId) throws Exception;
	
	List<MenuRating> getMenuRatings(String userSession) throws Exception;
	
}
