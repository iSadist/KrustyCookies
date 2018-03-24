-- Inserting Recipes
INSERT INTO products (product_name) VALUES ("Nut ring");
INSERT INTO products (product_name) VALUES ("Nut cookie");
INSERT INTO products (product_name) VALUES ("Amneris");
INSERT INTO products (product_name) VALUES ("Tango");
INSERT INTO products (product_name) VALUES ("Almond delight");
INSERT INTO products (product_name) VALUES ("Berliner");

-- Inserting ingredient relationship
INSERT INTO contains VALUES ('Nut ring', 'Flour', 450, 'g');
INSERT INTO contains VALUES ('Nut ring', 'Butter', 450, 'g');
INSERT INTO contains VALUES ('Nut ring', 'Icing sugar', 190, 'g');
INSERT INTO contains VALUES ('Nut ring', 'Roasted, chopped nuts', 225, 'g');

INSERT INTO contains VALUES ('Nut cookie', 'Fine-ground nuts', 750, 'g');
INSERT INTO contains VALUES ('Nut cookie', 'Ground, roasted nuts', 625, 'g');
INSERT INTO contains VALUES ('Nut cookie', 'Bread crumbs', 125, 'g');
INSERT INTO contains VALUES ('Nut cookie', 'Sugar', 375, 'g');
INSERT INTO contains VALUES ('Nut cookie', 'Egg whites', 3.5, 'dl');
INSERT INTO contains VALUES ('Nut cookie', 'Chocolate', 50, 'g');

INSERT INTO contains VALUES ('Amneris', 'Marzipan', 750, 'g');
INSERT INTO contains VALUES ('Amneris', 'Butter', 250, 'g');
INSERT INTO contains VALUES ('Amneris', 'Eggs', 250, 'g');
INSERT INTO contains VALUES ('Amneris', 'Potato starch', 25, 'g');
INSERT INTO contains VALUES ('Amneris', 'Wheat flour', 25, 'g');

INSERT INTO contains VALUES ('Tango', 'Butter', 200, 'g');
INSERT INTO contains VALUES ('Tango', 'Sugar', 250, 'g');
INSERT INTO contains VALUES ('Tango', 'Flour', 300, 'g');
INSERT INTO contains VALUES ('Tango', 'Sodium bicarbonate', 4, 'g');
INSERT INTO contains VALUES ('Tango', 'Vanilla', 2, 'g');

INSERT INTO contains VALUES ('Almond delight', 'Butter', 400, 'g');
INSERT INTO contains VALUES ('Almond delight', 'Sugar', 270, 'g');
INSERT INTO contains VALUES ('Almond delight', 'Chopped almonds', 279, 'g');
INSERT INTO contains VALUES ('Almond delight', 'Flour', 400, 'g');
INSERT INTO contains VALUES ('Almond delight', 'Cinnamon', 10, 'g');

INSERT INTO contains VALUES ('Berliner', 'Flour', 350, 'g');
INSERT INTO contains VALUES ('Berliner', 'Butter', 250, 'g');
INSERT INTO contains VALUES ('Berliner', 'Icing sugar', 100, 'g');
INSERT INTO contains VALUES ('Berliner', 'Eggs', 50, 'g');
INSERT INTO contains VALUES ('Berliner', 'Vanilla sugar', 5, 'g');
INSERT INTO contains VALUES ('Berliner', 'Chocolate', 50, 'g');
