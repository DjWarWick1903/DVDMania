package dvdmania.windows;

import dvdmania.management.StoreManager;
import dvdmania.products.Order;
import dvdmania.products.OrderManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewAllOrdersWindow extends JFrame {

    private static ViewAllOrdersWindow instance = null;

    private ViewAllOrdersWindow() {
        super();

        final OrderManager orderMan = OrderManager.getInstance();
        ArrayList<Order> orders = new ArrayList<>();
        String[] columns = new String[]{};
        if (GUI.getPriv() == 1) {
            orders = orderMan.getAllClientOrders(GUI.getLoggedClient());
            columns = new String[]{"Nume", "Prenume", "ID produs", "Data imprumutului", "Data returnarii", "Pret"};
        } else if (GUI.getPriv() == 2 || GUI.getPriv() == 3) {
            StoreManager storeMan = StoreManager.getInstance();
            orders = orderMan.getAllStoreOrders(storeMan.getStoreById(GUI.getLoggedEmployee().getIdMag()));
            columns = new String[]{"Nume", "Prenume", "CNP", "ID produs", "Data imprumutului", "Data returnarii", "Pret"};
        }
        final JTable table = new JTable();
        final DefaultTableModel tableModel;

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.setColumnIdentifiers(columns);

        for (int i = 0; i < orders.size(); i++) {
            final Order order = orders.get(i);
            if (GUI.getPriv() == 1) {
                final String[] row = new String[6];
                row[0] = order.getClient().getNume();
                row[1] = order.getClient().getPrenume();
                row[2] = order.getStock().getIdProduct() + "";
                row[3] = order.getBorrowDate().toString();
                row[4] = (order.getReturnDate() == null) ? "Imprumutat" : order.getReturnDate().toString();
                row[5] = order.getPrice() + "";
                tableModel.addRow(row);
            } else {
                final String[] row = new String[7];
                row[0] = order.getClient().getNume();
                row[1] = order.getClient().getPrenume();
                row[2] = order.getClient().getCnp();
                row[3] = order.getStock().getIdProduct() + "";
                row[4] = order.getBorrowDate().toString();
                row[5] = (order.getReturnDate() == null) ? "Imprumutat" : order.getReturnDate().toString();
                row[6] = order.getPrice() + "";
                tableModel.addRow(row);
            }
        }

        table.setModel(tableModel);
        final JScrollPane scroll = new JScrollPane(table);

        final JButton exit;
        final JPanel firstWindowPanel;

        exit = new JButton("Exit");
        firstWindowPanel = new JPanel(new BorderLayout());
        firstWindowPanel.add(scroll, BorderLayout.CENTER);
        firstWindowPanel.add(exit, BorderLayout.SOUTH);

        this.add(firstWindowPanel);
        this.setVisible(true);
        this.setSize(1000, 300);
        this.setTitle("Istoric");
        this.setLocationRelativeTo(null);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewAllOrdersWindow.getInstance().setVisible(false);
                ViewAllOrdersWindow.getInstance().dispose();
            }
        });
    }

    public static ViewAllOrdersWindow getInstance() {
        if (instance == null) {
            instance = new ViewAllOrdersWindow();
        }

        return instance;
    }
}
