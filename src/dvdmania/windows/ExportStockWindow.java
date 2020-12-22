package dvdmania.windows;

import dvdmania.management.Store;
import dvdmania.management.StoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ExportStockWindow extends JFrame {

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

        StoreManager storeMan = StoreManager.getInstance();
        ArrayList<Store> stores = storeMan.getStores();

        for (Store store : stores) {
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
                String city = (String) comboBoxStores.getSelectedItem();

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
