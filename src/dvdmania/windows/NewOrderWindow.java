package dvdmania.windows;

import dvdmania.management.*;
import dvdmania.products.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public final class NewOrderWindow extends JFrame {

    public NewOrderWindow() {
        super();

        final ClientManager clientMan = ClientManager.getInstance();
        final ArrayList<Client> customers = clientMan.getAllClients();
        final JTable table = new JTable();
        final DefaultTableModel tableModel;

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final String[] columns = new String[]{"ID", "Nume", "Prenume", "Adresa", "Oras", "Data nasterii", "CNP", "Telefon", "Email"};
        tableModel.setColumnIdentifiers(columns);

        for (int i = 0; i < customers.size(); i++) {
            final Client client = customers.get(i);
            tableModel.addRow(clientMan.clientToRow(client));
        }

        table.setModel(tableModel);
        final JScrollPane scroll = new JScrollPane(table);

        final JButton order, exit;
        final JPanel firstWindowPanel, firstWindowButtonsPanel;
        order = new JButton("New order");
        exit = new JButton("Exit");
        firstWindowButtonsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        firstWindowButtonsPanel.add(order);
        firstWindowButtonsPanel.add(exit);
        firstWindowPanel = new JPanel(new BorderLayout());
        firstWindowPanel.add(scroll, BorderLayout.CENTER);
        firstWindowPanel.add(firstWindowButtonsPanel, BorderLayout.SOUTH);

        this.add(firstWindowPanel);
        this.setVisible(true);
        this.setSize(1000, 300);
        this.setTitle("Clienti");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JPanel detalii, butoane, main;
                final JLabel categorie = new JLabel("Categorie:"), id = new JLabel("ID:");
                final JTextField idField = new JTextField();
                final JComboBox categorieBox = new JComboBox(new String[]{"Filme", "Jocuri", "Albume"});
                final JButton order = new JButton("Order"), exit = new JButton("Exit");

                detalii = new JPanel(new GridLayout(2, 2, 5, 5));
                detalii.add(categorie);
                detalii.add(categorieBox);
                detalii.add(id);
                detalii.add(idField);
                butoane = new JPanel(new GridLayout(1, 2, 5, 5));
                butoane.add(order);
                butoane.add(exit);
                main = new JPanel(new BorderLayout());
                main.add(detalii, BorderLayout.CENTER);
                main.add(butoane, BorderLayout.SOUTH);

                final JFrame orderFrame = new JFrame();
                orderFrame.add(main);
                orderFrame.setVisible(true);
                orderFrame.setSize(300, 200);
                orderFrame.setLocationRelativeTo(null);

                order.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (idField.getText().equals("")) {
                            final JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Introduceti ID-ul produsului", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            final String cat = categorieBox.getSelectedItem().toString();
                            final StockManager stockMan = StockManager.getInstance();
                            final StoreManager storeMan = StoreManager.getInstance();
                            final Store store = storeMan.getStoreByEmployee(GUI.getLoggedEmployee());
                            switch (cat) {
                                case "Filme":
                                    final MovieManager movieMan = MovieManager.getInstance();
                                    final Movie movie = movieMan.getMovieById(Integer.parseInt(idField.getText()));
                                    if (movie != null) {
                                        final int nrFilme = stockMan.checkMovieStock(movie, store);
                                        if (nrFilme > 0) {
                                            final OrderManager orderMan = OrderManager.getInstance();

                                            final ClientManager clientMan = ClientManager.getInstance();
                                            final Client client = clientMan.getClientById(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));

                                            if (client != null) {
                                                final LocalDate dataRet = orderMan.checkOutMovieOrder(movie, client, GUI.getLoggedEmployee());
                                                final JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul a fost inchiriat! Aceste trebuie returnat pana in data de " + dataRet, "Succes", JOptionPane.WARNING_MESSAGE);
                                                LogManager.getInstance().insertLog(GUI.getInstance().getLoggedEmployee(), "Rented movie to client with id " + client.getId());

                                            } else {
                                                final JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul nu a fost inchiriat deoarece a aparut o problema. Incercati din nou!", "Warning", JOptionPane.WARNING_MESSAGE);
                                            }
                                        } else {
                                            final JFrame dialog = new JFrame();
                                            JOptionPane.showMessageDialog(dialog, "Acest produs nu mai este in stoc.", "Warning", JOptionPane.WARNING_MESSAGE);
                                        }
                                    } else {
                                        final JFrame dialog = new JFrame();
                                        JOptionPane.showMessageDialog(dialog, "Acest produs nu exista in baza de date!", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                    break;

                                case "Jocuri":
                                    final GameManager gameMan = GameManager.getInstance();
                                    final Game game = gameMan.getGameById(Integer.parseInt(idField.getText()));
                                    if (game != null) {
                                        final int nrJocuri = stockMan.checkGameStock(game, store);
                                        if (nrJocuri > 0) {
                                            final OrderManager orderMan = OrderManager.getInstance();

                                            final ClientManager clientMan = ClientManager.getInstance();
                                            final Client client = clientMan.getClientById(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));

                                            if (client != null) {
                                                final LocalDate dataRet = orderMan.checkOutGameOrder(game, client, GUI.getLoggedEmployee());
                                                final JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul a fost inchiriat! Aceste trebuie returnat pana in data de " + dataRet, "Succes", JOptionPane.WARNING_MESSAGE);
                                                LogManager.getInstance().insertLog(GUI.getInstance().getLoggedEmployee(), "Rented game to client with id " + client.getId());
                                            } else {
                                                final JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul nu a fost inchiriat deoarece a aparut o problema. Incercati din nou!", "Warning", JOptionPane.WARNING_MESSAGE);
                                            }
                                        } else {
                                            final JFrame dialog = new JFrame();
                                            JOptionPane.showMessageDialog(dialog, "Acest produs nu mai este in stoc.", "Warning", JOptionPane.WARNING_MESSAGE);
                                        }
                                    } else {
                                        final JFrame dialog = new JFrame();
                                        JOptionPane.showMessageDialog(dialog, "Acest produs nu exista in baza de date!", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                    break;
                                case "Albume":
                                    final AlbumManager albumManager = AlbumManager.getInstance();
                                    final Album album = albumManager.getAlbumById(Integer.parseInt(idField.getText()));
                                    if (album != null) {
                                        final int nrAlbume = stockMan.checkAlbumStock(album, store);
                                        if (nrAlbume > 0) {
                                            final OrderManager orderMan = OrderManager.getInstance();

                                            final ClientManager clientMan = ClientManager.getInstance();
                                            final Client client = clientMan.getClientById(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));

                                            if (client != null) {
                                                final LocalDate dataRet = orderMan.checkOutAlbumOrder(album, client, GUI.getLoggedEmployee());
                                                final JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul a fost inchiriat! Aceste trebuie returnat pana in data de " + dataRet, "Succes", JOptionPane.WARNING_MESSAGE);
                                                LogManager.getInstance().insertLog(GUI.getInstance().getLoggedEmployee(), "Rented album to client with id " + client.getId());
                                            } else {
                                                final JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul nu a fost inchiriat deoarece a aparut o problema. Incercati din nou!", "Warning", JOptionPane.WARNING_MESSAGE);
                                            }
                                        } else {
                                            final JFrame dialog = new JFrame();
                                            JOptionPane.showMessageDialog(dialog, "Acest produs nu mai este in stoc.", "Warning", JOptionPane.WARNING_MESSAGE);
                                        }
                                    } else {
                                        final JFrame dialog = new JFrame();
                                        JOptionPane.showMessageDialog(dialog, "Acest produs nu exista in baza de date!", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                            }
                        }
                    }
                });

                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        orderFrame.setVisible(false);
                        orderFrame.dispose();
                    }
                });


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
