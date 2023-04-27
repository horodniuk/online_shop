
insert into users (first_name, last_name, email, balance, password, role)
values ('Ivan', 'Ivanov', 'ivanov@gmail.com', 200.00, '123','USER') ,
       ('Oleg', 'Olegov', 'olegov@gmail.com', 100.00, '123','ADMIN'),
       ('Sergey', 'Petrov', 'petrov@gmail.com', 100.00, '123','SUPER_ADMIN');

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