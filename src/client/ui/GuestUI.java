package client.ui;

import client.controller.GuestController;
import client.model.Drink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GuestUI extends JFrame {
    private GuestController controller;
    private JComboBox<String> drinkDropdown;
    private JButton orderButton;
    private JLabel resultLabel;
    private JComboBox<String> armbandDropdown;
    private DefaultComboBoxModel<String> drinkModel;

    public GuestUI(GuestController controller) {
        this.controller = controller;
        setTitle("Drink Ordering System - Guest");

        // Armband selection
        armbandDropdown = new JComboBox<>(new String[]{"Select Armband", "Orange (Over 18)", "Non-Orange (Under 18)"});
        armbandDropdown.addActionListener(e -> filterDrinks());

        // Drink selection
        drinkModel = new DefaultComboBoxModel<>();
        drinkDropdown = new JComboBox<>(drinkModel);

        // Order button and result label
        orderButton = new JButton("Order Drink");
        resultLabel = new JLabel();

        // Layout setup
        setLayout(new GridLayout(4, 1));
        add(new JLabel("Select Armband:"));
        add(armbandDropdown);
        add(new JLabel("Select Drink:"));
        add(drinkDropdown);
        add(orderButton);
        add(resultLabel);

        // Load drinks
        loadDrinks();

        // Order button listener
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String drinkName = (String) drinkDropdown.getSelectedItem();
                resultLabel.setText(controller.orderDrink(drinkName));
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadDrinks() {
        List<Drink> drinks = controller.getAvailableDrinks();
        for (Drink drink : drinks) {
            drinkModel.addElement(drink.getName());
        }
    }

    private void filterDrinks() {
        String armbandColor = (String) armbandDropdown.getSelectedItem();
        drinkModel.removeAllElements();
        List<Drink> drinks = controller.getAvailableDrinks();
        
        for (Drink drink : drinks) {
            if (armbandColor.equals("Orange (Over 18)") || (armbandColor.equals("Non-Orange (Under 18)") && drink.getType().equals("non-alcoholic"))) {
                drinkModel.addElement(drink.getName());
            }
        }
    }
}
