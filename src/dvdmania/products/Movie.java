package dvdmania.products;

public class Movie {
    private int idMovie;
    private String title;
    private String mainActor;
    private String director;
    private int duration;
    private String genre;
    private String year;
    private int audience;

    public Movie(int idMovie, String title, String mainActor, String director, int duration, String genre, String year, int audience) {
        this.idMovie = idMovie;
        this.title = title;
        this.mainActor = mainActor;
        this.director = director;
        this.duration = duration;
        this.genre = genre;
        this.year = year;
        this.audience = audience;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainActor() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }
}
