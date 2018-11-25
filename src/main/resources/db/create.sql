SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS restaurants (
    id serial PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL,
    address VARCHAR NOT NULL,
    zipcode VARCHAR NOT NULL,
    phone VARCHAR NOT NULL,
    website VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    img_url VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS foodtypes (
    id serial PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL
);

CREAT TABLE IF NOT EXISTS reviews (
    id serial PRIMARY KEY NOT NULL,
    content VARCHAR NOT NULL,
    written_by VARCHAR NOT NULL,
    rating VARCHAR NOT NULL,
    restaurant_ids INTEGER NOT NULL
);