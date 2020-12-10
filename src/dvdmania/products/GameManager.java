package dvdmania.products;

import dvdmania.management.Store;
import dvdmania.tools.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class GameManager {

    ConnectionManager connMan = ConnectionManager.getInstance();
    private static GameManager instance = null;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    public ArrayList<Game> getAllGames() {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Game> gameList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_joc, titlu, an, platforma, developer, publisher, gen, audienta FROM jocuri";
            result = statement.executeQuery(sql);

            while (result.next()) {
                int id = result.getInt("id_joc");
                String title = result.getString("titlu");
                String platform = result.getString("platforma");
                String developer = result.getString("developer");
                String publisher = result.getString("publisher");
                String genre = result.getString("gen");
                int audience = result.getInt("audienta");
                String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

                Game game = new Game(id, title, year, platform, developer, publisher, genre, audience);
                gameList.add(game);
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

        return gameList;
    }

    public ArrayList<Game> getGamesByStore(Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Game> gameList = new ArrayList<>();

        try {
            connection = connMan.openConnection();
            String sql = "SELECT j.id_joc, j.titlu, j.an, j.platforma, j.developer, j.publisher, j.gen, j.audienta FROM jocuri j " +
                    "JOIN produse p ON p.id_joc=j.id_joc JOIN magazin m ON m.id_mag=p.id_mag WHERE m.id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, store.getId());
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_joc");
                String title = result.getString("titlu");
                String platform = result.getString("platforma");
                String developer = result.getString("developer");
                String publisher = result.getString("publisher");
                String genre = result.getString("gen");
                int audience = result.getInt("audienta");
                String year = (String.valueOf(result.getDate("an"))).substring(0, 4);

                Game game = new Game(id, title, year, platform, developer, publisher, genre, audience);
                gameList.add(game);
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

        return gameList;
    }

    public Game getGameByTitle(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Game game = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_joc, an, platforma, developer, publisher, gen, audienta FROM dvdmania.jocuri WHERE titlu=?";
            connection.prepareStatement(sql);
            statement.setString(1, title);
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_joc");
                String year = (String.valueOf(result.getDate("an"))).substring(0, 4);
                String platform = result.getString("platforma");
                String developer = result.getString("developer");
                String publisher = result.getString("publisher");
                String genre = result.getString("gen");
                int audience = result.getInt("audienta");

                game = new Game(id, title, year, platform, developer, publisher, genre, audience);
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

        return game;
    }

    public Game getGameById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Game game = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT titlu, an, platforma, developer, publisher, gen, audienta FROM jocuri WHERE id_joc=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                String title = result.getString("titlu");
                String year = (String.valueOf(result.getDate("an"))).substring(0, 4);
                String platform = result.getString("platforma");
                String developer = result.getString("developer");
                String publisher = result.getString("publisher");
                String genre = result.getString("gen");
                int audience = result.getInt("audienta");

                game = new Game(id, title, year, platform, developer, publisher, genre, audience);
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

        return game;
    }

    public int createGame(Game game) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO jocuri (titlu, platforma, developer, publisher, gen, an, audienta) VALUES (?, ?, ?, ?, ?, YEAR(?), ?)";
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, game.getTitle());
            statement.setString(2, game.getPlatform());
            statement.setString(3, game.getDeveloper());
            statement.setString(4, game.getPublisher());
            statement.setString(5, game.getGenre());
            statement.setDate(6, Date.valueOf(game.getYear() + "-01-01"));
            statement.setInt(7, game.getAudience());
            rowsInserted = statement.executeUpdate();

            ResultSet keySet = statement.getGeneratedKeys();
            while (keySet.next()) {
                int id = keySet.getInt(1);
                game.setIdGame(id);
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

    public int updateGame(Game game) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsUpdated = 0;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.jocuri SET titlu=?, platforma=?, developer=?, publisher=?, gen=?, an=YEAR(?), audienta=? WHERE id_joc=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, game.getTitle());
            statement.setString(2, game.getPlatform());
            statement.setString(3, game.getDeveloper());
            statement.setString(4, game.getPublisher());
            statement.setString(5, game.getGenre());
            statement.setDate(6, Date.valueOf(game.getYear() + "-01-01"));
            statement.setInt(7, game.getAudience());
            statement.setInt(8, game.getIdGame());
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

    public int deleteGame(Game game) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "DELETE FROM dvdmania.jocuri WHERE id_joc=?";
            connection.prepareStatement(sql);
            statement.setInt(1, game.getIdGame());
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
            String sql = "SELECT DISTINCT gen FROM dvdmania.jocuri";
            statement = connection.createStatement();
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

    public String[] gameToRow(Game game, int price) {
        String[] row = new String[9];
        row[0] = game.getIdGame() + "";
        row[1] = game.getTitle();
        row[2] = game.getPlatform();
        row[3] = game.getDeveloper();
        row[4] = game.getPublisher();
        row[5] = game.getGenre();
        row[6] = game.getYear();
        row[7] = game.getAudience() + "";
        row[8] = price + "";

        return row;
    }
}
