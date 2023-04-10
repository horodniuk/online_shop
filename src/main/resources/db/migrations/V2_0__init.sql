ALTER TABLE users
add password varchar not null ,
    add role varchar not null;

insert into users (password,role)
values
    ('1234','ROLE_USER'),
    ('4321','ROLE_ADMIN');