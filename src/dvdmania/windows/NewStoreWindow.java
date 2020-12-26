package dvdmania.windows;

import dvdmania.management.Store;
import dvdmania.management.StoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class NewStoreWindow extends JFrame {

    private static NewStoreWindow instance = null;

    private NewStoreWindow() {
        super();

        final JLabel newAdresaLabel, newOrasLabel, newTelLabel;
        final JTextField newAdresaField, newOrasField, newTelField;
        final JButton save, exit;
        final JPanel newMainPanel, newButtonPanel, newDetaliiPanel;

        newAdresaLabel = new JLabel("Adresa:");
        newAdresaField = new JTextField();
        newOrasLabel = new JLabel("Oras:");
        newOrasField = new JTextField();
        newTelLabel = new JLabel("Telefon:");
        newTelField = new JTextField();
        save = new JButton("Save");
        exit = new JButton("Exit");

        newDetaliiPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        newDetaliiPanel.add(newAdresaLabel);
        newDetaliiPanel.add(newAdresaField);
        newDetaliiPanel.add(newOrasLabel);
        newDetaliiPanel.add(newOrasField);
        newDetaliiPanel.add(newTelLabel);
        newDetaliiPanel.add(newTelField);
        newButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        newButtonPanel.add(save);
        newButtonPanel.add(exit);
        newMainPanel = new JPanel(new BorderLayout());
        newMainPanel.add(newDetaliiPanel, BorderLayout.CENTER);
        newMainPanel.add(newButtonPanel, BorderLayout.SOUTH);

        this.add(newMainPanel);
        this.setVisible(true);
        this.setSize(300, 300);
        this.setTitle("Magazin nou");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String adresa = newAdresaField.getText();
                final String oras = newOrasField.getText();
                final String tel = newTelField.getText();

                if (adresa.equals("") || oras.equals("") || tel.equals("")) {
                    final JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final JFrame frame = new JFrame();
                    final int dialogResponse = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Exiting", JOptionPane.YES_NO_OPTION);
                    if (dialogResponse == JOptionPane.YES_OPTION) {
                        final Store store = new Store(0, adresa, oras, tel);
                        final StoreManager storeMan = StoreManager.getInstance();
                        storeMan.createStore(store);
                    }
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewStoreWindow.getInstance().setVisible(false);
                NewStoreWindow.getInstance().dispose();
            }
        });
    }

    public static NewStoreWindow getInstance() {
        if (instance == null) {
            instance = new NewStoreWindow();
        }

        return instance;
    }
}
