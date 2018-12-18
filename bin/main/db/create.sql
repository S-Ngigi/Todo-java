SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS restaurants (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    address VARCHAR,
    zipcode VARCHAR,
    phone VARCHAR,
    website VARCHAR,
    email VARCHAR,
    img_url VARCHAR
);

CREATE TABLE IF NOT EXISTS foodtypes (
    id int PRIMARY KEY auto_increment,
    name VARCHAR
);

CREATE TABLE IF NOT EXISTS reviews (
    id int PRIMARY KEY auto_increment,
    content VARCHAR,
    written_by VARCHAR,
    rating INTEGER,
    restaurant_id INTEGER,
    created_at BIGINT
);

CREATE TABLE IF NOT EXISTS restaurants_foodtypes (
    id int PRIMARY KEY auto_increment,
    foodtype_id INTEGER,
    restaurant_id INTEGER
) ; 