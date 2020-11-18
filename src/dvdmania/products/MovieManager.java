package dvdmania.products;

import dvdmania.tools.ConnectionManager;

import java.sql.*;

public class MovieManager {

    ConnectionManager connMan = new ConnectionManager();

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
        }

        return rowsDeleted;
    }

//    public ArrayList<Movie> getMoviesForStore(Store store) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ArrayList<Movie> movies =
//    }
}
