DROP TABLE if exists product;
CREATE TABLE product
(
    id VARCHAR (256) PRIMARY KEY ,
    name VARCHAR (256) ,
    price NUMERIC,
    measure_unit VARCHAR (256),
    provider VARCHAR (256),
    vat_type VARCHAR (100),
    category VARCHAR(256),
    initial_date DATE,
    day_of_week VARCHAR(20),
    num_of_periods INT,
    period VARCHAR(20),
    image CLOB
);

DROP TABLE if exists client;
CREATE TABLE client
(
    id VARCHAR (256) PRIMARY KEY ,
    username VARCHAR (255) UNIQUE,
    email VARCHAR (256),
    password VARCHAR(70) NOT NULL DEFAULT '{bcrypt}$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2'
);

DROP TABLE if EXISTS subscription;
CREATE TABLE subscription
(
    client_id VARCHAR (256),
    product_id VARCHAR (256),
    amount NUMBER NOT NULL DEFAULT 0,
    PRIMARY KEY (client_id, product_id),
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

DROP TABLE if EXISTS authorities;
CREATE TABLE authorities
(
    authority_id int(11)     NOT NULL AUTO_INCREMENT,
    username     varchar(45) NOT NULL,
    role         varchar(45) NOT NULL,
    PRIMARY KEY (authority_id),
    UNIQUE KEY uni_username_role (role,username),
    CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES client (username)
);

DROP TABLE if EXISTS store_order;
CREATE TABLE store_order
(
  id VARCHAR (256),
  client_id VARCHAR (256),
  creation_date DATE,
  delivery_date DATE,
  closing_date DATE,
  total_price DOUBLE,
  state NUMBER NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (client_id) REFERENCES client(id)
);

DROP TABLE if EXISTS order_product;
CREATE TABLE order_product
(
    order_id VARCHAR (256),
    product_id VARCHAR (256),
    product_name VARCHAR (256),
    amount NUMBER NOT NULL DEFAULT 0,
    FOREIGN KEY (order_id) REFERENCES store_order(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);