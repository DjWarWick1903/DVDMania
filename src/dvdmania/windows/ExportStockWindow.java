package dvdmania.windows;

import dvdmania.management.Stock;
import dvdmania.management.StockManager;
import dvdmania.management.Store;
import dvdmania.management.StoreManager;
import dvdmania.tools.ExportExcel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public final class ExportStockWindow extends JFrame {

    private static ExportStockWindow instance = null;

    private static JComboBox<String> comboBoxStores;
    private static JButton exitButton, exportButton;
    private static JLabel label1;
    private static JPanel mainPanel, formPanel, buttonPanel;

    private ExportStockWindow() {
        super();

        //create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //create and build form panel
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 1));
        label1 = new JLabel();
        label1.setText("Oras");
        comboBoxStores = new JComboBox<String>();
        comboBoxStores.addItem("Toate");

        final StoreManager storeMan = StoreManager.getInstance();
        final ArrayList<Store> stores = storeMan.getStores();

        for (final Store store : stores) {
            comboBoxStores.addItem(store.getOras());
        }

        formPanel.add(label1);
        formPanel.add(comboBoxStores);

        //create and build button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        exitButton = new JButton();
        exitButton.setText("Inapoi");
        exportButton = new JButton();
        exportButton.setText("Export");

        buttonPanel.add(exitButton);
        buttonPanel.add(exportButton);

        //build main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportStockWindow.getInstance().dispose();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String city = (String) comboBoxStores.getSelectedItem();

                final StockManager stockMan = StockManager.getInstance();
                ArrayList<Stock> stocks = null;

                if (city.equals("Toate")) {
                    stocks = stockMan.getAllStock();
                    ExportExcel.getInstance().writeStockToExcel(stocks, null);
                } else {
                    final StoreManager storeMan = StoreManager.getInstance();
                    final Store store = storeMan.getStoreByCity(city);
                    stocks = stockMan.getAllStock(store);
                    ExportExcel.getInstance().writeStockToExcel(stocks, city);
                }
            }
        });

        this.add(mainPanel);
        this.setVisible(true);
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public static ExportStockWindow getInstance() {
        if (instance == null) {
            instance = new ExportStockWindow();
        }

        return instance;
    }
}
