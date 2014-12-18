----------------------------------------------------------------------

CREATE TABLE Caterer  
(
	caterer_id		NUMBER			auto_increment	CONSTRAINT	caterer_pk	PRIMARY KEY,
	caterer_name	VARCHAR2(50)					CONSTRAINT	caterer_name_nn	NOT NULL
);


----------------------------------------------------------------------
CREATE TABLE Menu_Master
(
	item_id			NUMBER			auto_increment	CONSTRAINT	menu_master_pk	PRIMARY KEY,
	item_name		VARCHAR2(50)					CONSTRAINT	item_name_nn	NOT NULL,
	description		VARCHAR2(200),
	price			FLOAT							CONSTRAINT	price_nn		NOT NULL,
	prep_time		NUMBER
);


----------------------------------------------------------------------
CREATE TABLE CATERER_MENU_MAPPING 
(
    caterer_menu_mapping_id               NUMBER  auto_increment  CONSTRAINT  caterer_menu_mapping_pk    PRIMARY KEY,
	item_id			NUMBER,
	caterer_id		                      NUMBER 
);
ALTER TABLE	CATERER_MENU_MAPPING	ADD	FOREIGN KEY	(caterer_id)	REFERENCES CATERER(caterer_id); 
ALTER TABLE	CATERER_MENU_MAPPING	ADD	FOREIGN KEY	(item_id)		REFERENCES MENU_MASTER(item_id); 
ALTER TABLE	CATERER_MENU_MAPPING	ADD	CONSTRAINT	item_id_uk		UNIQUE KEY(item_id);



----------------------------------------------------------------------
CREATE TABLE Daily_Menu
(
	daily_menu_id	NUMBER		auto_increment	CONSTRAINT	daily_menu_pk	PRIMARY KEY,
	caterer_id		NUMBER						CONSTRAINT	caterer_id_nn	NOT NULL,
	menu_date		DATE						CONSTRAINT	menu_date_nn     NOT NULL,
	CONSTRAINT	Daily_Menu_Caterer_fk	FOREIGN KEY	(caterer_id)	REFERENCES	Caterer(caterer_id)
);


----------------------------------------------------------------------
CREATE  TABLE Daily_Menu_Mapping 
(               
    daily_menu_mapping_id	NUMBER    auto_increment	CONSTRAINT  daily_menu_mapping_id   PRIMARY KEY, 
	item_id					NUMBER,
	daily_menu_id			NUMBER,
	CONSTRAINT	Daily_Menu_Mapping_Menu_Master_fk	FOREIGN KEY	(item_id)		REFERENCES	Menu_Master(item_id),
	CONSTRAINT	Daily_Menu_Daily_Menu_Mapping_fk	FOREIGN KEY	(daily_menu_id)	REFERENCES	Daily_Menu(daily_menu_id)
);


------------------------------------------------------------------------------------
CREATE  TABLE  User
(
  user_id          NUMBER         CONSTRAINT  user_pk         PRIMARY KEY,
  first_name       VARCHAR2(30)   CONSTRAINT  first_name_nn   NOT NULL,
  last_name        NUMBER         CONSTRAINT  last_name       NOT NULL,
  password         VARCHAR2(30)   CONSTRAINT  password_nn     NOT NULL,  
  email_id         VARCHAR2(30)   CONSTRAINT  email_id_nn     NOT NULL 
  );

----------------------------------------------------------------------------------------
CREATE TABLE  Order_Master
(
  order_id     		NUMBER   auto_increment         CONSTRAINT   order_pk    PRIMARY KEY,
  user_id		                      NUMBER  ,
  total_cost   		FLOAT                                            CONSTRAINT total_cost_nn   	NOT NULL,  
  order_date   		DATE     		              CONSTRAINT order_date_nn   	NOT NULL,
  status       		                     VARCHAR2(30)   	              CONSTRAINT status_nn       	NOT NULL,
  remark                                                 VARCHAR2(200)
 );
 
 -----------------------------------------------------------------------------------------
CREATE TABLE  Order_Details
(
   order_details_id            NUMBER         CONSTRAINT   ordered_details_pk  PRIMARY KEY,
   order_id                   NUMBER,
   daily_menu_mapping_id      NUMBER,
   CONSTRAINT  order_fk                                 FOREIGN KEY (order_id)                                REFERENCES Order_Master(order_id),
   CONSTRAINT  daily_menu_mapping_fk  FOREIGN KEY (daily_menu_mapping_id)  REFERENCES Daily_Menu_Mapping(daily_menu_mapping_id)
);

-------------------------------------------------------------------------------------------

CREATE TABLE TAGS
(
  TAG_ID    NUMBER   CONSTRAINT TAGS_PK      PRIMARY KEY,
  TAG_NAME  VARCHAR2(30) CONSTRAINT  TAG_ID_NN  NOT NULL     
);
-------------------------------------------------------------------------------------------
CREATE  TABLE MENU_TAGS_MAPPING 
(
    MENU_TAGS_MAPPING_ID  NUMBER   CONSTRAINT MENU_TAGS_MAPPING_PK  PRIMARY KEY,
    ITEM_ID                       NUMBER  ,
    TAG_IDS                      VARCHAR2(100),
    CONSTRAINT   MENU_MASTER_FK FOREIGN KEY   (ITEM_ID) REFERENCES   MENU_MASTER(ITEM_ID)  
);
--------------------------------------------------------------------------------------------



