--MENU ITEM TYPE

CREATE TABLE menu_item_type (
	id       int primary key,
	type     varchar(80)

);


--MENU ITEM

CREATE TABLE menu_item (
	id                    int primary key,
	menu_item_type_id     int references menu_item_type(id),
	name			            varchar(80),
	description		        varchar(200),
	preparation_duration  interval

);


--FEEDBACK TO MENU ITEM M:M

CREATE TABLE feedback_menu_item (
	id                int primary key,
	menu_item_id      int references menu_item(id),
	rating 		        int,
	message           varchar(80)


);


--USER

CREATE TABLE users  (
	id                       int primary key,
  feedback_menu_item_id    int references feedback_menu_item(id)
);

--BILL

CREATE TABLE bill (
	id        int primary key,
	voucher		bool,
	sale 			int,
	total     money,
	user_id		int references users(id)

);


--TABLE

CREATE TABLE table_food  (
	id            int primary key,
	table_nr			int,
	seats         int,
	window_view   bool,
	floor			    int,
	free			    bool

);

--COMMAND

CREATE TABLE command  (
	id               int primary key,
	command_name		 varchar(200),
	specification	   varchar(200) ,
	is_packed			   bool,
	status           varchar(80),
	bill_id 			   int references bill(id),
	table_id			   int references table_food(id)

);


--USER COMMAND M:M

CREATE TABLE user_command (
	id             int primary key,
	user_id			   int references users(id),
	command_id		 int references command(id)


);

--RESERVATION

	CREATE TABLE reservation  (
	id                int primary key,
	time_check_in     timestamp,
	time_check_out    timestamp,
	user_id			      int references users(id),
	command_id		    int references command(id),
	confirmed         bool,
	status			      varchar(80)


);

--USER RESERVATION M:M

	CREATE TABLE user_reservation  (
	id                 int primary key,
	user_id			       int references users(id),
	reservation_id	   int references reservation(id)

);

--RESERVATION TABLE M:M

CREATE TABLE table_reservation  (
	id                 int primary key,
	reservation_id	   int references reservation(id),
	table_id			     int references table_food(id)

);


--MENU ITEM COMMAND M:M

CREATE TABLE menu_item_command (
	id               int primary key,
	menu_item_id   	 int references menu_item(id),
	command_id		   int references command(id),
	status		       varchar(80)


);

--INGREDIENT

CREATE TABLE ingredient (
	id         int primary key,
	name 			 varchar(200)


);

--MENU ITEM INGREDIENT M:M

CREATE TABLE menu_item_ingredient (
	id               int primary key,
	menu_item_id   	 int references menu_item(id),
	ingredient_id	   int references ingredient(id)


);
