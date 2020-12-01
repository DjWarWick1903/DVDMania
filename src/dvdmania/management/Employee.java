package dvdmania.management;

import java.time.LocalDate;

public class Employee {
    private int idEmp;
    private String nume;
    private String prenume;
    private String adresa;
    private String oras;
    private LocalDate datan;
    private String cnp;
    private String telefon;
    private String email;
    private String functie;
    private int salariu;
    private boolean activ;
    private int idMag;
    private Account account;

    public Employee() {

    }

    public Employee(int idEmp, String nume, String prenume, String adresa, String oras, LocalDate datan, String cnp, String telefon, String email, String functie, int salariu, boolean activ, int idMag) {
        this.idEmp = idEmp;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.oras = oras;
        this.datan = datan;
        this.cnp = cnp;
        this.telefon = telefon;
        this.email = email;
        this.functie = functie;
        this.salariu = salariu;
        this.activ = activ;
        this.idMag = idMag;
    }

    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public int getSalariu() {
        return salariu;
    }

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public int getIdMag() {
        return idMag;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
