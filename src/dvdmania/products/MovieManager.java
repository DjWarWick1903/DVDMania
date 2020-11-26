package dvdmania.products;

import dvdmania.management.Store;
import dvdmania.tools.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class MovieManager {

    ConnectionManager connMan = new ConnectionManager();

    public ArrayList<Movie> getAllMovies() {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Movie> movieList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_film, titlu, actor_pr, director, durata, gen, an, audienta FROM filme";
            result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("id_film");
                String title = result.getString("titlu");
                String mainActor = result.getString("actor_pr");
                String director = result.getString("director");
                int duration = result.getInt("durata");
                String genre = result.getString("gen");
                int audience = result.getInt("audienta");
                String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

                Movie movie = new Movie(id, title, mainActor, director, duration, genre, year, audience);
                movieList.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return movieList;
    }

    public ArrayList<Movie> getMovieByStore(Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Movie> movieList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT f.id_film, f.titlu, f.actor_pr, f.director, f.durata, f.gen, f.an, f.audienta FROM filme f " +
                    "JOIN produse p ON p.id_film=f.id_film JOIN magazin m ON m.id_mag=p.id_mag WHERE m.id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, store.getId());
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_film");
                String title = result.getString("titlu");
                String mainActor = result.getString("actor_pr");
                String director = result.getString("director");
                int duration = result.getInt("durata");
                String genre = result.getString("gen");
                int audience = result.getInt("audienta");
                String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

                Movie movie = new Movie(id, title, mainActor, director, duration, genre, year, audience);
                movieList.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return movieList;
    }

    public Movie getMovieByTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Movie movie = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_film, actor_pr, director, durata, gen, an, audienta FROM dvdmania.filme WHERE titlu=?";
            connection.prepareStatement(sql);
            statement.setString(1, title);
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_film");
                String mainActor = result.getString("actor_pr");
                String director = result.getString("director");
                int duration = result.getInt("durata");
                String genre = result.getString("gen");
                String year = (String.valueOf(result.getDate("an"))).substring(0, 4);
                int audience = result.getInt("audienta");

                movie = new Movie(id, title, mainActor, director, duration, genre, year, audience);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return movie;
    }

    public int createMovie(Movie movie) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO dvdmania.filme (titlu, actor_pr, director, durata, gen, an, audienta) VALUES (?, ?, ?, ?, ?, YEAR(?), ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getMainActor());
            statement.setString(3, movie.getDirector());
            statement.setInt(4, movie.getDuration());
            statement.setString(5, movie.getGenre());
            statement.setDate(6, Date.valueOf(movie.getYear() + "-01-01"));
            statement.setInt(7, movie.getAudience());

            rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet keySet = statement.getGeneratedKeys();
                if (keySet.next()) {
                    movie.setIdMovie(keySet.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsInserted;
    }

    public int updateMovie(Movie movie) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.filme SET titlu=?, actor_pr=?, director=?, durata=?, gen=?, an=YEAR(?), audienta=? WHERE id_film=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getMainActor());
            statement.setString(3, movie.getDirector());
            statement.setInt(4, movie.getDuration());
            statement.setString(5, movie.getGenre());
            statement.setDate(6, Date.valueOf(movie.getYear() + "-01-01"));
            statement.setInt(7, movie.getAudience());
            rowsInserted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsInserted;
    }

    public int deleteMovie(Movie movie) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "DELETE FROM dvdmania.filme WHERE id_film=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, movie.getIdMovie());
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsDeleted;
    }

    public ArrayList<String> getGenres() {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        ArrayList<String> genres = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT DISTINCT gen FROM dvdmania.filme";
            result = statement.executeQuery(sql);

            genres.add("Toate");
            while (result.next()) {
                genres.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return genres;
    }
}
