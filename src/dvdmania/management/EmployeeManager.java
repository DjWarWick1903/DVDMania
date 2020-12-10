package dvdmania.management;

import dvdmania.tools.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeManager {

    ConnectionManager connMan = ConnectionManager.getInstance();
    private static EmployeeManager instance = null;

    private EmployeeManager() {
    }

    public static EmployeeManager getInstance() {
        if (instance == null) {
            instance = new EmployeeManager();
        }

        return instance;
    }

    public int createEmployee(Employee employee) {
        Connection connection = null;
        PreparedStatement statement = null;
        int newKey = 0;

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO angajati(nume, pren, adresa, oras, " +
                    "datan, cnp, tel, email, functie, salariu, activ, id_mag) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getNume());
            statement.setString(2, employee.getPrenume());
            statement.setString(3, employee.getAdresa());
            statement.setString(4, employee.getOras());
            statement.setObject(5, employee.getDatan());
            statement.setString(6, employee.getCnp());
            statement.setString(7, employee.getTelefon());
            statement.setString(8, employee.getEmail());
            statement.setString(9, employee.getFunctie());
            statement.setInt(10, employee.getSalariu());
            statement.setString(11, "Activ");
            statement.setInt(12, employee.getIdMag());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted != 0) {
                ResultSet keySet = statement.getGeneratedKeys();
                if (keySet.next()) {
                    newKey = keySet.getInt(1);
                    employee.setIdEmp(newKey);
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

    public int updateEmployee(Employee employee) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.angajati SET nume=?, pren=?, adresa=?, oras=?, " +
                    "datan=?, cnp=?, tel=?, email=?, functie=?, salariu=?, activ=?, " +
                    "id_mag=? WHERE id_angaj='" + employee.getIdMag() + "'";

            statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getNume());
            statement.setString(2, employee.getPrenume());
            statement.setString(3, employee.getAdresa());
            statement.setString(4, employee.getOras());
            statement.setObject(5, employee.getDatan());
            statement.setString(6, employee.getCnp());
            statement.setString(7, employee.getTelefon());
            statement.setString(8, employee.getEmail());
            statement.setString(9, employee.getFunctie());
            statement.setInt(10, employee.getSalariu());
            if (employee.isActiv()) {
                statement.setString(11, "Activ");
            } else {
                statement.setString(11, "Inactiv");
            }
            statement.setInt(12, employee.getIdMag());

            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsInserted;
    }

    public Employee getEmployeeById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Employee emp = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT a.nume, a.pren, a.adresa, a.oras, a.datan, " +
                    "a.cnp, a.tel, a.email, a.functie, a.salariu, m.id_mag FROM angajati a " +
                    "JOIN magazin m ON m.id_mag=a.id_mag WHERE a.activ='Activ' AND a.id_angaj=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                String nume = result.getString("nume");
                String prenume = result.getString("pren");
                String adresa = result.getString("adresa");
                String oras = result.getString("oras");
                LocalDate datan = result.getDate("datan").toLocalDate();
                String cnp = result.getString("cnp");
                String telefon = result.getString("tel");
                String email = result.getString("email");
                String functie = result.getString("functie");
                int salariu = result.getInt("salariu");
                int idMag = result.getInt("id_mag");

                emp = new Employee(id, nume, prenume, adresa, oras, datan, cnp, telefon, email, functie, salariu, true, idMag);
                AccountManager accMan = AccountManager.getInstance();
                Account account = accMan.getEmployeeAccount(emp);
                emp.setAccount(account);
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

        return emp;
    }

    public ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT a.id_angaj, a.nume, a.pren, a.adresa, a.oras, a.datan, " +
                    "a.cnp, a.tel, a.email, a.functie, a.salariu, m.id_mag FROM dvdmania.angajati a, " +
                    "dvdmania.magazin m WHERE m.id_mag=a.id_mag AND a.activ='Activ'";
            statement = connection.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                int idEmp = result.getInt("id_angaj");
                String nume = result.getString("nume");
                String prenume = result.getString("pren");
                String adresa = result.getString("adresa");
                String oras = result.getString("oras");
                LocalDate datan = result.getDate("datan").toLocalDate();
                String cnp = result.getString("cnp");
                String telefon = result.getString("tel");
                String email = result.getString("email");
                String functie = result.getString("functie");
                int salariu = result.getInt("salariu");
                int idMag = result.getInt("id_mag");
                Employee employee = new Employee(idEmp, nume, prenume, adresa, oras, datan, cnp, telefon, email, functie, salariu, true, idMag);

                AccountManager accMan = AccountManager.getInstance();
                Account account = accMan.getEmployeeAccount(employee);
                employee.setAccount(account);

                employees.add(employee);
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

        return employees;
    }

    public String getEmployeePost(String cnp) {
        String post = new String();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String sql = "SELECT functie FROM dvdmania.angajati WHERE cnp=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, cnp);
            result = statement.executeQuery();

            while (result.next()) {
                post = result.getString(1);
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

        return post;
    }

    public String getEmployeeEmail(String cnp) {
        String email = new String();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT email FROM dvdmania.angajati WHERE cnp=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, cnp);
            result = statement.executeQuery();

            while (result.next()) {
                email = result.getString(1);
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

        return email;
    }

    public String[] employeeToRow(Employee employee) {
        String[] row = new String[14];
        row[0] = employee.getIdEmp() + "";
        row[1] = employee.getNume();
        row[2] = employee.getPrenume();
        row[3] = employee.getAdresa();
        row[4] = employee.getOras();
        row[5] = employee.getDatan().toString();
        row[6] = employee.getCnp();
        row[7] = employee.getTelefon();
        row[8] = employee.getEmail();
        row[9] = employee.getFunctie();
        row[10] = employee.getSalariu() + "";
        StoreManager storeMan = StoreManager.getInstance();
        Store store = storeMan.getStoreById(employee.getIdMag());
        row[11] = store.getOras();
        row[12] = employee.getAccount().getUsername();
        row[13] = employee.getAccount().getPassword();

        return row;
    }
}
