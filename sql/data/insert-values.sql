INSERT INTO roles ("name")
VALUES ('USER'),
       ('ADMIN'),
       ('MANAGER');

INSERT INTO covers ("name")
VALUES ('SOFT'),
       ('HARD'),
       ('SPECIAL');

INSERT INTO genres ("name")
VALUES ('FICTION'),
       ('MYSTERY'),
       ('THRILLER'),
       ('HORROR'),
       ('HISTORICAL'),
       ('ROMANCE'),
       ('WESTERN'),
       ('FLORISTICS'),
       ('SCIENCE FICTION'),
       ('DYSTOPIAN'),
       ('REALISM'),
       ('RELIGION'),
       ('MEDICINE'),
       ('ENGINEERING'),
       ('ART');

INSERT INTO order_status ("name")
VALUES ('OPEN'),
       ('CONFIRMED'),
       ('COMPLETED'),
       ('CANCELLED');

INSERT INTO payment_method ("name")
VALUES ('CASH'),
       ('CARD'),
       ('BANK_TRANSFER'),
       ('CASH_TO_COURIER'),
       ('CARD_TO_COURIER');

INSERT INTO payment_status ("name")
VALUES ('UNPAID'),
       ('FAILED'),
       ('PAID'),
       ('REFUNDED');

INSERT INTO delivery_type ("name")
VALUES ('COURIER'),
       ('BIKE'),
       ('CAR'),
       ('MAIL'),
       ('SELF_PICKUP');

