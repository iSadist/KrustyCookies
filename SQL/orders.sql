-- Add orders

INSERT INTO orders VALUES (1, 'Skånekakor AB', CURRENT_DATE);
INSERT INTO orders VALUES (2, 'Skånekakor AB', CURRENT_DATE);
INSERT INTO orders VALUES (3, 'Finkakor AB', CURRENT_DATE);
INSERT INTO orders VALUES (4, 'Bjudkakor AB', CURRENT_DATE);

-- Add products for the orders

INSERT INTO recipes_order VALUES ("Nut ring", 1, 2);
INSERT INTO recipes_order VALUES ("Tango", 2, 10);
INSERT INTO recipes_order VALUES ("Tango", 3, 5);
INSERT INTO recipes_order VALUES ("Berliner", 3, 15);
INSERT INTO recipes_order VALUES ("Nut cookie", 4, 200);
