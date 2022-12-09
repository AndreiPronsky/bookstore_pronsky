CREATE TABLE IF NOT EXISTS roles
(
    roles_id  SERIAL2 PRIMARY KEY,
    role_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS covers
(
    covers_id  SERIAL2 PRIMARY KEY,
    cover_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS genres
(
    genres_id  SERIAL2 PRIMARY KEY,
    genre_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS order_status
(
    status_id   SERIAL2 PRIMARY KEY,
    status_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS payment_method
(
    payment_method_id   SERIAL2 PRIMARY KEY,
    payment_method_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS payment_status
(
    payment_status_id   SERIAL2 PRIMARY KEY,
    payment_status_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS delivery_type
(
    delivery_type_id   SERIAL2 PRIMARY KEY,
    delivery_type_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
    user_id       BIGSERIAL PRIMARY KEY,
    firstname     VARCHAR(128),
    lastname      VARCHAR(128),
    email         VARCHAR(128) UNIQUE NOT NULL,
    user_password VARCHAR(128)        NOT NULL,
    role_id       SMALLINT REFERENCES roles,
    rating        NUMERIC(3, 2)
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id             BIGSERIAL PRIMARY KEY,
    user_id              BIGINT REFERENCES users NOT NULL,
    status         SERIAL2 REFERENCES order_status,
    payment_method SERIAL2 REFERENCES payment_method,
    payment_status SERIAL2 REFERENCES payment_status,
    delivery_type  SERIAL2 REFERENCES delivery_type,
    cost NUMERIC(6, 2)
);

CREATE TABLE IF NOT EXISTS books
(
    book_id BIGSERIAL PRIMARY KEY,
    title   VARCHAR(128)        NOT NULL,
    author  VARCHAR(128)        NOT NULL,
    isbn    VARCHAR(128) UNIQUE NOT NULL,
    genre   SMALLINT REFERENCES genres,
    cover   SMALLINT REFERENCES covers,
    pages   INTEGER,
    price   NUMERIC(5, 2)       NOT NULL,
    rating  NUMERIC(3, 2)
);

CREATE TABLE IF NOT EXISTS order_items
(
    item_id BIGSERIAL PRIMARY KEY,
    order_id   BIGINT REFERENCES orders,
    book_id    BIGINT REFERENCES books,
    quantity   SMALLINT,
    price NUMERIC(5, 2)
);

-- DROP TABLE orders;
-- DROP TABLE order_items;
-- DROP TABLE users CASCADE ;