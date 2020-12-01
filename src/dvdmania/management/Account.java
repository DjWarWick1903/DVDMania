package dvdmania.management;

import java.time.LocalDate;

public class Account {
    private int idAcc;
    private String username;
    private String password;
    private LocalDate data_creat;
    private int priv;
    private int idUtil;

    public Account() {
    }

    public Account(int idAcc, String username, String password, LocalDate data_creat, int priv, int idUtil) {
        this.idAcc = idAcc;
        this.username = username;
        this.password = password;
        this.data_creat = data_creat;
        this.priv = priv;
        this.idUtil = idUtil;
    }

    public int getIdAcc() {
        return idAcc;
    }

    public void setIdAcc(int idAcc) {
        this.idAcc = idAcc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getData_creat() {
        return data_creat;
    }

    public void setData_creat(LocalDate data_creat) {
        this.data_creat = data_creat;
    }

    public int getPriv() {
        return priv;
    }

    public void setPriv(int priv) {
        this.priv = priv;
    }

    public int getIdUtil() {
        return idUtil;
    }

    public void setIdUtil(int idUtil) {
        this.idUtil = idUtil;
    }
}
