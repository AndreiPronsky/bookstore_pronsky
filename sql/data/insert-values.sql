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
VALUES ('Harry Potter and the Philosopher’s Stone', 'J. K. Rowling', '2-1234-5678-1',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 432, 32.90),
       ('Harry Potter and the Chamber of Secrets', 'J. K. Rowling', '2-4343-7678-2',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 480, 32.90),
       ('Harry Potter and the Prisoner of Azkaban', 'J. K. Rowling', '3-0666-8990-3',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 528, 32.90),
       ('Harry Potter and the Goblet of Fire', 'J. K. Rowling', '4-8765-5678-1',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 704, 39.90),
       ('Harry Potter and the Order of the Phoenix', 'J. K. Rowling', '5-2323-4534-8',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 892, 48.90),
       ('Harry Potter and the Half-Blood Prince', 'J. K. Rowling', '2-2222-4567-3',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 672, 39.90),
       ('Harry Potter and the Deathly Hallows', 'J. K. Rowling', '3-4545-7678-9',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 704, 39.90),
       ('Fairy tale', 'Stephen Edwin King', '0-2345-9867-4',
        (SELECT g.id FROM genres g WHERE "name" = 'ROMANCE'),
        (SELECT c.id FROM covers c WHERE "name" = 'SPECIAL'), 487, 27.00),
       ('It', 'Stephen Edwin King', '3-3443-0099-4',
        (SELECT g.id FROM genres g WHERE "name" = 'HORROR'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 515, 32.99),
       ('Doctor Sleep', 'Stephen Edwin King', '2-7878-7866-8',
        (SELECT g.id FROM genres g WHERE "name" = 'THRILLER'),
        (SELECT c.id FROM covers c WHERE "name" = 'SPECIAL'), 498, 32.22),
       ('Mr. Mercedes', 'Stephen Edwin King', '1-1111-2233-5',
        (SELECT g.id FROM genres g WHERE "name" = 'HORROR'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 436, 35.66),
       ('The Holy Bible', ' ', '2-3456-0908-4',
        (SELECT g.id FROM genres g WHERE "name" = 'RELIGION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 612, 44.56),
       ('Glory', 'Noviolet Bulawayo', '5-4335-8787-4',
        (SELECT g.id FROM genres g WHERE "name" = 'FICTION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 675, 56.65),
       ('Liberation Day: Stories', 'George Saunders', '5-4335-8786-3',
        (SELECT g.id FROM genres g WHERE "name" = 'REALISM'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 356, 65.56),
       ('The School for Good Mothers', 'Jessamine Chan', '4-3434-6777-0',
        (SELECT g.id FROM genres g WHERE "name" = 'DYSTOPIAN'),
        (SELECT c.id FROM covers c WHERE "name" = 'SPECIAL'), 123, 23.00),
       ('Ancestor trouble', 'Maud Newton', '2-3456-7890-0',
        (SELECT g.id FROM genres g WHERE "name" = 'MYSTERY'),
        (SELECT c.id FROM covers c WHERE "name" = 'SPECIAL'), 400, 28.35),
       ('Animal Joy', 'Nuar Alsadir', '4-0911-0911-2',
        (SELECT g.id FROM genres g WHERE "name" = 'REALISM'),
        (SELECT c.id FROM covers c WHERE "name" = 'SPECIAL'), 345, 23.12),
       ('The Candy House', 'Jennifer Egan', '0-0000-0001-1',
        (SELECT g.id FROM genres g WHERE "name" = 'MYSTERY'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 468, 54.33),
       ('Six Faces of Globalization', 'A. Roberts and N. Lamp', '0-0666-0666-0',
        (SELECT g.id FROM genres g WHERE "name" = 'REALISM'),
        (SELECT c.id FROM covers c WHERE "name" = 'SOFT'), 455, 45.70),
       ('The Joy of Basketball', 'Ben Detrick', '3-3321-3321-9',
        (SELECT g.id FROM genres g WHERE "name" = 'RELIGION'),
        (SELECT c.id FROM covers c WHERE "name" = 'HARD'), 379, 18.99);

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