INSERT INTO users (firstname, lastname, email, "password", role_id)
VALUES ('Vladimir', 'Ulyanov', 'bolshevik1870@mail.ussr', '5695476E3B59B2294B494AC032DE1BBBD7A379B7',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('Bob', 'Marley', 'reggaeKing45@ja.maica', '44E9E72697F6A934F000328A4522C713F9B4BAEF',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('Tom', 'Marvolo Riddle', 'avadakedavra@hogwarts.uk', 'DBEEBA629BEE9D14CBC4764D4CE65E1F860B3175',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('Adolph', 'Hitler', 'adolph89@reich.de', 'E89DE20E996B8FA4572A01CC74388D4F7080A989',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('Arnold', 'Schwarzenegger', 'steelMuscles@gym.com', 'E87E13D3724506C8388E5D11F19798BC2288E2B2',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('Tim', 'Burton', 'tim_burton@mail.com', '9997E905C454768D9C510A7E62645BACB83E465E',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('Angelina', 'Jolie', 'angelina@hollywood.ru', 'BF0290A78BD54A9347C329387D89631B679D8870',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('Brad', 'Pitt', 'loveAngelina@ex-wife.com', '6208E2ED808FFACE07279B18A5422C02C6B3911F',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('Marilyn', 'Monroe', 'normaBaker@yandex.by', '85201D58046A2FD9C11A3B3935EB5A42F7CC57B8',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('Marilyn', 'Manson', 'wannaBeSomebody@gmail.ru', '5F70C1DFDE13B69FEBAF5E34AF6EF94B20328BA5',
        (SELECT r.id FROM roles r WHERE r.name = 'USER')),
       ('User', 'User', 'user@mail.com', '12DEA96FEC20593566AB75692C9949596833ADC9',
        (SELECT r.id FROM roles r WHERE "name" = 'USER')),
       ('Admin', 'Admin', 'admin@mail.com', 'D033E22AE348AEB5660FC2140AEC35850C4DA997',
        (SELECT r.id FROM roles r WHERE "name" = 'ADMIN')),
       ('Manager', 'Manager', 'manager@mail.com', '1A8565A9DC72048BA03B4156BE3E569F22771F23',
        (SELECT r.id FROM roles r WHERE "name" = 'MANAGER'));

INSERT INTO books (title, author, isbn, genre_id, cover_id, pages, price)
VALUES ('Revolution for workers and peasants', 'Karl Heinrich Marx', '2-1234-5678-1',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'SOFT'), 1200, 34.25),
       ('Lets plant together', 'Yurkovskaya Tatyana', '2-4343-7678-2',
        (SELECT g.id FROM genres g WHERE "name" = 'FLORISTICS'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 300, 15.34),
       ('Best spells to kill a teenager', 'Amayak Akopyan', '3-0666-8990-3',
        (SELECT g.id FROM genres g WHERE "name" = 'WESTERN'),
        (SELECT c.id FROM covers c WHERE "name" = 'SOFT'), 432, 28.67),
       ('Storm of Winter Palace for dummies', 'Francesco Rastrelli', '4-8765-5678-1',
        (SELECT g.id FROM genres g WHERE "name" = 'HISTORICAL'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 34, 15.15),
       ('Professional Genocide', 'Lucius Marcius Censorinus', '5-2323-4534-8',
        (SELECT g.id FROM genres g WHERE "name" = 'HISTORICAL'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 476, 45.44),
       ('Selfportrait for beginners', 'Vincent van Gogh', '2-2222-4567-3',
        (SELECT g.id FROM genres g WHERE "name" = 'ART'),
        (SELECT c.id FROM covers c WHERE "name" = 'SOFT'), 566, 66.23),
       ('Fundamentals of robotics', 'James Cameron', '3-4545-7678-9',
        (SELECT g.id FROM genres g WHERE "name" = 'ENGINEERING'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 452, 33.21),
       ('Classification of alien predators', 'Ridley Scott', '0-2345-9867-4',
        (SELECT g.id FROM genres g WHERE "name" = 'MEDICINE'),
        (SELECT c.id FROM covers c WHERE "name" = 'SPECIAL'), 231, 23.00),
       ('Easy way to become governor', 'Sergey Sobyanin', '3-3443-0099-4',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'SOFT'), 76, 11.01),
       ('Chocolate fabrics', 'Pyotr Poroshenko', '2-7878-7866-8',
        (SELECT g.id FROM genres g WHERE "name" = 'DYSTOPIAN'),
        (SELECT c.id FROM covers c WHERE "name" = 'SPECIAL'), 290, 32.22),
       ('Trending haircuts for victims', 'Sweeney Todd', '1-1111-2233-5',
        (SELECT g.id FROM genres g WHERE "name" = 'ART'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 343, 35.66),
       ('Adoption and law', 'Lemony Snicket', '2-3456-0908-4',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'SOFT'), 451, 44.56),
       ('Staying sexy after 45', 'Monica Bellucci', '5-4335-8787-4',
        (SELECT g.id FROM genres g WHERE "name" = 'ROMANCE'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 675, 56.65),
       ('Staying hot after 55', 'Jose Antonio Dominguez Banderas', '5-4335-8786-3',
        (SELECT g.id FROM genres g WHERE "name" = 'REALISM'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 879, 65.56),
       ('Doctors and borders', 'Victor Frankenstein', '4-3434-6777-0',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'SOFT'), 123, 23.00),
       ('Lightweight fabrics and aerodynamics', 'Wilbur & Orville Wright', '2-3456-7890-0',
        (SELECT g.id FROM genres g WHERE "name" = 'ENGINEERING'),
        (SELECT c.id FROM covers c WHERE "name" = 'SPECIAL'), 244, 28.35),
       ('Counting dosage', 'Elvis Presley', '4-0911-0911-2',
        (SELECT g.id FROM genres g WHERE "name" = 'MEDICINE'),
        (SELECT c.id FROM covers c WHERE "name" = 'SPECIAL'), 135, 23.12),
       ('Rib amputation', 'Adam Godson', '0-0000-0001-1',
        (SELECT g.id FROM genres g WHERE "name" = 'MEDICINE'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 468, 54.33),
       ('Satan bible', 'Anton Szandor LaVey', '0-0666-0666-0',
        (SELECT g.id FROM genres g WHERE "name" = 'RELIGION'),
        (SELECT c.id FROM covers c WHERE "name" = 'SOFT'), '666', 66.60),
       ('Reggae as a way of life', 'Jah', '3-3321-3321-9',
        (SELECT g.id FROM genres g WHERE "name" = 'RELIGION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 79, 18.99);

INSERT INTO orders (user_id, status_id, payment_method_id, payment_status_id, delivery_type_id)
VALUES (1, 2, 1, 3, 5),
       (2, 3, 4, 3, 2),
       (3, 1, 4, 1, 4),
       (4, 4, 5, 2, 2),
       (5, 3, 2, 3, 5),
       (6, 3, 3, 3, 1),
       (7, 3, 2, 3, 3),
       (8, 4, 5, 1, 5),
       (9, 4, 1, 1, 5),
       (10, 2, 2, 3, 4);

SELECT * FROM order_items;
