package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() throws SQLException {
        return (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static Map<String, Integer> searchMenuItemOptions(String name) throws SQLException {
        Map<String, Integer> optionsMap = new HashMap<>();
        String query = "SELECT options, price FROM menu_items WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Retrieve options and price from the ResultSet
                    String options = rs.getString("options");
                    int price = rs.getInt("price");
                    // Add each option and its price to the map
                    optionsMap.put(options, price);
                }
            }
        }
        return optionsMap;
        
    }
    
    public static List<MenuItem> filterMenuItemsByCategory(String category) throws SQLException {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items WHERE category = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MenuItem item = new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("options"),
                        rs.getString("category")
                    );
                    items.add(item);
                }
            }
        }
        return items;
    }
    
    public static boolean isItemOutOfStock(String itemName) throws SQLException {
        boolean outOfStock = true; // Assume out of stock until proven otherwise
        String query = "SELECT stock FROM menu_items WHERE name = ?";
        
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int stock = rs.getInt("stock");
                    outOfStock = stock <= 0; // If stock is less than or equal to 0, item is out of stock
                }
            }
        }
        
        return outOfStock;
    }
    
    public static void decreaseStock(String itemName, String option, int quantity) throws SQLException {
        String query = "UPDATE menu_items SET stock = stock - ? WHERE name = ? AND options = ?";
        
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, itemName);
            stmt.setString(3, option);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 0) {
                // Handle the case where the item or option is not found
                System.out.println("Item or option not found: " + itemName + " - " + option);
            }
        }
    }
    public static int getAvailableStock(String itemName, String option) throws SQLException {
        String query = "SELECT stock FROM menu_items WHERE name = ? AND options = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemName);
            stmt.setString(2, option);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                } else {
                    throw new SQLException("Item or option not found: " + itemName + " - " + option);
                }
            }
        }
    }

    public static int getOptionCountForFoodName(String foodName, String category) throws SQLException {
        int optionCount = 0;
        String query = "SELECT COUNT(*) AS optionCount FROM menu_items WHERE name = ? AND category = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, foodName);
            pstmt.setString(2, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    optionCount = rs.getInt("optionCount");
                }
            }
        }
        return optionCount;
    }
    
    public static boolean checkExistingFoodName(String foodName) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM menu_items WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, foodName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // If count > 0, the food name already exists
                }
            }
        }
        return false; // Default to false if an error occurs or no rows are returned
    }

    public static List<MenuItem> getMenuItems() throws SQLException {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MenuItem item = new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getInt("price"),
                        rs.getString("options"),
                        rs.getString("category")
                    );
                    items.add(item);
                }
            }
        }
        return items;
    }
    
    
    public static List<MenuItem> getMenuItemsByCategory(String category) throws SQLException {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items WHERE category = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Fetch options for this menu item
                    MenuItem item = new MenuItem(
                        rs.getInt("id"),      // Fetch the id
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getInt("price"),
                        rs.getString("options"), // Assign fetched options to the menu item
                        rs.getString("category")
                    );
                    items.add(item);
                }
            }
        }
        return items;
    }
    
    public static boolean isItemExists(String foodName, String option) throws SQLException {
        String query = "SELECT COUNT(*) FROM menu_items WHERE name = ? AND options = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, foodName);
            stmt.setString(2, option);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, item already exists
            }
            return false;
        }
    }
    
    public static void addMenuItem(MenuItem item) throws SQLException {
        String query = "INSERT INTO menu_items (name, price, stock, options, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, item.getFoodName());
            stmt.setInt(2, item.getPrice());
            stmt.setInt(3, item.getStock());
            stmt.setString(4, item.getOptions()); // Insert option as a string
            stmt.setString(5, item.getCategory());
            stmt.executeUpdate();
        }
    }



    public static void updateMenuItem(MenuItem item) throws SQLException {
        String query = "UPDATE menu_items SET name = ?, price = ?, stock = ?, options = ?, category = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, item.getFoodName());
            stmt.setInt(2, item.getPrice());
            stmt.setInt(3, item.getStock());
            stmt.setString(4, item.getOptions());
            stmt.setString(5, item.getCategory());
            stmt.setInt(6, item.getId());
            stmt.executeUpdate();
        }
    }

   

    public static void deleteMenuItem(int id) throws SQLException {
        String query = "DELETE FROM menu_items WHERE id = ?";
        int maxRetries = 3;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                success = true;
            } catch (SQLException e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw e;
                }
                // Log retry attempt
                System.err.println("Retry attempt " + attempt + " after failure: " + e.getMessage());
            }
        }
    }
}

    
    


