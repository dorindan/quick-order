INSERT INTO users(username,password,email, id) VALUES('quick_user',
'$2a$10$/TVePP2x5TTGFM.xCsnjYu8JNtQ4KT43VXbd5rP19J1e1j3z6Ge82','quickorder.user@yandex.com',1);
INSERT INTO users(username,password,email, id) VALUES('quick_waiter',
'$2a$10$/TVePP2x5TTGFM.xCsnjYu8JNtQ4KT43VXbd5rP19J1e1j3z6Ge82','quickorder.user@yandex.com',2);
INSERT INTO user_roles(user_id,role_id) VALUES(1,1);
INSERT INTO user_roles(user_id,role_id) VALUES(2,2);