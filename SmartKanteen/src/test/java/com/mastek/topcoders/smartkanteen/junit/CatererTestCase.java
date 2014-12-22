package com.mastek.topcoders.smartkanteen.junit;

import java.util.List;
import org.junit.Test; 
import org.hibernate.Session;
import org.junit.Assert;
import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.common.util.DatabaseUtil;
import com.mastek.topcoders.smartkanteen.service.MenuService;
import com.mastek.topcoders.smartkanteen.service.MenuServiceImpl;

public class CatererTestCase  
{
	

	@Test
	public void testAddCaterer()
	{
	  Session session=DatabaseUtil.getSession();	
	   Caterer caterer1= new Caterer();
	   MenuService service=new  MenuServiceImpl();
	   Integer result =  service.addCaterer(caterer1);
	   Caterer caterer2 =(Caterer) session.get(Caterer.class, result);   
	   Assert.assertEquals(caterer1.getCatererName(),caterer2.getCatererName());
	}

	@Test
	public  void testUpdateCaterer()
	{
		Session session=DatabaseUtil.getSession();
		
		Caterer caterer1= new Caterer();
		caterer1.setCatererId(3);
		caterer1.setCatererName("Rahul Caterer");
	    
		MenuServiceImpl service= new MenuServiceImpl();
	    boolean result2= service.updateCaterer(caterer1.getCatererId(), caterer1.getCatererName());
	    
	    Caterer caterer2=(Caterer) session.get(Caterer.class,3);
		
	    Assert.assertEquals(caterer1.getCatererName(),caterer2.getCatererName());	   
	}

	
	@Test
	public void testDeleteCaterer()
	{
	  boolean result1=false;	
	  
      MenuService   service= new MenuServiceImpl();
      boolean result2 = service.deleteCaterer(1);
	  Caterer caterer= (Caterer) service.getCaterer(1);
      
      if(caterer==null) 
      {
    	  result1=true;
      }
	
	 Assert.assertEquals(result2, result1);
	}
	
	
	
	@Test
	public void testGetCaterers()
	{
		MenuService service= new MenuServiceImpl();
		List<Caterer> catererList= service.getCaterers();
       Assert.assertEquals(4,catererList.size());   
		
	}
		
			 
}
