package dvdmania.windows;

import dvdmania.management.Account;
import dvdmania.management.AccountManager;
import dvdmania.management.Client;
import dvdmania.management.ClientManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public final class NewCustomerWindow extends JFrame {

    //window instance
    private static NewCustomerWindow instance = null;

    //window variables
    private static JPasswordField newPasswordText;
    private static JPanel newDataPanel, newBirthPanel, newButtonPanel, newMainPanel;
    private static JButton newCreateButton, newCancelButton;
    private static JLabel newUserLabel, newPasswordLabel, newEmailLabel, newNameLabel, newPrenLabel,
            newAddressLabel, newCityLabel, newCNPLabel, newTelLabel, newBirthYearLabel, newBirthMonthLabel,
            newBirthDayLabel;
    private static JTextField newUsernameText, newEmailText, newNameText, newPrenText, newAddressText, newCityText,
            newCNPText, newTelText, newBirthYearText, newBirthMonthText, newBirthDayText;

    private NewCustomerWindow() {
        super("New customer");

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

        //First Name
        newNameLabel = new JLabel();
        newNameLabel.setText("Nume: ");
        newNameText = new JTextField();

        //Last Name
        newPrenLabel = new JLabel();
        newPrenLabel.setText("Prenume: ");
        newPrenText = new JTextField();

        //Address
        newAddressLabel = new JLabel();
        newAddressLabel.setText("Address: ");
        newAddressText = new JTextField();

        //City
        newCityLabel = new JLabel();
        newCityLabel.setText("City: ");
        newCityText = new JTextField();

        //CNP
        newCNPLabel = new JLabel();
        newCNPLabel.setText("CNP: ");
        newCNPText = new JTextField();

        //Phone
        newTelLabel = new JLabel();
        newTelLabel.setText("Phone: ");
        newTelText = new JTextField();

        //Birthday
        newBirthYearLabel = new JLabel("Year(yyyy)");
        newBirthMonthLabel = new JLabel("Month(mm)");
        newBirthDayLabel = new JLabel("Day(dd)");
        newBirthYearText = new JTextField();
        newBirthMonthText = new JTextField();
        newBirthDayText = new JTextField();

        //Data panel
        newDataPanel = new JPanel(new GridLayout(9, 2, 5, 5));
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
        this.setSize(300, 330);
        this.setLocationRelativeTo(null);

        newCancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame dialogFrame = new JFrame();
                final int dialogResult = JOptionPane.showConfirmDialog(dialogFrame, "Are you sure?", "Exiting", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    getInstance().setVisible(false);
                    getInstance().dispose();
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

                final String year = newBirthYearText.getText();
                final String month = newBirthMonthText.getText();
                final String day = newBirthDayText.getText();

                if (username.equals("") || password.equals("") || email.equals("") || firstName.equals("") || lastName.equals("") || address.equals("") || city.equals("")
                        || cnp.equals("") || phone.equals("") || year.equals("") || month.equals("") || day.equals("")) {
                    final JFrame warningDialog = new JFrame();
                    JOptionPane.showMessageDialog(warningDialog, "You must complete all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final String birthdate = new String(year + "-" + month + "-" + day);

                    final Client client = new Client(0, lastName, firstName, address, city, LocalDate.parse(birthdate), cnp, phone, (email.isEmpty()) ? null : email, 5);
                    final Account account = new Account(0, username, password, null, 1, client.getId());
                    final AccountManager accountMan = AccountManager.getInstance();
                    final ClientManager clientMan = ClientManager.getInstance();

                    final int clID = clientMan.createClient(client);
                    account.setIdUtil(clID);
                    final int accID = accountMan.createClientAccount(account);

                    if (clID != 0) {
                        if (accID != 0) {
                            final JFrame confirmDialog = new JFrame();
                            JOptionPane.showMessageDialog(confirmDialog, "Client data has been successfully uploaded.");
                        } else {
                            final JFrame confirmDialog = new JFrame();
                            JOptionPane.showMessageDialog(confirmDialog, "There was a problem creating the account.");
                        }
                    } else {
                        final JFrame confirmDialog = new JFrame();
                        JOptionPane.showMessageDialog(confirmDialog, "There was a problem uploading the client.");
                    }
                }
            }
        });
    }

    public static NewCustomerWindow getInstance() {
        if (instance == null) {
            instance = new NewCustomerWindow();
        }

        return instance;
    }
}
