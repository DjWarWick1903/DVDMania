package dvdmania.windows;

import dvdmania.management.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public final class EditEmployeeWindow extends JFrame {

    private static EditEmployeeWindow instance = null;

    private EditEmployeeWindow() {
        super();
        final EmployeeManager empMan = EmployeeManager.getInstance();
        final ArrayList<Employee> employees = empMan.getEmployees();
        final JTable table = new JTable();
        final DefaultTableModel tableModel;

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final String[] columns = new String[]{"ID", "Nume", "Prenume", "Adresa", "Oras", "Data nasterii", "CNP", "Telefon", "Email", "Functie", "Salariu", "Adresa magazin", "Username", "Parola"};
        tableModel.setColumnIdentifiers(columns);

        for (int i = 0; i < employees.size(); i++) {
            final Employee emp = employees.get(i);
            tableModel.addRow(empMan.employeeToRow(emp));
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
        this.setSize(1000, 300);
        this.setTitle("Angajati");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectionModel().isSelectionEmpty()) {
                    final JFrame warningDialog = new JFrame();
                    JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai un client din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                } else {
                    final JLabel empNumeLabel, empPrenumeLabel, empAdresaLabel, empOrasLabel, empDatNasLabel, empCNPLabel, empTelLabel, empEmailLabel,
                            empUserLabel, empPassLabel, empFuncLabel, empSalLabel, empStoreLabel, empActvLabel;
                    final JTextField empNumeField, empPrenumeField, empAdresaField, empOrasField, empDatNasField, empCNPField, empTelField, empEmailField,
                            empUserField, empPassField, empFuncField, empSalField, empStoreField, empActivField;
                    final JButton exit, save;

                    empNumeLabel = new JLabel("Nume:");
                    empNumeField = new JTextField();
                    empNumeField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
                    empPrenumeLabel = new JLabel("Prenume:");
                    empPrenumeField = new JTextField();
                    empPrenumeField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
                    empAdresaLabel = new JLabel("Adresa:");
                    empAdresaField = new JTextField();
                    empAdresaField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
                    empOrasLabel = new JLabel("Oras:");
                    empOrasField = new JTextField();
                    empOrasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
                    empDatNasLabel = new JLabel("Data nasterii:");
                    empDatNasField = new JTextField();
                    empDatNasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));
                    empCNPLabel = new JLabel("CNP:");
                    empCNPField = new JTextField();
                    empCNPField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
                    empTelLabel = new JLabel("Telefon:");
                    empTelField = new JTextField();
                    empTelField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 7)));
                    empEmailLabel = new JLabel("Email:");
                    empEmailField = new JTextField();
                    empEmailField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 8)));
                    empFuncLabel = new JLabel("Functie:");
                    empFuncField = new JTextField();
                    empFuncField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 9)));
                    empSalLabel = new JLabel("Salariu:");
                    empSalField = new JTextField();
                    empSalField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 10)));
                    empStoreLabel = new JLabel("Magazin:");
                    empStoreField = new JTextField();
                    empStoreField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 11)));
                    empUserLabel = new JLabel("Username:");
                    empUserField = new JTextField();
                    empUserField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 12)));
                    empPassLabel = new JLabel("Password:");
                    empPassField = new JTextField();
                    empPassField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 13)));
                    empActvLabel = new JLabel("Activ/Inactiv:");
                    empActivField = new JTextField();
                    empActivField.setText("Activ");

                    exit = new JButton("Exit");
                    save = new JButton("Save");

                    final JPanel userDetaliiPanel, userMainPanel, userButtonPanel;
                    userDetaliiPanel = new JPanel(new GridLayout(14, 2, 5, 5));
                    userDetaliiPanel.add(empNumeLabel);
                    userDetaliiPanel.add(empNumeField);
                    userDetaliiPanel.add(empPrenumeLabel);
                    userDetaliiPanel.add(empPrenumeField);
                    userDetaliiPanel.add(empAdresaLabel);
                    userDetaliiPanel.add(empAdresaField);
                    userDetaliiPanel.add(empOrasLabel);
                    userDetaliiPanel.add(empOrasField);
                    userDetaliiPanel.add(empDatNasLabel);
                    userDetaliiPanel.add(empDatNasField);
                    userDetaliiPanel.add(empCNPLabel);
                    userDetaliiPanel.add(empCNPField);
                    userDetaliiPanel.add(empTelLabel);
                    userDetaliiPanel.add(empTelField);
                    userDetaliiPanel.add(empEmailLabel);
                    userDetaliiPanel.add(empEmailField);
                    userDetaliiPanel.add(empFuncLabel);
                    userDetaliiPanel.add(empFuncField);
                    userDetaliiPanel.add(empSalLabel);
                    userDetaliiPanel.add(empSalField);
                    userDetaliiPanel.add(empStoreLabel);
                    userDetaliiPanel.add(empStoreField);
                    userDetaliiPanel.add(empUserLabel);
                    userDetaliiPanel.add(empUserField);
                    userDetaliiPanel.add(empPassLabel);
                    userDetaliiPanel.add(empPassField);
                    userDetaliiPanel.add(empActvLabel);
                    userDetaliiPanel.add(empActivField);
                    userButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
                    userButtonPanel.add(save);
                    userButtonPanel.add(exit);
                    userMainPanel = new JPanel(new BorderLayout());
                    userMainPanel.add(userDetaliiPanel, BorderLayout.CENTER);
                    userMainPanel.add(userButtonPanel, BorderLayout.SOUTH);

                    final JFrame frame = new JFrame();
                    frame.add(userMainPanel);
                    frame.setVisible(true);
                    frame.setSize(300, 480);
                    frame.setTitle("Angajat");
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            final JFrame frame = new JFrame();
                            final int dialogResponse = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Saving", JOptionPane.YES_NO_OPTION);
                            if (dialogResponse == JOptionPane.YES_OPTION) {
                                final Employee emp = new Employee();
                                emp.setIdEmp(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
                                emp.setNume(empNumeField.getText());
                                emp.setPrenume(empPrenumeField.getText());
                                emp.setAdresa(empAdresaField.getText());
                                emp.setOras(empOrasField.getText());
                                emp.setDatan(LocalDate.parse(empDatNasField.getText()));
                                emp.setCnp(empCNPField.getText());
                                emp.setTelefon(empTelField.getText());
                                emp.setEmail(empEmailField.getText());
                                emp.setFunctie(empFuncField.getText());
                                emp.setSalariu(Integer.parseInt(empSalField.getText()));

                                final StoreManager storeMan = StoreManager.getInstance();
                                final Store store = storeMan.getStoreByCity(empStoreField.getText());

                                emp.setActiv((empActivField.getText().equals("Activ")) ? true : false);
                                empMan.updateEmployee(emp);
                                final Account account = new Account(0, empUserField.getText(), empPassField.getText(), null, 0, emp.getIdEmp());
                                final AccountManager accMan = AccountManager.getInstance();
                                accMan.updateEmployeeAccount(account);
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
                EditEmployeeWindow.getInstance().setVisible(false);
                EditEmployeeWindow.getInstance().dispose();
            }
        });
    }

    public static EditEmployeeWindow getInstance() {
        if (instance == null) {
            instance = new EditEmployeeWindow();
        }

        return instance;
    }
}
