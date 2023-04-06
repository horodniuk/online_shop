CREATE TABLE users (
       user_id            BIGINT       NOT NULL GENERATED ALWAYS AS IDENTITY,
       first_name         VARCHAR(255) NOT NULL,
       last_name          VARCHAR(255) NOT NULL,
       email              VARCHAR(255) NOT NULL,
       balance            DOUBLE PRECISION,
       PRIMARY KEY (user_id)
);

CREATE TABLE products (
      product_id          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
      name                VARCHAR(255),
      price               DOUBLE PRECISION,
      PRIMARY KEY (product_id)
);

CREATE TABLE orders (
        order_id         BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
        order_date       TIMESTAMP,
        user_id          BIGINT NOT NULL,
        PRIMARY KEY (order_id),
        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE order_product (
       id               BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
       order_id         BIGINT NOT NULL,
       product_id       BIGINT NOT NULL,
       quantity         INT,
       PRIMARY KEY (id),
       FOREIGN KEY (order_id) REFERENCES orders(order_id),
       FOREIGN KEY (product_id) REFERENCES products(product_id)
);


insert into users (first_name, last_name, email, balance) values
            ('Ivan', 'Ivanov', 'ivanov@gmail.com', 200.00),
            ('Oleg', 'Olegov', 'olegov@gmail.com', 200.00);

insert into products (name, price) VALUES
            ('beer', 50.00),
            ('cola', 30.00),
            ('soap', 20.00);

insert into orders (order_date, user_id) VALUES
             ('2023-04-01 10:00:00', 1),
             ('2023-04-02 11:00:00', 1),
             ('2023-04-03 12:00:00', 2),
             ('2023-04-04 13:00:00', 2);

INSERT INTO order_product (order_id, product_id, quantity) values
               (1, 1, 2),
               (1, 2, 1),
               (2, 1, 1),
               (2, 2, 3);

