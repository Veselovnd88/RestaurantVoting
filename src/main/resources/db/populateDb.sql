DELETE
FROM user_role;

DELETE
FROM users;
DELETE
FROM menu_dish;
DELETE
FROM menu;
DELETE
FROM dish;
DELETE
FROM vote;
DELETE
FROM restaurant;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User1', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('User2', 'user2@gmail.com', 'another');

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

INSERT INTO dish (name, price)
VALUES ('Unagi', 40000),         --9
       ('Philadelphia', 30000),  --10
       ('TastyRoll', 55000),     --11
       ('Pizza Arriva', 60000),  --12
       ('Diablo Pizza', 85000),  --13
       ('Margarita', 55000),     --14
       ('Double Burger', 65000), --15
       ('Triple Burger', 75000), --16
       ('Fries Potato', 35000); --17

INSERT INTO menu_dish(menu_id, dish_id)
VALUES (100006, 100009),
       (100006, 100010),
       (100006, 100011),
       (100007, 100012),
       (100007, 100013),
       (100007, 100014),
       (100008, 100015),
       (100008, 100016),
       (100008, 100017);

INSERT INTO vote (user_id, restaurant_id)
VALUES (100000, 100004), --18
       (100001, 100004), --19
       (100002, 100004), --20
       (100000, 100003) --21
