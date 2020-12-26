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

    public NewEmployeeWindow() {
        super();
        //Username
        final JLabel newUserLabel = new JLabel();
        newUserLabel.setText("Username: ");
        final JTextField newUsernameText = new JTextField();

        //Password
        final JLabel newPasswordLabel = new JLabel();
        newPasswordLabel.setText("Password: ");
        final JPasswordField newPasswordText = new JPasswordField();

        //Email
        final JLabel newEmailLabel = new JLabel();
        newEmailLabel.setText("Email: ");
        final JTextField newEmailText = new JTextField();

        //Nume
        final JLabel newNameLabel = new JLabel();
        newNameLabel.setText("Nume: ");
        final JTextField newNameText = new JTextField();

        //Prenume
        final JLabel newPrenLabel = new JLabel();
        newPrenLabel.setText("Prenume: ");
        final JTextField newPrenText = new JTextField();

        //Adresa
        final JLabel newAddressLabel = new JLabel();
        newAddressLabel.setText("Adresa: ");
        final JTextField newAddressText = new JTextField();

        //Oras
        final JLabel newCityLabel = new JLabel();
        newCityLabel.setText("Oras: ");
        final JTextField newCityText = new JTextField();

        //CNP
        final JLabel newCNPLabel = new JLabel();
        newCNPLabel.setText("CNP: ");
        final JTextField newCNPText = new JTextField();

        //Telefon
        final JLabel newTelLabel = new JLabel();
        newTelLabel.setText("Telefon: ");
        final JTextField newTelText = new JTextField();

        //Functie
        final JLabel newFuncLabel = new JLabel("Functie:");
        final JTextField newFuncField = new JTextField();

        //Salariu
        final JLabel newSalLabel = new JLabel("Salariu:");
        final JTextField newSalField = new JTextField();

        //Magazin
        final JLabel newMagLabel = new JLabel("Magazin:");
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
        final JLabel newBirthYearLabel = new JLabel("An(yyyy)");
        final JLabel newBirthMonthLabel = new JLabel("Luna(mm)");
        final JLabel newBirthDayLabel = new JLabel("Zi(dd)");
        final JTextField newBirthYearText = new JTextField();
        final JTextField newBirthMonthText = new JTextField();
        final JTextField newBirthDayText = new JTextField();

        //Data panel
        final JPanel newDataPanel = new JPanel(new GridLayout(12, 2, 5, 5));
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
        final JPanel newBirthPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        newBirthPanel.add(newBirthYearLabel);
        newBirthPanel.add(newBirthMonthLabel);
        newBirthPanel.add(newBirthDayLabel);
        newBirthPanel.add(newBirthYearText);
        newBirthPanel.add(newBirthMonthText);
        newBirthPanel.add(newBirthDayText);

        //Button Panel
        final JPanel newButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        final JButton newCreateButton = new JButton("Create");
        final JButton newCancelButton = new JButton("Cancel");
        newButtonPanel.add(newCreateButton);
        newButtonPanel.add(newCancelButton);

        //dvdmania.tools.Main Panel
        final JPanel newMainPanel = new JPanel(new BorderLayout());
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
                    setVisible(false);
                    dispose();
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
}
