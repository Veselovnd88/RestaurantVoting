DELETE
FROM user_role;

DELETE
FROM users;

DELETE
FROM dish;
DELETE FROM vote;
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
VALUES ('SushiWok'), --3
       ('DoDoPizza'), --4
       ('BurgerPizza'); --5

INSERT INTO dish (name, price, added_at, restaurant_id)
VALUES ('Unagi', 40000, now(), 100003), --6
       ('Philadelphia', 30000, now(), 100003), --7
       ('TastyRoll', 55000, now(), 100003), --8
       ('Pizza Arriva', 60000, now(), 100004), --9
       ('Diablo Pizza', 85000, now(), 100004), --10
       ('Margarita', 55000, now(), 100004), --11
       ('Double Burger', 65000, now(), 100005), --12
       ('Triple Burger', 75000, now(), 100005), --13
       ('Fries Potato', 35000, now(), 100005); --14

INSERT INTO vote (voted_at, vote_day, user_id, restaurant_id)
VALUES (now(), now(), 100000, 100004), --15
       (now(), now(), 100001, 100004), --16
       (now(), now(), 100002, 100004), --17
      (now(), now(), 100000, 100003) --18


