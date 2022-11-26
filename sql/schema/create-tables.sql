CREATE TABLE roles
(
    roles_id  SERIAL2 PRIMARY KEY,
    role_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE covers
(
    covers_id  SERIAL2 PRIMARY KEY,
    cover_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE genres
(
    genres_id  SERIAL2 PRIMARY KEY,
    genre_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE order_status
(
    status_id   SERIAL2 PRIMARY KEY,
    status_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE payment_method
(
    payment_method_id   SERIAL2 PRIMARY KEY,
    payment_method_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE payment_status
(
    payment_status_id   SERIAL2 PRIMARY KEY,
    payment_status_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE delivery_type
(
    delivery_type_id   SERIAL2 PRIMARY KEY,
    delivery_type_name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE users
(
    user_id       BIGSERIAL PRIMARY KEY,
    firstname     VARCHAR(128),
    lastname      VARCHAR(128),
    email         VARCHAR(128) UNIQUE NOT NULL,
    user_password VARCHAR(128)        NOT NULL,
    role_id       SMALLINT REFERENCES roles,
    rating        NUMERIC(3, 2)
);

CREATE TABLE orders
(
    order_id             BIGSERIAL PRIMARY KEY,
    user_id              BIGINT REFERENCES users NOT NULL,
    order_status         SERIAL2 REFERENCES order_status,
    order_payment_method SERIAL2 REFERENCES payment_method,
    order_payment_status SERIAL2 REFERENCES payment_status,
    order_delivery_type  SERIAL2 REFERENCES delivery_type
);

CREATE TABLE books
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

CREATE TABLE order_info
(
    order_id   BIGINT REFERENCES orders,
    book_id    BIGINT REFERENCES books,
    quantity   SMALLINT,
    book_price NUMERIC(5, 2) REFERENCES books,
    order_cost NUMERIC(6, 2) NOT NULL
);