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

    public int getIdUtil() {
        return idUtil;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getData_creat() {
        return data_creat;
    }

    public int getPriv() {
        return priv;
    }

    public void setPriv(int priv) {
        this.priv = priv;
    }

    public void setIdAcc(int idAcc) {
        this.idAcc = idAcc;
    }


}
