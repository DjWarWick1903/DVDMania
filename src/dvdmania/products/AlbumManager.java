package dvdmania.products;

import dvdmania.tools.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class AlbumManager {

    ConnectionManager connMan = null;

    public Album getAlbumByTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Album album = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_album, trupa, nr_mel, casa_disc, gen, an FROM dvdmania.jocuri WHERE titlu=?";
            connection.prepareStatement(sql);
            statement.setString(1, title);
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_album");
                String artist = result.getString("trupa");
                int nrSongs = result.getInt("nr_mel");
                String publisher = result.getString("casa_disc");
                String genre = result.getString("gen");
                String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

                album = new Album(id, artist, title, nrSongs, genre, publisher, year);
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

        return album;
    }

    public int createAlbum(Album album) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO dvdmania.jocuri (titlu, trupa, nr_mel , casa_disc, gen, an) VALUES (?, ?, ?, ?, ?, YEAR(?))";
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, album.getTitle());
            statement.setString(2, album.getArtist());
            statement.setInt(3, album.getNrMel());
            statement.setString(4, album.getProducer());
            statement.setString(5, album.getGenre());
            statement.setDate(6, Date.valueOf(album.getYear() + "-01-01"));

            rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet keySet = statement.getGeneratedKeys();
                if (keySet.next()) {
                    album.setIdAlbum(keySet.getInt(1));
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

    public int updateAlbum(Album album) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsUpdated = 0;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.albume SET titlu=?, trupa=?, nr_mel=?, casa_disc=?, gen=?, an=YEAR(?) WHERE id_album=?";
            connection.prepareStatement(sql);
            statement.setString(1, album.getTitle());
            statement.setString(2, album.getArtist());
            statement.setInt(3, album.getNrMel());
            statement.setString(4, album.getProducer());
            statement.setString(5, album.getGenre());
            statement.setDate(6, Date.valueOf(album.getYear() + "-01-01"));
            statement.setInt(7, album.getIdAlbum());
            rowsUpdated = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connMan.closeConnection(connection, statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rowsUpdated;
    }

    public int deleteAlbum(Album album) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "DELETE FROM dvdmania.albume WHERE id_album=?";
            connection.prepareStatement(sql);
            statement.setInt(1, album.getIdAlbum());
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

    public ArrayList<String> getAlbumNames() {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        ArrayList<String> albumNames = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT titlu FROM dvdmania.albume";
            result = statement.executeQuery(sql);

            while (result.next()) {
                albumNames.add(result.getString(1));
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

        return albumNames;
    }

    public ArrayList<String> getGenres() {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        ArrayList<String> genres = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT DISTINCT gen FROM dvdmania.albume";
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
