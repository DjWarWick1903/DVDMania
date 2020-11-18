package dvdmania.management;

public class Store {
    private int id;
    private String adresa;
    private String oras;
    private String telefon;

    Store(int id, String adresa, String oras, String telefon) {
        this.id = id;
        this.adresa = adresa;
        this.oras = oras;
        this.telefon = telefon;
    }

    Store(String adresa, String oras, String telefon) {
        this.adresa = adresa;
        this.oras = oras;
        this.telefon = telefon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
