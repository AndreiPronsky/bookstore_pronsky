INSERT INTO roles (role_name)
VALUES ('USER'),
       ('ADMIN'),
       ('MANAGER');

INSERT INTO covers (cover_name)
VALUES ('SOFT'),
       ('HARD'),
       ('SPECIAL');

INSERT INTO genres (genre_name)
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

INSERT INTO order_status (status_name)
VALUES ('OPEN'),
       ('CONFIRMED'),
       ('COMPLETED'),
       ('CANCELLED');

INSERT INTO payment_method (payment_method_name)
VALUES ('CASH'),
       ('CARD'),
       ('BANK_TRANSFER'),
       ('CASH_TO_COURIER'),
       ('CARD_TO_COURIER');

INSERT INTO payment_status (payment_status_name)
VALUES ('UNPAID'),
       ('FAILED'),
       ('PAID'),
       ('REFUNDED');

INSERT INTO delivery_type (delivery_type_name)
VALUES ('COURIER'),
       ('BIKE'),
       ('CAR'),
       ('MAIL'),
       ('SELF_PICKUP');

INSERT INTO users (firstname, lastname, email, user_password, role_id)
VALUES ('Vladimir', 'Ulyanov', 'bolshevik1870@mail.ussr', 'RSDRP4ever',
        (SELECT roles_id FROM roles WHERE role_name = 'USER')),
       ('Bob', 'Marley', 'reggaeKing45@ja.maica', 'SunIsShining',
        (SELECT roles_id FROM roles WHERE role_name = 'USER')),
       ('Tom', 'Marvolo Riddle', 'avadakedavra@hogwarts.uk', 'iAmLordVoldemort',
        (SELECT roles_id FROM roles WHERE role_name = 'USER')),
       ('Adolph', 'Hitler', 'adolph89@reich.de', 'meinKampf',
        (SELECT roles_id FROM roles WHERE role_name = 'USER')),
       ('Arnold', 'Schwarzenegger', 'steelMuscles@gym.com', 'IllBeBack',
        (SELECT roles_id FROM roles WHERE role_name = 'USER')),
       ('Tim', 'Burton', 'tim_burton@mail.com', 'Frankenweenie',
        (SELECT roles_id FROM roles WHERE role_name = 'USER')),
       ('Angelina', 'Jolie', 'angelina@hollywood.ru', 'HelpChildrenWorlwide',
        (SELECT roles_id FROM roles WHERE role_name = 'USER')),
       ('Brad', 'Pitt', 'loveAngelina@ex-wife.com', 'ISecretlyStillLoveJennifer',
        (SELECT roles_id FROM roles WHERE role_name = 'USER')),
       ('Marilyn', 'Monroe', 'normaBaker@yandex.by', 'pupidupu',
        (SELECT roles_id FROM roles WHERE role_name = 'USER')),
       ('Marilyn', 'Manson', 'wannaBeSomebody@gmail.ru', 'sweetDreams69',
        (SELECT roles_id FROM roles WHERE role_name = 'USER'));

