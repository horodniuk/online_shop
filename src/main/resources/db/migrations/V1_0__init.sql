CREATE TABLE users(
    user_id                 int  not null generated always as identity,
    first_name              varchar not null,
    last_name               varchar not null,
    email                   varchar not null,
    balance                 double precision,
    password                varchar,
    role                    varchar ,
    is_blocked              boolean default false,
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
