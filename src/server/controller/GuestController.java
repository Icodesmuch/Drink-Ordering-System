package server.controller;

import client.model.Drink;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class GuestController {

    public List<Drink> getAvailableDrinks() {
        List<Drink> drinks = null;
        try (Socket socket = new Socket("localhost", 1234);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject("getDrinks");
            drinks = (List<Drink>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drinks;
    }
}