INSERT INTO books (title, author, isbn, genre, cover, pages, price)
VALUES ('Revolution for workers and peasants', 'Karl Heinrich Marx', '2-1234-5678-1',
        (SELECT genres_id FROM genres WHERE genre_name = 'FICTION'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SOFT'), 1200, 34.25),
       ('Lets plant together', 'Yurkovskaya Tatyana', '2-4343-7678-2',
        (SELECT genres_id FROM genres WHERE genre_name = 'FLORISTICS'),
        (SELECT covers_id FROM covers WHERE cover_name = 'HARD'), 300, 15.34),
       ('Best spells to kill a teenager', 'Amayak Akopyan', '3-0666-8990-3',
        (SELECT genres_id FROM genres WHERE genre_name = 'WESTERN'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SOFT'), 432, 28.67),
       ('Storm of Winter Palace for dummies', 'Francesco Rastrelli', '4-8765-5678-1',
        (SELECT genres_id FROM genres WHERE genre_name = 'HISTORICAL'),
        (SELECT covers_id FROM covers WHERE cover_name = 'HARD'), 34, 15.15),
       ('Professional Genocide', 'Lucius Marcius Censorinus', '5-2323-4534-8',
        (SELECT genres_id FROM genres WHERE genre_name = 'HISTORICAL'),
        (SELECT covers_id FROM covers WHERE cover_name = 'HARD'), 476, 45.44),
       ('Selfportrait for beginners', 'Vincent van Gogh', '2-2222-4567-3',
        (SELECT genres_id FROM genres WHERE genre_name = 'ART'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SOFT'), 566, 66.23),
       ('Fundamentals of robotics', 'James Cameron', '3-4545-7678-9',
        (SELECT genres_id FROM genres WHERE genre_name = 'ENGINEERING'),
        (SELECT covers_id FROM covers WHERE cover_name = 'HARD'), 452, 33.21),
       ('Classification of alien predators', 'Ridley Scott', '0-2345-9867-4',
        (SELECT genres_id FROM genres WHERE genre_name = 'MEDICINE'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SPECIAL'), 231, 23.00),
       ('Easy way to become governor', 'Sergey Sobyanin', '3-3443-0099-4',
        (SELECT genres_id FROM genres WHERE genre_name = 'FICTION'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SOFT'), 76, 11.01),
       ('Chocolate fabrics', 'Pyotr Poroshenko', '2-7878-7866-8',
        (SELECT genres_id FROM genres WHERE genre_name = 'DYSTOPIAN'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SPECIAL'), 290, 32.22),
       ('Trending haircuts for victims', 'Sweeney Todd', '1-1111-2233-5',
        (SELECT genres_id FROM genres WHERE genre_name = 'ART'),
        (SELECT covers_id FROM covers WHERE cover_name = 'HARD'), 343, 35.66),
       ('Adoption and law', 'Lemony Snicket', '2-3456-0908-4',
        (SELECT genres_id FROM genres WHERE genre_name = 'FICTION'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SOFT'), 451, 44.56),
       ('Staying sexy after 45', 'Monica Bellucci', '5-4335-8787-4',
        (SELECT genres_id FROM genres WHERE genre_name = 'ROMANCE'),
        (SELECT covers_id FROM covers WHERE cover_name = 'HARD'), 675, 56.65),
       ('Staying hot after 55', 'Jose Antonio Dominguez Banderas', '5-4335-8786-3',
        (SELECT genres_id FROM genres WHERE genre_name = 'REALISM'),
        (SELECT covers_id FROM covers WHERE cover_name = 'HARD'), 879, 65.56),
       ('Doctors and borders', 'Victor Frankenstein', '4-3434-6777-0',
        (SELECT genres_id FROM genres WHERE genre_name = 'FICTION'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SOFT'), 123, 23.00),
       ('Lightweight fabrics and aerodynamics', 'Wilbur & Orville Wright', '2-3456-7890-0',
        (SELECT genres_id FROM genres WHERE genre_name = 'ENGINEERING'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SPECIAL'), 244, 28.35),
       ('Counting dosage', 'Elvis Presley', '4-0911-0911-2',
        (SELECT genres_id FROM genres WHERE genre_name = 'MEDICINE'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SPECIAL'), 135, 23.12),
       ('Rib amputation', 'Adam Godson', '0-0000-0001-1',
        (SELECT genres_id FROM genres WHERE genre_name = 'MEDICINE'),
        (SELECT covers_id FROM covers WHERE cover_name = 'HARD'), 468, 54.33),
       ('Satan bible', 'Anton Szandor LaVey', '0-0666-0666-0',
        (SELECT genres_id FROM genres WHERE genre_name = 'RELIGION'),
        (SELECT covers_id FROM covers WHERE cover_name = 'SOFT'), '666', 66.60),
       ('Reggae as a way of life', 'Jah', '3-3321-3321-9',
        (SELECT genres_id FROM genres WHERE genre_name = 'RELIGION'),
        (SELECT covers_id FROM covers WHERE cover_name = 'HARD'), 79, 18.99);

INSERT INTO orders (user_id, order_status, order_payment_method, order_payment_status, order_delivery_type)
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
