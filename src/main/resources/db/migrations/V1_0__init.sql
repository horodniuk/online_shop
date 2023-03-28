CREATE TABLE IF NOT EXISTS users(
    user_id    serial       primary key,
    first_name varchar      not null,
    last_name  varchar      not null,
    email      varchar      not null,
    balance    numeric
);

CREATE TABLE IF NOT EXISTS products(
    product_id serial      primary key,
    name       varchar     not null,
    price      numeric     not null
);

CREATE TABLE IF NOT EXISTS cart(
    cart_id    serial       primary key,
    user_id    bigint       not null references users (user_id),
    product_id bigint       not null references products (product_id),
    quantity   integer      not null
);

CREATE TABLE IF NOT EXISTS orders(
    order_id   serial       primary key,
    order_date timestamp    not null,
    user_id    bigint       not null references users (user_id),
    cost       numeric      not null
);


--fill products
INSERT INTO products (name, price)
VALUES
    ('Head First Java', 20.00),
    ('Effective Java', 25.00),
    ('Java Concurrency', 30.00),
    ('Clean Code', 35.00),
    ('Thinking in Java', 40.00);