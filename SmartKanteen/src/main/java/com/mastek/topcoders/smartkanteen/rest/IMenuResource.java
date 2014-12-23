package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;


public interface IMenuResource {
	
	/*Methods related to caterer*/
	
		List<Caterer> getCaterers();
		
		Caterer getCaterer(Integer catererId);
		
		Response addCaterer(Caterer caterer);
		
		Response updateCaterer(Integer catererId, String catererName);
		
		Response deleteCaterer(Integer catererId);
		
		/*Methods Ends*/
		

	/*Methods related to Master Menu*/
			
	List<Menu> getMenuMasterByCaterer(Integer catererId);
	
	Response addUpdateItemInMenuMaster(Menu menuMaster, int catererId);
		
	Response deleteItemFromMenuMaster(Integer itemId);
	
	/*Methods Ends*/
	

	
	/*Methods related to DailyMenu*/
	
	List<Menu> getDailyMenu(DateParam menuDate, Integer catererId);
	
	void addDailyMenu(List<Menu> menu,Integer catererId, DateParam menuDate);
	
	void updateDailyMenu(List<Menu> menuList,Integer catererId, DateParam menuDate);
	
	Response deleteDailyMenu(Integer catererId, DateParam menuDate);

	/*Method Ends*/

}
