package client.controller;

import client.model.Drink;
import java.io.File;
import java.util.List;

public class ManagerController {
    private ServerConnection serverConnection = new ServerConnection();

    public List<Drink> getAvailableDrinks() {
        return serverConnection.getDrinks();
    }

    public String addDrink(String name, String type) {
        return serverConnection.addDrink(name, type);
    }

    public File generateReport() {
        return serverConnection.generateReport();
    }
}
