package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.Menu_old;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;
@Path("/menu")
public class MenuResource implements IMenuResource {
	@Context
	UriInfo uriInfo;

	@GET
	@Produces ("application/xml")
	@Override
	public List<Menu> getMenuMaster() {
		// TODO Auto-generated method stub
		List<Menu> menu;
		MenuService menuService= new MenuServiceImpl();
		//GenericEntity<List<Menu>> genMenu;
		//ArrayList<Menu> m= new ArrayList<Menu>();
		menu= menuService.getMenuMaster();
		//genMenu= new GenericEntity<List<Menu>>(menu){};
		//return Response.ok(genMenu).build();

		return menu;
	}

	@Path("/caterer/{catererId}")
	@GET
	@Produces ("application/xml")
	@Override
	public List<Menu> getMenuMasterByCaterer(Integer catererId) {
		// TODO Auto-generated method stub
		List<Menu> menu;
		MenuService menuService= new MenuServiceImpl();
		menu= menuService.getMenuMasterByCaterer(catererId);
		return menu;
	}

	@Path("/demo")
	@GET
	@Produces("application/xml")
	public ArrayList<Menu_old> getMenuList() {
		ArrayList<Menu_old> menus = new ArrayList<Menu_old>();
		Menu_old menu=new Menu_old(1,"Thali","des",100.0f,10);
		menus.add(menu);
		menu=new Menu_old(2,"Pizza","des",20.5f,10);
		menus.add(menu);
		return menus;
	}

	@Path("/add")
	@POST
	@Produces("application/xml")
	@Override
	public void addItemMenuMaster(
			@FormParam("itemname") String itemName,
			@FormParam("desc") String description,
			@FormParam("price") BigDecimal price,
			@FormParam("time") Integer prepTime,
			@FormParam("category") Integer category){
		if(itemName !=null && description !=null && price !=null)// && prepTime !=null && category !=null)
		{
			if(itemName.length()>50)
			{
			System.out.println("Name should not be greater than 50 characters!!");	
			}
			else if(description.length()>250)
			{
				System.out.println("Desc should not be greater than 250 characters!!");
			}
			else
			{
				MenuService menuService= new MenuServiceImpl();
			menuService.addItemMenuMaster(itemName, description, price, prepTime, category);
			}
			
		}
	}

}
