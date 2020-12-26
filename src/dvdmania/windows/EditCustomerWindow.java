package dvdmania.windows;

import dvdmania.management.AccountManager;
import dvdmania.management.Client;
import dvdmania.management.ClientManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class EditCustomerWindow extends JFrame {

    public EditCustomerWindow() {
        final ClientManager clientMan = ClientManager.getInstance();
        final ArrayList<Client> customers = clientMan.getAllClients();
        final JTable table = new JTable();
        final DefaultTableModel tableModel;

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final String[] columns = new String[]{"ID", "Nume", "Prenume", "Adresa", "Oras", "Data nasterii", "CNP", "Telefon", "Email", "Loialitate", "Username", "Password"};
        tableModel.setColumnIdentifiers(columns);

        for (int i = 0; i < customers.size(); i++) {
            final Client client = customers.get(i);
            tableModel.addRow(clientMan.clientToRow(client));
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
        JFrame firstWindow = new JFrame();
        firstWindow.add(firstWindowPanel);
        firstWindow.setVisible(true);
        firstWindow.setSize(1000, 300);
        firstWindow.setTitle("Clienti");
        firstWindow.setLocationRelativeTo(null);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectionModel().isSelectionEmpty()) {
                    final JFrame warningDialog = new JFrame();
                    JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai un client din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                } else {
                    final JLabel custNumeLabel, custPrenumeLabel, custAdresaLabel, custOrasLabel, custDatNasLabel, custCNPLabel, custTelLabel, custEmailLabel,
                            custUserLabel, custPassLabel;
                    final JTextField custNumeField, custPrenumeField, custAdresaField, custOrasField, custDatNasField, custCNPField, custTelField, custEmailField,
                            custUserField, custPassField;
                    final JButton exit, save;

                    custNumeLabel = new JLabel("Nume:");
                    custNumeField = new JTextField();
                    custNumeField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
                    custPrenumeLabel = new JLabel("Prenume:");
                    custPrenumeField = new JTextField();
                    custPrenumeField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
                    custAdresaLabel = new JLabel("Adresa:");
                    custAdresaField = new JTextField();
                    custAdresaField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
                    custOrasLabel = new JLabel("Oras:");
                    custOrasField = new JTextField();
                    custOrasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
                    custDatNasLabel = new JLabel("Data nasterii:");
                    custDatNasField = new JTextField();
                    custDatNasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));
                    custCNPLabel = new JLabel("CNP:");
                    custCNPField = new JTextField();
                    custCNPField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
                    custTelLabel = new JLabel("Telefon:");
                    custTelField = new JTextField();
                    custTelField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 7)));
                    custEmailLabel = new JLabel("Email:");
                    custEmailField = new JTextField();
                    custEmailField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 8)));
                    custUserLabel = new JLabel("Username:");
                    custUserField = new JTextField();
                    custUserField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 10)));
                    custPassLabel = new JLabel("Password:");
                    custPassField = new JTextField();
                    custPassField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 11)));

                    exit = new JButton("Exit");
                    save = new JButton("Save");

                    final JPanel userDetaliiPanel, userMainPanel, userButtonPanel;
                    userDetaliiPanel = new JPanel(new GridLayout(10, 10, 5, 5));
                    userDetaliiPanel.add(custNumeLabel);
                    userDetaliiPanel.add(custNumeField);
                    userDetaliiPanel.add(custPrenumeLabel);
                    userDetaliiPanel.add(custPrenumeField);
                    userDetaliiPanel.add(custAdresaLabel);
                    userDetaliiPanel.add(custAdresaField);
                    userDetaliiPanel.add(custOrasLabel);
                    userDetaliiPanel.add(custOrasField);
                    userDetaliiPanel.add(custDatNasLabel);
                    userDetaliiPanel.add(custDatNasField);
                    userDetaliiPanel.add(custCNPLabel);
                    userDetaliiPanel.add(custCNPField);
                    userDetaliiPanel.add(custTelLabel);
                    userDetaliiPanel.add(custTelField);
                    userDetaliiPanel.add(custEmailLabel);
                    userDetaliiPanel.add(custEmailField);
                    userDetaliiPanel.add(custUserLabel);
                    userDetaliiPanel.add(custUserField);
                    userDetaliiPanel.add(custPassLabel);
                    userDetaliiPanel.add(custPassField);
                    userButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
                    userButtonPanel.add(save);
                    userButtonPanel.add(exit);
                    userMainPanel = new JPanel(new BorderLayout());
                    userMainPanel.add(userDetaliiPanel, BorderLayout.CENTER);
                    userMainPanel.add(userButtonPanel, BorderLayout.SOUTH);

                    final JFrame frame = new JFrame();
                    frame.add(userMainPanel);
                    frame.setVisible(true);
                    frame.setSize(300, 300);
                    frame.setTitle("Client");

                    save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            final JFrame frame = new JFrame();
                            final int dialogResult = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Saving", JOptionPane.YES_NO_OPTION);
                            if (dialogResult == JOptionPane.YES_OPTION) {
                                final AccountManager accMan = AccountManager.getInstance();

                                final Client client = new Client();
                                client.setId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
                                client.setNume(custNumeField.getText());
                                client.setPrenume(custPrenumeField.getText());
                                client.setAdresa(custAdresaField.getText());
                                client.setOras(custOrasField.getText());
                                client.setDatan(LocalDate.parse(custDatNasField.getText()));
                                client.setCnp(custCNPField.getText());
                                client.setTel(custTelField.getText());
                                client.setEmail((custEmailField.getText().isEmpty()) ? null : custEmailField.getText());
                                client.setAccount(accMan.getClientAccount(client));
                                client.getAccount().setUsername(custUserField.getText());
                                client.getAccount().setPassword(custPassField.getText());

                                accMan.updateClientAccount(client.getAccount());
                                clientMan.updateClient(client);
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
                firstWindow.setVisible(false);
                firstWindow.dispose();
            }
        });
    }
}
