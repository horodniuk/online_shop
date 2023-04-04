CREATE TABLE IF NOT EXISTS users
(
    user_id    serial primary key,
    first_name varchar not null,
    last_name  varchar not null,
    email      varchar not null,
    balance    decimal
);

CREATE TABLE IF NOT EXISTS user_cart
(
    cart_id   bigserial primary key,
    user_id   bigint references users (user_id),
    constraint fk_user_cart_user_id foreign key (user_id)
        references users (user_id)
);

CREATE TABLE IF NOT EXISTS products
(
    product_id bigserial primary key,
    name       varchar(255),
    price      numeric(19, 2)
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id   serial primary key,
    order_date timestamp,
    cart_id    bigint references user_cart (cart_id),
    constraint fk_order_cart_id foreign key (cart_id)
        references user_cart (cart_id)
);

--fill products
INSERT INTO products (name, price)
VALUES ('Head First Java', 20.00),
       ('Effective Java', 25.00),
       ('Java Concurrency', 30.00),
       ('Clean Code', 35.00),
       ('Thinking in Java', 40.00);