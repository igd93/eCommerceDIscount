CREATE TABLE Inventory (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductType ENUM('Beer', 'Bread', 'Vegetable') NOT NULL
);

CREATE TABLE Beers (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    Price NUMERIC(8, 2) NOT NULL,
    IsPack BOOLEAN,
    InventoryId BIGINT NOT NULL,
    FOREIGN KEY (InventoryId) REFERENCES Inventory(Id)
);

CREATE TABLE QuantityDiscount (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    BeerId BIGINT NOT NULL,
    Quantity INT NOT NULL,
    DiscountAmount NUMERIC(8, 2) NOT NULL,
    FOREIGN KEY (BeerId) REFERENCES Beers(Id)    
);

CREATE TABLE Breads (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    Price NUMERIC(8, 2) NOT NULL,
    Age INT NOT NULL,
    InventoryId BIGINT NOT NULL,
    FOREIGN KEY (InventoryId) REFERENCES Inventory(Id)
);

CREATE TABLE BreadDiscount (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    MinDaysOld INT NOT NULL,
    MaxDaysOld INT NOT NULL,
    DiscountPercentage NUMERIC(8,2) NOT NULL
);

CREATE TABLE Vegetables (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    Price NUMERIC(8, 2) NOT NULL,
    InventoryId BIGINT NOT NULL,
    FOREIGN KEY (Id) REFERENCES Inventory(Id)
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
    Quantity INT NOT NULL,
    Price Numeric(8,2) NOT NULL,
    FOREIGN KEY (InventoryId) REFERENCES Inventory(Id)
);


