
import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String URL = "jdbc:mysql://localhost:3306/shopdb";
    static final String USER = "root";
    static final String PASSWORD = "your_password";

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            int choice;
            do {
                System.out.println("\n--- SHOP MANAGEMENT SYSTEM ---");
                System.out.println("1. Add Product");
                System.out.println("2. Sell Product");
                System.out.println("3. Low Stock Products");
                System.out.println("4. Search Product");
                System.out.println("5. Display All Products");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1: addProduct(); break;
                    case 2: sellProduct(); break;
                    case 3: lowStock(); break;
                    case 4: searchProduct(); break;
                    case 5: displayProducts(); break;
                    case 6: System.out.println("Goodbye!"); break;
                    default: System.out.println("Invalid choice!");
                }
            } while (choice != 6);

            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void addProduct() throws SQLException {
        System.out.print("Product ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Product name: ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        double price = sc.nextDouble();

        System.out.print("Quantity: ");
        int qty = sc.nextInt();

        if (price < 0 || qty < 0) {
            System.out.println("Invalid price or quantity!");
            return;
        }

        String sql = "INSERT INTO products VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, qty);
            ps.executeUpdate();
            System.out.println("Product added successfully!");
        }
    }

    static void sellProduct() throws SQLException {
        System.out.print("Product ID: ");
        int id = sc.nextInt();

        System.out.print("Quantity sold: ");
        int sold = sc.nextInt();

        if (sold <= 0) {
            System.out.println("Enter a positive quantity!");
            return;
        }

        String sql = "UPDATE products SET quantity = quantity - ? " +
                     "WHERE product_id = ? AND quantity >= ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, sold);
            ps.setInt(2, id);
            ps.setInt(3, sold);

            if (ps.executeUpdate() > 0)
                System.out.println("Sale successful! Stock updated.");
            else
                System.out.println("Product not found or insufficient stock!");
        }
    }

    static void lowStock() throws SQLException {
        String sql = "SELECT * FROM products WHERE quantity < 10";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            printResults(rs);
        }
    }

    static void searchProduct() throws SQLException {
        System.out.println("1. Search by ID");
        System.out.println("2. Search by name");
        int choice = sc.nextInt();
        sc.nextLine();

        String sql;
        if (choice == 1) {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sql = "SELECT * FROM products WHERE product_id = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    printResults(rs);
                }
            }
        } else if (choice == 2) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            sql = "SELECT * FROM products WHERE name LIKE ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, "%" + name + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    printResults(rs);
                }
            }
        } else {
            System.out.println("Invalid choice!");
        }
    }

    static void displayProducts() throws SQLException {
        String sql = "SELECT * FROM products";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            printResults(rs);
        }
    }

    static void printResults(ResultSet rs) throws SQLException {
        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.println(
                "ID: " + rs.getInt("product_id") +
                " | Name: " + rs.getString("name") +
                " | Price: " + rs.getDouble("price") +
                " | Quantity: " + rs.getInt("quantity")
            );
        }
        if (!found)
            System.out.println("No products found.");
    }
}
