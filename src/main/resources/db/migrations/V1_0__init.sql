CREATE TABLE users(
    user_id                 int  not null generated always as identity,
    first_name              varchar not null,
    last_name               varchar not null,
    email                   varchar not null,
    balance                 double precision,
    password                varchar,
    role                    varchar ,
    PRIMARY KEY (user_id)
);

CREATE TABLE products(
    product_id              int  not null generated always as identity,
    name                    varchar not null,
    price                   double precision,
    PRIMARY KEY (product_id)
);

CREATE TABLE orders(
    order_id                int  not null generated always as identity,
    order_date              timestamp,
    user_id                 int not null,
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id) references users (user_id)
);

CREATE TABLE order_product(
    order_id                int not null,
    product_id              int not null,
    quantity                int,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) references orders (order_id),
    FOREIGN KEY (product_id) references products (product_id)
);


insert into users (first_name, last_name, email, balance, password, role)
values ('Ivan', 'Ivanov', 'ivanov@gmail.com', 200.00, '1234','ROLE_USER'),
       ('Oleg', 'Olegov', 'olegov@gmail.com', 100.00, '4321','ROLE_ADMIN' );

insert into products (name, price)
VALUES ('beer', 50.00),
       ('cola', 30.00),
       ('soap', 20.00);

insert into orders (order_date, user_id)
VALUES ('2023-04-01 10:00:00', 1),
       ('2023-04-02 11:00:00', 1),
       ('2023-04-03 12:00:00', 2),
       ('2023-04-04 13:00:00', 2);

INSERT INTO order_product (order_id, product_id, quantity)
values (1, 1, 2),
       (1, 2, 1),
       (2, 1, 1),
       (2, 2, 3);