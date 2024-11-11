package client.ui;

import client.controller.ManagerController;
import client.model.Drink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagerUI extends JFrame {
    private ManagerController controller;
    private JTextField drinkNameField;
    private JComboBox<String> drinkTypeDropdown;
    private JButton addDrinkButton;
    private JButton removeDrinkButton;
    private JList<String> drinkList;
    private DefaultListModel<String> drinkListModel;
    private JLabel resultLabel;

    public ManagerUI(ManagerController controller) {
        this.controller = controller;
        setTitle("Drink Management - Manager");

        // Drink input fields
        drinkNameField = new JTextField(10);
        drinkTypeDropdown = new JComboBox<>(new String[]{"alcoholic", "non-alcoholic"});
        addDrinkButton = new JButton("Add Drink");
        removeDrinkButton = new JButton("Remove Selected Drink");

        // Drink list
        drinkListModel = new DefaultListModel<>();
        drinkList = new JList<>(drinkListModel);
        loadDrinks();

        // Layout
        setLayout(new GridLayout(5, 2));
        add(new JLabel("Drink Name:"));
        add(drinkNameField);
        add(new JLabel("Drink Type:"));
        add(drinkTypeDropdown);
        add(addDrinkButton);
        add(removeDrinkButton);
        add(new JScrollPane(drinkList));
        add(resultLabel);

        addDrinkButton.addActionListener(e -> addDrink());
        removeDrinkButton.addActionListener(e -> removeDrink());

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadDrinks() {
        List<Drink> drinks = controller.getAvailableDrinks();
        for (Drink drink : drinks) {
            drinkListModel.addElement(drink.getName() + " (" + drink.getType() + ")");
        }
    }

    private void addDrink() {
        String name = drinkNameField.getText();
        String type = (String) drinkTypeDropdown.getSelectedItem();
        resultLabel.setText(controller.addDrink(name, type));
        loadDrinks();
    }

    private void removeDrink() {
        String selectedDrink = drinkList.getSelectedValue();
        if (selectedDrink != null) {
            controller.removeDrink(selectedDrink.split(" ")[0]);
            loadDrinks();
        }
    }
}
