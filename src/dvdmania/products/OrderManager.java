package dvdmania.products;

import dvdmania.management.*;
import dvdmania.tools.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderManager {

    ConnectionManager connMan = ConnectionManager.getInstance();
    private static OrderManager instance = null;

    private OrderManager() {
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }

        return instance;
    }

    public int checkAvailability(Stock stock, Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        int results = 0;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT COUNT(id_prod) FROM imprumuturi WHERE id_prod=? AND data_ret=NULL";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, stock.getIdProduct());
            result = statement.executeQuery();

            while (result.next()) {
                results = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    public int checkInOrder(Stock stock, Client client, LocalDate returnDate) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        int rowsUpdated = 0;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE imprumuturi SET data_ret=SYSDATE() WHERE id_prod=? AND id_cl=? AND data_imp=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, stock.getIdProduct());
            statement.setInt(2, client.getId());
            statement.setObject(3, returnDate);
            rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                sql = "SELECT DATEDIFF(data_ret, data_imp) FROM imprumuturi WHERE id_prod=? AND id_cl=? AND data_imp=?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, stock.getIdProduct());
                statement.setInt(2, client.getId());
                statement.setObject(3, returnDate);
                result = statement.executeQuery();

                while (result.next()) {
                    rowsUpdated = result.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsUpdated;
    }

    public LocalDate checkOutMovieOrder(Movie movie, Client client, Employee employee) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        LocalDate date = null;

        StockManager stockMan = StockManager.getInstance();
        Store store = new Store();
        store.setId(employee.getIdMag());
        Stock stock = stockMan.getMovieStock(movie, store);

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO imprumuturi(id_prod, id_cl, id_angaj, id_mag, data_imp, pret) VALUES(?,?,?,?, SYSDATE(),?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, stock.getIdProduct());
            statement.setInt(2, client.getId());
            statement.setInt(3, employee.getIdEmp());
            statement.setInt(4, employee.getIdMag());
            statement.setInt(5, stock.getPrice());
            statement.executeUpdate();

            sql = "SELECT DATE_ADD(sysdate(), INTERVAL 7 day)";
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                date = result.getDate(1).toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public LocalDate checkOutGameOrder(Game game, Client client, Employee employee) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        LocalDate date = null;

        StockManager stockMan = StockManager.getInstance();
        Store store = new Store();
        store.setId(employee.getIdMag());
        Stock stock = stockMan.getGameStock(game, store);

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO imprumuturi(id_prod, id_cl, id_angaj, id_mag, data_imp, pret) VALUES(?,?,?,?, SYSDATE(),?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, stock.getIdProduct());
            statement.setInt(2, client.getId());
            statement.setInt(3, employee.getIdEmp());
            statement.setInt(4, employee.getIdMag());
            statement.setInt(5, stock.getPrice());
            statement.executeUpdate();

            sql = "SELECT DATE_ADD(sysdate(), INTERVAL 7 day)";
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                date = result.getDate(1).toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public LocalDate checkOutAlbumOrder(Album album, Client client, Employee employee) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        LocalDate date = null;

        StockManager stockMan = StockManager.getInstance();
        Store store = new Store();
        store.setId(employee.getIdMag());
        Stock stock = stockMan.getAlbumStock(album, store);

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO imprumuturi(id_prod, id_cl, id_angaj, id_mag, data_imp, pret) VALUES(?,?,?,?, SYSDATE(),?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, stock.getIdProduct());
            statement.setInt(2, client.getId());
            statement.setInt(3, employee.getIdEmp());
            statement.setInt(4, employee.getIdMag());
            statement.setInt(5, stock.getPrice());
            statement.executeUpdate();

            sql = "SELECT DATE_ADD(sysdate(), INTERVAL 7 day)";
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                date = result.getDate(1).toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public ArrayList<Order> getStoreActiveOrders(Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Order> orderList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT i.id_prod, i.id_cl, i.id_angaj, i.data_imp, i.pret FROM imprumuturi i JOIN magazin m USING(id_mag) " +
                    "WHERE i.data_ret IS NULL AND m.id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, store.getId());
            result = statement.executeQuery();

            StockManager stockMan = StockManager.getInstance();
            ClientManager clientMan = ClientManager.getInstance();
            EmployeeManager empMan = EmployeeManager.getInstance();

            while (result.next()) {
                int idStock = result.getInt("id_prod");
                int idClient = result.getInt("id_cl");
                int idEmp = result.getInt("id_angaj");
                LocalDate date = result.getDate("data_imp").toLocalDate();
                int price = result.getInt("pret");

                Stock stock = stockMan.getStockById(idStock);
                Client client = clientMan.getClientById(idClient);
                Employee emp = empMan.getEmployeeById(idEmp);

                Order order = new Order(stock, client, emp, store, date, null, price);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return orderList;
    }

    public ArrayList<Order> getAllStoreOrders(Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Order> orderList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_prod, id_cl, id_angaj, data_imp, data_ret, pret FROM imprumuturi " +
                    "WHERE id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, store.getId());
            result = statement.executeQuery();

            StockManager stockMan = StockManager.getInstance();
            ClientManager clientMan = ClientManager.getInstance();
            EmployeeManager empMan = EmployeeManager.getInstance();

            while (result.next()) {
                int idStock = result.getInt("id_prod");
                int idClient = result.getInt("id_cl");
                int idEmp = result.getInt("id_angaj");
                LocalDate date = result.getDate("data_imp").toLocalDate();
                LocalDate retDate = (result.getDate("data_ret") == null) ? null : result.getDate("data_ret").toLocalDate();
                int price = result.getInt("pret");

                Stock stock = stockMan.getStockById(idStock);
                Client client = clientMan.getClientById(idClient);
                Employee emp = empMan.getEmployeeById(idEmp);

                Order order = new Order(stock, client, emp, store, date, retDate, price);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return orderList;
    }

    public ArrayList<Order> getAllClientOrders(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Order> orderList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_mag, id_prod, id_angaj, data_imp, data_ret, pret FROM imprumuturi " +
                    "WHERE id_cl=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, client.getId());
            result = statement.executeQuery();

            StockManager stockMan = StockManager.getInstance();
            StoreManager storeMan = StoreManager.getInstance();
            EmployeeManager empMan = EmployeeManager.getInstance();

            while (result.next()) {
                int idStock = result.getInt("id_prod");
                int idStore = result.getInt("id_mag");
                int idEmp = result.getInt("id_angaj");
                LocalDate date = result.getDate("data_imp").toLocalDate();
                LocalDate retDate = (result.getDate("data_ret") == null) ? null : result.getDate("data_ret").toLocalDate();
                int price = result.getInt("pret");

                Stock stock = stockMan.getStockById(idStock);
                Store store = storeMan.getStoreById(idStore);
                Employee emp = empMan.getEmployeeById(idEmp);

                Order order = new Order(stock, client, emp, store, date, retDate, price);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return orderList;
    }

    public int getActiveOrders(int idStock, int idStore) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        int orders = 0;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT COUNT(*) FROM imprumuturi " +
                    "WHERE data_ret IS NULL AND id_prod=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idStock);
            statement.setInt(2, idStore);
            result = statement.executeQuery();

            while (result.next()) {
                orders = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return orders;
    }

    public String[] orderToRow(Order order) {
        String[] row = new String[5];
        row[0] = order.getClient().getNume();
        row[1] = order.getClient().getPrenume();
        row[2] = order.getClient().getCnp();
        row[3] = order.getStock().getIdProduct() + "";
        row[4] = order.getBorrowDate().toString();

        return row;
    }

    public ArrayList<Order> getOrdersByDates(LocalDate date1, LocalDate date2, Store oras) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Order> orderList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_prod, id_cl, id_angaj, id_mag, data_imp, data_ret, pret FROM dvdmania.imprumuturi ";

            if (date1 != null && date2 != null) {
                if (oras == null) {
                    sql += "WHERE data_imp > ? AND data_imp < ?";
                    statement = connection.prepareStatement(sql);
                    statement.setObject(1, date1);
                    statement.setObject(2, date2);
                    result = statement.executeQuery();
                } else {
                    sql += "WHERE data_imp > ? AND data_imp < ? AND id_mag = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setObject(1, date1);
                    statement.setObject(2, date2);
                    statement.setInt(3, oras.getId());
                    result = statement.executeQuery();
                }
            } else if (date1 == null && date2 == null) {
                if (oras != null) {
                    sql += "WHERE id_mag = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, oras.getId());
                    result = statement.executeQuery();
                } else {
                    statement = connection.prepareStatement(sql);
                    result = statement.executeQuery();
                }
            } else if (date1 == null) {
                if (oras == null) {
                    sql += "WHERE data_imp > ?";
                    statement = connection.prepareStatement(sql);
                    statement.setObject(1, date1);
                    result = statement.executeQuery();
                } else {
                    sql += "WHERE data_imp > ? AND id_mag = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setObject(1, date1);
                    statement.setInt(2, oras.getId());
                    result = statement.executeQuery();
                }
            } else if (date2 == null) {
                if (oras == null) {
                    sql += "WHERE data_imp < ?";
                    statement = connection.prepareStatement(sql);
                    statement.setObject(1, date1);
                    result = statement.executeQuery();
                } else {
                    sql += "WHERE data_imp < ? AND id_mag = ?";
                    statement = connection.prepareStatement(sql);
                    statement.setObject(1, date1);
                    statement.setInt(2, oras.getId());
                    result = statement.executeQuery();
                }
            }

            StockManager stockMan = StockManager.getInstance();
            ClientManager clientMan = ClientManager.getInstance();
            EmployeeManager empMan = EmployeeManager.getInstance();

            while (result.next()) {
                int idStock = result.getInt("id_prod");
                int idClient = result.getInt("id_cl");
                int idEmp = result.getInt("id_angaj");
                LocalDate date = result.getDate("data_imp").toLocalDate();
                LocalDate retDate = (result.getDate("data_ret") == null) ? null : result.getDate("data_ret").toLocalDate();
                int price = result.getInt("pret");

                Store store;

                if (oras == null) {
                    int storeId = result.getInt("id_mag");
                    store = StoreManager.getInstance().getStoreById(storeId);
                } else {
                    store = oras;
                }

                Stock stock = stockMan.getStockById(idStock);
                Client client = clientMan.getClientById(idClient);
                Employee emp = empMan.getEmployeeById(idEmp);

                Order order = new Order(stock, client, emp, store, date, retDate, price);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return orderList;
    }
}
