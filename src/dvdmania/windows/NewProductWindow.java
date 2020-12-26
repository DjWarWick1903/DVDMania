package dvdmania.windows;

import dvdmania.management.StockManager;
import dvdmania.management.Store;
import dvdmania.management.StoreManager;
import dvdmania.products.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public final class NewProductWindow extends JFrame {

    private static JButton newFilm, newJoc, newAlbum, newSong, exit;

    public NewProductWindow() {
        super("Produs nou");

        //creare butoane
        newFilm = new JButton("Film");
        newJoc = new JButton("Joc");
        newAlbum = new JButton("Album");
        newSong = new JButton("Melodie");
        exit = new JButton("Exit");

        //creare fereastra principala
        final JPanel newProductButtons = new JPanel(new GridLayout(2, 2, 2, 2));
        newProductButtons.add(newFilm);
        newProductButtons.add(newJoc);
        newProductButtons.add(newAlbum);
        newProductButtons.add(newSong);

        final JPanel newProductMain = new JPanel(new BorderLayout());
        newProductMain.add(newProductButtons, BorderLayout.CENTER);
        newProductMain.add(exit, BorderLayout.SOUTH);

        this.setVisible(true);
        this.add(newProductMain);
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setMovieListener();
        setGameListener();
        setAlbumListener();
        setSongListener();

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }

    private void setMovieListener() {
        //creare fereastra filme
        newFilm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JTextField newFilmTitluField, newFilmActorField, newFilmDirectorField, newFilmDurataField, newFilmGenField, newFilmAnField,
                        newFilmAudientaField, newFilmCantField, newFilmPretField;
                final JLabel newFilmTitluLabel, newFilmActorLabel, newFilmDirectorLabel, newFilmDurataLabel, newFilmGenLabel, newFilmAnLabel,
                        newFilmAudientaLabel, newFilmCantLabel, newFilmPretLabel;
                final JButton save, exit;

                newFilmTitluLabel = new JLabel("Titlu:");
                newFilmTitluField = new JTextField();
                newFilmActorLabel = new JLabel("Actor principal:");
                newFilmActorField = new JTextField();
                newFilmDirectorLabel = new JLabel("Director:");
                newFilmDirectorField = new JTextField();
                newFilmDurataLabel = new JLabel("Durata:");
                newFilmDurataField = new JTextField();
                newFilmGenField = new JTextField();
                newFilmGenLabel = new JLabel("Gen:");
                newFilmAnLabel = new JLabel("An:");
                newFilmAnField = new JTextField();
                newFilmAudientaLabel = new JLabel("Audienta:");
                newFilmAudientaField = new JTextField();
                newFilmCantLabel = new JLabel("Cantitate:");
                newFilmCantField = new JTextField();
                newFilmPretLabel = new JLabel("Pret:");
                newFilmPretField = new JTextField();
                save = new JButton("Save");
                exit = new JButton("Exit");

                final JFrame newFilmWindow = new JFrame();
                final JPanel detaliiPanel = new JPanel(new GridLayout(9, 9, 5, 5));
                final JPanel butoanePanel = new JPanel(new GridLayout(1, 2, 5, 5));
                final JPanel newFilmMain = new JPanel(new BorderLayout());
                detaliiPanel.add(newFilmTitluLabel);
                detaliiPanel.add(newFilmTitluField);
                detaliiPanel.add(newFilmActorLabel);
                detaliiPanel.add(newFilmActorField);
                detaliiPanel.add(newFilmDirectorLabel);
                detaliiPanel.add(newFilmDirectorField);
                detaliiPanel.add(newFilmDurataLabel);
                detaliiPanel.add(newFilmDurataField);
                detaliiPanel.add(newFilmGenLabel);
                detaliiPanel.add(newFilmGenField);
                detaliiPanel.add(newFilmAnLabel);
                detaliiPanel.add(newFilmAnField);
                detaliiPanel.add(newFilmAudientaLabel);
                detaliiPanel.add(newFilmAudientaField);
                detaliiPanel.add(newFilmCantLabel);
                detaliiPanel.add(newFilmCantField);
                detaliiPanel.add(newFilmPretLabel);
                detaliiPanel.add(newFilmPretField);
                butoanePanel.add(save);
                butoanePanel.add(exit);
                newFilmMain.add(detaliiPanel, BorderLayout.CENTER);
                newFilmMain.add(butoanePanel, BorderLayout.SOUTH);

                newFilmWindow.add(newFilmMain);
                newFilmWindow.setSize(300, 330);
                newFilmWindow.setVisible(true);
                newFilmWindow.setLocationRelativeTo(null);

                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final String titlu, actor, director, durata, gen, an, audienta, cant, pret;
                        titlu = newFilmTitluField.getText();
                        actor = newFilmActorField.getText();
                        director = newFilmDirectorField.getText();
                        durata = newFilmDurataField.getText();
                        gen = newFilmGenField.getText();
                        an = newFilmAnField.getText();
                        audienta = newFilmAudientaField.getText();
                        cant = newFilmCantField.getText();
                        pret = newFilmPretField.getText();

                        if (titlu.isEmpty() || actor.isEmpty() || director.isEmpty() || durata.isEmpty() || gen.isEmpty() || an.isEmpty() || audienta.isEmpty() || cant.isEmpty() || pret.isEmpty()) {
                            final JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            final MovieManager movieMan = MovieManager.getInstance();
                            final StockManager stockMan = StockManager.getInstance();
                            final StoreManager storeMan = StoreManager.getInstance();

                            final Movie movie = new Movie(0, titlu, actor, director, Integer.parseInt(durata), gen, an, Integer.parseInt(audienta));
                            final Store store = storeMan.getStoreByCity(GUI.getLoggedEmployee().getOras());

                            movieMan.createMovie(movie);
                            stockMan.insertMovieStock(movie, store, Integer.parseInt(cant), Integer.parseInt(pret));
                        }
                    }
                });

                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        newFilmWindow.setVisible(false);
                        newFilmWindow.dispose();
                    }
                });
            }
        });
    }

    private void setGameListener() {
        newJoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JTextField newJocTitluField, newJocPlatformaField, newJocDeveloperField, newJocPublisherField, newJocGenField, newJocAnField, newJocAudientaField, newJocCantField, newJocPretField;
                final JLabel newJocTitluLabel, newJocPlatformaLabel, newJocDeveloperLabel, newJocPublisherLabel, newJocGenLabel, newJocAnLabel, newJocAudientaLabel, newJocCantLabel, newJocPretLabel;
                final JButton save, exit;

                newJocTitluLabel = new JLabel("Titlu:");
                newJocTitluField = new JTextField();
                newJocPlatformaLabel = new JLabel("Platforma:");
                newJocPlatformaField = new JTextField();
                newJocDeveloperLabel = new JLabel("Developer:");
                newJocDeveloperField = new JTextField();
                newJocPublisherLabel = new JLabel("Publisher:");
                newJocPublisherField = new JTextField();
                newJocGenField = new JTextField();
                newJocGenLabel = new JLabel("Gen:");
                newJocAnLabel = new JLabel("An:");
                newJocAnField = new JTextField();
                newJocAudientaLabel = new JLabel("Audienta:");
                newJocAudientaField = new JTextField();
                newJocCantLabel = new JLabel("Cantitate:");
                newJocCantField = new JTextField();
                newJocPretLabel = new JLabel("Pret:");
                newJocPretField = new JTextField();
                save = new JButton("Save");
                exit = new JButton("Exit");

                final JFrame newJocWindow = new JFrame();
                final JPanel detaliiPanel = new JPanel(new GridLayout(9, 9, 5, 5));
                final JPanel butoanePanel = new JPanel(new GridLayout(1, 2, 5, 5));
                final JPanel newJocMain = new JPanel(new BorderLayout());
                detaliiPanel.add(newJocTitluLabel);
                detaliiPanel.add(newJocTitluField);
                detaliiPanel.add(newJocPlatformaLabel);
                detaliiPanel.add(newJocPlatformaField);
                detaliiPanel.add(newJocDeveloperLabel);
                detaliiPanel.add(newJocDeveloperField);
                detaliiPanel.add(newJocPublisherLabel);
                detaliiPanel.add(newJocPublisherField);
                detaliiPanel.add(newJocGenLabel);
                detaliiPanel.add(newJocGenField);
                detaliiPanel.add(newJocAnLabel);
                detaliiPanel.add(newJocAnField);
                detaliiPanel.add(newJocAudientaLabel);
                detaliiPanel.add(newJocAudientaField);
                detaliiPanel.add(newJocCantLabel);
                detaliiPanel.add(newJocCantField);
                detaliiPanel.add(newJocPretLabel);
                detaliiPanel.add(newJocPretField);
                butoanePanel.add(save);
                butoanePanel.add(exit);
                newJocMain.add(detaliiPanel, BorderLayout.CENTER);
                newJocMain.add(butoanePanel, BorderLayout.SOUTH);

                newJocWindow.add(newJocMain);
                newJocWindow.setSize(300, 330);
                newJocWindow.setVisible(true);
                newJocWindow.setLocationRelativeTo(null);

                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final String titlu, platforma, developer, publisher, gen, an, audienta, cant, pret;
                        titlu = newJocTitluField.getText();
                        platforma = newJocPlatformaField.getText();
                        developer = newJocDeveloperField.getText();
                        publisher = newJocPublisherField.getText();
                        gen = newJocGenField.getText();
                        an = newJocAnField.getText();
                        audienta = newJocAudientaField.getText();
                        cant = newJocCantField.getText();
                        pret = newJocPretField.getText();

                        if (titlu.isEmpty() || platforma.isEmpty() || developer.isEmpty() || publisher.isEmpty() || gen.isEmpty() || an.isEmpty() || audienta.isEmpty() || cant.isEmpty() || pret.isEmpty()) {
                            final JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            final GameManager gameMan = GameManager.getInstance();
                            final StockManager stockMan = StockManager.getInstance();
                            final StoreManager storeMan = StoreManager.getInstance();

                            final Game game = new Game(0, titlu, an, platforma, developer, publisher, gen, Integer.parseInt(audienta));
                            final Store store = storeMan.getStoreByCity(GUI.getLoggedEmployee().getOras());

                            gameMan.createGame(game);
                            stockMan.insertGameStock(game, store, Integer.parseInt(cant), Integer.parseInt(pret));
                        }
                    }
                });

                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        newJocWindow.setVisible(false);
                        newJocWindow.dispose();
                    }
                });
            }
        });
    }

    private void setAlbumListener() {
        newAlbum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JTextField newAlbumTitluField, newAlbumTrupaField, newAlbumMelodiiField, newAlbumCasaDiscuriField,
                        newAlbumGenField, newAlbumAnField, newAlbumCantField, newAlbumPretField;
                final JLabel newAlbumTitluLabel, newAlbumTrupaLabel, newAlbumMelodiiLabel, newAlbumCasaDiscuriLabel,
                        newAlbumGenLabel, newAlbumAnLabel, newAlbumCantLabel, newAlbumPretLabel;
                final JButton save, exit;

                newAlbumTitluLabel = new JLabel("Titlu:");
                newAlbumTitluField = new JTextField();
                newAlbumTrupaLabel = new JLabel("Trupa:");
                newAlbumTrupaField = new JTextField();
                newAlbumMelodiiLabel = new JLabel("Numar melodii:");
                newAlbumMelodiiField = new JTextField();
                newAlbumCasaDiscuriLabel = new JLabel("Casa discuri:");
                newAlbumCasaDiscuriField = new JTextField();
                newAlbumGenField = new JTextField();
                newAlbumGenLabel = new JLabel("Gen:");
                newAlbumAnLabel = new JLabel("An:");
                newAlbumAnField = new JTextField();
                newAlbumCantLabel = new JLabel("Cantitate:");
                newAlbumCantField = new JTextField();
                newAlbumPretLabel = new JLabel("Pret:");
                newAlbumPretField = new JTextField();
                save = new JButton("Save");
                exit = new JButton("Exit");

                final JFrame newAlbumWindow = new JFrame();
                final JPanel detaliiPanel = new JPanel(new GridLayout(8, 8, 5, 5));
                final JPanel butoanePanel = new JPanel(new GridLayout(1, 2, 5, 5));
                final JPanel newAlbumMain = new JPanel(new BorderLayout());
                detaliiPanel.add(newAlbumTitluLabel);
                detaliiPanel.add(newAlbumTitluField);
                detaliiPanel.add(newAlbumTrupaLabel);
                detaliiPanel.add(newAlbumTrupaField);
                detaliiPanel.add(newAlbumMelodiiLabel);
                detaliiPanel.add(newAlbumMelodiiField);
                detaliiPanel.add(newAlbumCasaDiscuriLabel);
                detaliiPanel.add(newAlbumCasaDiscuriField);
                detaliiPanel.add(newAlbumGenLabel);
                detaliiPanel.add(newAlbumGenField);
                detaliiPanel.add(newAlbumAnLabel);
                detaliiPanel.add(newAlbumAnField);
                detaliiPanel.add(newAlbumCantLabel);
                detaliiPanel.add(newAlbumCantField);
                detaliiPanel.add(newAlbumPretLabel);
                detaliiPanel.add(newAlbumPretField);
                butoanePanel.add(save);
                butoanePanel.add(exit);
                newAlbumMain.add(detaliiPanel, BorderLayout.CENTER);
                newAlbumMain.add(butoanePanel, BorderLayout.SOUTH);

                newAlbumWindow.add(newAlbumMain);
                newAlbumWindow.setSize(300, 330);
                newAlbumWindow.setVisible(true);
                newAlbumWindow.setLocationRelativeTo(null);

                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final String titlu, trupa, melodii, casaDisc, gen, an, cant, pret;
                        titlu = newAlbumTitluField.getText();
                        trupa = newAlbumTrupaField.getText();
                        melodii = newAlbumMelodiiField.getText();
                        casaDisc = newAlbumCasaDiscuriField.getText();
                        gen = newAlbumGenField.getText();
                        an = newAlbumAnField.getText();
                        cant = newAlbumCantField.getText();
                        pret = newAlbumPretField.getText();

                        if (titlu.isEmpty() || trupa.isEmpty() || melodii.isEmpty() || casaDisc.isEmpty() || gen.isEmpty() || an.isEmpty() || cant.isEmpty() || pret.isEmpty()) {
                            final JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            final AlbumManager albumMan = AlbumManager.getInstance();
                            final StockManager stockMan = StockManager.getInstance();
                            final StoreManager storeMan = StoreManager.getInstance();

                            final Album album = new Album(0, titlu, trupa, Integer.parseInt(melodii), casaDisc, gen, an);
                            final Store store = storeMan.getStoreByCity(GUI.getLoggedEmployee().getOras());

                            albumMan.createAlbum(album);
                            stockMan.insertAlbumStock(album, store, Integer.parseInt(cant), Integer.parseInt(pret));
                        }
                    }
                });

                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        newAlbumWindow.setVisible(false);
                        newAlbumWindow.dispose();
                    }
                });
            }
        });
    }

    private void setSongListener() {
        newSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JTextField newSongNumeField, newSongDurataField;
                final JLabel newSongNumeLabel, newSongDurataLabel, newSongAlbumLabel;
                final JComboBox newSongAlbumBox;
                final JButton save, exit;

                newSongNumeLabel = new JLabel("Nume:");
                newSongNumeField = new JTextField();
                newSongDurataLabel = new JLabel("Durata:");
                newSongDurataField = new JTextField();
                newSongAlbumLabel = new JLabel("Album");
                final AlbumManager albumMan = AlbumManager.getInstance();
                final ArrayList<Album> albume = albumMan.getAllAlbums();
                final ArrayList<String> numeAlbume = new ArrayList<>();
                final Iterator iterator = albume.iterator();
                while (iterator.hasNext()) {
                    Album album = (Album) iterator.next();
                    numeAlbume.add(album.getTitle());
                }
                newSongAlbumBox = new JComboBox(numeAlbume.toArray());
                save = new JButton("Save");
                exit = new JButton("Exit");

                final JFrame newSongWindow = new JFrame();
                final JPanel detaliiPanel = new JPanel(new GridLayout(3, 3, 5, 5));
                final JPanel butoanePanel = new JPanel(new GridLayout(1, 2, 5, 5));
                final JPanel newSongMain = new JPanel(new BorderLayout());
                detaliiPanel.add(newSongAlbumLabel);
                detaliiPanel.add(newSongAlbumBox);
                detaliiPanel.add(newSongNumeLabel);
                detaliiPanel.add(newSongNumeField);
                detaliiPanel.add(newSongDurataLabel);
                detaliiPanel.add(newSongDurataField);
                butoanePanel.add(save);
                butoanePanel.add(exit);
                newSongMain.add(detaliiPanel, BorderLayout.CENTER);
                newSongMain.add(butoanePanel, BorderLayout.SOUTH);

                newSongWindow.add(newSongMain);
                newSongWindow.setSize(300, 330);
                newSongWindow.setVisible(true);
                newSongWindow.setLocationRelativeTo(null);

                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final String albumName, nume, durata;
                        albumName = String.valueOf(newSongAlbumBox.getSelectedItem());
                        nume = newSongNumeField.getText();
                        durata = newSongDurataField.getText();

                        if (nume.isEmpty() || durata.isEmpty()) {
                            final JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            final SongManager songMan = SongManager.getInstance();
                            final Iterator iterator = albume.iterator();
                            while (iterator.hasNext()) {
                                final Album album = (Album) iterator.next();
                                if (album.getTitle().equals(albumName)) {
                                    final Song song = new Song(0, nume, Integer.parseInt(durata));
                                    songMan.CreateSong(album, song);
                                    break;
                                }
                            }
                        }
                    }
                });

                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        newSongWindow.setVisible(false);
                        newSongWindow.dispose();
                    }
                });
            }
        });
    }
}
