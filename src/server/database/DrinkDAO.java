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

    public void updateDrink(int id, String newName, String newType) {
        String sql = "UPDATE drinks SET name = ?, type = ? WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setString(2, newType);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void deleteDrink(int id) {
        String sql = "DELETE FROM drinks WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
