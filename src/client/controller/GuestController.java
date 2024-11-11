package client.controller;

import client.model.Drink;
import java.util.List;

public class GuestController {

    private ServerConnection serverConnection;

    // Constructor to initialize the server connection
    public GuestController() {
        this.serverConnection = new ServerConnection();
    }

    // Method to fetch the available drinks from the server
    public List<Drink> getAvailableDrinks() {
        List<Drink> availableDrinks = null;
        try {
            availableDrinks = serverConnection.getDrinks();
        } catch (Exception e) {
            System.err.println("Error retrieving available drinks: " + e.getMessage());
        }
        return availableDrinks;
    }

    // Method for guests to order a drink by name
    public String orderDrink(String drinkName) {
        String response = "";
        try {
            response = serverConnection.orderDrink(drinkName);
        } catch (Exception e) {
            System.err.println("Error ordering drink: " + e.getMessage());
        }
        return response;
    }
}
