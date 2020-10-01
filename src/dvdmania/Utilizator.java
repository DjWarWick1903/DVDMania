package dvdmania;

public class Utilizator {

    //variabile clienti
    String nume;
    String prenume;
    String adresa;
    String oras;
    String dataNasterii;
    String cnp;
    String telefon;
    String email;
    int loialitate;

    //variabile angajati
    String functie;
    String adresaMagazin;
    String salariu;

    Utilizator() { }

    //angajat
    Utilizator(String nume, String prenume, String adresa, String oras, String datan, String cnp, String telefon, String email, String functie, String salariu, String adresaMagazin) {
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.oras = oras;
        this.dataNasterii = datan;
        this.cnp = cnp;
        this.telefon = telefon;
    }

    //client fara email
    Utilizator(String nume, String prenume, String adresa, String oras, String datan, String cnp, String telefon, int loialitate) {
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.oras = oras;
        this.dataNasterii = datan;
        this.cnp = cnp;
        this.telefon = telefon;
        this.loialitate = loialitate;
    }

    //client cu email
    Utilizator(String nume, String prenume, String adresa, String oras, String datan, String cnp, String telefon, String email, int loialitate) {
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.oras = oras;
        this.dataNasterii = datan;
        this.cnp = cnp;
        this.telefon = telefon;
        this.email = email;
        this.loialitate = loialitate;
    }

    void setNume(String nume) {
        this.nume = nume;
    }

    String getNume() {
        return nume;
    }

    void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    String getPrenume() {
        return prenume;
    }

    void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    String getAdresa() {
        return adresa;
    }

    void setOras(String oras) {
        this.oras = oras;
    }

    String getOras() {
        return oras;
    }

    void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    String getTelefon() {
        return telefon;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getEmail() {
        return email;
    }

    void setDataNasterii(String date) {
        this.dataNasterii = date;
    }

    String getDataNasterii() {
        return dataNasterii;
    }

    void setCNP(String cnp) {
        this.cnp = cnp;
    }

    String getCNP() {
        return cnp;
    }

    void setLoialitate(int loialitate) {
        this.loialitate = loialitate;
    }

    int getLoialitate() {
        return loialitate;
    }

    void setFunctie(String functie) {
        this.functie = functie;
    }

    String getFunctie() {
        return functie;
    }

    void setAdresaMagazin(String adresaMagazin) {
        this.adresaMagazin = adresaMagazin;
    }

    String getAdresaMagazin() {
        return adresaMagazin;
    }

    void setSalariu(String salariu) {
        this.salariu = salariu;
    }

    String getSalariu() {
        return salariu;
    }
}
