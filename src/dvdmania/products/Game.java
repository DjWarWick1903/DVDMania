package dvdmania.products;

public class Game {
    private int idGame;
    private String title;
    private String year;
    private String platform;
    private String developer;
    private String publisher;
    private String genre;
    private int audience;

    public Game() {

    }

    public Game(int idGame, String title, String year, String platform, String developer, String publisher, String genre, int audience) {
        this.idGame = idGame;
        this.title = title;
        this.year = year;
        this.platform = platform;
        this.developer = developer;
        this.publisher = publisher;
        this.genre = genre;
        this.audience = audience;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }
}
