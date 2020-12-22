package dvdmania.windows;

import dvdmania.management.Store;
import dvdmania.management.StoreManager;
import dvdmania.products.Order;
import dvdmania.products.OrderManager;
import dvdmania.tools.DataValidator;
import dvdmania.tools.ExportExcel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public final class ExportOrdersWindow extends JFrame {

    private static ExportOrdersWindow instance = null;

    private static JPanel mainPanel, forms, buttons;
    private static JLabel label1, label2, label3;
    private static JTextField fromDateField, toDateField;
    private static JButton exitButton, exportButton;
    private static JComboBox<String> comboBoxStores;

    private ExportOrdersWindow() {
        super("Export orders");

        //create main panel for window
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //create panel that contains the forms
        forms = new JPanel();
        forms.setLayout(new GridLayout(6, 1));
        label1 = new JLabel();
        label1.setText("De la data:");
        fromDateField = new JTextField();
        label2 = new JLabel();
        label2.setText("La data:");
        toDateField = new JTextField();
        forms.add(label1);
        forms.add(fromDateField);
        forms.add(label2);
        forms.add(toDateField);
        label3 = new JLabel();
        label3.setText("Oras");
        comboBoxStores = new JComboBox<String>();
        comboBoxStores.addItem("Toate");

        StoreManager storeMan = StoreManager.getInstance();
        ArrayList<Store> stores = storeMan.getStores();
        for (Store store : stores) {
            comboBoxStores.addItem(store.getOras());
        }

        //create button panel
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        exitButton = new JButton("Inchide");
        exportButton = new JButton("Export");
        buttons.add(exitButton);
        buttons.add(exportButton);

        //add panels to main panel
        mainPanel.add(forms, BorderLayout.CENTER);
        mainPanel.add(buttons, BorderLayout.SOUTH);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportOrdersWindow.getInstance().dispose();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportExcel excel = ExportExcel.getInstance();
                OrderManager ordMan = OrderManager.getInstance();

                String fromDate = fromDateField.getText();
                String toDate = toDateField.getText();
                String city = (String) comboBoxStores.getSelectedItem();
                Store selectedStore = null;
                if (!city.equals("Toate")) {
                    for (Store store : stores) {
                        if (store.getOras().equals(city)) {
                            selectedStore = store;
                            break;
                        }
                    }
                }

                if (fromDate == null || fromDate.isBlank()) {
                    if (toDate == null || toDate.isBlank()) {
                        ArrayList<Order> orders = ordMan.getOrdersByDates(null, null, selectedStore);
                        excel.writeOrdersToExcel(orders);
                    } else {
                        LocalDate toDate1 = DataValidator.checkDate(toDate);
                        if (toDate1 != null) {
                            ArrayList<Order> orders = ordMan.getOrdersByDates(null, toDate1, selectedStore);
                            excel.writeOrdersToExcel(orders);
                        } else {
                            JFrame warningDialog = new JFrame();
                            JOptionPane.showMessageDialog(warningDialog, "Date format not correct! Use yyyy/MM/dd format.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    if (toDate == null || toDate.isBlank()) {
                        LocalDate fromDate1 = DataValidator.checkDate(fromDate);
                        if (fromDate1 != null) {
                            ArrayList<Order> orders = ordMan.getOrdersByDates(fromDate1, null, selectedStore);
                            excel.writeOrdersToExcel(orders);
                        } else {
                            JFrame warningDialog = new JFrame();
                            JOptionPane.showMessageDialog(warningDialog, "Date format not correct! Use yyyy/MM/dd format.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        LocalDate fromDate1 = DataValidator.checkDate(fromDate);
                        LocalDate toDate1 = DataValidator.checkDate(toDate);
                        if (fromDate1 != null && toDate1 != null) {
                            ArrayList<Order> orders = ordMan.getOrdersByDates(fromDate1, toDate1, selectedStore);
                            excel.writeOrdersToExcel(orders);
                        } else {
                            JFrame warningDialog = new JFrame();
                            JOptionPane.showMessageDialog(warningDialog, "Date format not correct! Use yyyy/MM/dd format.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });

        this.add(mainPanel);
        this.setVisible(true);
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JFrame warningDialog = new JFrame();
        JOptionPane.showMessageDialog(warningDialog, "Leave fields empty in order to export all orders. Otherwise, use the yyyy/MM/dd date format.", "Info", JOptionPane.WARNING_MESSAGE);
    }

    public static ExportOrdersWindow getInstance() {
        if (instance == null) {
            instance = new ExportOrdersWindow();
        }

        return instance;
    }


}
