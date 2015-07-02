----------------------------------------------------------------------
CREATE TABLE CATERER  
(
	CATERER_ID		INT				auto_increment	CONSTRAINT	caterer_pk		PRIMARY KEY,
	CATERER_NAME	VARCHAR2(50)					CONSTRAINT	caterer_name_nn	NOT NULL,
	CATERER_DETAILS	VARCHAR2(200)
);

----------------------------------------------------------------------
CREATE TABLE MENU_MASTER
(
	item_id			INT				auto_increment	CONSTRAINT	menu_master_pk	PRIMARY KEY,
	item_name		VARCHAR2(50)					CONSTRAINT	item_name_nn	NOT NULL,
	description		VARCHAR2(200),
	price			FLOAT							CONSTRAINT	price_nn		NOT NULL,
	prep_time		INT
);

----------------------------------------------------------------------
CREATE TABLE CATERER_MENU_MAPPING
(
	caterer_menu_mapping_id	INT		auto_increment	CONSTRAINT	caterer_menu_mapping_pk	PRIMARY KEY,
	item_id					NUMBER,
	caterer_id				NUMBER 
);
ALTER TABLE	CATERER_MENU_MAPPING	ADD	FOREIGN KEY	(caterer_id)	REFERENCES CATERER(caterer_id);
ALTER TABLE	CATERER_MENU_MAPPING	ADD	FOREIGN KEY	(item_id)		REFERENCES MENU_MASTER(item_id);
ALTER TABLE	CATERER_MENU_MAPPING	ADD	CONSTRAINT	item_id_uk		UNIQUE KEY(item_id);

----------------------------------------------------------------------
CREATE TABLE Daily_Menu
(
	daily_menu_id	INT			auto_increment	CONSTRAINT	daily_menu_pk	PRIMARY KEY,
	caterer_id		INT							CONSTRAINT	caterer_id_nn	NOT NULL,
	menu_date		DATE						CONSTRAINT	menu_date_nn     NOT NULL,
	CONSTRAINT	Daily_Menu_Caterer_fk	FOREIGN KEY	(caterer_id)	REFERENCES	CATERER(caterer_id)
);


----------------------------------------------------------------------
CREATE  TABLE Daily_Menu_Mapping
(
	daily_menu_mapping_id	INT		auto_increment	CONSTRAINT	daily_menu_mapping_id	PRIMARY KEY,
	item_id					INT,
	daily_menu_id			INT,
	CONSTRAINT	Daily_Menu_Mapping_Menu_Master_fk	FOREIGN KEY	(item_id)		REFERENCES	Menu_Master(item_id),
	CONSTRAINT	Daily_Menu_Daily_Menu_Mapping_fk	FOREIGN KEY	(daily_menu_id)	REFERENCES	Daily_Menu(daily_menu_id)
);

------------------------------------------------------------------------------------
CREATE  TABLE  User
(
	user_id					INT				auto_increment	CONSTRAINT	user_pk						PRIMARY KEY,
	login_id				VARCHAR2(30)					CONSTRAINT  login_nn					NOT NULL,
	password				VARCHAR2(32)					CONSTRAINT	password_nn					NOT NULL,
	email_id				VARCHAR2(50)					CONSTRAINT	email_id_nn					NOT NULL,
	account_creation_date	DATE							CONSTRAINT  account_generation_date_nn  NOT NULL
);
ALTER TABLE	User	ADD	CONSTRAINT	login_id_uk	UNIQUE KEY(login_id);
ALTER TABLE	User	ADD	CONSTRAINT	email_id_uk	UNIQUE KEY(email_id);

----------------------------------------------------------------------------
CREATE TABLE  USER_DETAILS
(
	user_details_id		INT	  auto_increment     CONSTRAINT  user_details_pk   PRIMARY KEY,
	first_name			VARCHAR2(30),
	last_name			VARCHAR2(30),
	gender				VARCHAR2(1),
	date_of_birth		DATE,
	contact_no			INT,
	extension_no		INT,
	employee_id			INT,
	user_id				INT,
	CONSTRAINT	user_fk	FOREIGN KEY	(user_id)	REFERENCES USER(user_id)
);

