package server.database;

import client.model.Drink;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrinkDAO {

    public List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM drinks")) {

            while (rs.next()) {
                Drink drink = new Drink(rs.getInt("id"), rs.getString("name"), rs.getString("type"));
                drinks.add(drink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }

    public void addDrink(String name, String type) {
        String sql = "INSERT INTO drinks (name, type) VALUES (?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Additional methods for updating or removing drinks can be added here
}
