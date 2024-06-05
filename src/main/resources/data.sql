-- Insert into Inventory
INSERT INTO Inventory (ProductType) 
VALUES  ('Beer'),
        ('Beer'),
        ('Beer');


INSERT INTO Beers (ProductName, PricePerUnit, IsPack, InventoryId)
VALUES  ('Dutch', 1.29, FALSE, 1),
        ('Belgium', 1.49, FALSE, 2),
        ('German', 1.19, FALSE, 3);

INSERT INTO Inventory (ProductType)
 VALUES ('Vegetable'),
        ('Vegetable'),
        ('Vegetable');


INSERT INTO Vegetables (ProductName, PricePer100g, InventoryId)
VALUES ('Carrot', 1.29, 4),
       ('Potato', 1.04, 5),
       ('Cucumber', 1.14, 6);

INSERT INTO Inventory (ProductType) 
VALUES  ('Bread'),
        ('Bread'),
        ('Bread');

INSERT INTO Breads (ProductName, PricePerUnit, Age, InventoryId)
VALUES  ('Ciabata', 1.49, 1, 7),
        ('Rye Bread', 1.69, 4, 8),
        ('Baguette', 1.79, 6, 9);

INSERT INTO QuantityDiscount (InventoryId, Quantity, BeerId, DiscountAmount)
VALUES  (1, 6, 1, 2),
        (2, 6, 2, 3),
        (3, 6, 3, 4);

INSERT INTO BreadDiscount (MinDaysOld, MaxDaysOld, QuantityMultiplier)
VALUES  (3, 5, 2),
        (6, 6, 3);

INSERT INTO VegetablesDiscount (MinGrams, MaxGrams, DiscountPercentage)
VALUES  (0, 100, 0.05),
        (101, 500, 0.07),
        (501, 9999, 0.1);


















