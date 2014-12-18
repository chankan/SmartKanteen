  
 User :-   usergivingOrder     (means addingDailyMenu To Order  and  Order_Daily_Menu_Mapping)  / viewOrders  with totalCostCalculated / userDeleteItemFromOrder  /declineOrder  or cancelOrder  (deleteOrderById)  
 
 
 
 Caterer :- viewOrderByDateAndCatererId //  viewAllOrderByCatererId   //AcceptOrder // RejectOrder     
 
 Admin :-   viewAllOrder/viewTodaysOrder 		  

 
 
 
 
Admin
   admin_id
   pwd    


    One  Order  may have many DailyMenuItems  so ONE-MANY-RELATIONSHIP
	
	Daily_Menu_Mapping   
		daily_menu_mapping_id
		daily_menu_id
         menu_id  		

                Order_Daily_Menu_Mapping   
					Order_Daily_Menu_Id  
					daily_ menu_mapping
					order_id 			 

					
					
Order
  Order_Id 
  Issued_Date
  Total_Cost
  Order_Status 
                    					
	One   Order belongs to one user and  One user can place many Orders 
	
 
								
			 User_Order_Mapping 
					User_Order_Id 
					User_Id
					Order_Id 				
				
	
	User
		User_id
		password		


----------------------------------------------------------------------------------
CREATE TABLE  Order 
(
  order_id     		NUMBER   		CONSTRAINT   order_id_pk     	PRIMARY KEY,
  user_id      		NUMBER,
  total_cost   		FLOAT           CONSTRAINT   total_cost_nn   	NOT NULL,  
  order_date   		DATE     		CONSTRAINT   order_date_nn   	NOT NULL,
  status       		VARCHAR2(30)   	CONSTRAINT   status_nn       	NOT NULL,
 );		


---------------------------------------------------------------------------------
CREATE TABLE  Ordered_Items
(
   ordered_item_id            NUMBER         CONSTRAINT   ordered_item_id_pk  PRIMARY KEY,
   order_id                   NUMBER,
   daily_menu_mapping_id      NUMBER,
   CONSTRAINT  order_id  FOREIGN KEY (order_id)  REFERENCES Order(order_id),
   CONSTRAINT  order_id  FOREIGN KEY (daily_menu_mapping_id)  REFERENCES Daily_Menu_Mapping(daily_menu_mapping_id)
);


------------------------------------------------------------------------------------
CREATE  TABLE  User
(
  user_id          NUMBER         CONSTRAINT  user1_id        PRIMARY KEY,
  first_name       VARCHAR2(30)   CONSTRAINT  first_name_nn   NOT NULL,
  last_name        NUMBER         CONSTRAINT  last_name       NOT NULL,
) 

