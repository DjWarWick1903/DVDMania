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

    public ExportOrdersWindow() {
        super("Export orders");

        //create main panel for window
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //create panel that contains the forms
        final JPanel forms = new JPanel();
        forms.setLayout(new GridLayout(6, 1));
        final JLabel label1 = new JLabel();
        label1.setText("De la data:");
        final JTextField fromDateField = new JTextField();
        final JLabel label2 = new JLabel();
        label2.setText("La data:");
        final JTextField toDateField = new JTextField();
        final JLabel label3 = new JLabel();
        label3.setText("Oras");
        final JComboBox comboBoxStores = new JComboBox<String>();
        comboBoxStores.addItem("Toate");
        forms.add(label1);
        forms.add(fromDateField);
        forms.add(label2);
        forms.add(toDateField);
        forms.add(label3);
        forms.add(comboBoxStores);

        final StoreManager storeMan = StoreManager.getInstance();
        final ArrayList<Store> stores = storeMan.getStores();
        for (Store store : stores) {
            comboBoxStores.addItem(store.getOras());
        }

        //create button panel
        final JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        final JButton exitButton = new JButton("Inchide");
        final JButton exportButton = new JButton("Export");
        buttons.add(exitButton);
        buttons.add(exportButton);

        //add panels to main panel
        mainPanel.add(forms, BorderLayout.CENTER);
        mainPanel.add(buttons, BorderLayout.SOUTH);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final ExportExcel excel = ExportExcel.getInstance();
                final OrderManager ordMan = OrderManager.getInstance();

                final String fromDate = fromDateField.getText();
                final String toDate = toDateField.getText();
                final String city = (String) comboBoxStores.getSelectedItem();
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
                        final ArrayList<Order> orders = ordMan.getOrdersByDates(null, null, selectedStore);
                        excel.writeOrdersToExcel(orders);
                    } else {
                        final LocalDate toDate1 = DataValidator.checkDate(toDate);
                        if (toDate1 != null) {
                            final ArrayList<Order> orders = ordMan.getOrdersByDates(null, toDate1, selectedStore);
                            excel.writeOrdersToExcel(orders);
                        } else {
                            final JFrame warningDialog = new JFrame();
                            JOptionPane.showMessageDialog(warningDialog, "Date format not correct! Use yyyy/MM/dd format.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    if (toDate == null || toDate.isBlank()) {
                        final LocalDate fromDate1 = DataValidator.checkDate(fromDate);
                        if (fromDate1 != null) {
                            final ArrayList<Order> orders = ordMan.getOrdersByDates(fromDate1, null, selectedStore);
                            excel.writeOrdersToExcel(orders);
                        } else {
                            final JFrame warningDialog = new JFrame();
                            JOptionPane.showMessageDialog(warningDialog, "Date format not correct! Use yyyy/MM/dd format.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        final LocalDate fromDate1 = DataValidator.checkDate(fromDate);
                        final LocalDate toDate1 = DataValidator.checkDate(toDate);
                        if (fromDate1 != null && toDate1 != null) {
                            final ArrayList<Order> orders = ordMan.getOrdersByDates(fromDate1, toDate1, selectedStore);
                            excel.writeOrdersToExcel(orders);
                        } else {
                            final JFrame warningDialog = new JFrame();
                            JOptionPane.showMessageDialog(warningDialog, "Date format not correct! Use yyyy/MM/dd format.", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        });

        this.add(mainPanel);
        this.setVisible(true);
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        final JFrame warningDialog = new JFrame();
        JOptionPane.showMessageDialog(warningDialog, "Leave fields empty in order to export all orders. Otherwise, use the yyyy/MM/dd date format.", "Info", JOptionPane.WARNING_MESSAGE);
    }
}
