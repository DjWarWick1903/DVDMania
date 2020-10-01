package dvdmania;

import java.time.LocalDate;

public class Account {
    private int id;
    private String username;
    private String password;
    private LocalDate data_creat;
    private int priv;

    public Account() {
    }

    public Account(int id, String username, String password, LocalDate data_creat, int priv) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.data_creat = data_creat;
        this.priv = priv;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }
}
