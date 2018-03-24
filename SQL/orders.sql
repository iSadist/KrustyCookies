-- Add orders

INSERT INTO orders VALUES (1, 'Skånekakor AB', CURRENT_DATE - 7);
INSERT INTO orders VALUES (2, 'Finkakor AB', CURRENT_DATE - 6);
INSERT INTO orders VALUES (3, 'Bjudkakor AB', CURRENT_DATE - 5);

-- Add products for the orders

INSERT INTO product_orders VALUES ("Nut ring", 1, 2);
INSERT INTO product_orders VALUES ("Tango", 2, 1);
INSERT INTO product_orders VALUES ("Tango", 2, 1);
INSERT INTO product_orders VALUES ("Berliner", 3, 3);
INSERT INTO product_orders VALUES ("Nut cookie", 3, 2);
