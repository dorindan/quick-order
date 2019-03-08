--UserAttribute

CREATE TABLE user_attribute  (
	id                        serial primary key,
	user_id			              int references users(id),
  language                  varchar (5)
);
