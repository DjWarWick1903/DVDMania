package dvdmania.management;

import dvdmania.tools.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClientManager {

    ConnectionManager connMan = new ConnectionManager();

    public Client getClientById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Client client = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT nume, pren, adresa, oras, datan, cnp, tel, email, loialitate FROM clienti " +
                    "WHERE id_cl=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                String nume = result.getString("nume");
                String prenume = result.getString("pren");
                String adress = result.getString("adresa");
                String oras = result.getString("oras");
                LocalDate date = result.getDate("datan").toLocalDate();
                String cnp = result.getString("cnp");
                String tel = result.getString("tel");
                String email = result.getString("email");
                int loyal = result.getInt("loialitate");

                client = new Client(id, nume, prenume, adress, oras, date, cnp, tel, email, loyal);

                AccountManager accMan = new AccountManager();
                Account account = accMan.getClientAccount(client);
                client.setAccount(account);
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

        return client;
    }

    public ArrayList<Client> getAllClients() {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Client> clientList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_cl, nume, pren, adresa, oras, datan, cnp, tel, email, loialitate FROM clienti";
            result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("id_cl");
                String nume = result.getString("nume");
                String prenume = result.getString("prenume");
                String adress = result.getString("adresa");
                String oras = result.getString("oras");
                LocalDate date = result.getDate("datan").toLocalDate();
                String cnp = result.getString("cnp");
                String tel = result.getString("tel");
                String email = result.getString("email");
                int loyal = result.getInt("loialitate");

                Client client = new Client(id, nume, prenume, adress, oras, date, cnp, tel, email, loyal);
                AccountManager accMan = new AccountManager();
                Account account = accMan.getClientAccount(client);
                client.setAccount(account);
                clientList.add(client);
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

        return clientList;
    }

    public int createClient(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        int newKey = 0;

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO dvdmania.clienti " +
                    "(nume, pren, adresa, oras, datan, cnp, tel, email) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, client.getNume());
            statement.setString(2, client.getPrenume());
            statement.setString(3, client.getAdresa());
            statement.setString(4, client.getOras());
            statement.setObject(5, client.getDatan());
            statement.setString(6, client.getCnp());
            statement.setString(7, client.getTel());
            statement.setString(8, client.getEmail());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted != 0) {
                ResultSet keySet = statement.getGeneratedKeys();
                if (keySet.next()) {
                    newKey = keySet.getInt(1);
                    client.setId(newKey);
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

    public int updateClient(Client client) {
        int rowsUpdated = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.clienti SET nume=?, pren=?, adresa=?, oras=?, datan=?, cnp=?, tel=?, email=? loialitate=? WHERE id_cl=?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, client.getNume());
            statement.setString(2, client.getPrenume());
            statement.setString(3, client.getAdresa());
            statement.setString(4, client.getOras());
            statement.setObject(5, client.getDatan());
            statement.setString(6, client.getCnp());
            statement.setString(7, client.getTel());
            statement.setString(8, client.getEmail());
            statement.setInt(9, client.getLoialitate());
            statement.setInt(10, client.getId());
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

    public int rewardClient(Client client) {
        int rowsUpdated = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.clienti SET loialitate=loialitate+1 WHERE id_cl=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, client.getId());
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

    public int punishClient(Client client) {
        int rowsUpdated = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.clienti SET loialitate=loialitate-1 WHERE id_cl=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, client.getId());
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

    public String[] clientToRow(Client client) {
        String[] row = new String[12];
        row[0] = client.getId() + "";
        row[1] = client.getNume();
        row[2] = client.getPrenume();
        row[3] = client.getAdresa();
        row[4] = client.getOras();
        row[5] = client.getDatan().toString();
        row[6] = client.getCnp();
        row[7] = client.getTel();
        row[8] = client.getEmail();
        row[9] = client.getLoialitate() + "";
        row[10] = client.getAccount().getUsername();
        row[11] = client.getAccount().getPassword();

        return row;
    }
}
