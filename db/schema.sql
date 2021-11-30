CREATE TABLE if not exists items
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR,
    created     timestamp,
    done        boolean,
    user_id     INT REFERENCES users (id)
);
CREATE TABLE if not exists users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR,
    name     VARCHAR,
    password VARCHAR
);
CREATE TABLE if not exists categories
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR
);
