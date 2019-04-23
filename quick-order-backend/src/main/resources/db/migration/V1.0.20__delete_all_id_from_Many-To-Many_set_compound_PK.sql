-- menu_item - command
ALTER TABLE menu_item_command DROP COLUMN id;

ALTER TABLE menu_item_command
ADD PRIMARY KEY (menu_item_id, command_id);

-- menu_item - ingredient
ALTER TABLE menu_item_ingredient
ADD PRIMARY KEY (menu_item_id, ingredient_id);

-- table - reservation
ALTER TABLE table_reservation DROP COLUMN id;

ALTER TABLE table_reservation
ADD PRIMARY KEY (table_id, reservation_id);

-- user - command
ALTER TABLE user_command DROP COLUMN id;

ALTER TABLE user_command
ADD PRIMARY KEY (user_id, command_id);

-- user - reservation
ALTER TABLE user_reservation DROP COLUMN id;

ALTER TABLE user_reservation
ADD PRIMARY KEY (user_id, reservation_id);