----------------------------------------------------------------------------------------
CREATE TABLE  Order_Master
(
	order_id			INT	auto_increment	CONSTRAINT	order_pk				PRIMARY KEY,
	user_id				INT,
	total_cost			FLOAT					CONSTRAINT	total_cost_nn			NOT NULL,
	order_created_date	DATE					CONSTRAINT	order_created_date_nn	NOT NULL,
	order_date        	TIME                    CONSTRAINT  order_timing_nn         NOT NULL,             
	status				VARCHAR2(30)			CONSTRAINT	status_nn				NOT NULL,
	remark				VARCHAR2(200),
	caterer_id			INT,  
	CONSTRAINT	user_id_fk	FOREIGN KEY	(user_id)	 REFERENCES User(user_id),
    CONSTRAINT  caterer_fk  FOREIGN KEY (caterer_id) REFERENCES Caterer(caterer_id)    
);

-----------------------------------------------------------------------------------------
CREATE TABLE  Order_Details
(
	order_details_id		INT	auto_increment	CONSTRAINT	ordered_details_pk	PRIMARY KEY,
	order_map_id				INT,
	item_id					INT,
	quantity				INT,
	CONSTRAINT	order_fk	FOREIGN KEY	(order_id)	REFERENCES	Order_Master(order_id),
	CONSTRAINT	menu_fk		FOREIGN KEY	(item_id)	REFERENCES	MENU_MASTER(item_id)
);

-------------------------------------------------------------------------------------------
CREATE TABLE TAGS
(
	TAG_ID		INT			auto_increment	CONSTRAINT	TAGS_PK		PRIMARY KEY,
	TAG_NAME	VARCHAR2(30)				CONSTRAINT	TAG_ID_NN	NOT NULL
);
 
-------------------------------------------------------------------------------------------
CREATE  TABLE MENU_TAGS_MAPPING
(
	MENU_TAGS_MAPPING_ID	INT	auto_increment	CONSTRAINT	MENU_TAGS_MAPPING_PK	PRIMARY KEY,
	ITEM_ID					INT,
	TAG_IDS					VARCHAR2(100),
	CONSTRAINT	MENU_MASTER_FK	FOREIGN KEY	(ITEM_ID)	REFERENCES	MENU_MASTER(ITEM_ID)
);

--------------------------------------------------------------------------------------------
CREATE TABLE ROLE 
(
	role_id		INT			auto_increment	CONSTRAINT	role_pk			PRIMARY KEY,
	role_name	VARCHAR2(30)				CONSTRAINT	role_name_nn	NOT NULL
);

----------------------------------------------------------------------------
CREATE TABLE  USER_ROLE_MAPPING
(
	user_role_mapping_id	INT	auto_increment	CONSTRAINT	user_role_mapping_pk	PRIMARY KEY,
	role_id					INT,
	user_id					INT,
	CONSTRAINT	role_fk		FOREIGN KEY	(role_id)	REFERENCES	role(role_id),
	CONSTRAINT	user_role_fk		FOREIGN KEY	(user_id)	REFERENCES	user(user_id)
);

----------------------------------------------------------------------------
CREATE TABLE  USER_CATERER_MAPPING
(
	user_caterer_mapping_id	INT	auto_increment	CONSTRAINT	user_role_mapping_pk	PRIMARY KEY,
	user_id					INT,
	caterer_id				INT,
	CONSTRAINT	user_caterer_mapping_user_fk		FOREIGN KEY	(user_id)		REFERENCES	USER(user_id),
	CONSTRAINT	user_caterer_mapping_caterer_fk		FOREIGN KEY	(caterer_id)	REFERENCES	CATERER(caterer_id)
);