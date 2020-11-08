package dvdmania.humanresources;

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

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }

    public String getFunctie() {
        return functie;
    }

    public int getSalariu() {
        return salariu;
    }

    public boolean isActiv() {
        return activ;
    }

    public int getIdMag() {
        return idMag;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public void setIdMag(int idMag) {
        this.idMag = idMag;
    }
}
