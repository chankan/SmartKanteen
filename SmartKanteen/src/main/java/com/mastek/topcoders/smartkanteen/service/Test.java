package   com.mastek.topcoders.smartkanteen.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.mastek.topcoders.smartkanteen.bean.Caterer;
import com.mastek.topcoders.smartkanteen.bean.DailyMenu;
import com.mastek.topcoders.smartkanteen.bean.Menu;

public class Test
{
	public static void main(String[] args)
	{
		try
		{
			
			
			//testingMenuTable();

			//testingCatererTable();

		 	//testingDailyMenu();
		      
		/*	boolean result=  testingDeleteCaterer(2);
		    if(result==true)
		    {
		      System.out.println("deleted successfully...");	
		    }
		    else
		    {
		    	System.out.println("deleted successfully...");
		    }*/
			
			List<Menu> menu= testingGetCatererByName("Pav Bhaji");
		    if(menu !=null && menu.size()>0)
		  {
		      	
			  System.out.println("Found..." +menu.size());
		  }
		  else 
		  {
			  System.out.println("Not Found..."); 
		  }
		  
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static List<Menu> testingGetCatererByName(String catererName)
	{
		MenuServiceImpl  service= new MenuServiceImpl();
	    List<Menu> menuList= service.getMenuByName(catererName);
	    return menuList;
	}
	
	public static Boolean  testingDeleteCaterer(Integer catererId)
	{
		MenuServiceImpl service= new MenuServiceImpl();
		boolean result=  service.deleteCaterer(catererId);
	    return result;
	}

	public static void testingDailyMenu()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "9-12-2014 0:0:0";
		Date date=null;
		try
		{
			date = sdf.parse(dateInString);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<DailyMenu> dailyMenuList = service.getDailyMenu(date, 2);
		
		System.out.println(dailyMenuList);
	}

	public static void testingCatererTable()
	{
		/*testDeleteAllCaterers();
		System.out.println("Initial DB");
		displayCaterers();

		testAddCaterer();
		System.out.println("After Adding Caterer");
		displayCaterers();

		testUpdateCaterer();
		System.out.println("After Updating Caterer");
		displayCaterers();*/

		System.out.println("After Getting Caterer by ID");
		testgetCatererById();

		testDeleteCaterer();
		System.out.println("After Deleting Caterer");
		displayCaterers();
	}

	public static void testgetCatererById()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		System.out.println(service.getCaterer(1));
	}

	public static void testAddCaterer()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Caterer caterer = new Caterer();
		caterer.setCatererId(3);
		caterer.setCatererName("Tadka Caterer");

		Caterer caterer1 = new Caterer();
		caterer1.setCatererId(4);
		caterer1.setCatererName("Nitya Caterer");

		service.addCaterer(caterer);
		service.addCaterer(caterer1);
	}

	public static void testUpdateCaterer()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Caterer caterer = new Caterer();
		caterer.setCatererId(1);
		caterer.setCatererName("Royal Kings");

		service.updateCaterer(caterer.getCatererId(), caterer.getCatererName());
	}

	public static void testDeleteCaterer()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		service.deleteCaterer(4);
	}

	public static void displayCaterers()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		System.out.println(service.getCaterers());
	}

	public static void testDeleteAllCaterers()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		service.deleteAllCaterers();
	}

	public static void testingMenuTable()
	{
		testDeleteAllItems();
		System.out.println("Initial DB");
		displayMenuItems();

		testAddMenu();
		System.out.println("After Adding Items");
		displayMenuItems();

		testAddMenuWithCaterer();
		System.out.println("After Adding Menu Items With Caterer");
		displayMenuItems();

		testUpdateMenuItems();
		System.out.println("After Updating Items");
		displayMenuItems();

		testDeleteItems();
		System.out.println("After Deleting Items");
		displayMenuItems();
	}

	public static void testAddMenuWithCaterer()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menuMaster = new Menu();
		menuMaster.setItemId(11);
		menuMaster.setItemName("Fruit Custord");
		menuMaster.setDescription("Dessert");
		menuMaster.setPrepTime(15);
		menuMaster.setPrice(new BigDecimal(65));

		Menu menuMaster1 = new Menu();
		menuMaster1.setItemId(12);
		menuMaster1.setItemName("Falooda");
		menuMaster1.setDescription("Dessert");
		menuMaster1.setPrepTime(5);
		menuMaster1.setPrice(new BigDecimal(20));

		Caterer caterer = new Caterer();
		caterer.setCatererId(1);

		Caterer caterer1 = new Caterer();
		caterer1.setCatererId(2);

		service.addItemInMenuMaster(menuMaster, caterer);
		service.addItemInMenuMaster(menuMaster1, caterer1);

	}

	public static void testDeleteItems()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		service.deleteItemFromMenuMaster(1);
		service.deleteItemFromMenuMaster(3);
	}

	public static void testAddMenu()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		Menu menuMaster = new Menu();
		menuMaster.setItemId(1);
		menuMaster.setItemName("Medu Wada");
		menuMaster.setDescription("South Indian");
		menuMaster.setPrepTime(5);
		menuMaster.setPrice(new BigDecimal(40));
		service.addItemInMenuMaster(menuMaster);

		Menu menuMaster1 = new Menu();
		menuMaster1.setItemId(2);
		menuMaster1.setItemName("Chole Bhature");
		menuMaster1.setDescription("Punjabi");
		menuMaster1.setPrepTime(15);
		menuMaster1.setPrice(new BigDecimal(40));
		service.addItemInMenuMaster(menuMaster1);

		Menu menuMaster2 = new Menu();
		menuMaster2.setItemId(3);
		menuMaster2.setItemName("Dum Briyani");
		menuMaster2.setDescription("Mughlai");
		menuMaster2.setPrepTime(25);
		menuMaster2.setPrice(new BigDecimal(40));
		service.addItemInMenuMaster(menuMaster2);

		Menu menuMaster3 = new Menu();
		menuMaster3.setItemId(4);
		menuMaster3.setItemName("Palak Paneer");
		menuMaster3.setDescription("North Indian");
		menuMaster3.setPrepTime(10);
		menuMaster3.setPrice(new BigDecimal(40));
		service.addItemInMenuMaster(menuMaster3);

		Menu menuMaster4 = new Menu();
		menuMaster4.setItemId(5);
		menuMaster4.setItemName("Thali");
		menuMaster4.setDescription("Malwani");
		menuMaster4.setPrepTime(20);
		menuMaster4.setPrice(new BigDecimal(40));
		service.addItemInMenuMaster(menuMaster4);

	}

	public static void testDeleteAllItems()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		service.deleteAllItemsFromMenuMaster();
	}

	public static void testUpdateMenuItems()
	{
		MenuServiceImpl service = new MenuServiceImpl();

		service.updateItemInMenuMaster(4, "Five Rice", "Chinese", null, null);
		service.updateItemInMenuMaster(1, null, null, new BigDecimal(25), null);
	}

	public static void displayMenuItems()
	{
		MenuServiceImpl service = new MenuServiceImpl();
		System.out.println(service.getMenuMaster());
	}
}
