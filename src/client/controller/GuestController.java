package client.controller;

import client.model.Drink;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.List;

public class GuestController {


    private Socket socket=null;
    private ObjectOutputStream os=null;
    private ObjectInputStream in=null;


    private Connection conn;


    public GuestController() {
        try {
            socket = new Socket("127.0.0.1",1234);
            os=new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(String username, String password) {
        // Sending a request to the server and receiving a response
        if (username!=null && password!=null) {
            try {
                os.writeObject(username);
                os.writeObject(password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Username and password cannot be empty");
        }

    }

    public void receiveResponse() {
        try {
            try {
                String response = (String) in.readObject();
                System.out.println(response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
