package dvdmania.products;

public class Album {
    int idAlbum;
    String artist;
    String title;
    int nrMel;
    String genre;
    String producer;
    String year;

    public Album(int idAlbum, String artist, String title, int nrMel, String genre, String producer, String year) {
        this.idAlbum = idAlbum;
        this.artist = artist;
        this.title = title;
        this.nrMel = nrMel;
        this.genre = genre;
        this.producer = producer;
        this.year = year;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNrMel() {
        return nrMel;
    }

    public void setNrMel(int nrMel) {
        this.nrMel = nrMel;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
