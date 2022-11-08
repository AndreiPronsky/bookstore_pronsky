CREATE TABLE users (
user_id BIGSERIAL PRIMARY KEY,
firstname VARCHAR(128),
lastname VARCHAR(128),
email VARCHAR(128) UNIQUE NOT NULL,
user_password VARCHAR(128) NOT NULL,
user_role INTEGER,
rating NUMERIC
);