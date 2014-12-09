package com.mastek.topcoders.smartkanteen.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.bean.Menu_old;



public interface IMenuResource {
	@GET
	@Produces ("application/xml")
	public  List<Menu> getMenuMaster ();
	
	@Path("/caterer/{catererId}")
	@GET
	@Produces ("application/xml")
	public List<Menu> getMenuMasterByCaterer (@PathParam("catererId") Integer catererId);
	
	@Path("/demo")
	@GET
	@Produces("application/xml")
	public ArrayList<Menu_old> getMenuList();
	
	@POST
	@Produces("application/xml")
	public void addItemMenuMaster(String itemName, String description,
			BigDecimal price, Integer prepTime,Integer category);
	

}
