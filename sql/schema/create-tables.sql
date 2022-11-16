CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(128),
    lastname VARCHAR(128),
    email VARCHAR(128) UNIQUE NOT NULL,
    user_password VARCHAR(128) NOT NULL,
    user_role VARCHAR(128),
    rating NUMERIC(3, 2)
    );

CREATE TABLE books (
    book_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(128) NOT NULL,
    author VARCHAR(128) NOT NULL,
    isbn VARCHAR(128) UNIQUE NOT NULL,
    cover VARCHAR(128),
    pages INTEGER,
    price NUMERIC(5, 2) NOT NULL,
    rating NUMERIC(3, 2)
    );