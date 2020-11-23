package dvdmania.tools;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;

public class ConnectionManager {
    public Connection openConnection() throws SQLException {
        Connection myConn = null;
        String[] array = readFromFile();

        if (array[0] != null && array[1] != null && array[2] != null) {
            myConn = DriverManager.getConnection(array[0], array[1], array[2]);
        }

        return myConn;
    }

    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    public void closeConnection(Connection connection, Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    private static String[] readFromFile() {
        String[] array = new String[3];
        Path path = FileSystems.getDefault().getPath("connection.dat");

        try (DataInputStream locFile = new DataInputStream(new BufferedInputStream(new FileInputStream(path.toAbsolutePath().toString())))) {
            array[0] = locFile.readUTF();
            array[1] = locFile.readUTF();
            array[2] = locFile.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return array;
    }

    public static void writeToFile(String url, String user, String password) {
        ArrayList<String> list = new ArrayList<String>();

        try (DataOutputStream locFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("connection.dat")))) {
            locFile.writeUTF(url);
            locFile.writeUTF(user);
            locFile.writeUTF(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
