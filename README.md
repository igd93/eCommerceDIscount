# Inventory Management System

This project is a Spring-based application designed to manage an inventory system for various products, including beers, breads, and vegetables. The application uses an H2 in-memory relational database schema to organize products, track pricing, apply discounts, and manage shopping cart items.

## Features

- **Hierarchical Inventory Structure**: Organize products into categories and sub-categories for better management.
- **Product Management**: Detailed information storage for beers, breads, and vegetables.
- **Discount Management**: Apply quantity-based, age-based, and weight-based discounts to products.
- **Shopping Cart**: Add, view, and manage items in a shopping cart.

## Database Schema Overview

## For detailed schema overview refer to SCHEMA.md

### Tables

- **Inventory**: Holds basic information about products and supports hierarchical categorization.
- **Beer**: Stores details specific to beer products.
- **Bread**: Stores details specific to bread products.
- **Vegetable**: Stores details specific to vegetable products.
- **QuantityDiscount**: Manages quantity-based discounts for beer products.
- **BreadDiscount**: Manages age-based discounts for bread products.
- **VegetableDiscount**: Manages weight-based discounts for vegetable products.
- **CartItem**: Stores items added to the shopping cart.

## Technologies Used

- **Spring Framework**: Core framework for building the application.
- **Spring Data JPA**: For database interactions.
- **H2 Database**: In-memory database for development and testing.
- **Spring Boot**: For application configuration and management.
- **Open API and Swagger UI**: For documenting the API and simple visualization

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/igd93/eCommerceDIscount.git
   ```
2. **Navigate to the project directory**:

   ```bash
   cd eCommerceDiscount
   ```

3. **Build the project**:
   ```bash
   ./gradlew build
   ```
   **on Windows**
   ```bash
   gradlew.bat build
   ```
4. **Run the app**:
   ```bash
   ./gradlew bootRun
   ```
   **On Windows**
   ```bash
   gradlew.bat bootRun
   ```

### Usage

Access the application at http://localhost:8080.
Use the API endpoints to manage inventory items, apply discounts, and manage the shopping cart.

### ToDo

- **Validation**
- **Custom Exception Handling**
- **Authentication and Authorization**
