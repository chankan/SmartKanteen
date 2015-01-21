Use smartkanteen; 

CREATE TABLE CATERER  
(
	CATERER_ID		INT				auto_increment,	
	CATERER_NAME	VARCHAR(50) 	NOT NULL,
	CATERER_DETAILS	VARCHAR(200),
	CONSTRAINT	caterer_pk		PRIMARY KEY (CATERER_ID)
);

CREATE TABLE MENU_MASTER
(
	item_id			INT				auto_increment,
	item_name		VARCHAR(50)		NOT NULL,
	description		VARCHAR(200),
	price			FLOAT			NOT NULL,
	prep_time		INT,
    CONSTRAINT	menu_master_pk	PRIMARY KEY(item_id)
);

CREATE TABLE CATERER_MENU_MAPPING
(
	caterer_menu_mapping_id	INT		auto_increment,
	item_id					INT,
	caterer_id				INT,
    CONSTRAINT	caterer_menu_mapping_pk	PRIMARY KEY(caterer_menu_mapping_id)
);
ALTER TABLE	CATERER_MENU_MAPPING	ADD	FOREIGN KEY	(caterer_id)	REFERENCES CATERER(caterer_id);
ALTER TABLE	CATERER_MENU_MAPPING	ADD	FOREIGN KEY	(item_id)		REFERENCES MENU_MASTER(item_id);
ALTER TABLE	CATERER_MENU_MAPPING	ADD	CONSTRAINT	item_id_uk		UNIQUE KEY(item_id);

CREATE TABLE Daily_Menu
(
	daily_menu_id	INT			auto_increment,
	caterer_id		INT			NOT NULL,
	menu_date		DATE		NOT NULL,
    CONSTRAINT	daily_menu_pk	PRIMARY KEY(daily_menu_id),
	CONSTRAINT	Daily_Menu_Caterer_fk	FOREIGN KEY	(caterer_id)	REFERENCES	CATERER(caterer_id)
);

CREATE  TABLE Daily_Menu_Mapping
(
	daily_menu_mapping_id	INT		auto_increment,	
	item_id					INT,
	daily_menu_id			INT,
    CONSTRAINT	daily_menu_mapping_id	PRIMARY KEY(daily_menu_mapping_id),
	CONSTRAINT	Daily_Menu_Mapping_Menu_Master_fk	FOREIGN KEY	(item_id)		REFERENCES	Menu_Master(item_id),
	CONSTRAINT	Daily_Menu_Daily_Menu_Mapping_fk	FOREIGN KEY	(daily_menu_id)	REFERENCES	Daily_Menu(daily_menu_id)
);

CREATE  TABLE  User
(
	user_id					INT				auto_increment,
	login_id				VARCHAR(30)		NOT NULL,
	password				VARCHAR(32)		NOT NULL,
	email_id				VARCHAR(50)		NOT NULL,
	account_creation_date	DATE			NOT NULL,
    CONSTRAINT	user_pk						PRIMARY KEY(user_id)
);
ALTER TABLE	User	ADD	CONSTRAINT	login_id_uk	UNIQUE KEY(login_id);
ALTER TABLE	User	ADD	CONSTRAINT	email_id_uk	UNIQUE KEY(email_id);

CREATE TABLE  USER_DETAILS
(
	user_details_id		INT	  auto_increment,     
	first_name			VARCHAR(30),
	last_name			VARCHAR(30),
	gender				VARCHAR(1),
	date_of_birth		DATE,
	contact_no			INT,
	extension_no		INT,
	employee_id			INT,
	user_id				INT,
    CONSTRAINT  user_details_pk   PRIMARY KEY(user_details_id),
	CONSTRAINT	user_fk	FOREIGN KEY	(user_id)	REFERENCES USER(user_id)
);

CREATE TABLE  Order_Master
(
	order_id			INT	auto_increment,	
	user_id				INT,
	total_cost			FLOAT		NOT NULL,
	order_created_date	DATE		NOT NULL,
	order_date        	TIME        NOT NULL,             
	status				VARCHAR(30)	NOT NULL,
	remark				VARCHAR(200),
	caterer_id			INT,  
    CONSTRAINT	order_pk	PRIMARY KEY(order_id),
	CONSTRAINT	user_id_fk	FOREIGN KEY	(user_id)	 REFERENCES User(user_id),
    CONSTRAINT  caterer_fk  FOREIGN KEY (caterer_id) REFERENCES Caterer(caterer_id)    
);

CREATE TABLE  Order_Details
(
	order_details_id		INT	auto_increment,
	order_id				INT,
	item_id					INT,
	quantity				INT,
	CONSTRAINT	ordered_details_pk	PRIMARY KEY(order_details_id),
	CONSTRAINT	order_fk	FOREIGN KEY	(order_id)	REFERENCES	Order_Master(order_id),
	CONSTRAINT	menu_fk		FOREIGN KEY	(item_id)	REFERENCES	MENU_MASTER(item_id)
);

CREATE TABLE TAGS
(
	TAG_ID		INT			auto_increment,
	TAG_NAME	VARCHAR(30)	NOT NULL,
	CONSTRAINT	TAGS_PK		PRIMARY KEY(TAG_ID)
);

CREATE  TABLE MENU_TAGS_MAPPING
(
	MENU_TAGS_MAPPING_ID	INT	auto_increment,	
	ITEM_ID					INT,
	TAG_IDS					VARCHAR(100),
	CONSTRAINT	MENU_TAGS_MAPPING_PK	PRIMARY KEY(MENU_TAGS_MAPPING_ID),
	CONSTRAINT	MENU_MASTER_FK	FOREIGN KEY	(ITEM_ID)	REFERENCES	MENU_MASTER(ITEM_ID)
);

CREATE TABLE ROLE 
(
	role_id		INT			auto_increment,	
	role_name	VARCHAR(30)	NOT NULL,
	CONSTRAINT	role_pk			PRIMARY KEY(role_id)
);

CREATE TABLE  USER_ROLE_MAPPING
(
	user_role_mapping_id	INT	auto_increment,	
	role_id					INT,
	user_id					INT,
	CONSTRAINT	user_role_mapping_pk	PRIMARY KEY(user_role_mapping_id),
	CONSTRAINT	role_fk		FOREIGN KEY	(role_id)	REFERENCES	role(role_id),
	CONSTRAINT	user_role_fk		FOREIGN KEY	(user_id)	REFERENCES	user(user_id)
);

CREATE TABLE  USER_CATERER_MAPPING
(
	user_caterer_mapping_id	INT	auto_increment,
	user_id					INT,
	caterer_id				INT,
	CONSTRAINT	user_role_mapping_pk	PRIMARY KEY(user_caterer_mapping_id),
	CONSTRAINT	user_caterer_mapping_user_fk		FOREIGN KEY	(user_id)		REFERENCES	USER(user_id),
	CONSTRAINT	user_caterer_mapping_caterer_fk		FOREIGN KEY	(caterer_id)	REFERENCES	CATERER(caterer_id)
);