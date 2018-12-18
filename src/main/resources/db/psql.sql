CREATE DATABASE yelpish;
\c yelpish;

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

CREATE TABLE IF NOT EXISTS reviews (
    id serial PRIMARY KEY NOT NULL,
    content VARCHAR NOT NULL
);

CREATE DATABASE yelpish_test TEMPLATE yelpish;