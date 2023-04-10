ALTER TABLE users
add password varchar not null ,
    add role varchar not null ;

insert into users (password,role) values ('1234','ROLE_USER') where user_id = 1;
insert into users (password,role) values ('4321','ROLE_ADMIN') where user_id = 2;