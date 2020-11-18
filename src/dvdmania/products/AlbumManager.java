package dvdmania.products;

import dvdmania.tools.ConnectionManager;

import java.sql.*;

public class AlbumManager {

    ConnectionManager connMan = null;

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
        }

        return rowsInserted;
    }
}
