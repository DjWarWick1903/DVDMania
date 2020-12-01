package dvdmania.management;

import dvdmania.tools.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class StoreManager {

    ConnectionManager connMan = new ConnectionManager();

    public Store getStoreById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Store store = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT adresa, oras, tel FROM magazin WHERE id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                String adress = result.getString(1);
                String city = result.getString(2);
                String tel = result.getString(3);

                store = new Store(id, adress, city, tel);
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

        return store;
    }

    public Store getStoreByCity(String city) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Store store = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_mag, adresa, tel FROM dvdmania.magazin WHERE oras=?";
            connection.prepareStatement(sql);
            statement.setString(1, city);
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                String adress = result.getString(2);
                String tel = result.getString(3);

                store = new Store(id, adress, city, tel);
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

        return store;
    }

    public int createStore(Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        int newKey = 0;
        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO dvdmania.magazin (adresa, oras, tel) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, store.getAdresa());
            statement.setString(2, store.getOras());
            statement.setString(3, store.getTelefon());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted != 0) {
                ResultSet keySet = statement.getGeneratedKeys();
                if (keySet.next()) {
                    newKey = keySet.getInt(1);
                    store.setId(newKey);
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

        return newKey;
    }

    public int updateStore(Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsUpdated = 0;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.magazin SET adresa=?, oras=?, tel=? WHERE id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, store.getAdresa());
            statement.setString(2, store.getOras());
            statement.setString(3, store.getTelefon());
            statement.setInt(4, store.getId());

            rowsUpdated = statement.executeUpdate();

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

    public ArrayList<Store> getStores() {
        ArrayList<Store> stores = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_mag, adresa, oras, tel FROM dvdmania.magazin";
            statement = connection.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                int idMag = result.getInt("id_mag");
                String adresa = result.getString("adresa");
                String oras = result.getString("oras");
                String telefon = result.getString("tel");

                Store store = new Store(idMag, adresa, oras, telefon);
                stores.add(store);
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

        return stores;
    }

    public LinkedList<String> getStoreCities() {
        LinkedList<String> cities = new LinkedList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT oras FROM dvdmania.magazin";
            result = statement.executeQuery(sql);

            while (result.next()) {
                String city = result.getString("oras");
                cities.add(city);
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

        return cities;
    }

    public Store getStoreByEmployee(Employee employee) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Store store = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT m.id_mag, m.adresa, m.oras, m.tel FROM magazin m JOIN angajati a USING(id_mag) WHERE id_ang=?";
            connection.prepareStatement(sql);
            statement.setInt(1, employee.getIdEmp());
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                String adress = result.getString(2);
                String city = result.getString(3);
                String tel = result.getString(4);

                store = new Store(id, adress, city, tel);
                employee.setIdMag(id);
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

        return store;
    }

    public String[] storeToRow(Store store) {
        String[] row = new String[4];
        row[0] = store.getId() + "";
        row[1] = store.getAdresa();
        row[2] = store.getOras();
        row[3] = store.getTelefon();

        return row;
    }
}
