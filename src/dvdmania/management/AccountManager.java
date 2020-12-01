package dvdmania.management;

import dvdmania.tools.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;

public class AccountManager {

    ConnectionManager connMan = new ConnectionManager();

    public Account getAccount(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Account account = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_cont, data_creat, id_cl, id_angaj FROM dvdmania.conturi WHERE util=? AND parola=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                LocalDate date = result.getDate(2).toLocalDate();
                int idClient = result.getInt(3);
                int idEmployee = result.getInt(4);

                if (idClient != 0) {
                    account = new Account(id, username, password, date, 1, idClient);
                } else if (idEmployee != 0) {
                    account = new Account(id, username, password, date, 2, idEmployee);
                }
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

        return account;
    }

    public Account getClientAccount(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Account account = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_cont, util, parola, data_creat FROM conturi WHERE id_cl=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, client.getId());
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                String username = result.getString(2);
                String password = result.getString(3);
                LocalDate date = result.getDate(4).toLocalDate();

                account = new Account(id, username, password, date, 1, client.getId());
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

        return account;
    }

    public boolean updateClientAccount(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isUpdated = false;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE conturi(util, parola) VALUES(?,?) WHERE id_cl=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setInt(3, account.getIdUtil());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                isUpdated = true;
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

        return isUpdated;
    }

    public boolean updateEmployeeAccount(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isUpdated = false;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE conturi(util, parola) VALUES(?,?) WHERE id_ang=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setInt(3, account.getIdUtil());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                isUpdated = true;
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

        return isUpdated;
    }

    public Account getEmployeeAccount(Employee employee) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Account account = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_cont, util, parola, data_creat FROM conturi WHERE id_ang=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, employee.getIdEmp());
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                String username = result.getString(2);
                String password = result.getString(3);
                LocalDate date = result.getDate(4).toLocalDate();

                account = new Account(id, username, password, date, 1, employee.getIdEmp());
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

        return account;
    }

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

    public int createEmployeeAccount(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;
        int newKey = 0;

        boolean exists = checkAccountExists(account);

        if (!exists) {
            try {
                connection = connMan.openConnection();
                String sql = "INSERT INTO dvdmania.conturi (util, parola, data_creat, id_angaj) " +
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
                result = statement.executeQuery();

                while (result.next()) {
                    isCorrect = true;
                }
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

        return isCorrect;
    }

    public int checkAccountPrivilege(Account account) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        int priv = 1;

        try {
            connection = connMan.openConnection();
            if (account.getPriv() == 2) {
                priv = 2;
                String sql = "SELECT functie FROM angajati WHERE id_angaj=?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, account.getIdUtil());
                result = statement.executeQuery();

                while (result.next()) {
                    String post = result.getString(1);
                    if (post.equals("Director") || post.equals("Manager")) {
                        priv = 3;
                        account.setPriv(3);
                    }
                }
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

        return priv;
    }
}
