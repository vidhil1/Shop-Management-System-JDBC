
# Shop Management System using JDBC

## Objective
To develop a Java application for managing shop products
and stock quantities using JDBC and MySQL.

## Features
1. Add products with ID, name, price, and quantity.
2. Update stock quantities after a sale.
3. Display products with stock below 10.
4. Search products by ID or name.
5. Display all products.

## Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL
- BlueJ IDE

## Database Details
- Database: shopdb
- Table: products
- Columns: product_id, name, price, quantity

## Files
- Main.java: Main application code
- database.sql: Database and sample product records
- README.md: Project documentation

## JDBC Connection

The application connects Java to the MySQL `shopdb` database using JDBC and MySQL Connector/J.

**Connection commands:**

```java
import java.sql.*;

Class.forName("com.mysql.cj.jdbc.Driver");

Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/shopdb",
    "root",
    "your_password"
);
```

- `Class.forName()` loads the MySQL JDBC driver.
- `DriverManager.getConnection()` establishes the database connection.
- `PreparedStatement` executes INSERT, UPDATE, and search queries.
- `Statement` executes simple SELECT queries.
- `ResultSet` retrieves the query results.

**Connection flow:** Java Application → JDBC API → MySQL JDBC Driver → MySQL Database.

