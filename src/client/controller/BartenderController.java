package client.controller;

import client.model.Order;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class BartenderController {

    // Server URL for fetching pending orders and updating order status
    private static final String SERVER_URL = "http://localhost/drink-ordering-system/server/api/";

    // Method to retrieve all pending orders from the server
    public List<Order> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
        
        try {
            // Create the URL to get pending orders
            URL url = new URL(SERVER_URL + "getPendingOrders.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            // Parse the response into Order objects
            JSONArray ordersJsonArray = new JSONArray(response.toString());
            
            for (int i = 0; i < ordersJsonArray.length(); i++) {
                JSONObject orderJson = ordersJsonArray.getJSONObject(i);
                Order order = new Order(
                    orderJson.getInt("id"),
                    orderJson.getString("guestName"),
                    orderJson.getString("drinkName"),
                    orderJson.getString("status")
                );
                pendingOrders.add(order);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return pendingOrders;
    }

    // Method to update the status of an order
    public void updateOrderStatus(Order order, String newStatus) {
        try {
            // Create the URL to update the order status
            String urlString = SERVER_URL + "updateOrderStatus.php?orderId=" + order.getId() + "&newStatus=" + newStatus;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            // Send the request and get the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            // Check if the update was successful (You can enhance this logic if necessary)
            if (response.toString().equals("success")) {
                System.out.println("Order status updated successfully.");
            } else {
                System.out.println("Failed to update order status.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
