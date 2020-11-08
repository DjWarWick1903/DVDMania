package dvdmania.humanresources;

import java.sql.*;
import java.util.LinkedList;

public class StoreManager {

    ConnectionManager connMan = new ConnectionManager();

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

    public LinkedList<Store> getStores() {
        LinkedList<Store> stores = new LinkedList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_mag, adresa, oras, tel FROM dvdmania.magazin";
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
}
