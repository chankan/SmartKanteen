package com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;
import com.mastek.topcoders.smartkanteen.dao.MenuDAO;

public class MenuServiceImpl implements MenuService
{
	public List<Menu> getMenuMaster()
	{
		MenuDAO dao = new MenuDAO();
		return dao.getMenuMaster();
	}

	public List<Menu> getMenuMasterByCaterer(Integer catererId)
	{
		MenuDAO dao = new MenuDAO();
		return dao.getMenuMaster(catererId);
	}

	public Integer addItemInMenuMaster(Menu menuMaster)
	{
		MenuDAO dao = new MenuDAO();
		return dao.addItem(menuMaster);
	}

	public void updateItemInMenuMaster(Integer itemId, String itemName, String description, BigDecimal price,
			Integer prepTime)
	{
		if (itemId == null || itemId == 0)
		{
			System.out.println("Item not found");
		}
		
		MenuDAO dao = new MenuDAO();
		
		Menu menuMaster = dao.getItem(itemId);
		
		if(itemName!=null)
		{
			menuMaster.setItemName(itemName);
		}
		
		if(description!=null)
		{
			menuMaster.setDescription(description);
		}
		
		if(price!=null)
		{
			menuMaster.setPrice(price);
		}
		
		if(prepTime!=null)
		{
			menuMaster.setPrepTime(prepTime);
		}
		
		
		dao.updateItem(menuMaster);
	}

	public void deleteItemFromMenuMaster(Integer itemId)
	{
		MenuDAO dao = new MenuDAO();
		dao.deleteItem(itemId);
	}

	public void deleteAllItems()
	{
		MenuDAO dao = new MenuDAO();
		dao.deleteAllItems();
	}

	public Integer addItemInMenuMaster(Menu menuMaster, Caterer caterer)
	{
		MenuDAO dao = new MenuDAO();
		dao.addItemInMenuMaster(menuMaster, caterer);
		return null;
	}

	public Caterer getCaterer(Integer catererId)
	{
		 MenuDAO dao=new MenuDAO();
		  Caterer caterer = dao.getCaterer(catererId);
		 return caterer;
	}

	public List<Caterer> getCaterers()
	{
	  MenuDAO  dao=new MenuDAO();
	  List<Caterer> catererList=  dao.getCaterers();
      return catererList;
	}
	
	public  Integer addCaterer(Caterer caterer) {
     MenuDAO dao=new MenuDAO();
     Integer result = dao.addCaterer(caterer);
	 return result;
	}
	
	public  boolean updateCaterer(Integer catererId,String catererName) {
    
		boolean result=false;
		
		if(catererId==0 && catererId==null)
		{
			System.out.println("CatererId not found");
		}
		 MenuDAO dao=new MenuDAO();
	     Caterer caterer = dao.getCaterer(catererId); 
		 
		if(caterer!=null)
		{
				if(catererName!=null)
				{
					caterer.setCatererName(catererName);
				} 		
		   result =  dao.updateCaterer(caterer);
		}
	return result;	
	}
	
	public  boolean deleteCaterer(Integer catererId) {
     
		MenuDAO dao=new MenuDAO();
        boolean result= dao.deleteCaterer(catererId);
	    return result;
	}

	public DailyMenu getDailyMenu(Date menuDate, Integer catererId)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/* Added by @vaibhav
	 * testing Post Operation on RS
	 */
	@Override
	public void addItemMenuMaster(String itemName, String description,
			BigDecimal price, Integer prepTime,Integer category) {
		// TODO Auto-generated method stub
		MenuDAO dao = new MenuDAO();
		//dao.addItemInMenuMaster(itemName,  description,
			//	 price, prepTime,category);
		
		//MenuDAO dao = new MenuDAO();
		
		//Menu menuMaster = dao.getItem(itemId);
		
		Menu menuMaster =new Menu();
		
		if(itemName!=null)
		{
			menuMaster.setItemName(itemName);
		}
		
		if(description!=null)
		{
			menuMaster.setDescription(description);
		}
		
		if(price!=null)
		{
			menuMaster.setPrice(price);
		}
		
		if(prepTime!=null)
		{
			menuMaster.setPrepTime(prepTime);
		}
		
		if(category!=null)
		{
			//menuMaster.se(prepTime);
		}
		
		dao.addItem(menuMaster);
	}
	
	public static void main(String[] args)
	{
		try
		{
			MenuServiceImpl service = new MenuServiceImpl();

			System.out.println("Menu List");

			//service.deleteAllItems();
			System.out.println(service.getMenuMaster());

			/*Menu menuMaster = new Menu();
			menuMaster.setItemId(26);
			menuMaster.setItemName("Thai Rice");
			menuMaster.setDescription("Chinese preparation");
			menuMaster.setPrepTime(5);
			menuMaster.setPrice(new BigDecimal(40));
			service.addItemInMenuMaster(menuMaster);

			System.out.println("After Adding Item");
			System.out.println(service.getMenuMaster());

			service.updateItemInMenuMaster(26, "Five Rice", null, null, null);
			
			System.out.println("After Updating Item");
			System.out.println(service.getMenuMaster());
			
			service.deleteItemFromMenuMaster(26);

			System.out.println("After Deleting Item");
			System.out.println(service.getMenuMaster());*/

			// System.out.println(service.getMenuMasterByCaterer(1));

		}
		catch (HibernateException e)
		{
			e.printStackTrace();
		}
	}

	
	
}
