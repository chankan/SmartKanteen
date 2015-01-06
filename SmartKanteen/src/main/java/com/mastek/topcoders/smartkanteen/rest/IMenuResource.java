package com.mastek.topcoders.smartkanteen.rest;

import java.util.List;

import javax.ws.rs.core.Response;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.Tag;

public interface IMenuResource
{
	/*Methods related to caterer*/

	List<Caterer> getCaterers(String userSession) throws Exception;

	Caterer getCaterer(String userSession, Integer catererId) throws Exception;

	Caterer addCaterer(String userSession, Caterer caterer) throws Exception;

	Caterer updateCaterer(String userSession, Integer catererId, Caterer caterer) throws Exception;

	Response deleteCaterer(String userSession, Integer catererId) throws Exception;

	
	/*Methods related to Master Menu*/

	List<Menu> getMenuMasterByCaterer(String userSession, Integer catererId) throws Exception;

	Menu addUpdateItemInMenuMaster(String userSession, Menu menuMaster, Integer catererId) throws Exception;

	Response deleteItemFromMenuMaster(String userSession, Integer itemId) throws Exception;

	
	/*Methods related to DailyMenu*/

	List<Menu> getDailyMenu(String userSession, DateParam menuDate, Integer catererId) throws Exception;

	DailyMenu addDailyMenu(String userSession, List<Menu> menu, Integer catererId, DateParam menuDate) throws Exception;

	DailyMenu updateDailyMenu(String userSession, List<Menu> menuList, Integer catererId, DateParam menuDate)
			throws Exception;

	Response deleteDailyMenu(String userSession, Integer catererId, DateParam menuDate) throws Exception;


	/*Methods related to tags */
	List<Tag> getTags(String userSession) throws Exception;

	Tag addTag(String userSession, Tag tag) throws Exception;

	Tag updateTag(String userSession, Tag tag) throws Exception;

	Response deleteTag(String userSession, Tag tag) throws Exception;
}
