package dvdmania.windows;

import dvdmania.management.Stock;
import dvdmania.management.StockManager;
import dvdmania.management.Store;
import dvdmania.products.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class EditProductWindow extends JFrame {

    private static JPanel firstPanel, detaliiPanel, buttonPanel;
    private static JLabel editCategorieLabel, editIDLabel;
    private static JComboBox editCategorieBox;
    private static JTextField editIDField;
    private static JButton exit, proceed, saveSecond, exitSecond;

    public EditProductWindow() {
        super("Edit product");

        //first window
        editCategorieLabel = new JLabel("Categorie:");
        editIDLabel = new JLabel("ID produs:");
        String[] items = new String[]{"Filme", "Jocuri", "Albume"};
        editCategorieBox = new JComboBox(items);
        editIDField = new JTextField();
        exit = new JButton("Exit");
        proceed = new JButton("Proceed");

        detaliiPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        detaliiPanel.add(editCategorieLabel);
        detaliiPanel.add(editCategorieBox);
        detaliiPanel.add(editIDLabel);
        detaliiPanel.add(editIDField);

        buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(proceed);
        buttonPanel.add(exit);

        firstPanel = new JPanel(new BorderLayout());
        firstPanel.add(detaliiPanel, BorderLayout.CENTER);
        firstPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(firstPanel);
        this.setVisible(true);
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);

        proceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editCategorieBox.getSelectedItem().equals("Filme")) {
                    movieWindow();
                } else if (editCategorieBox.getSelectedItem().equals("Jocuri")) {
                    gameWindow();
                } else if (editCategorieBox.getSelectedItem().equals("Albume")) {
                    albumWindow();
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }

    private void movieWindow() {
        JLabel editProdTitluLabel, editProdActorLabel, editProdDirectorLabel, editProdDurataLabel, editProdGenLabel, editProdAnLabel, editProdAudientaLabel,
                editProdCantLabel, editProdPretLabel;
        JTextField editProdTitluField, editProdActorField, editProdDirectorField, editProdDurataField, editProdGenField, editProdAnField, editProdAudientaField,
                editProdCantField, editProdPretField;

        JButton save, exit, delete;
        JPanel secondWindowMain, secondWindowDetalii, secondWindowButoane;

        StockManager stockMan = StockManager.getInstance();
        MovieManager movieMan = MovieManager.getInstance();
        GameManager gameMan = GameManager.getInstance();
        AlbumManager albumMan = AlbumManager.getInstance();

        Movie movie = new Movie();
        movie.setIdMovie(Integer.parseInt(editIDField.getText()));
        Store store = new Store();
        store.setId(GUI.getLoggedEmployee().getIdMag());
        Stock stock = stockMan.getMovieStock(movie, store);

        if (stock == null) {
            JFrame dialog = new JFrame();
            JOptionPane.showMessageDialog(dialog, "ID-ul introdus nu exista in baza de date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            stock.setMovie(movieMan.getMovieById(movie.getIdMovie()));
            movie = stock.getMovie();
            editProdTitluLabel = new JLabel("Titlu:");
            editProdTitluField = new JTextField();
            editProdTitluField.setText(movie.getTitle());
            editProdActorLabel = new JLabel("Actor:");
            editProdActorField = new JTextField();
            editProdActorField.setText(movie.getMainActor());
            editProdDirectorLabel = new JLabel("Director:");
            editProdDirectorField = new JTextField();
            editProdDirectorField.setText(movie.getDirector());
            editProdDurataLabel = new JLabel("Durata:");
            editProdDurataField = new JTextField();
            editProdDurataField.setText(movie.getDuration() + "");
            editProdGenLabel = new JLabel("Gen:");
            editProdGenField = new JTextField();
            editProdGenField.setText(movie.getGenre());
            editProdAnLabel = new JLabel("An:");
            editProdAnField = new JTextField();
            editProdAnField.setText(movie.getYear());
            editProdAudientaLabel = new JLabel("Audienta:");
            editProdAudientaField = new JTextField();
            editProdAudientaField.setText(movie.getAudience() + "");
            editProdCantLabel = new JLabel("Cantitate:");
            editProdCantField = new JTextField();
            editProdCantField.setText(stock.getQuantity() + "");
            editProdPretLabel = new JLabel("Pret:");
            editProdPretField = new JTextField();
            editProdPretField.setText(stock.getPrice() + "");
            save = new JButton("Save");
            exit = new JButton("Exit");
            delete = new JButton("Delete");

            secondWindowDetalii = new JPanel(new GridLayout(9, 9, 5, 5));
            secondWindowDetalii.add(editProdTitluLabel);
            secondWindowDetalii.add(editProdTitluField);
            secondWindowDetalii.add(editProdActorLabel);
            secondWindowDetalii.add(editProdActorField);
            secondWindowDetalii.add(editProdDirectorLabel);
            secondWindowDetalii.add(editProdDirectorField);
            secondWindowDetalii.add(editProdDurataLabel);
            secondWindowDetalii.add(editProdDurataField);
            secondWindowDetalii.add(editProdGenLabel);
            secondWindowDetalii.add(editProdGenField);
            secondWindowDetalii.add(editProdAnLabel);
            secondWindowDetalii.add(editProdAnField);
            secondWindowDetalii.add(editProdAudientaLabel);
            secondWindowDetalii.add(editProdAudientaField);
            secondWindowDetalii.add(editProdCantLabel);
            secondWindowDetalii.add(editProdCantField);
            secondWindowDetalii.add(editProdPretLabel);
            secondWindowDetalii.add(editProdPretField);
            secondWindowButoane = new JPanel(new GridLayout(1, 3, 5, 5));
            secondWindowButoane.add(save);
            secondWindowButoane.add(delete);
            secondWindowButoane.add(exit);

            secondWindowMain = new JPanel(new BorderLayout());
            secondWindowMain.add(secondWindowDetalii, BorderLayout.CENTER);
            secondWindowMain.add(secondWindowButoane, BorderLayout.SOUTH);
            JFrame secondWindow = new JFrame();
            secondWindow.add(secondWindowMain);
            secondWindow.setVisible(true);
            secondWindow.setSize(300, 300);

            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame dialog = new JFrame();
                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa modificati produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (a == JOptionPane.YES_OPTION) {
                        Movie movie = stock.getMovie();
                        movie.setIdMovie(Integer.parseInt(editIDField.getText()));
                        movie.setTitle(editProdTitluField.getText());
                        movie.setMainActor(editProdActorField.getText());
                        movie.setDirector(editProdDirectorField.getText());
                        movie.setDuration(Integer.parseInt(editProdDurataField.getText()));
                        movie.setGenre(editProdGenField.getText());
                        movie.setYear(editProdAnField.getText());
                        movie.setAudience(Integer.parseInt(editProdAudientaField.getText()));
                        stock.setPrice(Integer.parseInt(editProdPretField.getText()));
                        stock.setQuantity(Integer.parseInt(editProdCantField.getText()));

                        movieMan.updateMovie(movie);
                        stockMan.updateMovieStock(movie, stock.getStore(), stock.getQuantity(), stock.getPrice());
                    }
                }
            });

            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame dialog = new JFrame();
                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa stergeti produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (a == JOptionPane.YES_OPTION) {
                        stockMan.deleteMovieStock(stock.getMovie(), stock.getStore());
                        secondWindow.setVisible(false);
                        secondWindow.dispose();
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    secondWindow.setVisible(false);
                    secondWindow.dispose();
                }
            });
        }
    }

    private void gameWindow() {
        JLabel editProdTitluLabel, editProdPlatformaLabel, editProdDeveloperLabel, editProdPublisherLabel, editProdGenLabel, editProdAnLabel, editProdAudientaLabel,
                editProdCantLabel, editProdPretLabel;
        JTextField editProdTitluField, editProdPlatformaField, editProdDeveloperField, editProdPublisherField, editProdGenField, editProdAnField, editProdAudientaField,
                editProdCantField, editProdPretField;

        JButton save, exit, delete;
        JPanel secondWindowMain, secondWindowDetalii, secondWindowButoane;
        StockManager stockMan = StockManager.getInstance();
        MovieManager movieMan = MovieManager.getInstance();
        GameManager gameMan = GameManager.getInstance();
        AlbumManager albumMan = AlbumManager.getInstance();

        Game game = new Game();
        game.setIdGame(Integer.parseInt(editIDField.getText()));
        Store store = new Store();
        store.setId(GUI.getLoggedEmployee().getIdMag());
        Stock stock = stockMan.getGameStock(game, store);
        stock.setGame(gameMan.getGameById(game.getIdGame()));

        game = stock.getGame();
        if (stock == null) {
            JFrame dialog = new JFrame();
            JOptionPane.showMessageDialog(dialog, "ID-ul introdus nu exista in baza de date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            editProdTitluLabel = new JLabel("Titlu:");
            editProdTitluField = new JTextField();
            editProdTitluField.setText(game.getTitle());
            editProdPlatformaLabel = new JLabel("Platforma:");
            editProdPlatformaField = new JTextField();
            editProdPlatformaField.setText(game.getPlatform());
            editProdDeveloperLabel = new JLabel("Developer:");
            editProdDeveloperField = new JTextField();
            editProdDeveloperField.setText(game.getDeveloper());
            editProdPublisherLabel = new JLabel("Publisher:");
            editProdPublisherField = new JTextField();
            editProdPublisherField.setText(game.getPublisher());
            editProdGenLabel = new JLabel("Gen:");
            editProdGenField = new JTextField();
            editProdGenField.setText(game.getGenre());
            editProdAnLabel = new JLabel("An:");
            editProdAnField = new JTextField();
            editProdAnField.setText(game.getYear());
            editProdAudientaLabel = new JLabel("Audienta:");
            editProdAudientaField = new JTextField();
            editProdAudientaField.setText(game.getAudience() + "");
            editProdCantLabel = new JLabel("Cantitate:");
            editProdCantField = new JTextField();
            editProdCantField.setText(stock.getQuantity() + "");
            editProdPretLabel = new JLabel("Pret:");
            editProdPretField = new JTextField();
            editProdPretField.setText(stock.getPrice() + "");
            save = new JButton("Save");
            exit = new JButton("Exit");
            delete = new JButton("Delete");

            secondWindowDetalii = new JPanel(new GridLayout(9, 9, 5, 5));
            secondWindowDetalii.add(editProdTitluLabel);
            secondWindowDetalii.add(editProdTitluField);
            secondWindowDetalii.add(editProdPlatformaLabel);
            secondWindowDetalii.add(editProdPlatformaField);
            secondWindowDetalii.add(editProdDeveloperLabel);
            secondWindowDetalii.add(editProdDeveloperField);
            secondWindowDetalii.add(editProdPublisherLabel);
            secondWindowDetalii.add(editProdPublisherField);
            secondWindowDetalii.add(editProdGenLabel);
            secondWindowDetalii.add(editProdGenField);
            secondWindowDetalii.add(editProdAnLabel);
            secondWindowDetalii.add(editProdAnField);
            secondWindowDetalii.add(editProdAudientaLabel);
            secondWindowDetalii.add(editProdAudientaField);
            secondWindowDetalii.add(editProdCantLabel);
            secondWindowDetalii.add(editProdCantField);
            secondWindowDetalii.add(editProdPretLabel);
            secondWindowDetalii.add(editProdPretField);
            secondWindowButoane = new JPanel(new GridLayout(1, 3, 5, 5));
            secondWindowButoane.add(save);
            secondWindowButoane.add(delete);
            secondWindowButoane.add(exit);

            secondWindowMain = new JPanel(new BorderLayout());
            secondWindowMain.add(secondWindowDetalii, BorderLayout.CENTER);
            secondWindowMain.add(secondWindowButoane, BorderLayout.SOUTH);
            JFrame secondWindow = new JFrame();
            secondWindow.add(secondWindowMain);
            secondWindow.setVisible(true);
            secondWindow.setSize(300, 300);

            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame dialog = new JFrame();
                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa modificati produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (a == JOptionPane.YES_OPTION) {
                        Game game = stock.getGame();
                        game.setIdGame(Integer.parseInt(editIDField.getText()));
                        game.setTitle(editProdTitluField.getText());
                        game.setPlatform(editProdPlatformaField.getText());
                        game.setDeveloper(editProdDeveloperField.getText());
                        game.setPublisher(editProdPublisherField.getText());
                        game.setYear(editProdAnField.getText());
                        game.setGenre(editProdGenField.getText());
                        game.setAudience(Integer.parseInt(editProdAudientaField.getText()));
                        stock.setQuantity(Integer.parseInt(editProdCantField.getText()));
                        stock.setPrice(Integer.parseInt(editProdPretField.getText()));

                        gameMan.updateGame(game);
                        stockMan.updateGameStock(game, stock.getStore(), stock.getQuantity(), stock.getPrice());
                    }
                }
            });

            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame dialog = new JFrame();
                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa stergeti produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (a == JOptionPane.YES_OPTION) {
                        stockMan.deleteGameStock(stock.getGame(), stock.getStore());
                        secondWindow.setVisible(false);
                        secondWindow.dispose();
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    secondWindow.setVisible(false);
                    secondWindow.dispose();
                }
            });
        }
    }

    private void albumWindow() {

        JButton save, exit, delete;
        JPanel secondWindowMain, secondWindowDetalii, secondWindowButoane;
        StockManager stockMan = StockManager.getInstance();
        MovieManager movieMan = MovieManager.getInstance();
        GameManager gameMan = GameManager.getInstance();
        AlbumManager albumMan = AlbumManager.getInstance();

        JLabel editProdTitluLabel, editProdTrupaLabel, editProdMelodiiLabel, editProdCasaDiscLabel, editProdGenLabel, editProdAnLabel,
                editProdCantLabel, editProdPretLabel;
        JTextField editProdTitluField, editProdTrupaField, editProdMelodiiField, editProdCasaDiscField, editProdGenField, editProdAnField,
                editProdCantField, editProdPretField;

        Album album = new Album();
        album.setIdAlbum(Integer.parseInt(editIDField.getText()));
        Store store = new Store();
        store.setId(GUI.getLoggedEmployee().getIdMag());
        Stock stock = stockMan.getAlbumStock(album, store);
        stock.setAlbum(albumMan.getAlbumById(album.getIdAlbum()));

        album = stock.getAlbum();
        if (stock == null) {
            JFrame dialog = new JFrame();
            JOptionPane.showMessageDialog(dialog, "ID-ul introdus nu exista in baza de date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            editProdTitluLabel = new JLabel("Titlu:");
            editProdTitluField = new JTextField();
            editProdTitluField.setText(album.getTitle());
            editProdTrupaLabel = new JLabel("Trupa:");
            editProdTrupaField = new JTextField();
            editProdTrupaField.setText(album.getArtist());
            editProdMelodiiLabel = new JLabel("Numar melodii:");
            editProdMelodiiField = new JTextField();
            editProdMelodiiField.setText(album.getNrMel() + "");
            editProdCasaDiscLabel = new JLabel("Casa discuri:");
            editProdCasaDiscField = new JTextField();
            editProdCasaDiscField.setText(album.getProducer());
            editProdGenLabel = new JLabel("Gen:");
            editProdGenField = new JTextField();
            editProdGenField.setText(album.getGenre());
            editProdAnLabel = new JLabel("An:");
            editProdAnField = new JTextField();
            editProdAnField.setText(album.getYear());
            editProdCantLabel = new JLabel("Cantitate:");
            editProdCantField = new JTextField();
            editProdCantField.setText(stock.getQuantity() + "");
            editProdPretLabel = new JLabel("Pret:");
            editProdPretField = new JTextField();
            editProdPretField.setText(stock.getPrice() + "");
            save = new JButton("Save");
            exit = new JButton("Exit");
            delete = new JButton("Delete");

            secondWindowDetalii = new JPanel(new GridLayout(8, 8, 5, 5));
            secondWindowDetalii.add(editProdTitluLabel);
            secondWindowDetalii.add(editProdTitluField);
            secondWindowDetalii.add(editProdTrupaLabel);
            secondWindowDetalii.add(editProdTrupaField);
            secondWindowDetalii.add(editProdMelodiiLabel);
            secondWindowDetalii.add(editProdMelodiiField);
            secondWindowDetalii.add(editProdCasaDiscLabel);
            secondWindowDetalii.add(editProdCasaDiscField);
            secondWindowDetalii.add(editProdGenLabel);
            secondWindowDetalii.add(editProdGenField);
            secondWindowDetalii.add(editProdAnLabel);
            secondWindowDetalii.add(editProdAnField);
            secondWindowDetalii.add(editProdCantLabel);
            secondWindowDetalii.add(editProdCantField);
            secondWindowDetalii.add(editProdPretLabel);
            secondWindowDetalii.add(editProdPretField);
            secondWindowButoane = new JPanel(new GridLayout(1, 3, 5, 5));
            secondWindowButoane.add(save);
            secondWindowButoane.add(delete);
            secondWindowButoane.add(exit);

            secondWindowMain = new JPanel(new BorderLayout());
            secondWindowMain.add(secondWindowDetalii, BorderLayout.CENTER);
            secondWindowMain.add(secondWindowButoane, BorderLayout.SOUTH);
            JFrame secondWindow = new JFrame();
            secondWindow.add(secondWindowMain);
            secondWindow.setVisible(true);
            secondWindow.setSize(300, 300);

            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame dialog = new JFrame();
                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa modificati produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (a == JOptionPane.YES_OPTION) {
                        Album album = stock.getAlbum();
                        album.setIdAlbum(Integer.parseInt(editIDField.getText()));
                        album.setTitle(editProdTitluField.getText());
                        album.setArtist(editProdTrupaField.getText());
                        album.setNrMel(Integer.parseInt(editProdMelodiiField.getText()));
                        album.setProducer(editProdCasaDiscField.getText());
                        album.setYear(editProdAnField.getText());
                        album.setGenre(editProdGenField.getText());
                        stock.setQuantity(Integer.parseInt(editProdCantField.getText()));
                        stock.setPrice(Integer.parseInt(editProdPretField.getText()));

                        albumMan.updateAlbum(album);
                        stockMan.updateAlbumStock(album, stock.getStore(), stock.getQuantity(), stock.getPrice());
                    }
                }
            });

            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame dialog = new JFrame();
                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa stergeti produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (a == JOptionPane.YES_OPTION) {
                        stockMan.deleteAlbumStock(stock.getAlbum(), stock.getStore());
                        secondWindow.setVisible(false);
                        secondWindow.dispose();
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    secondWindow.setVisible(false);
                    secondWindow.dispose();
                }
            });
        }
    }
}