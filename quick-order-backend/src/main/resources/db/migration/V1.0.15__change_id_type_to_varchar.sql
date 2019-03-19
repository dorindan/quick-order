----------------------------------- Delete all foreign key  ----------------------
-- bill
ALTER TABLE bill ADD id_uuid varchar(40);

UPDATE bill
set id_uuid = cast(user_id as varchar(40));

ALTER TABLE bill DROP COLUMN user_id;

ALTER TABLE bill
RENAME COLUMN id_uuid TO user_id;


-- command 1
ALTER TABLE command ADD id_uuid varchar(40);

UPDATE command
set id_uuid = cast(table_id as varchar(40));

ALTER TABLE command DROP COLUMN table_id;

ALTER TABLE command
RENAME COLUMN id_uuid TO table_id;

-- command 2
ALTER TABLE command ADD id_uuid varchar(40);

UPDATE command
set id_uuid = cast(bill_id as varchar(40));

ALTER TABLE command DROP COLUMN bill_id;

ALTER TABLE command
RENAME COLUMN id_uuid TO bill_id;


-- feedback_menu_item 1
ALTER TABLE feedback_menu_item ADD id_uuid varchar(40);

UPDATE feedback_menu_item
set id_uuid = cast(user_id as varchar(40));

ALTER TABLE feedback_menu_item DROP COLUMN user_id;

ALTER TABLE feedback_menu_item
RENAME COLUMN id_uuid TO user_id;

-- feedback_menu_item 2
ALTER TABLE feedback_menu_item ADD id_uuid varchar(40);

UPDATE feedback_menu_item
set id_uuid = cast(menu_item_id as varchar(40));

ALTER TABLE feedback_menu_item DROP COLUMN menu_item_id;

ALTER TABLE feedback_menu_item
RENAME COLUMN id_uuid TO menu_item_id;


-- menu_item 1
ALTER TABLE menu_item ADD id_uuid varchar(40);

UPDATE menu_item
set id_uuid = cast(menu_item_type_id as varchar(40));

ALTER TABLE menu_item DROP COLUMN menu_item_type_id;

ALTER TABLE menu_item
RENAME COLUMN id_uuid TO menu_item_type_id;


-- menu_item_command 1
ALTER TABLE menu_item_command ADD id_uuid varchar(40);

UPDATE menu_item_command
set id_uuid = cast(menu_item_id as varchar(40));

ALTER TABLE menu_item_command DROP COLUMN menu_item_id;

ALTER TABLE menu_item_command
RENAME COLUMN id_uuid TO menu_item_id;

-- menu_item_command 2
ALTER TABLE menu_item_command ADD id_uuid varchar(40);

UPDATE menu_item_command
set id_uuid = cast(command_id as varchar(40));

ALTER TABLE menu_item_command DROP COLUMN command_id;

ALTER TABLE menu_item_command
RENAME COLUMN id_uuid TO command_id;


-- menu_item_ingredient 1
ALTER TABLE menu_item_ingredient ADD id_uuid varchar(40);

UPDATE menu_item_ingredient
set id_uuid = cast(menu_item_id as varchar(40));

ALTER TABLE menu_item_ingredient DROP COLUMN menu_item_id;

ALTER TABLE menu_item_ingredient
RENAME COLUMN id_uuid TO menu_item_id;

-- menu_item_ingredient 2
ALTER TABLE menu_item_ingredient ADD id_uuid varchar(40);

UPDATE menu_item_ingredient
set id_uuid = cast(ingredient_id as varchar(40));

ALTER TABLE menu_item_ingredient DROP COLUMN ingredient_id;

ALTER TABLE menu_item_ingredient
RENAME COLUMN id_uuid TO ingredient_id;


-- reservation 1
ALTER TABLE reservation ADD id_uuid varchar(40);

UPDATE reservation
set id_uuid = cast(command_id as varchar(40));

ALTER TABLE reservation DROP COLUMN command_id;

ALTER TABLE reservation
RENAME COLUMN id_uuid TO command_id;

-- reservation 2
ALTER TABLE reservation ADD id_uuid varchar(40);

UPDATE reservation
set id_uuid = cast(user_id as varchar(40));

ALTER TABLE reservation DROP COLUMN user_id;

ALTER TABLE reservation
RENAME COLUMN id_uuid TO user_id;


-- table_reservation 1
ALTER TABLE table_reservation ADD id_uuid varchar(40);

UPDATE table_reservation
set id_uuid = cast(reservation_id as varchar(40));

ALTER TABLE table_reservation DROP COLUMN reservation_id;

ALTER TABLE table_reservation
RENAME COLUMN id_uuid TO reservation_id;

-- table_reservation 2
ALTER TABLE table_reservation ADD id_uuid varchar(40);

