package dvdmania;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {

    ConnectionManager connMan = new ConnectionManager();

    public Account createClientAccount(Account account, boolean client) {
        Connection connection = null;
        PreparedStatement statement = null;
        int newKey = 0;

        boolean exists = checkAccountExists(account);

        if (!exists) {

            try {
                connection = connMan.openConnection();
                String sql = "INSERT INTO dvdmania.conturi (util, parola, data_creat, id_cl) " +
                        "VALUES(?,?,SYSDATE(),?)";
                statement = connection.prepareStatement(sql);
                statement
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


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
