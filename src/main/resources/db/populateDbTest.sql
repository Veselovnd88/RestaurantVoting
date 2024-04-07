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

INSERT INTO users (name, email, password, registered)
VALUES ('User1', 'user@yandex.ru', 'password', '2024-03-06 00:00:01'),
       ('Admin', 'admin@gmail.com', 'admin', '2024-03-06 00:00:01'),
       ('User2', 'user2@gmail.com', 'another', '2024-03-06 00:00:01');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001),
       ('USER', 100002);

INSERT INTO restaurant (name)
VALUES ('SushiWok'),  --3
       ('DoDoPizza'), --4
       ('BurgerPizza'); --5

INSERT INTO menu(restaurant_id, added_at)
VALUES (100003, '2024-03-06'), --6
       (100004, '2024-03-06'), --7
       (100005, '2024-03-06'); --8

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

INSERT INTO vote (user_id, voted_at, menu_id)
VALUES (100000, '2024-03-06', 100006), --18
       (100001, '2024-03-06', 100006), --19
       (100002, '2024-03-06', 100006), --20
       (100000, '2024-03-06', 100007) --21
