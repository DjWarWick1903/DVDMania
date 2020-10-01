package dvdmania;

import java.sql.*;

public class ClientManager {

    ConnectionManager connMan = new ConnectionManager();

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
}
