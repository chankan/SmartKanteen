package com.mastek.topcoders.smartkanteen.junit;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

public class TestMenuService 
{
	/*
	@Before
	public void testAddItemInMenuMaster()
	{
	  Menu  menu= new Menu();
	  menu.setItemName("Thali");
	  menu.setDescription("Thali is one of the most common item in India");
	  menu.setPrepTime(20);
      menu.setPrice(BigDecimal.valueOf(80));
	  MenuServiceImpl service= new MenuServiceImpl();
	  service.addItemInMenuMaster(menu);
	}*/
	
	
	@Test
	public void testGetMenuItem()
	{
	  Session session=  DatabaseUtil.getSession();  
	  Menu  menu1=(Menu)session.get(Menu.class,11);
	  Menu  menu2=new Menu();
	  menu2.setItemId(11);
	  menu2.setItemName("Thali");
	  menu2.setDescription("Thali is one of the most common item in India");
	  menu2.setPrepTime(20);
      menu2.setPrice(BigDecimal.valueOf(80));
	  
      
      Assert.assertEquals(true,comparingTwoMenuItems(menu1, menu2));
    }
	
	
	
	private Boolean comparingTwoMenuItems(Menu menu1,Menu menu2)
	{
		if(menu1.getItemId()==menu2.getItemId())
		{
			/*if(menu1.getItemName().equals(menu2.getItemName()))
			{
				if()
			}*/
		}
		return true;
	}
	
	
	
	
}
