package server;

import server.database.DrinkDAO;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

public class Server {

    // Variables for the server
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private int clientCount = 0;



    // Constructor for the server
    public Server() {
        try {

            serverSocket = new ServerSocket(1234);
            System.out.println("Server started\t" + new Date().toString());

            // While loop to keep the server running and accept clients
            while (true) {
                socket = serverSocket.accept();
                clientCount++;
                System.out.println("Client " + clientCount + " connected at " + new Date().toString());

                ClientHandler handler = new ClientHandler(socket);
                Thread clientThread = new Thread(handler);
                clientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inner class to handle client requests in a separate thread
    public class ClientHandler implements Runnable {

        private ObjectOutputStream out = null;
        private ObjectInputStream in = null;
        private Socket socket = null;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                out = new ObjectOutputStream(this.socket.getOutputStream());
                in = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            // While loop to keep the client handler running
            while (true) {
                String username = "";
                String password = "";

                try {
                    // Receive username and password from the client
                    username = (String) in.readObject();
                    password = (String) in.readObject();
                    // Response from the client
                    if (username != null && password != null) {
                        System.out.println("Client"+clientCount+ "Connected with username: "+username+" and password: "+password+"\n");

                    } else {
                        System.out.println("Failure to connect");

                    }
                } catch (SocketException e) {
                    System.out.println("Client disconnected: " + e.getMessage());
                    break;
                } catch (EOFException e) {
                    System.out.println("End of File: " + e.getMessage());
                    break;
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }

            try  {

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

                    case "updateDrink":
                        int id = (int) in.readObject();
                        String newName = (String) in.readObject();
                        String newType = (String) in.readObject();
                        new DrinkDAO().updateDrink(id, newName, newType);
                        out.writeObject("Drink updated successfully");
                        break;
                    case "deleteDrink":
                        int drinkId = (int) in.readObject();
                        new DrinkDAO().deleteDrink(drinkId);
                        out.writeObject("Drink deleted successfully");
                        break;
                    default:
                        out.writeObject("Invalid action");
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }


    }



    // Main method to start the server
    public static void main(String[] args) {
        new Server();
    }


}
