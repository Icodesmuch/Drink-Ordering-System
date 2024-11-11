package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import server.database.DrinkDAO;

public class ServerApp {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        System.out.println("Server is starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String action = (String) in.readObject();
            
            switch (action) {
                case "getDrinks":
                    DrinkDAO drinkDAO = new DrinkDAO();
                    out.writeObject(drinkDAO.getAllDrinks());
                    break;

                case "addDrink":
                    String name = (String) in.readObject();
                    String type = (String) in.readObject();
                    new DrinkDAO().addDrink(name, type);
                    out.writeObject("Drink added successfully");
                    break;

                // Add cases for other actions like 'orderDrink', 'getOrders', etc.
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
