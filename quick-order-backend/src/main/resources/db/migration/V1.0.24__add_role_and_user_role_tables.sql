create table roles
(
  id varchar(40) not null
    constraint roles_pkey
    primary key,
  name varchar(60)
    constraint unique_role
    unique
);

INSERT INTO roles(id,name) VALUES(1,'ROLE_USER');
INSERT INTO roles(id,name) VALUES(2,'ROLE_WAITER');

create table user_roles
(
  user_id varchar(40) not null
    constraint user_id_constraint
    references users,
  role_id varchar(40) not null
    constraint role_id_constraint
    references roles,
  constraint user_roles_pkey
  primary key (user_id, role_id)
);
