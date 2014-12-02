package com.mastek.topcoders.smartkanteen.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mastek.topcoders.smartkanteen.bean.ItemDVO;
import com.mastek.topcoders.smartkanteen.bean.Menu;

public class DatabaseController {

	// JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/db/SmartKanteen";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";
    
    private static Connection conn = null;
    
    public static Connection createConnection(){
       try {
	    	   if(conn!=null){
	    		   return conn;
	    	   }else{
	               //STEP 1: Register JDBC driver
	               Class.forName(JDBC_DRIVER);
	               //STEP 2: Open a connection
	               System.out.println("Connecting to a selected database...");
	               conn = DriverManager.getConnection(DB_URL, USER, PASS);
	               System.out.println("Connected database successfully..."); 
	    	   }             
           } catch (Exception e) {
	           e.printStackTrace();
           } //end try
       return conn;
    }
   
    public static void clearConnection(){
    	try{
    		if(conn!=null){
    			conn.close();
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
	public static ArrayList<Menu> getMenu(String a_strQuery) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Menu> a_lstData = new ArrayList();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(a_strQuery);
			Menu item = null;
			int parameterIndex=0;
			while (rs.next()) {
				item = new Menu();
				parameterIndex = 0;
				item.setItemID(rs.getInt(++parameterIndex));
				item.setItemName(rs.getString(++parameterIndex));
				item.setDescription(rs.getString(++parameterIndex));
				item.setPrice((float)rs.getDouble(++parameterIndex));
				item.setPrepTime(rs.getInt(++parameterIndex));
				a_lstData.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a_lstData;
	}
    
	public static void addData(String a_strQuery, List a_lstData){
    	PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(a_strQuery);
			int parameterIndex = 0;
			ItemDVO item = null;
			for (int i = 0; i < a_lstData.size(); i++) {
				parameterIndex=0;
				item = (ItemDVO)a_lstData.get(i);
				stmt.setInt(++parameterIndex, item.getItemId());
				stmt.setString(++parameterIndex, item.getItemName());
				stmt.setString(++parameterIndex, item.getItemDesc());
				stmt.setDouble(++parameterIndex, item.getItemPrice());
				stmt.setInt(++parameterIndex, item.getItemPrepTime());
				stmt.addBatch();
			}						
			stmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
    public static void updateData(String a_strQuery, List a_lstData){
    	PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(a_strQuery);
			int parameterIndex = 0;
			ItemDVO item = null;
			for (int i = 0; i < a_lstData.size(); i++) {
				item = (ItemDVO)a_lstData.get(i);
				parameterIndex = 0;
				stmt.setString(++parameterIndex, item.getItemName());
				stmt.setString(++parameterIndex, item.getItemDesc());
				stmt.setDouble(++parameterIndex, item.getItemPrice());
				stmt.setInt(++parameterIndex, item.getItemPrepTime());
				stmt.setInt(++parameterIndex, item.getItemId());
				stmt.addBatch();
			}						
			stmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void removeData(String a_strQuery, List a_lstData){
    	PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(a_strQuery);
			int parameterIndex = 0;
			ItemDVO item = null;
			for (int i = 0; i < a_lstData.size(); i++) {
				item = (ItemDVO)a_lstData.get(i);
				stmt.setInt(++parameterIndex, item.getItemId());
				stmt.addBatch();
			}						
			stmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
   /* public static List maniplateData (ItemDVO a_item,String a_strInput,Connection conn, boolean blnQueryRequired) {
        Statement stmt = null;
        ResultSet rs = null; 
        List lstItems = null;
        try {
            
        	//conn = getConnection();
        	
        	//STEP 2: create Statement
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();
            
            //STEP 3: preparing query
            String sql = null; 
            if(blnQueryRequired){
            	sql = prepareQuery(a_item, a_strInput);	
            }else{
            	sql = a_strInput; // "update SK_ITEMS_MASTER set price="+100.00+" where ";
            }
            
            
            if("SELECTALL".equals(a_strInput) || "SELECT".equals(a_strInput)){
            	rs = stmt.executeQuery(sql);
            	lstItems = new ArrayList();
            	ItemDVO item = null;
            	
            	while(rs.next()){
            		
            		item = new ItemDVO();
            		
            		item.setItemId(rs.getInt(1));
            		item.setItemName(rs.getString(2));
            		item.setItemDesc(rs.getString(3));
            		item.setItemPrice(rs.getDouble(4));
            		item.setItemPrepTime(rs.getInt(5));
            		
            		lstItems.add(item);
            		
            	}
            	
            }else{
            	stmt.execute(sql);
            }
            
            //STEP 4: Execute the query            
            System.out.println("records impacted in the table...");

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt!=null)
                	stmt.close();
            } catch (SQLException se) {
            } // do nothing
            try {
                if (conn!=null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } //end finally try
        } //end try

        System.out.println("Goodbye!");
        return lstItems;

    }*/ //end main
   

    /*private static String prepareQuery(ItemDVO a_item, String a_strOperation){
    	 String sql = null;
 		
         if("INSERT".equals(a_strOperation)){
         	sql = " INSERT INTO SK_ITEMS_MASTER VALUES("+a_item.getItemId()+", '"+a_item.getItemName()+"', '"+a_item.getItemDesc()+"', "+a_item.getItemPrice()+", "+a_item.getItemPrepTime()+")";	
         }else if("UPDATE".equals(a_strOperation)){
         	sql = " UPDATE SK_ITEMS_MASTER set( NAME = '"+a_item.getItemName()+"', DESC = '"+a_item.getItemDesc()+"', PRICE = "+a_item.getItemPrice()+", PREP_TIME = "+a_item.getItemPrepTime()+") WHERE ID = "+a_item.getItemId();
         }else if("DELETE".equals(a_strOperation)){
         	sql = " DELETE FROM SK_ITEMS_MASTER WHERE ID = "+a_item.getItemId();
         }else if("SELECT".equals(a_strOperation)){
         	sql = " SELECT * FROM SK_ITEMS_MASTER WHERE ID = "+a_item.getItemId();
         }else if("SELECTALL".equals(a_strOperation)){
         	sql = " SELECT * FROM SK_ITEMS_MASTER ";
         }
         
         return sql;
    }*/
	public static void main(String[] args) throws SQLException {
		DatabaseController.createConnection();
		
		List<ItemDVO> lstData1 = new ArrayList<ItemDVO>();
		
		ItemDVO item = new ItemDVO();
		item.setItemId(1001);
		item.setItemName("Item_1");
		item.setItemDesc("ITEM_1 Desc");
		item.setItemPrice(75);
		item.setItemPrepTime(40);
		
		ItemDVO item1 = new ItemDVO();
		item1.setItemId(1002);
		item1.setItemName("Item_2");
		item1.setItemDesc("Item_2 Desc");
		item1.setItemPrice(60);
		item1.setItemPrepTime(30);
		
		lstData1.add(item);
		lstData1.add(item1);		
		
		DatabaseController.addData("INSERT INTO MENU_MASTER VALUES(?,?,?,?,?)", lstData1);
		
		List<ItemDVO> lstData = new ArrayList<ItemDVO>();
//		DatabaseController.getData(" SELECT * FROM MENU_MASTER ",lstData);
		for (ItemDVO items : lstData) {
			System.out.println(items.getItemId());
			System.out.println(items.getItemName());
			System.out.println(items.getItemDesc());
			System.out.println(items.getItemPrice());
			System.out.println(item.getItemPrepTime());
		}
		DatabaseController.clearConnection();
	}
    
} //end JDBCExample
