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

    ConnectionManager connMan = null;

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

        StockManager stockMan = new StockManager();
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

        StockManager stockMan = new StockManager();
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

        StockManager stockMan = new StockManager();
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
            String sql = "SELECT i.id_prod, i.id_cl, i.id_angaj, i.data_imp, i.pret FROM imprumuturi JOIN magazin m USING(id_mag) " +
                    "WHERE i.data_ret IS NULL AND m.id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, store.getId());
            result = statement.executeQuery();

            StockManager stockMan = new StockManager();
            ClientManager clientMan = new ClientManager();
            EmployeeManager empMan = new EmployeeManager();

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
            String sql = "SELECT id_prod, id_cl, id_angaj, data_imp, pret FROM imprumuturi" +
                    "WHERE id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, store.getId());
            result = statement.executeQuery();

            StockManager stockMan = new StockManager();
            ClientManager clientMan = new ClientManager();
            EmployeeManager empMan = new EmployeeManager();

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

    public ArrayList<Order> getAllClientOrders(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Order> orderList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_mag, id_prod, id_angaj, data_imp, pret FROM imprumuturi " +
                    "WHERE id_cl=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, client.getId());
            result = statement.executeQuery();

            StockManager stockMan = new StockManager();
            StoreManager storeMan = new StoreManager();
            EmployeeManager empMan = new EmployeeManager();

            while (result.next()) {
                int idStock = result.getInt("id_prod");
                int idStore = result.getInt("id_mag");
                int idEmp = result.getInt("id_angaj");
                LocalDate date = result.getDate("data_imp").toLocalDate();
                int price = result.getInt("pret");

                Stock stock = stockMan.getStockById(idStock);
                Store store = storeMan.getStoreById(idStore);
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

    public String[] orderToRow(Order order) {
        String[] row = new String[5];
        row[0] = order.getClient().getNume();
        row[1] = order.getClient().getPrenume();
        row[2] = order.getClient().getCnp();
        row[3] = order.getStock().getIdProduct() + "";
        row[4] = order.getBorrowDate().toString();

        return row;
    }
}
