-- Insert into Inventory
INSERT INTO Inventory (ProductType) VALUES ('Beer');
INSERT INTO Inventory (ProductType) VALUES ('Beer');
INSERT INTO Inventory (ProductType) VALUES ('Beer');

INSERT INTO Beers (ProductName, PricePerUnit, IsPack, InventoryId)
VALUES  ('Dutch', 1.29, FALSE, 1),
        ('Belgium', 1.49, FALSE, 2),
        ('German', 1.19, FALSE, 3);

INSERT INTO Inventory (ProductType) VALUES ('Vegetable');
INSERT INTO Inventory (ProductType) VALUES ('Vegetable');
INSERT INTO Inventory (ProductType) VALUES ('Vegetable');

INSERT INTO Vegetables (ProductName, PricePer100g, InventoryId)
VALUES ('Carrot', 1.29, 4),
       ('Potato', 1.04, 5),
       ('Cucumber', 1.14, 6);

INSERT INTO Inventory (ProductType) VALUES ('Bread');
INSERT INTO Inventory (ProductType) VALUES ('Bread');
INSERT INTO Inventory (ProductType) VALUES ('Bread');

INSERT INTO Breads (ProductName, PricePerUnit, Age, InventoryId)
VALUES  ('Ciabata', 1.49, 1, 7),
        ('Rye Bread', 1.69, 4, 8),
        ('Baguette', 1.79, 6, 9);
















