package com.mastek.topcoders.smartkanteen.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.dao.DatabaseController;

@Path("/service")
public class MenuService {

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public ArrayList<Menu> getMenuList() {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		Menu menu=new Menu(1,"Thali","des",10.5f,10);
		menus.add(menu);
		menu=new Menu(2,"Pizza","des",20.5f,10);
		menus.add(menu);
		DatabaseController.createConnection();
		menus=DatabaseController.getMenu("SELECT itemID ,itemName  ,description , price  ,prepTime  FROM MENU_MASTER");
//		Menu[] mmm={new Menu(1,"Thali","des",10.5f,10),new Menu(2,"Pizza","des",10.5f,10)};
		
//		return  menus.toArray(Menu[] a);
		return menus;
	}
	
	@Path("/menu")
	@Produces({MediaType.APPLICATION_JSON})
	public Menu getMenu() {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		Menu menu=new Menu(1,"Thali","des",10.5f,10);
		menus.add(menu);
		menu=new Menu(2,"Pizza","des",20.5f,10);
		menus.add(menu);
//		Menu[] mmm={new Menu(1,"Thali","des",10.5f,10),new Menu(2,"Pizza","des",10.5f,10)};
		
//		return  menus.toArray(Menu[] a);
		return menu;
	}

	
	public String addMenu(Menu newMenu) {
		String result = "ERROR";
		return result;
	}


	public String updateMenu() {
		String result = "ERROR";
		return result;
	}

}
