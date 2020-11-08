package dvdmania.humanresources;

import java.sql.*;

public class AccountManager {

    ConnectionManager connMan = new ConnectionManager();

    public int createClientAccount(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;
        int newKey = 0;

        boolean exists = checkAccountExists(account);

        if (!exists) {
            try {
                connection = connMan.openConnection();
                String sql = "INSERT INTO dvdmania.conturi (util, parola, data_creat, id_cl) " +
                        "VALUES(?,?,SYSDATE(),?)";
                statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, account.getUsername());
                statement.setString(2, account.getPassword());
                statement.setInt(3, account.getIdUtil());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted != 0) {
                    ResultSet keySet = statement.getGeneratedKeys();
                    if (keySet.next()) {
                        newKey = keySet.getInt(1);
                        account.setIdAcc(newKey);
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
        }

        return newKey;
    }

//    public int createEmployeeAccount(Employee employee) {
//
//    }


    public boolean checkAccountExists(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        boolean isCorrect = false;

        try {
            connection = connMan.openConnection();
            if (connection != null) {
                String sql = "SELECT util, parola " +
                        "FROM conturi " +
                        "WHERE util = ? AND parola = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, account.getUsername());
                statement.setString(2, account.getPassword());
                statement.executeQuery();

                while (result.next()) {
                    isCorrect = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isCorrect;
    }
}
