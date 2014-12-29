--- Insert Scripts for Caterer ----
----------------------------------------------------------------------
INSERT INTO caterer(caterer_name, caterer_details) values ('Royal Caterer', 'All food cuisines');	
INSERT INTO caterer(caterer_name, caterer_details) values ('Famous Caterer', 'Continental Food');


--- Insert Scripts for Menu_Master ----
----------------------------------------------------------------------
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (1, 'Biryani', 'Biryani is one of the most popular food items', 100, 20);
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (2, 'Fried Rice', 'Fried Rice is one of the most popular food items', 80, 20);
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (3, 'Pav Bhaji', 'Pav Bhaji is one of the most popular food items', 40, 25);
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (4, 'Misal Pav', 'Misal Pav  is one of the most popular food items', 60, 20);
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (5, 'Schezwan Rice', 'Schezwan  Rice is one of the most popular food items', 100, 25);
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (6, 'Dal Fry', 'Dal Fry is one of the most popular food items', 80, 20);
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (7, 'Poha', 'Poha is one of the most popular food items', 30, 10);
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (8, 'Palak Paneer', 'Palak Paneer is one of the most popular food items', 120, 25);
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (9, 'Paneer Tikka Masala', 'Paneer Tikka Masala  is one of the most popular food items', 100, 25);
INSERT INTO Menu_Master(item_id, item_name, description, price, prep_time) values (10, 'Manchurian Fried Rice', 'Manchurian Fried Rice is one of the most popular food items', 100, 25);

--- Insert Scripts for Tags ----
----------------------------------------------------------------------
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (1, 'Lunch');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (2, 'Dinner');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (3, 'Snacks');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (4, 'Breakfast');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (5, 'Chinese');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (6, 'North Indian');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (7, 'Beverages');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (8, 'Health Menu');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (9, 'Bread Preparations');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (10, 'Veg');
INSERT INTO TAGS(TAG_ID, TAG_NAME) values (11, 'Non-Veg');


--- Insert Scripts for MENU_TAGS_MAPPING ----
----------------------------------------------------------------------
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (1, '1,2,10', 1);
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (2, '1,2,5,10', 2);
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (3, '3,10', 3);
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (4, '3,4,10', 4);
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (5, '1,2,5,10', 5);
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (6, '1,2,6,10', 6);
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (7, '3,4,10', 7);
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (8, '1,2,6,10', 8);
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (9, '1,2,6,10', 9);
INSERT INTO MENU_TAGS_MAPPING(MENU_TAGS_MAPPING_ID, TAG_IDS, ITEM_ID) values (10, '1,2,5,10', 10);


--- Insert Scripts for Daily_Menu ----
-----------------------------------------------------------------------
INSERT INTO DAILY_MENU(daily_menu_id, caterer_id, menu_date) VALUES (1, 1, SYSDATE);
INSERT INTO DAILY_MENU(daily_menu_id, caterer_id, menu_date) VALUES (2, 2, SYSDATE);


--- Insert Scripts for CATERER_MENU_MAPPING ----
-------------------------------------------------------------------
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (1, 1, 1);
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (2, 1, 2);
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (3, 1, 3);
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (4, 1, 4);
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (5, 1, 5);
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (6, 2, 6);
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (7, 2, 7);
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (8, 2, 8);
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (9, 2, 9);
INSERT  INTO CATERER_MENU_MAPPING(CATERER_MENU_MAPPING_ID, caterer_id, item_id) VALUES (10, 2, 10);


--- Insert Scripts for Daily_Menu_Mapping ----
--------------------------------------------------------------------
INSERT INTO DAILY_MENU_MAPPING(DAILY_MENU_MAPPING_ID, ITEM_ID, DAILY_MENU_ID) VALUES (1, 1, 1); 
INSERT INTO DAILY_MENU_MAPPING(DAILY_MENU_MAPPING_ID, ITEM_ID, DAILY_MENU_ID) VALUES (2, 2, 1);
INSERT INTO DAILY_MENU_MAPPING(DAILY_MENU_MAPPING_ID, ITEM_ID, DAILY_MENU_ID) VALUES (3, 3, 1);
INSERT INTO DAILY_MENU_MAPPING(DAILY_MENU_MAPPING_ID, ITEM_ID, DAILY_MENU_ID) VALUES (4, 4, 1);
INSERT INTO DAILY_MENU_MAPPING(DAILY_MENU_MAPPING_ID, ITEM_ID, DAILY_MENU_ID) VALUES (5, 6, 2);
INSERT INTO DAILY_MENU_MAPPING(DAILY_MENU_MAPPING_ID, ITEM_ID, DAILY_MENU_ID) VALUES (6, 7, 2);
INSERT INTO DAILY_MENU_MAPPING(DAILY_MENU_MAPPING_ID, ITEM_ID, DAILY_MENU_ID) VALUES (7, 8, 2);


--- Insert Scripts for USER ----
--------------------------------------------------------------------
INSERT INTO USER(USER_ID, LOGIN_ID, PASSWORD, EMAIL_ID, ACCOUNT_CREATION_DATE) VALUES (1, 'tarul', 'tarul', 'tarul.yadav@mastek.com', SYSDATE);
INSERT INTO USER(USER_ID, LOGIN_ID, PASSWORD, EMAIL_ID, ACCOUNT_CREATION_DATE) VALUES (2, 'rahul', 'rahul', 'rahul.panchal@mastek.com', SYSDATE);
INSERT INTO USER(USER_ID, LOGIN_ID, PASSWORD, EMAIL_ID, ACCOUNT_CREATION_DATE) VALUES (3, 'vaibhav', 'vaibhav', 'vaibhav.karanjkar@mastek.com', SYSDATE);


--- Insert Scripts for USER_DETAILS ----
--------------------------------------------------------------------
INSERT INTO USER_DETAILS(USER_DETAILS_ID, FIRST_NAME, LAST_NAME, GENDER, DATE_OF_BIRTH, CONTACT_NO, EXTENSION_NO, EMPLOYEE_ID, USER_ID) VALUES (1, 'Tarul', 'Yadav', 'F', '1985-08-22', 9619123456, 5678, 11050, 1);
INSERT INTO USER_DETAILS(USER_DETAILS_ID, FIRST_NAME, LAST_NAME, GENDER, DATE_OF_BIRTH, CONTACT_NO, EXTENSION_NO, EMPLOYEE_ID, USER_ID) VALUES (2, 'Rahul', 'Panchal', 'M', '1991-03-21', 9619123456, 5678, 11050, 2);
INSERT INTO USER_DETAILS(USER_DETAILS_ID, FIRST_NAME, LAST_NAME, GENDER, DATE_OF_BIRTH, CONTACT_NO, EXTENSION_NO, EMPLOYEE_ID, USER_ID) VALUES (3, 'Vaibhav', 'Karanjkar', 'M', '1987-02-02', 9619123456, 5678, 11050, 3);