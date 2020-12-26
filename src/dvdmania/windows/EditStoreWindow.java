package dvdmania.windows;

import dvdmania.management.Store;
import dvdmania.management.StoreManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public final class EditStoreWindow extends JFrame {

    public EditStoreWindow() {
        super();

        final StoreManager storeMan = StoreManager.getInstance();
        final ArrayList<Store> stores = storeMan.getStores();
        final JTable table = new JTable();
        final DefaultTableModel tableModel;

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final String[] columns = new String[]{"ID", "Adresa", "Oras", "Telefon"};
        tableModel.setColumnIdentifiers(columns);

        for (int i = 0; i < stores.size(); i++) {
            final Store store = stores.get(i);
            tableModel.addRow(storeMan.storeToRow(store));
        }

        table.setModel(tableModel);
        final JScrollPane scroll = new JScrollPane(table);

        final JButton edit, exit;
        final JPanel firstWindowPanel, firstWindowButtonsPanel;
        edit = new JButton("Edit");
        exit = new JButton("Exit");
        firstWindowButtonsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        firstWindowButtonsPanel.add(edit);
        firstWindowButtonsPanel.add(exit);
        firstWindowPanel = new JPanel(new BorderLayout());
        firstWindowPanel.add(scroll, BorderLayout.CENTER);
        firstWindowPanel.add(firstWindowButtonsPanel, BorderLayout.SOUTH);

        this.add(firstWindowPanel);
        this.setVisible(true);
        this.setSize(500, 300);
        this.setTitle("Magazine");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectionModel().isSelectionEmpty()) {
                    final JFrame warningDialog = new JFrame();
                    JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai un magazin din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                } else {
                    final JLabel magAdresaLabel, magOrasLabel, magTelLabel;
                    final JTextField magAdresaField, magOrasField, magTelField;
                    final JButton exit, save;

                    magAdresaLabel = new JLabel("Adresa:");
                    magAdresaField = new JTextField();
                    magAdresaField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
                    magOrasLabel = new JLabel("Oras:");
                    magOrasField = new JTextField();
                    magOrasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
                    magTelLabel = new JLabel("Telefon:");
                    magTelField = new JTextField();
                    magTelField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));

                    exit = new JButton("Exit");
                    save = new JButton("Save");

                    final JPanel magDetaliiPanel, magMainPanel, magButtonPanel;
                    magDetaliiPanel = new JPanel(new GridLayout(3, 2, 5, 5));
                    magDetaliiPanel.add(magAdresaLabel);
                    magDetaliiPanel.add(magAdresaField);
                    magDetaliiPanel.add(magOrasLabel);
                    magDetaliiPanel.add(magOrasField);
                    magDetaliiPanel.add(magTelLabel);
                    magDetaliiPanel.add(magTelField);
                    magButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
                    magButtonPanel.add(save);
                    magButtonPanel.add(exit);
                    magMainPanel = new JPanel(new BorderLayout());
                    magMainPanel.add(magDetaliiPanel, BorderLayout.CENTER);
                    magMainPanel.add(magButtonPanel, BorderLayout.SOUTH);

                    final JFrame frame = new JFrame();
                    frame.add(magMainPanel);
                    frame.setVisible(true);
                    frame.setSize(300, 300);
                    frame.setTitle("Magazin");
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            final JFrame frame = new JFrame();
                            final int dialogResponse = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Saving", JOptionPane.YES_NO_OPTION);
                            if (dialogResponse == JOptionPane.YES_OPTION) {
                                final Store store = new Store();
                                store.setId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
                                store.setAdresa(magAdresaField.getText());
                                store.setOras(magOrasField.getText());
                                store.setTelefon(magTelField.getText());

                                storeMan.updateStore(store);
                            }
                        }
                    });

                    exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            frame.setVisible(false);
                            frame.dispose();
                        }
                    });
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }
}
