package dvdmania.management;

import java.time.LocalDate;

public class Client {
    private int id;
    private String nume;
    private String prenume;
    private String adresa;
    private String oras;
    private LocalDate datan;
    private String cnp;
    private String tel;
    private String email;
    private int loialitate;
    private Account account;

    public Client() {

    }

    //client cu email
    public Client(int id, String nume, String prenume, String adresa, String oras, LocalDate datan, String cnp, String tel, String email, int loialitate) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.oras = oras;
        this.datan = datan;
        this.cnp = cnp;
        this.tel = tel;
        this.email = email;
        this.loialitate = loialitate;
    }

    //client fara email
    public Client(int id, String nume, String prenume, String adresa, String oras, LocalDate datan, String cnp, String tel, int loialitate) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.oras = oras;
        this.datan = datan;
        this.cnp = cnp;
        this.tel = tel;
        this.loialitate = loialitate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public LocalDate getDatan() {
        return datan;
    }

    public void setDatan(LocalDate datan) {
        this.datan = datan;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLoialitate() {
        return loialitate;
    }

    public void setLoialitate(int loialitate) {
        this.loialitate = loialitate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
