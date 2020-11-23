package dvdmania.products;

public class Song {
    private int idSong;
    private String nume;
    private int duration;

    public Song(int idSong, String nume, int duration) {
        this.idSong = idSong;
        this.nume = nume;
        this.duration = duration;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
