-- Database: product-manager

-- DROP DATABASE "product-manager";

CREATE TABLE product(
    id serial,
	name varchar(50),
	price decimal,
	best_before date,
	category varchar(50),
	volume decimal

);
SELECT * FROM product;
SELECT id, name, price, best_before, category, volume FROM product WHERE name='Sushi';
SELECT id, name, price, best_before, category, volume FROM product WHERE id=4;
INSERT INTO product(name, price, best_before,category) VALUES ('Avocado', 3.99, '2021-09-08', 'FRUIT');
UPDATE product SET volume=3.0 WHERE ID = 9;
UPDATE product SET category = 'GRAIN' WHERE ID = 1;
UPDATE product SET category = 'ANIMAL_SOURCE' WHERE ID = 1;
SELECT id, name, price, best_before, category, volume FROM product;
DELETE FROM product WHERE ID = 1;