UPDATE table_reservation
set id_uuid = cast(table_id as varchar(40));

ALTER TABLE table_reservation DROP COLUMN table_id;

ALTER TABLE table_reservation
RENAME COLUMN id_uuid TO table_id;


-- user_attribute 1
ALTER TABLE user_attribute ADD id_uuid varchar(40);

UPDATE user_attribute
set id_uuid = cast(user_id as varchar(40));

ALTER TABLE user_attribute DROP COLUMN user_id;

ALTER TABLE user_attribute
RENAME COLUMN id_uuid TO user_id;


-- user_command 1
ALTER TABLE user_command ADD id_uuid varchar(40);

UPDATE user_command
set id_uuid = cast(user_id as varchar(40));

ALTER TABLE user_command DROP COLUMN user_id;

ALTER TABLE user_command
RENAME COLUMN id_uuid TO user_id;

-- user_command 2
ALTER TABLE user_command ADD id_uuid varchar(40);

UPDATE user_command
set id_uuid = cast(command_id as varchar(40));

ALTER TABLE user_command DROP COLUMN command_id;

ALTER TABLE user_command
RENAME COLUMN id_uuid TO command_id;


-- user_reservation 1
ALTER TABLE user_reservation ADD id_uuid varchar(40);

UPDATE user_reservation
set id_uuid = cast(user_id as varchar(40));

ALTER TABLE user_reservation DROP COLUMN user_id;

ALTER TABLE user_reservation
RENAME COLUMN id_uuid TO user_id;

-- user_reservation 2
ALTER TABLE user_reservation ADD id_uuid varchar(40);

UPDATE user_reservation
set id_uuid = cast(reservation_id as varchar(40));

ALTER TABLE user_reservation DROP COLUMN reservation_id;

ALTER TABLE user_reservation
RENAME COLUMN id_uuid TO reservation_id;


---------------------------- Change primary key -------------------------------

-- bill
ALTER TABLE bill ADD id_uuid varchar(40);

UPDATE bill
set id_uuid = cast(id as varchar(40));

ALTER TABLE bill DROP COLUMN id;

ALTER TABLE bill
ADD PRIMARY KEY (id_uuid);

ALTER TABLE bill
RENAME COLUMN id_uuid TO id;


-- command
ALTER TABLE command ADD id_uuid varchar(40);

UPDATE command
set id_uuid = cast(id as varchar(40));

ALTER TABLE command DROP COLUMN id;

ALTER TABLE command
ADD PRIMARY KEY (id_uuid);

ALTER TABLE command
RENAME COLUMN id_uuid TO id;


-- feedback_menu_item
ALTER TABLE feedback_menu_item ADD id_uuid varchar(40);

UPDATE feedback_menu_item
set id_uuid = cast(id as varchar(40));

ALTER TABLE feedback_menu_item DROP COLUMN id;

ALTER TABLE feedback_menu_item
ADD PRIMARY KEY (id_uuid);

ALTER TABLE feedback_menu_item
RENAME COLUMN id_uuid TO id;


-- ingredient
ALTER TABLE ingredient ADD id_uuid varchar(40);

UPDATE ingredient
set id_uuid = cast(id as varchar(40));

ALTER TABLE ingredient DROP COLUMN id;

ALTER TABLE ingredient
ADD PRIMARY KEY (id_uuid);

ALTER TABLE ingredient
RENAME COLUMN id_uuid TO id;


-- menu_item
ALTER TABLE menu_item ADD id_uuid varchar(40);

UPDATE menu_item
set id_uuid = cast(id as varchar(40));

ALTER TABLE menu_item DROP COLUMN id;

ALTER TABLE menu_item
ADD PRIMARY KEY (id_uuid);

ALTER TABLE menu_item
RENAME COLUMN id_uuid TO id;


-- menu_item_command
ALTER TABLE menu_item_command ADD id_uuid varchar(40);

UPDATE menu_item_command
set id_uuid = cast(id as varchar(40));

ALTER TABLE menu_item_command DROP COLUMN id;

ALTER TABLE menu_item_command
ADD PRIMARY KEY (id_uuid);

ALTER TABLE menu_item_command
RENAME COLUMN id_uuid TO id;


-- menu_item_ingredient
ALTER TABLE menu_item_ingredient ADD id_uuid varchar(40);

UPDATE menu_item_ingredient
set id_uuid = cast(id as varchar(40));

ALTER TABLE menu_item_ingredient DROP COLUMN id;

ALTER TABLE menu_item_ingredient
ADD PRIMARY KEY (id_uuid);

ALTER TABLE menu_item_ingredient
RENAME COLUMN id_uuid TO id;


-- menu_item_type
ALTER TABLE menu_item_type ADD id_uuid varchar(40);

