INSERT INTO users(username,password,email, id) VALUES('quick_user',
'$2a$10$WLB7YVjd1t6KVJawpysv5exFt3yWQ8A.ZVHXC/b0kHRpOc7yZCgtG','quick_user@yahoo.com',1);
INSERT INTO users(username,password,email, id) VALUES('quick_waiter',
'$2a$10$WLB7YVjd1t6KVJawpysv5exFt3yWQ8A.ZVHXC/b0kHRpOc7yZCgtG','quick_waiter@yahoo.com',2);
INSERT INTO user_roles(user_id,role_id) VALUES(1,1);
INSERT INTO user_roles(user_id,role_id) VALUES(2,2);