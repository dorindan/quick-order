ALTER TABLE users
DROP COLUMN feedback_menu_item_id;


ALTER TABLE feedback_menu_item
ADD COLUMN user_id int references users(id);

