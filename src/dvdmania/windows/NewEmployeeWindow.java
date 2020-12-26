package dvdmania.windows;

import dvdmania.management.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public final class NewEmployeeWindow extends JFrame {

    private static NewEmployeeWindow instance = null;

    private static JLabel newUserLabel, newPasswordLabel, newEmailLabel, newNameLabel, newPrenLabel, newAddressLabel,
            newCityLabel, newCNPLabel, newTelLabel, newFuncLabel, newSalLabel, newMagLabel, newBirthYearLabel,
            newBirthMonthLabel, newBirthDayLabel;
    private static JTextField newUsernameText, newEmailText, newNameText, newPrenText, newAddressText, newCityText,
            newCNPText, newTelText, newFuncField, newSalField, newBirthYearText, newBirthMonthText, newBirthDayText;
    private static JPasswordField newPasswordText;
    private static JPanel newDataPanel, newBirthPanel, newButtonPanel, newMainPanel;
    private static JButton newCreateButton, newCancelButton;

    private NewEmployeeWindow() {
        super();
        //Username
        newUserLabel = new JLabel();
        newUserLabel.setText("Username: ");
        newUsernameText = new JTextField();

        //Password
        newPasswordLabel = new JLabel();
        newPasswordLabel.setText("Password: ");
        newPasswordText = new JPasswordField();

        //Email
        newEmailLabel = new JLabel();
        newEmailLabel.setText("Email: ");
        newEmailText = new JTextField();

        //Nume
        newNameLabel = new JLabel();
        newNameLabel.setText("Nume: ");
        newNameText = new JTextField();

        //Prenume
        newPrenLabel = new JLabel();
        newPrenLabel.setText("Prenume: ");
        newPrenText = new JTextField();

        //Adresa
        newAddressLabel = new JLabel();
        newAddressLabel.setText("Adresa: ");
        newAddressText = new JTextField();

        //Oras
        newCityLabel = new JLabel();
        newCityLabel.setText("Oras: ");
        newCityText = new JTextField();

        //CNP
        newCNPLabel = new JLabel();
        newCNPLabel.setText("CNP: ");
        newCNPText = new JTextField();

        //Telefon
        newTelLabel = new JLabel();
        newTelLabel.setText("Telefon: ");
        newTelText = new JTextField();

        //Functie
        newFuncLabel = new JLabel("Functie:");
        newFuncField = new JTextField();

        //Salariu
        newSalLabel = new JLabel("Salariu:");
        newSalField = new JTextField();

        //Magazin
        newMagLabel = new JLabel("Magazin:");
        final StoreManager storeMan = StoreManager.getInstance();
        final ArrayList<Store> stores = storeMan.getStores();
        final ArrayList<String> cities = new ArrayList<>();
        final Iterator iter = stores.iterator();
        while (iter.hasNext()) {
            final Store store = (Store) iter.next();
            cities.add(store.getOras());
        }
        final JComboBox newMagBox = new JComboBox(cities.toArray());

        //Birthday
        newBirthYearLabel = new JLabel("An(yyyy)");
        newBirthMonthLabel = new JLabel("Luna(mm)");
        newBirthDayLabel = new JLabel("Zi(dd)");
        newBirthYearText = new JTextField();
        newBirthMonthText = new JTextField();
        newBirthDayText = new JTextField();

        //Data panel
        newDataPanel = new JPanel(new GridLayout(12, 2, 5, 5));
        newDataPanel.add(newUserLabel);
        newDataPanel.add(newUsernameText);
        newDataPanel.add(newPasswordLabel);
        newDataPanel.add(newPasswordText);
        newDataPanel.add(newEmailLabel);
        newDataPanel.add(newEmailText);
        newDataPanel.add(newNameLabel);
        newDataPanel.add(newNameText);
        newDataPanel.add(newPrenLabel);
        newDataPanel.add(newPrenText);
        newDataPanel.add(newAddressLabel);
        newDataPanel.add(newAddressText);
        newDataPanel.add(newCityLabel);
        newDataPanel.add(newCityText);
        newDataPanel.add(newCNPLabel);
        newDataPanel.add(newCNPText);
        newDataPanel.add(newTelLabel);
        newDataPanel.add(newTelText);
        newDataPanel.add(newFuncLabel);
        newDataPanel.add(newFuncField);
        newDataPanel.add(newSalLabel);
        newDataPanel.add(newSalField);
        newDataPanel.add(newMagLabel);
        newDataPanel.add(newMagBox);

        //Birth Panel
        newBirthPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        newBirthPanel.add(newBirthYearLabel);
        newBirthPanel.add(newBirthMonthLabel);
        newBirthPanel.add(newBirthDayLabel);
        newBirthPanel.add(newBirthYearText);
        newBirthPanel.add(newBirthMonthText);
        newBirthPanel.add(newBirthDayText);

        //Button Panel
        newButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        newCreateButton = new JButton("Create");
        newCancelButton = new JButton("Cancel");
        newButtonPanel.add(newCreateButton);
        newButtonPanel.add(newCancelButton);

        //dvdmania.tools.Main Panel
        newMainPanel = new JPanel(new BorderLayout());
        newMainPanel.add(newDataPanel, BorderLayout.NORTH);
        newMainPanel.add(newBirthPanel, BorderLayout.CENTER);
        newMainPanel.add(newButtonPanel, BorderLayout.SOUTH);

        this.add(newMainPanel);
        this.setVisible(true);
        this.setSize(300, 480);
        this.setTitle("Creare angajat");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        newCancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame();
                final int dialogResponse = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Exiting", JOptionPane.YES_NO_OPTION);
                if (dialogResponse == JOptionPane.YES_OPTION) {
                    NewEmployeeWindow.getInstance().setVisible(false);
                    NewEmployeeWindow.getInstance().dispose();
                }
            }
        });

        newCreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String username = newUsernameText.getText();
                final String password = String.valueOf(newPasswordText.getPassword());
                final String email = newEmailText.getText();
                final String firstName = newNameText.getText();
                final String lastName = newPrenText.getText();
                final String address = newAddressText.getText();
                final String city = newCityText.getText();
                final String cnp = newCNPText.getText();
                final String phone = newTelText.getText();
                final String functie = newFuncField.getText();
                final String salariu = newSalField.getText();
                final String mag = newMagBox.getSelectedItem().toString();

                final String year = newBirthYearText.getText();
                final String month = newBirthMonthText.getText();
                final String day = newBirthDayText.getText();

                if (username.equals("") || password.equals("") || email.equals("") || firstName.equals("") || lastName.equals("") || address.equals("") || city.equals("")
                        || cnp.equals("") || phone.equals("") || year.equals("") || month.equals("") || day.equals("") || functie.equals("") || salariu.equals("")) {
                    final JFrame warningDialog = new JFrame();
                    JOptionPane.showMessageDialog(warningDialog, "Trebuie completate toate campurile!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final String birthdate = year + "-" + month + "-" + day;

                    final EmployeeManager empMan = EmployeeManager.getInstance();
                    final AccountManager accMan = AccountManager.getInstance();
                    final StoreManager storeMan = StoreManager.getInstance();
                    final Store store = storeMan.getStoreByCity(mag);

                    final Employee emp = new Employee(0, lastName, firstName, address, city, LocalDate.parse(birthdate), cnp, phone, email, functie, Integer.parseInt(salariu), true, store.getId());
                    empMan.createEmployee(emp);
                    final Account acc = new Account(0, username, password, null, 2, emp.getIdEmp());
                    accMan.createEmployeeAccount(acc);

                    final JFrame confirmDialog = new JFrame();
                    JOptionPane.showMessageDialog(confirmDialog, "Your account has successfully been created!");
                }
            }
        });
    }

    public static NewEmployeeWindow getInstance() {
        if (instance == null) {
            instance = new NewEmployeeWindow();
        }

        return instance;
    }
}
