CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(32)
);

CREATE TABLE covers (
    id BIGSERIAL PRIMARY KEY,
    cover_name VARCHAR(32)
);

CREATE TABLE genres (
    id BIGSERIAL PRIMARY KEY,
    genre_name VARCHAR(32)
);

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(128),
    lastname VARCHAR(128),
    email VARCHAR(128) UNIQUE NOT NULL,
    user_password VARCHAR(128) NOT NULL,
    role_id BIGINT REFERENCES roles,
    rating NUMERIC(3, 2)
    );

CREATE TABLE books (
    book_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(128) NOT NULL,
    author VARCHAR(128) NOT NULL,
    isbn VARCHAR(128) UNIQUE NOT NULL,
    genre BIGINT REFERENCES genres,
    cover BIGINT REFERENCES covers,
    pages INTEGER,
    price NUMERIC(5, 2) NOT NULL,
    rating NUMERIC(3, 2)
    );