package dvdmania.products;

import dvdmania.tools.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongManager {

    ConnectionManager connMan = ConnectionManager.getInstance();
    private static SongManager instance = null;

    private SongManager() {
    }

    public static SongManager getInstance() {
        if (instance == null) {
            instance = new SongManager();
        }

        return instance;
    }

    public ArrayList<Song> getSongs(int idAlbum) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Song> songs = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_muzica, nume, durata FROM dvdmania.muzica WHERE id_album=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idAlbum);
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                int duration = result.getInt(3);
                Song song = new Song(id, name, duration);
                songs.add(song);
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

        return songs;
    }

    public int CreateSong(Album album, Song song) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO dvdmania.muzica (nume, durata, id_album) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, song.getNume());
            statement.setInt(2, song.getDuration());
            statement.setInt(3, album.getIdAlbum());
            rowsInserted = statement.executeUpdate();

            if (rowsInserted != 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    song.setIdSong(keys.getInt(1));
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

    public String[] songToRow(Song song) {
        String[] row = new String[3];
        row[0] = song.getIdSong() + "";
        row[1] = song.getNume();
        row[2] = song.getDuration() + "";

        return row;
    }
}
