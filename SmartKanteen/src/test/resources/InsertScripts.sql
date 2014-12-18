======================================================================
Insert Query

Caterer
----------------------------------------------------------------------
INSERT INTO caterer(caterer_name)values('Royal Caterer');	
INSERT INTO caterer(caterer_name)values('Famous Caterer');


Menu_Master
----------------------------------------------------------------------
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(1,'Biryani','Biryani is one of the most popular food items',100,20);
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(2,'Fried Rice ','Fried Rice is one of the most popular food items',80,20);
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(3,' Pav Bhaji','Pav Bhaji is one of the most popular food items',40,25);
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(4,'Misal Pav ','Misal Pav  is one of the most popular food items',60,20);
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(5,'Schezwan  Rice','Schezwan  Rice is one of the most popular food items',100,25);
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(6,'Dal Fry ','Dal Fry is one of the most popular food items',80,20);
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(7,'Poha','Poha is one of the most popular food items',30,10);
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(8,'Palak Paneer ','Palak Paneer is one of the most popular food items',120,25);
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(9,'Paneer Tikka Masala ','Paneer Tikka Masala  is one of the most popular food items',100,25);
INSERT INTO Menu_Master(item_id,item_name,description,price,prep_time)values(10,'Manchurian Fried Rice','Manchurian Fried Rice is one of the most popular food items',100,25);


DailyMenu
-----------------------------------------------------------------------
INSERT INTO DAILY_MENU(daily_menu_id,caterer_id,menu_date)VALUES(1,1,SYSDATE);
INSERT INTO DAILY_MENU(daily_menu_id,caterer_id,menu_date)VALUES(2,2,SYSDATE);
INSERT INTO DAILY_MENU(daily_menu_id,caterer_id,menu_date)VALUES(3,1,SYSDATE);
INSERT INTO DAILY_MENU(daily_menu_id,caterer_id,menu_date)VALUES(4,2,SYSDATE);


CATERER_MENU_MAPPING
-------------------------------------------------------------------
INSERT  INTO CATERER_MENU_MAPPING (CATERER_MENU_MAPPING_ID ,caterer_id,item_id) VALUES(1,1,1);
INSERT  INTO CATERER_MENU_MAPPING (CATERER_MENU_MAPPING_ID ,caterer_id,item_id) VALUES(2,1,2);
INSERT  INTO CATERER_MENU_MAPPING (CATERER_MENU_MAPPING_ID ,caterer_id,item_id) VALUES(3,1,3);
INSERT  INTO CATERER_MENU_MAPPING (CATERER_MENU_MAPPING_ID ,caterer_id,item_id) VALUES(4,2,4);
INSERT  INTO CATERER_MENU_MAPPING (CATERER_MENU_MAPPING_ID ,caterer_id,item_id) VALUES(5,2,5);
INSERT  INTO CATERER_MENU_MAPPING (CATERER_MENU_MAPPING_ID ,caterer_id,item_id) VALUES(6,2,6);
INSERT  INTO CATERER_MENU_MAPPING (CATERER_MENU_MAPPING_ID ,caterer_id,item_id) VALUES(7,2,7);






Daily_Menu_Mapping
--------------------------------------------------------------------
INSERT INTO DAILY_MENU_MAPPING  (DAILY_MENU_MAPPING_ID,ITEM_ID,DAILY_MENU_ID)VALUES(1,1,1); 
INSERT INTO DAILY_MENU_MAPPING  (DAILY_MENU_MAPPING_ID,ITEM_ID,DAILY_MENU_ID)VALUES(2,2,2);
INSERT INTO DAILY_MENU_MAPPING  (DAILY_MENU_MAPPING_ID,ITEM_ID,DAILY_MENU_ID)VALUES(3,3,3);
INSERT INTO DAILY_MENU_MAPPING  (DAILY_MENU_MAPPING_ID,ITEM_ID,DAILY_MENU_ID)VALUES(4,4,4);
INSERT INTO DAILY_MENU_MAPPING  (DAILY_MENU_MAPPING_ID,ITEM_ID,DAILY_MENU_ID)VALUES(5,5,1);
INSERT INTO DAILY_MENU_MAPPING  (DAILY_MENU_MAPPING_ID,ITEM_ID,DAILY_MENU_ID)VALUES(6,6,2);
   
  