package dvdmania.management;

import dvdmania.products.*;
import dvdmania.tools.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockManager {

    ConnectionManager connMan = new ConnectionManager();

    //TODO: finish method
    public Stock getStockById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Stock stock = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_film, id_joc, id_album, id_mag, cant, pret FROM produse " +
                    "WHERE id_prod=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                int idMovie = result.getInt(1);
                int idGame = result.getInt(2);
                int idAlbum = result.getInt(3);
                int idStore = result.getInt(4);
                int quantity = result.getInt(5);
                int price = result.getInt(6);

                stock = new Stock();
                stock.setIdProduct(id);
                stock.setQuantity(quantity);
                stock.setPrice(price);
                stock.setIdMovie(idMovie);
                stock.setIdStore(idStore);
                stock.setIdGame(idGame);
                stock.setIdAlbum(idAlbum);
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

        return stock;
    }

    public Stock getMovieStock(Movie movie, Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Stock stock = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_prod, cant, pret FROM produse " +
                    "WHERE id_film=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, movie.getIdMovie());
            statement.setInt(2, store.getId());
            result = statement.executeQuery();

            while (result.next()) {
                int idStock = result.getInt(1);
                int quantity = result.getInt(2);
                int price = result.getInt(3);

                stock = new Stock();
                stock.setIdProduct(idStock);
                stock.setQuantity(quantity);
                stock.setPrice(price);
                stock.setIdMovie(movie.getIdMovie());
                stock.setIdStore(store.getId());
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

        return stock;
    }

    public Stock getGameStock(Game game, Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Stock stock = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_prod, cant, pret FROM produse " +
                    "WHERE id_joc=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, game.getIdGame());
            statement.setInt(2, store.getId());
            result = statement.executeQuery();

            while (result.next()) {
                int idStock = result.getInt(1);
                int quantity = result.getInt(2);
                int price = result.getInt(3);

                stock = new Stock();
                stock.setIdProduct(idStock);
                stock.setQuantity(quantity);
                stock.setPrice(price);
                stock.setIdGame(game.getIdGame());
                stock.setIdStore(store.getId());
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

        return stock;
    }

    public Stock getAlbumStock(Album album, Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Stock stock = null;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT id_prod, cant, pret FROM produse " +
                    "WHERE id_album=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, album.getIdAlbum());
            statement.setInt(2, store.getId());
            result = statement.executeQuery();

            while (result.next()) {
                int idStock = result.getInt(1);
                int quantity = result.getInt(2);
                int price = result.getInt(3);

                stock = new Stock();
                stock.setIdProduct(idStock);
                stock.setQuantity(quantity);
                stock.setPrice(price);
                stock.setIdAlbum(album.getIdAlbum());
                stock.setIdStore(store.getId());
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

        return stock;
    }

    public int insertMovieStock(Movie movie, Store store, int quantity, int price) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO dvdmania.produse (id_film, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, movie.getIdMovie());
            statement.setInt(2, store.getId());
            statement.setInt(3, quantity);
            statement.setInt(4, price);

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

    public int insertGameStock(Game game, Store store, int quantity, int price) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO dvdmania.produse (id_joc, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, game.getIdGame());
            statement.setInt(2, store.getId());
            statement.setInt(3, quantity);
            statement.setInt(4, price);

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

    public int insertAlbumStock(Album album, Store store, int quantity, int price) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsInserted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "INSERT INTO dvdmania.produse (id_album, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, album.getIdAlbum());
            statement.setInt(2, store.getId());
            statement.setInt(3, quantity);
            statement.setInt(4, price);

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

    public int updateMovieStock(Movie movie, Store store, int quantity, int price) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsUpdated = 0;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_film=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, price);
            statement.setInt(3, movie.getIdMovie());
            statement.setInt(4, store.getId());

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

    public int updateGameStock(Game game, Store store, int quantity, int price) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsUpdated = 0;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_joc=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, price);
            statement.setInt(3, game.getIdGame());
            statement.setInt(4, store.getId());

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

    public int updateAlbumStock(Album album, Store store, int quantity, int price) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsUpdated = 0;

        try {
            connection = connMan.openConnection();
            String sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_album=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, price);
            statement.setInt(3, album.getIdAlbum());
            statement.setInt(4, store.getId());

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

    public int deleteMovieStock(Movie movie, Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "DELETE FROM dvdmania.produse WHERE id_film=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, movie.getIdMovie());
            statement.setInt(2, store.getId());

            rowsDeleted = statement.executeUpdate();

            if (checkMovieStock(movie) == 0) {
                MovieManager movieMan = new MovieManager();
                movieMan.deleteMovie(movie);
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

        return rowsDeleted;
    }

    public int deleteGameStock(Game game, Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "DELETE FROM dvdmania.produse WHERE id_joc=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, game.getIdGame());
            statement.setInt(2, store.getId());

            rowsDeleted = statement.executeUpdate();

            if (checkGameStock(game) == 0) {
                GameManager gameMan = new GameManager();
                gameMan.deleteGame(game);
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

        return rowsDeleted;
    }

    public int deleteAlbumStock(Album album, Store store) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            connection = connMan.openConnection();
            String sql = "DELETE FROM dvdmania.produse WHERE id_album=? AND id_mag=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, album.getIdAlbum());
            statement.setInt(2, store.getId());

            rowsDeleted = statement.executeUpdate();

            if (checkAlbumStock(album) == 0) {
                AlbumManager albumMan = new AlbumManager();
                albumMan.deleteAlbum(album);
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

        return rowsDeleted;
    }

    public int checkMovieStock(Movie movie) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        int stock = 0;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_film=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, movie.getIdMovie());
            result = statement.executeQuery();

            if (result.next()) {
                stock = result.getInt(1);
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

        return stock;
    }

    public int checkGameStock(Game game) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        int stock = 0;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_joc=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, game.getIdGame());
            result = statement.executeQuery();

            if (result.next()) {
                stock = result.getInt(1);
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

        return stock;
    }

    public int checkAlbumStock(Album album) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        int stock = 0;

        try {
            connection = connMan.openConnection();
            String sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_album=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, album.getIdAlbum());
            result = statement.executeQuery();

            if (result.next()) {
                stock = result.getInt(1);
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

        return stock;
    }
}
