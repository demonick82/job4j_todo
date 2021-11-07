CREATE TABLE if not exists items
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR,
    created     timestamp,
    done        boolean
);