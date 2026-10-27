
CREATE DATABASE IF NOT EXISTS shopdb;
USE shopdb;

CREATE TABLE IF NOT EXISTS products (
    product_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL
);

INSERT INTO products (product_id, name, price, quantity)
VALUES
(101, 'Notebook', 50.0, 25),
(102, 'Pen', 10.0, 8),
(103, 'Bag', 750.0, 5),
(104, 'Pencil', 5.0, 40),
(105, 'Water Bottle', 150.0, 12);
