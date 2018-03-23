PRAGMA foreign_keys = OFF;

DROP TABLE IF EXISTS blocked;
DROP TABLE IF EXISTS pallets;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS recipes_order;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS ingredient_updates;
DROP TABLE IF EXISTS contains;
DROP TABLE IF EXISTS loading_orders;
DROP TABLE IF EXISTS loading_customers;

PRAGMA foreign_keys = ON;

CREATE TABLE blocked (
  pallet_id INTEGER,
  FOREIGN KEY (pallet_id) REFERENCES pallets(pallet_id)
);

CREATE TABLE pallets (
  pallet_id INTEGER,
  product_name VARCHAR(100) NOT NULL,
  receiver VARCHAR(100),
  location VARCHAR(100) DEFAULT ('Freezer'),
  in_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  out_time TIMESTAMP,
  PRIMARY KEY (pallet_id),
  FOREIGN KEY (product_name) REFERENCES recipes(product_name),
  FOREIGN KEY (receiver) REFERENCES customers(company_name)
);

CREATE TABLE recipes (
  product_name VARCHAR(100) PRIMARY KEY,
  instructions TEXT,
  in_production BOOLEAN DEFAULT FALSE
);

CREATE TABLE recipes_order (
  product_name VARCHAR(100) NOT NULL,
  order_id INTEGER NOT NULL,
  amount INTEGER,
  FOREIGN KEY (product_name) REFERENCES recipes(product_name),
  FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

CREATE TABLE customers (
  company_name VARCHAR(100) PRIMARY KEY,
  address VARCHAR(100)
);

CREATE TABLE orders (
  order_id INTEGER PRIMARY KEY,
  company_name VARCHAR(100) NOT NULL,
  delivery_date DATE,
  FOREIGN KEY (company_name) REFERENCES customers(company_name)
);

CREATE TABLE contains (
  product_name VARCHAR(100) NOT NULL,
  ingredient_name VARCHAR(100) NOT NULL,
  amount INTEGER NOT NULL,
  type VARCHAR(10) NOT NULL,
  FOREIGN KEY (product_name) REFERENCES recipes(product_name),
  FOREIGN KEY (ingredient_name) REFERENCES ingredients(name)
);

CREATE TABLE ingredients (
  name VARCHAR(100) PRIMARY KEY,
  type VARCHAR(10) DEFAULT('g')
);

CREATE TABLE ingredient_updates (
  ingredient_name VARCHAR(100),
  day TIMESTAMP NOT NULL,
  amount INTEGER,
  FOREIGN KEY (ingredient_name) REFERENCES ingredients(name)
);

-- TODO Create a type table?

-- Tables for loading uses

CREATE TABLE loading_orders (
  loading_id INTEGER PRIMARY KEY,
  truck_id CHAR(6) NOT NULL
);

CREATE TABLE loading_customers (
  loading_id INTEGER NOT NULL,
  company_name VARCHAR(100) NOT NULL,
  FOREIGN KEY (loading_id) REFERENCES loading_orders(loading_id),
  FOREIGN KEY (company_name) REFERENCES customers(company_name)
);
