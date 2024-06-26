DELETE
FROM user_role;

DELETE
FROM users;

DELETE
FROM dish;

DELETE
FROM vote;

DELETE
FROM menu;


DELETE
FROM restaurant;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User1', 'user@yandex.ru', '$2a$10$9uj7bHjBG/mJ612n7WIw8eFTLZPb6AplrSLk3SNlXwM0Gx3fXv6HW'), --password
       ('Admin', 'admin@gmail.com', '$2a$10$hJ715fui1n.CAgq81eAH.u5wz2WHGoAO3iudAiPpzeW61YsOjSA7m'), --admin
       ('User2', 'user2@gmail.com', '$2a$10$Y9AkgdJzKBoDBNDDRA/fqe1UqWBADSdjdh7xTWx9f5Fc/qLW8Mf9y'); --another

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001),
       ('USER', 100002);

INSERT INTO restaurant (name)
VALUES ('SushiWok'),  --3
       ('DoDoPizza'), --4
       ('BurgerPizza'); --5

INSERT INTO menu(restaurant_id)
VALUES (100003), --6
       (100004), --7
       (100005); --8

INSERT INTO dish (name, price, menu_id)
VALUES ('Unagi', 40000, 100006),         --9
       ('Philadelphia', 30000, 100006),  --10
       ('TastyRoll', 55000, 100006),     --11
       ('Pizza Arriva', 60000, 100007),  --12
       ('Diablo Pizza', 85000, 100007),  --13
       ('Margarita', 55000, 100007),     --14
       ('Double Burger', 65000, 100008), --15
       ('Triple Burger', 75000, 100008), --16
       ('Fries Potato', 35000, 100008); --17

INSERT INTO vote (user_id, restaurant_id)
VALUES (100000, 100003), --18
       (100001, 100003), --19
       (100002, 100003); --20

INSERT INTO users (name, email, password)
VALUES
    ('User3', 'user3@gmail.com', '$2a$10$Y9AkgdJzKBoDBNDDRA/fqe1UqWBADSdjdh7xTWx9f5Fc/qLW8Mf9y'); --another id 21

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100021);