--MENU ITEM TYPE

CREATE TABLE menuItemType (
	id       int primary key,
	type     varchar(80)

);


--MENU ITEM

CREATE TABLE menuItem (
	id               int primary key,
	menuItemTypeId   int references menuItemType(id),
	name			       varchar(80),
	description		   varchar(200),
	preparationTime  int

);


--FEEDBACK TO MENU ITEM M:M

CREATE TABLE feedbackmenuItem (
	id           int primary key,
	menuItemId   int references menuItem(id),
	raiting 		 int,
	message      varchar(80)


);


--USER

CREATE TABLE userQ  (
	id                    int primary key,
  feedbackMenuItemId    int references feedbackMenuItem(id)
);

--BILL

CREATE TABLE bill (
	id        int primary key,
	voucher		bool,
	sale 			int,
	total     float8,
	userId		int references userQ(id)

);


--TABLE

CREATE TABLE tableQ  (
	id          int primary key,
	tableNr			int,
	seats       int,
	windowView  bool,
	floor			  int,
	free			  bool

);

--COMMAND

CREATE TABLE command  (
	id             int primary key,
	commandIdUi		 varchar(200),
	specfication	 varchar(200) ,
	toGo			     bool,
	status         bool,
	billId 			   int references bill(id),
	tableId			   int references tableQ(id)

);


--USER COMMAND M:M

CREATE TABLE userCommand (
	id           int primary key,
	userId			 int references userQ(id),
	commandId		 int references command(id)


);

--RESERVATION

	CREATE TABLE reservation  (
	id              int primary key,
	timeCheckIn     timestamp,
	timeCheckOut    timestamp,
	userId			    int references userQ(id),
	commandId		    int references command(id),
	confirmed       bool,
	status			    bool


);

--USER RESERVATION M:M

	CREATE TABLE userReservation  (
	id               int primary key,
	userId			     int references userQ(id),
	reservationId	   int references reservation(id)

);

--RESERVATION TABLE M:M

CREATE TABLE tableReservation  (
	id               int primary key,
	reservationId	   int references reservation(id),
	tableId			     int references tableQ(id)

);


--MENU ITEM COMMAND M:M

CREATE TABLE menuItemCommand (
	id             int primary key,
	menuItemId   	 int references menuItem(id),
	commandId		   int references command(id),
	statusFood		 bool


);

--INGREDIENT

CREATE TABLE ingredient (
	id         int primary key,
	name 			 varchar(200)


);

--MENU ITEM INGREDIENT M:M

CREATE TABLE menuItemIngredient (
	id             int primary key,
	menuItemId   	 int references menuItem(id),
	ingredientId	 int references ingredient(id)


);





alter table userQ add column feedbackMenuItemId int references feedbackMenuItem(id);