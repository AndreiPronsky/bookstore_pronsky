CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL4 PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS covers
(
    id   SERIAL4 PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS genres
(
    id   SERIAL4 PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS order_status
(
    id   SERIAL4 PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS payment_method
(
    id   SERIAL4 PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS payment_status
(
    id   SERIAL4 PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS delivery_type
(
    id   SERIAL4 PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
    id               BIGSERIAL PRIMARY KEY,
    firstname        VARCHAR(128),
    lastname         VARCHAR(128),
    email            VARCHAR(128) UNIQUE NOT NULL,
    "password"       VARCHAR(128)        NOT NULL,
    role_id          SERIAL4 REFERENCES roles,
    rating           NUMERIC(3, 2),
    preferred_locale VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS orders
(
    id                BIGSERIAL PRIMARY KEY,
    user_id           BIGINT REFERENCES users NOT NULL,
    status_id         SERIAL4 REFERENCES order_status,
    payment_method_id SERIAL4 REFERENCES payment_method,
    payment_status_id SERIAL4 REFERENCES payment_status,
    delivery_type_id  SERIAL4 REFERENCES delivery_type,
    cost              NUMERIC(6, 2)
);

CREATE TABLE IF NOT EXISTS books
(
    id        BIGSERIAL PRIMARY KEY,
    title     VARCHAR(128)        NOT NULL,
    author    VARCHAR(128)        NOT NULL,
    isbn      VARCHAR(128) UNIQUE NOT NULL,
    genre_id  SERIAL4 REFERENCES genres,
    cover_id  SERIAL4 REFERENCES covers,
    pages     INTEGER,
    price     NUMERIC(5, 2)       NOT NULL,
    rating    NUMERIC(3, 2),
    available BOOLEAN             NOT NULL DEFAULT false,
    file_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS order_items
(
    id       BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders,
    book_id  BIGINT REFERENCES books,
    quantity SERIAL4,
    price    NUMERIC(5, 2)
);
