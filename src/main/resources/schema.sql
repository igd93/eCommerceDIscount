CREATE TABLE Inventory (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductType VARCHAR(50) NOT NULL CHECK (ProductType IN ('Beer', 'Bread', 'Vegetable'))
);

CREATE TABLE Beers (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    PricePerUnit NUMERIC(8, 2) NOT NULL,
    IsPack BOOLEAN,
    InventoryId BIGINT UNIQUE NOT NULL,
    FOREIGN KEY (InventoryId) REFERENCES Inventory(Id)
);

CREATE TABLE Breads (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    PricePerUnit NUMERIC(8, 2) NOT NULL,
    Age INT NOT NULL,
    InventoryId BIGINT UNIQUE NOT NULL,
    FOREIGN KEY (InventoryId) REFERENCES Inventory(Id)
);

CREATE TABLE Vegetables (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    PricePer100g NUMERIC(8, 2) NOT NULL,
    InventoryId BIGINT UNIQUE NOT NULL,
    FOREIGN KEY (InventoryId) REFERENCES Inventory(Id)
);

CREATE TABLE QuantityDiscount (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    InventoryId BIGINT NOT NULL,
    Quantity INT NOT NULL,
    DiscountAmount NUMERIC(8, 2) NOT NULL,
    FOREIGN KEY (InventoryId) REFERENCES Inventory(Id)
);

CREATE TABLE BreadDiscount (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    MinDaysOld INT NOT NULL,
    MaxDaysOld INT NOT NULL,
    QuantityMultiplier INT NOT NULL
);

CREATE TABLE VegetablesDiscount (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    MinGrams INT NOT NULL,
    MaxGrams INT NOT NULL,
    DiscountPercentage NUMERIC(8,2) NOT NULL
);

CREATE TABLE CartItem (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    InventoryId BIGINT NOT NULL,
    Quantity INT NOT NULL, -- Quantity in units or grams
    Price NUMERIC(8,2) NOT NULL,
    FOREIGN KEY (InventoryId) REFERENCES Inventory(Id)
);
