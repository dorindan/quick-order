ALTER TABLE menu_item_command DROP COLUMN status;
ALTER TABLE menu_item_command ADD amount INTEGER;
ALTER TABLE menu_item_command ADD id VARCHAR(40);

ALTER TABLE menu_item_command DROP CONSTRAINT menu_item_command_pkey;

ALTER TABLE menu_item_command
ADD CONSTRAINT FK_menu_item_command PRIMARY KEY (id);

