package client.ui;

import client.controller.BartenderController;
import client.model.Order;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BartenderUI extends JFrame {
    private JList<Order> orderList;
    private JButton serveButton;
    private BartenderController controller;

    public BartenderUI(BartenderController controller) {
        this.controller = controller;
        setTitle("CaribResort - Bartender View");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        orderList = new JList<>(controller.getPendingOrders().toArray(new Order[0]));
        serveButton = new JButton("Mark as Served");

        serveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order selectedOrder = orderList.getSelectedValue();
                if (selectedOrder != null) {
                    controller.updateOrderStatus(selectedOrder, "served");
                    refreshOrderList();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JScrollPane(orderList));
        panel.add(serveButton);
        add(panel);
    }

    private void refreshOrderList() {
        orderList.setListData(controller.getPendingOrders().toArray(new Order[0]));
    }
}
