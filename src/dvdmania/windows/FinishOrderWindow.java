package dvdmania.windows;

import dvdmania.management.ClientManager;
import dvdmania.management.Store;
import dvdmania.management.StoreManager;
import dvdmania.products.Order;
import dvdmania.products.OrderManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public final class FinishOrderWindow extends JFrame {

    private static FinishOrderWindow instance = null;

    private FinishOrderWindow() {
        super();

        final OrderManager orderMan = OrderManager.getInstance();
        final StoreManager storeMan = StoreManager.getInstance();
        final Store store = storeMan.getStoreByCity(GUI.getLoggedEmployee().getOras());
        final ArrayList<Order> orders = orderMan.getStoreActiveOrders(store);
        final JTable table = new JTable();
        final DefaultTableModel tableModel;

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final String[] columns = new String[]{"Nume", "Prenume", "CNP", "ID produs", "Data imprumutului"};
        tableModel.setColumnIdentifiers(columns);

        for (int i = 0; i < orders.size(); i++) {
            final Order order = orders.get(i);
            tableModel.addRow(orderMan.orderToRow(order));
        }

        table.setModel(tableModel);
        final JScrollPane scroll = new JScrollPane(table);

        final JButton order, exit;
        final JPanel firstWindowPanel, firstWindowButtonsPanel;
        order = new JButton("Return order");
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
        this.setTitle("Imprumuturi");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectionModel().isSelectionEmpty()) {
                    final JFrame warningDialog = new JFrame();
                    JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai o comanda din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                } else {
                    final Iterator iter = orders.iterator();
                    Order order = null;
                    while (iter.hasNext()) {
                        final Order ord = (Order) iter.next();
                        final int idProd = ord.getStock().getIdProduct();
                        final String cnp = ord.getClient().getCnp();
                        final LocalDate dataImp = ord.getBorrowDate();
                        if (idProd == Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 3))) &&
                                cnp.equals(String.valueOf(table.getValueAt(table.getSelectedRow(), 2))) &&
                                dataImp.equals(LocalDate.parse(String.valueOf(table.getValueAt(table.getSelectedRow(), 4))))) {
                            order = ord;
                        }
                    }
                    final int rez = orderMan.checkInOrder(order.getStock(), order.getClient(), order.getBorrowDate());
                    if (rez <= 7) {
                        final ClientManager clientMan = ClientManager.getInstance();
                        clientMan.rewardClient(order.getClient());
                        final JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "Clientul a adus produsul la timp!");
                    } else {
                        final ClientManager clientMan = ClientManager.getInstance();
                        clientMan.punishClient(order.getClient());
                        final JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "Atentie! Clientul nu a adus produsul la timp!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinishOrderWindow.getInstance().setVisible(false);
                FinishOrderWindow.getInstance().dispose();
            }
        });
    }

    public static FinishOrderWindow getInstance() {
        if (instance == null) {
            instance = new FinishOrderWindow();
        }

        return instance;
    }
}