UPDATE menu_item_type
set id_uuid = cast(id as varchar(40));

ALTER TABLE menu_item_type DROP COLUMN id;

ALTER TABLE menu_item_type
ADD PRIMARY KEY (id_uuid);

ALTER TABLE menu_item_type
RENAME COLUMN id_uuid TO id;


-- reservation
ALTER TABLE reservation ADD id_uuid varchar(40);

UPDATE reservation
set id_uuid = cast(id as varchar(40));

ALTER TABLE reservation DROP COLUMN id;

ALTER TABLE reservation
ADD PRIMARY KEY (id_uuid);

ALTER TABLE reservation
RENAME COLUMN id_uuid TO id;


-- table_food
ALTER TABLE table_food ADD id_uuid varchar(40);

UPDATE table_food
set id_uuid = cast(id as varchar(40));

ALTER TABLE table_food DROP COLUMN id;

ALTER TABLE table_food
ADD PRIMARY KEY (id_uuid);

ALTER TABLE table_food
RENAME COLUMN id_uuid TO id;


-- table_reservation
ALTER TABLE table_reservation ADD id_uuid varchar(40);

UPDATE table_reservation
set id_uuid = cast(id as varchar(40));

ALTER TABLE table_reservation DROP COLUMN id;

ALTER TABLE table_reservation
ADD PRIMARY KEY (id_uuid);

ALTER TABLE table_reservation
RENAME COLUMN id_uuid TO id;


-- user_attribute
ALTER TABLE user_attribute ADD id_uuid varchar(40);

UPDATE user_attribute
set id_uuid = cast(id as varchar(40));

ALTER TABLE user_attribute DROP COLUMN id;

ALTER TABLE user_attribute
ADD PRIMARY KEY (id_uuid);

ALTER TABLE user_attribute
RENAME COLUMN id_uuid TO id;


-- user_command
ALTER TABLE user_command ADD id_uuid varchar(40);

UPDATE user_command
set id_uuid = cast(id as varchar(40));

ALTER TABLE user_command DROP COLUMN id;

ALTER TABLE user_command
ADD PRIMARY KEY (id_uuid);

ALTER TABLE user_command
RENAME COLUMN id_uuid TO id;


-- user_reservation
ALTER TABLE user_reservation ADD id_uuid varchar(40);

UPDATE user_reservation
set id_uuid = cast(id as varchar(40));

ALTER TABLE user_reservation DROP COLUMN id;

ALTER TABLE user_reservation
ADD PRIMARY KEY (id_uuid);

ALTER TABLE user_reservation
RENAME COLUMN id_uuid TO id;


-- users
ALTER TABLE users ADD id_uuid varchar(40);

UPDATE users
set id_uuid = cast(id as varchar(40));

ALTER TABLE users DROP COLUMN id;

ALTER TABLE users
ADD PRIMARY KEY (id_uuid);

ALTER TABLE users
RENAME COLUMN id_uuid TO id;


----------------------------------- Set all foreign key back ---------------------------------


ALTER TABLE bill
ADD CONSTRAINT users FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE command
ADD CONSTRAINT table_food FOREIGN KEY (table_id) REFERENCES table_food (id);

ALTER TABLE command
ADD CONSTRAINT bill FOREIGN KEY (bill_id) REFERENCES bill (id);

ALTER TABLE feedback_menu_item
ADD CONSTRAINT users FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE menu_item
ADD CONSTRAINT menu_item_type FOREIGN KEY (menu_item_type_id) REFERENCES menu_item_type (id);

ALTER TABLE menu_item_command
ADD CONSTRAINT menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item (id);

ALTER TABLE menu_item_command
ADD CONSTRAINT command FOREIGN KEY (command_id) REFERENCES command (id);

ALTER TABLE menu_item_ingredient
ADD CONSTRAINT menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_item (id);

ALTER TABLE menu_item_ingredient
ADD CONSTRAINT ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient (id);

ALTER TABLE reservation
ADD CONSTRAINT users FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE reservation
ADD CONSTRAINT command FOREIGN KEY (command_id) REFERENCES command (id);

ALTER TABLE table_reservation
ADD CONSTRAINT reservation FOREIGN KEY (reservation_id) REFERENCES reservation (id);

ALTER TABLE table_reservation
ADD CONSTRAINT table_food FOREIGN KEY (table_id) REFERENCES table_food (id);

ALTER TABLE user_attribute
ADD CONSTRAINT users FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_command
ADD CONSTRAINT users FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_command
ADD CONSTRAINT command FOREIGN KEY (command_id) REFERENCES command (id);

ALTER TABLE user_reservation
ADD CONSTRAINT users FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_reservation
ADD CONSTRAINT reservation FOREIGN KEY (reservation_id) REFERENCES reservation (id);