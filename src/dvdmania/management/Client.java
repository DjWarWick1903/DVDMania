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

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getOras() {
        return oras;
    }

    public LocalDate getDatan() {
        return datan;
    }

    public String getCnp() {
        return cnp;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public int getLoialitate() {
        return loialitate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
