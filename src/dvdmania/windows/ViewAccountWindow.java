package dvdmania.windows;

import dvdmania.management.Client;
import dvdmania.management.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class ViewAccountWindow extends JFrame {

    public ViewAccountWindow() {
        super();

        if (GUI.getPriv() == 1) {
            final JLabel nume, prenume, adresa, oras, datan, cnp, tel, email, loialitate;
            final JButton exit;

            final Client client = GUI.getLoggedClient();

            nume = new JLabel("Nume: " + client.getNume());
            prenume = new JLabel("Prenume: " + client.getPrenume());
            adresa = new JLabel("Adresa: " + client.getAdresa());
            oras = new JLabel("Oras: " + client.getOras());
            datan = new JLabel("Data nasterii: " + client.getDatan());
            cnp = new JLabel("CNP: " + client.getCnp());
            tel = new JLabel("Telefon: " + client.getTel());
            email = new JLabel("Email: " + client.getEmail());
            loialitate = new JLabel("Loialitate: " + client.getLoialitate());
            exit = new JButton("Exit");

            final JPanel main, detail;
            detail = new JPanel(new GridLayout(9, 1, 5, 5));
            detail.add(nume);
            detail.add(prenume);
            detail.add(adresa);
            detail.add(oras);
            detail.add(datan);
            detail.add(cnp);
            detail.add(tel);
            detail.add(email);
            detail.add(loialitate);
            main = new JPanel(new BorderLayout());
            main.add(detail, BorderLayout.CENTER);
            main.add(exit, BorderLayout.SOUTH);

            this.add(main);
            this.setVisible(true);
            this.setSize(300, 300);
            this.setLocationRelativeTo(null);
            this.setTitle("Detalii cont");
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
            });
        } else if (GUI.getPriv() != 0) {
            final JLabel nume, prenume, adresa, oras, datan, cnp, tel, email, functie;
            final JButton exit;

            final Employee employee = GUI.getLoggedEmployee();

            nume = new JLabel("Nume: " + employee.getNume());
            prenume = new JLabel("Prenume: " + employee.getPrenume());
            adresa = new JLabel("Adresa: " + employee.getAdresa());
            oras = new JLabel("Oras: " + employee.getOras());
            datan = new JLabel("Data nasterii: " + employee.getDatan());
            cnp = new JLabel("CNP: " + employee.getCnp());
            tel = new JLabel("Telefon: " + employee.getTelefon());
            email = new JLabel("Email: " + employee.getEmail());
            functie = new JLabel("Functie: " + employee.getFunctie());
            exit = new JButton("Exit");

            final JPanel main, detail;
            detail = new JPanel(new GridLayout(9, 1, 5, 5));
            detail.add(nume);
            detail.add(prenume);
            detail.add(adresa);
            detail.add(oras);
            detail.add(datan);
            detail.add(cnp);
            detail.add(tel);
            detail.add(email);
            detail.add(functie);
            main = new JPanel(new BorderLayout());
            main.add(detail, BorderLayout.CENTER);
            main.add(exit, BorderLayout.SOUTH);

            this.add(main);
            this.setVisible(true);
            this.setSize(300, 300);
            this.setLocationRelativeTo(null);
            this.setTitle("Detalii cont");
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
            });
        } else {
            final JFrame dialog = new JFrame();
            JOptionPane.showMessageDialog(dialog, "Sunteti logat ca si musafir. Creati mai intai un cont personal!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
