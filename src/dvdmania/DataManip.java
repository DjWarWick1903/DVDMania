package dvdmania;

import java.sql.*;
import java.util.ArrayList;

public class DataManip {

    //date de logare in baza de date
    String url = "jdbc:mysql://localhost:3306/?user=root";
    String user = "root";
    String password = "robertmaster1";

    //variable cache
    Connection myConn;
    Statement statement;

    DataManip() {
        try {
            myConn = DriverManager.getConnection(url, user, password);
            statement = myConn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    boolean checkAccount(String user, String password) {
        String sql = "SELECT util, parola FROM dvdmania.conturi";
        boolean isCorrect = false;

        try {
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                String username = result.getString(1);
                String pass = result.getString(2);

                if(username.equals(user) && pass.equals(password)) {
                    isCorrect = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isCorrect;
    }

    int checkProduct(int id, String currentCategory, String oras) {

        String sql;
        int result = 0;
        ResultSet resultSet;

        switch (currentCategory) {
            case "Filme":
                try {
                    sql = "SELECT p.id_prod, p.cant FROM dvdmania.produse p, dvdmania.magazin m  WHERE p.id_film="+ id + " AND p.id_mag=m.id_mag AND m.oras='" + oras + "'";
                    resultSet = statement.executeQuery(sql);
                    if(resultSet.next()) {
                        int idProd = resultSet.getInt(1);
                        int nrTot = resultSet.getInt(2);


                        sql = "SELECT COUNT(id_prod) FROM dvdmania.imprumuturi WHERE id_prod='" + idProd + "' AND data_ret=NULL";
                        resultSet = statement.executeQuery(sql);
                        resultSet.next();
                        int nrImp = resultSet.getInt(1);

                        result = nrTot - nrImp;
                    } else {
                        result = -1;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "Jocuri":
                try {
                    sql = "SELECT p.id_prod, p.cant FROM dvdmania.produse p, dvdmania.magazin m WHERE p.id_joc="+ id + " AND p.id_mag=m.id_mag AND m.oras='" + oras + "'";
                    resultSet = statement.executeQuery(sql);
                    if(resultSet.next()) {
                        int idProd = resultSet.getInt(1);
                        int nrTot = resultSet.getInt(2);

                        sql = "SELECT COUNT(id_prod) FROM dvdmania.imprumuturi WHERE id_prod=" + idProd + " AND data_ret=NULL";
                        resultSet = statement.executeQuery(sql);
                        resultSet.next();
                        int nrImp = resultSet.getInt(1);

                        result = nrTot - nrImp;
                    } else {
                        result = -1;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "Albume":
                try {
                    sql = "SELECT p.id_prod, p.cant FROM dvdmania.produse p, dvdmania.magazin m WHERE p.id_album="+ id + " AND p.id_mag=m.id_mag AND m.oras='" + oras + "'";
                    resultSet = statement.executeQuery(sql);
                    if(resultSet.next()) {
                        int idProd = resultSet.getInt(1);
                        int nrTot = resultSet.getInt(2);

                        sql = "SELECT COUNT(id_prod) FROM dvdmania.imprumuturi WHERE id_prod=" + idProd + " AND data_ret=NULL";
                        resultSet = statement.executeQuery(sql);
                        resultSet.next();
                        int nrImp = resultSet.getInt(1);

                        result = nrTot - nrImp;
                    } else {
                        result = -1;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }

        return result;
    }

    int checkPriviledge(String user, String password) {
        String sql = "SELECT id_cl, id_angaj FROM dvdmania.conturi WHERE util='" + user +
                "' AND parola='" + password + "'";

        int id_cl = 0;
        int id_angaj = 0;
        int priviledge = 0;

        try {
            ResultSet result = statement.executeQuery(sql);

            result.next();
            id_cl = result.getInt(1);
            id_angaj = result.getInt(2);
            if(id_cl != 0)
                priviledge = 1;
            else if(id_angaj != 0) {
                String sqlP = "SELECT functie FROM dvdmania.angajati WHERE id_angaj="
                            + id_angaj;
                ResultSet priv = statement.executeQuery(sqlP);
                priv.next();
                String functie = priv.getString(1);
                if(functie.equals("Vanzator")) {
                    priviledge = 2;
                }
                else
                    priviledge = 3;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return priviledge;
    }

    void createClient(Utilizator util) {
        String sql = "INSERT INTO dvdmania.clienti (nume, pren, adresa, oras, datan, cnp, tel, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, util.getNume());
            statement.setString(2, util.getPrenume());
            statement.setString(3, util.getAdresa());
            statement.setString(4, util.getOras());
            statement.setDate(5, Date.valueOf(util.getDataNasterii()));
            statement.setString(6, util.getCNP());
            statement.setString(7, util.getTelefon());
            statement.setString(8,util.getEmail());


            int rowInserted = statement.executeUpdate();
            if(rowInserted > 0) {
                System.out.println("A new user was inserted!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createEmployee(Utilizator util) {

        try {
            String sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + util.getAdresaMagazin() + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idMag = rs.getInt(1);

            sql = "INSERT INTO dvdmania.angajati (nume, pren, adresa, oras, datan, cnp, tel, email, functie, salariu, activ, id_mag " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, util.getNume());
            statement.setString(2, util.getPrenume());
            statement.setString(3, util.getAdresa());
            statement.setString(4, util.getOras());
            statement.setDate(5, Date.valueOf(util.getDataNasterii()));
            statement.setString(6, util.getCNP());
            statement.setString(7, util.getTelefon());
            statement.setString(8,util.getEmail());
            statement.setString(9, util.getFunctie());
            statement.setInt(10, Integer.parseInt(util.getSalariu()));
            statement.setString(11, "Activ");
            statement.setInt(12, idMag);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createAccount(Utilizator util, String username, String password, boolean client) {

        String sql;
        String sqlSelect;

        if(client == true) {
            try {

                sql = "INSERT INTO dvdmania.conturi (util, parola, data_creat, id_cl) VALUES(?,?,SYSDATE(),?)";
                sqlSelect = "SELECT id_cl FROM dvdmania.clienti WHERE cnp='" + util.getCNP() + "'";
                ResultSet result = statement.executeQuery(sqlSelect);
                result.next();
                int id = result.getInt(1);

                PreparedStatement statement = myConn.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setInt(3, id);

                int rowInserted = statement.executeUpdate();
                if(rowInserted > 0) {
                    System.out.println("A new user was inserted!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {

                sql = "INSERT INTO dvdmania.conturi (util, parola, data_creat, id_angaj) VALUES(?,?,SYSDATE(),?)";
                sqlSelect = "SELECT id_angaj FROM dvdmania.angajati WHERE cnp=" + util.getCNP();
                ResultSet result = statement.executeQuery(sqlSelect);
                result.next();
                int id = result.getInt(1);

                PreparedStatement statement = myConn.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setInt(3, id);

                int rowInserted = statement.executeUpdate();
                if(rowInserted > 0) {
                    System.out.println("A new user was inserted!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void createFilm(String titlu, String actor, String director, String durata, String gen, String an, String audienta, String magazin, String cant, String pret) {
        String sql = "INSERT INTO dvdmania.filme (titlu, actor_pr, director, durata, gen, an, audienta) VALUES (?, ?, ?, ?, ?, YEAR(?), ?)";
        try {
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, titlu);
            statement.setString(2, actor);
            statement.setString(3, director);
            statement.setInt(4, Integer.parseInt(durata));
            statement.setString(5, gen);
            statement.setDate(6, Date.valueOf(an + "-01-01"));
            statement.setInt(7, Integer.parseInt(audienta));

            int rowInserted = statement.executeUpdate();
            if(rowInserted > 0) {
                System.out.println("A new movie was inserted!");
            }

            sql = "SELECT id_film FROM dvdmania.filme WHERE titlu='" + titlu + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idFilm = rs.getInt(1);

            sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + magazin + "'";
            rs = statement.executeQuery(sql);
            rs.next();
            int idMag = rs.getInt(1);

            sql = "INSERT INTO dvdmania.produse (id_film, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
            statement = myConn.prepareStatement(sql);
            statement.setInt(1, idFilm);
            statement.setInt(2, idMag);
            statement.setInt(3, Integer.parseInt(cant));
            statement.setInt(4, Integer.parseInt(pret));

            rowInserted = statement.executeUpdate();
            if(rowInserted > 0) {
                System.out.println("A new movie was inserted in products!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createJoc(String titlu, String platforma, String developer, String publisher, String gen, String an, String audienta, String magazin, String cant, String pret) {
        String sql = "INSERT INTO dvdmania.jocuri (titlu, platforma, developer, publisher, gen, an, audienta) VALUES (?, ?, ?, ?, ?, YEAR(?), ?)";
        try {
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, titlu);
            statement.setString(2, platforma);
            statement.setString(3, developer);
            statement.setString(4, publisher);
            statement.setString(5, gen);
            statement.setDate(6, Date.valueOf(an + "-01-01"));
            statement.setInt(7, Integer.parseInt(audienta));

            int rowInserted = statement.executeUpdate();
            if(rowInserted > 0) {
                System.out.println("A new game was inserted!");
            }

            sql = "SELECT id_joc FROM dvdmania.jocuri WHERE titlu='" + titlu + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idJoc = rs.getInt(1);

            sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + magazin + "'";
            rs = statement.executeQuery(sql);
            rs.next();
            int idMag = rs.getInt(1);

            sql = "INSERT INTO dvdmania.produse (id_joc, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
            statement = myConn.prepareStatement(sql);
            statement.setInt(1, idJoc);
            statement.setInt(2, idMag);
            statement.setInt(3, Integer.parseInt(cant));
            statement.setInt(4, Integer.parseInt(pret));

            rowInserted = statement.executeUpdate();
            if(rowInserted > 0) {
                System.out.println("A new game was inserted in products!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createAlbum(String titlu, String trupa, String melodii, String disc, String gen, String an, String magazin, String cant, String pret) {
        String sql = "INSERT INTO dvdmania.jocuri (titlu, trupa, nr_mel , casa_disc, gen, an) VALUES (?, ?, ?, ?, ?, YEAR(?))";
        try {
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, titlu);
            statement.setString(2, trupa);
            statement.setInt(3, Integer.parseInt(melodii));
            statement.setString(4, disc);
            statement.setString(5, gen);
            statement.setDate(6, Date.valueOf(an + "-01-01"));

            int rowInserted = statement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("A new album was inserted!");
            }
            sql = "SELECT id_album FROM dvdmania.albume WHERE titlu='" + titlu + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idFilm = rs.getInt(1);

            sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + magazin + "'";
            rs = statement.executeQuery(sql);
            rs.next();
            int idMag = rs.getInt(1);

            sql = "INSERT INTO dvdmania.produse (id_album, id_mag, cant, pret) VALUES (?, ?, ?, ?)";
            statement = myConn.prepareStatement(sql);
            statement.setInt(1, idFilm);
            statement.setInt(2, idMag);
            statement.setInt(3, Integer.parseInt(cant));
            statement.setInt(4, Integer.parseInt(pret));

            rowInserted = statement.executeUpdate();
            if(rowInserted > 0) {
                System.out.println("A new album was inserted in products!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createSong(String album, String nume, String durata) {
        try {
            ResultSet rs = statement.executeQuery("SELECT id_album FROM dvdmania.albume WHERE titlu='" + album + "'");
            rs.next();
            int idAlbum = rs.getInt(1);

            String sql = "INSERT INTO dvdmania.muzica (nume, durata, id_album) VALUES (?, ?, ?)";
            PreparedStatement statement = myConn.prepareStatement(sql);

            statement.setString(1,nume);
            statement.setInt(2, Integer.parseInt(durata));
            statement.setInt(3, idAlbum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyFilm(String id, String oras, String titlu, String actor_pr, String director, String durata, String gen, String an, String audienta, String cant, String pret) {
        try {
            String sql = "UPDATE dvdmania.filme SET titlu=?, actor_pr=?, director=?, durata=?, gen=?, an=YEAR(?), audienta=? WHERE id_film='" + id + "'";

            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, titlu);
            statement.setString(2, actor_pr);
            statement.setString(3, director);
            statement.setInt(4, Integer.parseInt(durata));
            statement.setString(5, gen);
            statement.setDate(6, Date.valueOf(an + "-01-01"));
            statement.setInt(7, Integer.parseInt(audienta));
            statement.executeUpdate();

            sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + oras + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idmag = rs.getInt(1);

            sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_film='" + id + "' AND id_mag='" + idmag + "'";
            statement = myConn.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(cant));
            statement.setInt(2, Integer.parseInt(pret));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void deleteFilm(String id, String oras) {
        try {
            String sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + oras + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idmag = rs.getInt(1);

            sql = "DELETE FROM dvdmania.produse WHERE id_film='" + id + "' AND id_mag='" + idmag + "'";
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.executeUpdate();

            sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_film='" + id + "'";
            rs = statement.executeQuery(sql);
            rs.next();
            int count = rs.getInt(1);

            if(count == 0) {
                sql = "DELETE FROM dvdmania.filme WHERE id_film='" + id + "'";
                statement = myConn.prepareStatement(sql);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyJoc(String id, String oras, String titlu, String platforma, String developer, String publisher, String an, String gen, String audienta, String cant, String pret) {
        try {
            String sql = "UPDATE dvdmania.jocuri SET titlu=?, platforma=?, developer=?, publisher=?, gen=?, an=YEAR(?), audienta=? WHERE id_joc='" + id + "'";

            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, titlu);
            statement.setString(2, platforma);
            statement.setString(3, developer);
            statement.setString(4, publisher);
            statement.setString(5, gen);
            statement.setDate(6, Date.valueOf(an + "-01-01"));
            statement.setInt(7, Integer.parseInt(audienta));
            statement.executeUpdate();

            sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + oras + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idmag = rs.getInt(1);

            sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_joc='" + id + "' AND id_mag='" + idmag + "'";
            statement = myConn.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(cant));
            statement.setInt(2, Integer.parseInt(pret));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void deleteJoc(String id, String oras) {
        try {
            String sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + oras + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idmag = rs.getInt(1);

            sql = "DELETE FROM dvdmania.produse WHERE id_joc='" + id + "' AND id_mag='" + idmag + "'";
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.executeUpdate();

            sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_joc='" + id + "'";
            rs = statement.executeQuery(sql);
            rs.next();
            int count = rs.getInt(1);

            if(count == 0) {
                sql = "DELETE FROM dvdmania.jocuri WHERE id_joc='" + id + "'";
                statement = myConn.prepareStatement(sql);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyAlbum(String id, String oras, String titlu, String trupa, String nr_mel, String casaDisc, String an, String gen, String cant, String pret) {
        try {
            String sql = "UPDATE dvdmania.albume SET titlu=?, trupa=?, nr_mel=?, casa_disc=?, gen=?, an=YEAR(?) WHERE id_album='" + id + "'";

            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, titlu);
            statement.setString(2, trupa);
            statement.setInt(3, Integer.parseInt(nr_mel));
            statement.setString(4, casaDisc);
            statement.setString(5, gen);
            statement.setDate(6, Date.valueOf(an + "-01-01"));
            statement.executeUpdate();

            sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + oras + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idmag = rs.getInt(1);

            sql = "UPDATE dvdmania.produse SET cant=?, pret=? WHERE id_album='" + id + "' AND id_mag='" + idmag + "'";
            statement = myConn.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(cant));
            statement.setInt(2, Integer.parseInt(pret));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void deleteAlbum(String id, String oras) {
        try {
            String sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + oras + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idmag = rs.getInt(1);

            sql = "DELETE FROM dvdmania.produse WHERE id_album='" + id + "' AND id_mag='" + idmag + "'";
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.executeUpdate();

            sql = "SELECT COUNT(*) FROM dvdmania.produse WHERE id_film='" + id + "'";
            rs = statement.executeQuery(sql);
            rs.next();
            int count = rs.getInt(1);

            if(count == 0) {
                sql = "DELETE FROM dvdmania.albume WHERE id_album='" + id + "'";
                statement = myConn.prepareStatement(sql);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyClient(String id, String nume, String pren, String adresa, String oras, String datan, String cnp, String tel, String email, String util, String parola) {
        try {
            String sql = "UPDATE dvdmania.clienti SET nume=?, pren=?, adresa=?, oras=?, datan=?, cnp=?, tel=?, email=? WHERE id_cl='" + id + "'";

            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, nume);
            statement.setString(2, pren);
            statement.setString(3, adresa);
            statement.setString(4, oras);
            statement.setDate(5, Date.valueOf(datan));
            statement.setString(6, cnp);
            statement.setString(7, tel);
            statement.setString(8, email);
            statement.executeUpdate();

            sql = "UPDATE dvdmania.conturi SET util=?, parola=? WHERE id_cl='" + id + "'";
            statement = myConn.prepareStatement(sql);
            statement.setString(1, util);
            statement.setString(2, parola);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyAngajat(String id, String nume, String pren, String adresa, String oras, String datan, String cnp, String tel, String email, String funct,
                       String sal, String store, String activ, String util, String parola) {
        try {
            String sql = "SELECT id_mag FROM dvdmania.magazin WHERE oras='" + store + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idMag = rs.getInt(1);

            sql = "UPDATE dvdmania.angajati SET nume=?, pren=?, adresa=?, oras=?, datan=?, cnp=?, tel=?, email=?, functie=?, salariu=?, activ=?, id_mag=? WHERE id_angaj='" + id + "'";

            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1, nume);
            statement.setString(2, pren);
            statement.setString(3, adresa);
            statement.setString(4, oras);
            statement.setDate(5, Date.valueOf(datan));
            statement.setString(6, cnp);
            statement.setString(7, tel);
            statement.setString(8, email);
            statement.setString(9, funct);
            statement.setInt(10, Integer.parseInt(sal));
            statement.setString(11, activ);
            statement.setInt(12, idMag);
            statement.executeUpdate();

            sql = "UPDATE dvdmania.conturi SET util=?, parola=? WHERE id_angaj='" + id + "'";
            statement = myConn.prepareStatement(sql);
            statement.setString(1, util);
            statement.setString(2, parola);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createStore(String adresa, String oras, String tel) {
        try {
            String sql = "INSERT INTO dvdmania.magazin (adresa, oras, tel) VALUES (?, ?, ?)";
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1,adresa);
            statement.setString(2, oras);
            statement.setString(3, tel);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyMagazin(String id, String adresa, String oras, String tel) {
        try {
            String sql = "UPDATE dvdmania.magazin SET adresa=?, oras=?, tel=? WHERE id_mag='" + id + "'";
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setString(1,adresa);
            statement.setString(2, oras);
            statement.setString(3, tel);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void rewardClient(String cnp, boolean plus) {
        try {
            String sql;
            if(plus == true) {
                sql = "UPDATE dvdmania.clienti SET loialitate=loialitate+1 WHERE cnp='" + cnp + "'";
                PreparedStatement statement = myConn.prepareStatement(sql);
                statement.executeUpdate();
            } else {
                sql = "UPDATE dvdmania.clienti SET loialitate=loialitate-1 WHERE cnp='" + cnp + "'";
                PreparedStatement statement = myConn.prepareStatement(sql);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int returnOrder(String idProd, String cnp, String dataImp) {
        int rez = 0;
        try {
            String sql = "SELECT id_cl FROM dvdmania.clienti WHERE cnp='" + cnp + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idCl = rs.getInt(1);

            sql = "UPDATE dvdmania.imprumuturi SET data_ret=SYSDATE() WHERE id_prod='" + idProd + "' AND id_cl='" + idCl + "' AND data_imp='" + dataImp + "'";
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.executeUpdate();

            rs = statement.executeQuery("SELECT DATEDIFF(data_ret, data_imp) FROM dvdmania.imprumuturi");
            rs.next();
            rez = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez;
    }

    String newOrder(String idProd, String idCl, String cnpAngaj, String cat) {
        String res = new String();
        try {
            String sql = "SELECT id_angaj, id_mag FROM dvdmania.angajati WHERE cnp='" + cnpAngaj + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int idAng = rs.getInt(1);
            int idMag = rs.getInt(2);
            int idProdus = 0;
            int pret = 0;

            if(cat.equals("Filme")) {
                sql = "SELECT id_prod, pret FROM dvdmania.produse WHERE id_film='" + idProd +"'";
                rs = statement.executeQuery(sql);
                rs.next();
                idProdus = rs.getInt(1);
                pret = rs.getInt(2);
            } else if(cat.equals("Jocuri")) {
                sql = "SELECT id_prod, pret FROM dvdmania.produse WHERE id_joc='" + idProd +"'";
                rs = statement.executeQuery(sql);
                rs.next();
                idProdus = rs.getInt(1);
                pret = rs.getInt(2);
            } else if(cat.equals("Albume")) {
                sql = "SELECT id_prod, pret FROM dvdmania.produse WHERE id_album='" + idProd +"'";
                rs = statement.executeQuery(sql);
                rs.next();
                idProdus = rs.getInt(1);
                pret = rs.getInt(2);
            }

            sql = "INSERT INTO dvdmania.imprumuturi (id_prod, id_cl, id_angaj, id_mag, data_imp, pret) VALUES (?, ?, ?, ?, SYSDATE(), ?)";
            PreparedStatement statement = myConn.prepareStatement(sql);
            statement.setInt(1,idProdus);
            statement.setInt(2, Integer.parseInt(idCl));
            statement.setInt(3, idAng);
            statement.setInt(4, idMag);
            statement.setInt(5, pret);

            statement.executeUpdate();

            rs = statement.executeQuery("SELECT DATE_ADD(sysdate(), INTERVAL 7 day)");
            rs.next();
            res =  String.valueOf(rs.getDate(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    String getFunctie(String cnp) {
        String rez = new String();
        try {
            String sql = "SELECT functie FROM dvdmania.angajati WHERE cnp='" + cnp + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            rez = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rez;
    }

    String getEmail(String cnp) {
        String rez = new String();
        try {
            String sql = "SELECT email FROM dvdmania.angajati WHERE cnp='" + cnp + "'";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            rez = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rez;
    }

    ArrayList<String[]> getActiveOrders(String oras) {
        ArrayList<String[]> orders = new ArrayList<>();
        try {
            String sql = "SELECT cl.nume, cl.pren, cl.cnp, p.id_prod, i.data_imp FROM dvdmania.imprumuturi i, dvdmania.clienti cl, dvdmania.produse p, dvdmania.magazin m  WHERE " +
                    "p.id_prod=i.id_prod AND cl.id_cl=i.id_cl AND data_ret IS NULL AND m.id_mag=i.id_mag AND m.oras='" + oras + "'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String nume = rs.getString(1);
                String pren = rs.getString(2);
                String cnp = rs.getString(3);
                String idProd = Integer.toString(rs.getInt(4));
                String dataImp = String.valueOf(rs.getDate(5));
                orders.add( new String[] {nume, pren, cnp, idProd, dataImp});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    ArrayList<String[]> getAllOrders(String oras) {

        ArrayList<String[]> orders = new ArrayList<>();

        try {
            String sql = "SELECT cl.nume, cl.pren, cl.cnp, p.id_prod, i.data_imp, i.data_ret, i.pret FROM dvdmania.imprumuturi i, dvdmania.clienti cl, dvdmania.produse p, dvdmania.magazin m WHERE " +
                    "p.id_prod=i.id_prod AND cl.id_cl=i.id_cl AND m.id_mag=i.id_mag AND m.oras='" + oras + "'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String nume = rs.getString(1);
                String pren = rs.getString(2);
                String cnp = rs.getString(3);
                String idProd = Integer.toString(rs.getInt(4));
                String dataImp = String.valueOf(rs.getDate(5));
                String dataRet = String.valueOf(rs.getDate(6));
                String pret = Integer.toString(rs.getInt(7));
                orders.add( new String[] {nume, pren, cnp, idProd, dataImp, dataRet, pret});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    ArrayList<String[]> getAllOrdersClient(String cnp) {
        ArrayList<String[]> orders = new ArrayList<>();

        try {
            String sql = "SELECT cl.nume, cl.pren, p.id_prod, i.data_imp, i.data_ret, i.pret FROM dvdmania.imprumuturi i, dvdmania.clienti cl, dvdmania.produse p WHERE " +
                    "p.id_prod=i.id_prod AND cl.id_cl=i.id_cl AND cl.cnp='" + cnp + "'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String nume = rs.getString(1);
                String pren = rs.getString(2);
                String idProd = Integer.toString(rs.getInt(3));
                String dataImp = String.valueOf(rs.getDate(4));
                String dataRet = String.valueOf(rs.getDate(5));
                String pret = Integer.toString(rs.getInt(6));
                orders.add( new String[] {nume, pren, cnp, idProd, dataImp, dataRet, pret});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    ArrayList<String> getProduct(String id, String cat, String oras) {
        int idMag = 0;
        ArrayList<String> produs = new ArrayList<>();
        if(cat.equals("Filme")) {

            try {
                ResultSet rs = statement.executeQuery("SELECT id_mag FROM dvdmania.magazin WHERE oras='" + oras + "'");
                if(rs.next()) {
                    idMag = rs.getInt(1);
                    String sql = "SELECT f.titlu, f.actor_pr, f.director, f.durata, f.gen, f.an, f.audienta, p.cant, p.pret FROM dvdmania.filme f, dvdmania.produse p " +
                            "WHERE p.id_film=f.id_film AND p.id_mag='" + idMag + "' AND f.id_film='" + id + "'";
                    rs = statement.executeQuery(sql);
                    rs.next();
                    produs.add(rs.getString(1));
                    produs.add(rs.getString(2));
                    produs.add(rs.getString(3));
                    produs.add(Integer.toString(rs.getInt(4)));
                    produs.add(rs.getString(5));
                    produs.add((String.valueOf(rs.getDate(6))).substring(0,4));
                    produs.add(Integer.toString(rs.getInt(7)));
                    produs.add(Integer.toString(rs.getInt(8)));
                    produs.add(Integer.toString(rs.getInt(9)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if(cat.equals("Jocuri")) {

            try {
                ResultSet rs = statement.executeQuery("SELECT id_mag FROM dvdmania.magazin WHERE oras='" + oras + "'");
                if(rs.next()) {
                    idMag = rs.getInt(1);
                    String sql = "SELECT j.titlu, j.platforma, j.developer, j.publisher, j.gen, j.an, j.audienta, p.cant, p.pret FROM dvdmania.jocuri j, dvdmania.produse p " +
                            "WHERE p.id_joc=j.id_joc AND p.id_mag='" + idMag + "' AND j.id_joc='" + id + "'";
                    rs = statement.executeQuery(sql);
                    rs.next();
                    produs.add(rs.getString(1));
                    produs.add(rs.getString(2));
                    produs.add(rs.getString(3));
                    produs.add(rs.getString(4));
                    produs.add(rs.getString(5));
                    produs.add((String.valueOf(rs.getDate(6))).substring(0,4));
                    produs.add(Integer.toString(rs.getInt(7)));
                    produs.add(Integer.toString(rs.getInt(8)));
                    produs.add(Integer.toString(rs.getInt(9)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if(cat.equals("Albume")) {

            try {
                ResultSet rs = statement.executeQuery("SELECT id_mag FROM dvdmania.magazin WHERE oras='" + oras + "'");
                if(rs.next()) {
                    idMag = rs.getInt(1);
                    String sql = "SELECT a.titlu, a.trupa, a.nr_mel, a.casa_disc, a.gen, a.an, p.cant, p.pret FROM dvdmania.albume a, dvdmania.produse p " +
                            "WHERE p.id_album=a.id_album AND p.id_mag='" + idMag + "' AND a.id_album='" + id + "'";
                    rs = statement.executeQuery(sql);
                    rs.next();
                    produs.add(rs.getString(1));
                    produs.add(rs.getString(2));
                    produs.add(Integer.toString(rs.getInt(3)));
                    produs.add(rs.getString(4));
                    produs.add(rs.getString(5));
                    produs.add((String.valueOf(rs.getDate(6))).substring(0,4));
                    produs.add(Integer.toString(rs.getInt(7)));
                    produs.add(Integer.toString(rs.getInt(8)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return produs;
    }

    ArrayList<String> getAlbume() {
        String sql = "SELECT titlu FROM dvdmania.albume";
        ArrayList<String> albume = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                albume.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return albume;
    }

    ArrayList<String[]> getUser(String user, String password) {
        ArrayList<String[]> detalii = new ArrayList<>();

        try {
            int id = 0;
            ResultSet rs = statement.executeQuery("SELECT id_cl FROM dvdmania.conturi WHERE util='" + user + "' AND parola='" + password +"'");
            if(rs.next()) {
                id = rs.getInt(1);
                if(id == 0) {
                    //angajat
                    ResultSet newRs = statement.executeQuery("SELECT id_angaj FROM dvdmania.conturi WHERE util='" + user + "' AND parola='" + password +"'");
                    newRs.next();
                    id = newRs.getInt(1);

                    rs = statement.executeQuery("SELECT a.nume, a.pren, a.adresa, a.oras, a.datan, a.cnp, a.tel, a.email, a.functie, a.salariu, m.adresa " +
                            "FROM dvdmania.angajati a, dvdmania.magazin m WHERE a.id_angaj='" + id + "' AND a.id_mag=m.id_mag");

                    rs.next();

                    detalii.add(new String[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), String.valueOf(rs.getDate(5)),
                    rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), Integer.toString(rs.getInt(10)), rs.getString(11)});

                } else {
                    //client
                    rs = statement.executeQuery("SELECT c.nume, c.pren, c.adresa, c.oras, c.datan, c.cnp, c.tel, c.email, c.loialitate " +
                            "FROM dvdmania.clienti c, dvdmania.magazin m WHERE c.id_cl='" + id + "'");
                    rs.next();

                    detalii.add(new String[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), String.valueOf(rs.getDate(5)),
                            rs.getString(6), rs.getString(7), rs.getString(8), Integer.toString(rs.getInt(9))});

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalii;
    }

    ArrayList<String> getGen(String categorie) {
        ArrayList<String> gen = new ArrayList<String>();
        gen.add("Toate");

        switch (categorie) {
            case "Filme":
                try {
                    ResultSet result = statement.executeQuery("SELECT DISTINCT gen FROM dvdmania.filme");

                    while (result.next()) {
                        gen.add(result.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Jocuri":
                try {
                    ResultSet result = statement.executeQuery("SELECT DISTINCT gen FROM dvdmania.jocuri");

                    while (result.next()) {
                        gen.add(result.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "Muzica":
                try {
                    ResultSet result = statement.executeQuery("SELECT DISTINCT gen FROM dvdmania.albume");

                    while (result.next()) {
                        gen.add(result.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }

        return gen;
    }

    ArrayList<String[]> getSongs(int id) {
        ArrayList<String[]> produse = new ArrayList<String[]>();

        try {
            ResultSet result = statement.executeQuery("SELECT nume, durata FROM dvdmania.muzica" +
                    " WHERE id_album=" + id);
            while (result.next()) {
                String nume = result.getString(1);
                int calcDurata = result.getInt(2);
                String durata;
                if(calcDurata%60 >= 10) {
                    durata = new String(calcDurata / 60 + ":" + calcDurata % 60);
                } else {
                    durata = new String(calcDurata / 60 + ":0" + calcDurata % 60);
                }

                produse.add(new String[]{nume, durata});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produse;
    }

    ArrayList<String[]> getStores() {
        String sql = "SELECT * FROM dvdmania.magazin";
        ArrayList<String[]> stores = new ArrayList<>();

        try {
            ResultSet result = statement.executeQuery(sql);

            while(result.next()) {
                stores.add(new String[] {Integer.toString(result.getInt(1)), result.getString(2), result.getString(3), result.getString(4) });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stores;
    }

    ArrayList<String[]> getProducts(String categorie, String magazin) {
        ArrayList<String[]> produse = new ArrayList<String[]>();

        if(magazin.equals("Toate")) {
            switch(categorie) {
                case "Filme":
                    try {
                        ResultSet result = statement.executeQuery("SELECT f.id_film, f.titlu, f.actor_pr, f.director, f.durata, f.gen, f.an, f.audienta, p.pret FROM dvdmania.filme f," +
                                " dvdmania.produse p WHERE f.id_film=p.id_film");
                        while(result.next()) {
                            String id_film = Integer.toString(result.getInt(1));
                            String titlu = result.getString(2);
                            String act_pr = result.getString(3);
                            String director = result.getString(4);
                            int calcDurata = result.getInt(5);
                            String durata;
                            if(calcDurata%60 >= 10) {
                                durata = new String(calcDurata / 60 + ":" + calcDurata % 60);
                            } else {
                                durata = new String(calcDurata / 60 + ":0" + calcDurata % 60);
                            }
                            String gen = result.getString(6);
                            String an = (String.valueOf(result.getDate(7))).substring(0,4);
                            String audienta = Integer.toString(result.getInt(8));
                            String pret = Integer.toString(result.getInt(9));

                            produse.add(new String[] {id_film, titlu, act_pr, director, durata, gen, an, audienta, pret});
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Jocuri":
                    try {
                        ResultSet result = statement.executeQuery("SELECT j.id_joc, j.titlu, j.platforma, j.developer, j.publisher, j.gen, j.an, j.audienta, p.pret" +
                                " FROM dvdmania.jocuri j, dvdmania.produse p WHERE j.id_joc=p.id_joc");
                        while(result.next()) {
                            String id_joc = Integer.toString(result.getInt(1));
                            String titlu = result.getString(2);
                            String platforma = result.getString(3);
                            String developer = result.getString(4);
                            String publisher = result.getString(5);
                            String gen = result.getString(6);
                            String an = (String.valueOf(result.getDate(7))).substring(0,4);
                            String audienta = Integer.toString(result.getInt(8));
                            String pret = Integer.toString(result.getInt(9));

                            produse.add(new String[] {id_joc, titlu, platforma, developer, publisher, gen, an, audienta, pret});
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Albume":
                    try {
                        ResultSet result = statement.executeQuery("SELECT a.id_album, a.trupa, a.titlu, a.nr_mel, a.casa_disc, a.gen, a.an, p.pret" +
                                " FROM dvdmania.albume a, dvdmania.produse p WHERE a.id_album=p.id_album");
                        while(result.next()) {
                            String id_album = Integer.toString(result.getInt(1));
                            String trupa = result.getString(2);
                            String titlu = result.getString(3);
                            String nr_mel = Integer.toString(result.getInt(4));
                            String casa_disc = result.getString(5);
                            String gen = result.getString(6);
                            String an = (String.valueOf(result.getDate(7))).substring(0,4);
                            String pret = Integer.toString(result.getInt(8));

                            produse.add(new String[] {id_album, trupa, titlu, casa_disc, nr_mel, gen, an, pret});
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            switch(categorie) {
                case "Filme":
                    try {
                        ResultSet result = statement.executeQuery("SELECT f.id_film, f.titlu, f.actor_pr, f.director, f.durata, f.gen, f.an, f.audienta, p.pret FROM dvdmania.filme f," +
                                " dvdmania.produse p, dvdmania.magazin m WHERE f.id_film = p.id_film AND m.id_mag = p.id_mag AND m.oras='" + magazin + "'");
                        while(result.next()) {
                            String id_film = Integer.toString(result.getInt(1));
                            String titlu = result.getString(2);
                            String act_pr = result.getString(3);
                            String director = result.getString(4);
                            int calcDurata = result.getInt(5);
                            String durata;
                            if(calcDurata%60 >= 10) {
                                durata = new String(calcDurata / 60 + ":" + calcDurata % 60);
                            } else {
                                durata = new String(calcDurata / 60 + ":0" + calcDurata % 60);
                            }
                            String gen = result.getString(6);
                            String an = (String.valueOf(result.getDate(7))).substring(0,4);
                            String audienta = Integer.toString(result.getInt(8));
                            String pret = Integer.toString(result.getInt(9));

                            produse.add(new String[] {id_film, titlu, act_pr, director, durata, gen, an, audienta, pret});
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Jocuri":
                    try {
                        ResultSet result = statement.executeQuery("SELECT j.id_joc, j.titlu, j.an, j.platforma, j.developer, j.publisher, j.gen, j.audienta, p.pret FROM dvdmania.jocuri j," +
                                " dvdmania.produse p, dvdmania.magazin m WHERE j.id_joc = p.id_joc AND m.id_mag = p.id_mag AND m.oras='" + magazin + "'");
                        while(result.next()) {
                            String id_joc = Integer.toString(result.getInt(1));
                            String titlu = result.getString(2);
                            String platforma = result.getString(4);
                            String developer = result.getString(5);
                            String publisher = result.getString(6);
                            String gen = result.getString(7);
                            String an = (String.valueOf(result.getDate(3))).substring(0,4);
                            String audienta = Integer.toString(result.getInt(8));
                            String pret = Integer.toString(result.getInt(9));

                            produse.add(new String[] {id_joc, titlu, platforma, developer, publisher, gen, an, audienta, pret});
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Albume":
                    try {
                        ResultSet result = statement.executeQuery("SELECT a.id_album, a.trupa, a.titlu, a.nr_mel, a.gen, a.casa_disc, a.an, p.pret FROM dvdmania.albume a," +
                                " dvdmania.produse p, dvdmania.magazin m WHERE a.id_album = p.id_album AND m.id_mag = p.id_mag AND m.oras='" + magazin + "'");
                        while(result.next()) {
                            String id_album = Integer.toString(result.getInt(1));
                            String trupa = result.getString(2);
                            String titlu = result.getString(3);
                            String nr_mel = Integer.toString(result.getInt(4));
                            String casa_disc = result.getString(6);
                            String gen = result.getString(5);
                            String an = (String.valueOf(result.getDate(7))).substring(0,4);
                            String pret = Integer.toString(result.getInt(8));

                            produse.add(new String[] {id_album, trupa, titlu, casa_disc, nr_mel, gen, an, pret});
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }


        return produse;
    }

    ArrayList<String[]> getCustomers() {
        ArrayList<String[]> customers = new ArrayList<>();
        try {
            String sql = "SELECT cl.id_cl, cl.nume, cl.pren, cl.adresa, cl.oras, cl.datan, cl.cnp, cl.tel, cl.email, cl.loialitate, c.util, c.parola FROM dvdmania.clienti cl, " +
                    "dvdmania.conturi c WHERE cl.id_cl=c.id_cl";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String id = Integer.toString(rs.getInt(1));
                String nume = rs.getString(2);
                String pren = rs.getString(3);
                String adresa = rs.getString(4);
                String oras = rs.getString(5);
                String datan = String.valueOf(rs.getDate(6));
                String cnp = rs.getString(7);
                String tel = rs.getString(8);
                String email = rs.getString(9);
                String loial = Integer.toString(rs.getInt(10));
                String user = rs.getString(11);
                String pass = rs.getString(12);


                customers.add(new String[] {id, nume, pren, adresa, oras, datan, cnp, tel, email, loial, user, pass});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    ArrayList<String[]> getEmployees() {
        ArrayList<String[]> employees = new ArrayList<>();
        try {
            String sql = "SELECT a.id_angaj, a.nume, a.pren, a.adresa, a.oras, a.datan, a.cnp, a.tel, a.email, a.functie, a.salariu, m.oras, c.util, c.parola FROM dvdmania.angajati a, " +
                    "dvdmania.conturi c, dvdmania.magazin m WHERE a.id_angaj=c.id_angaj AND m.id_mag=a.id_mag AND a.activ='Activ'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String id = Integer.toString(rs.getInt(1));
                String nume = rs.getString(2);
                String pren = rs.getString(3);
                String adresa = rs.getString(4);
                String oras = rs.getString(5);
                String datan = String.valueOf(rs.getDate(6));
                String cnp = rs.getString(7);
                String tel = rs.getString(8);
                String email = rs.getString(9);
                String functie = rs.getString(10);
                String salariu = Integer.toString(rs.getInt(11));
                String adMag = rs.getString(12);
                String util = rs.getString(13);
                String parola = rs.getString(14);

                employees.add(new String[] {id, nume, pren, adresa, oras, datan, cnp, tel, email, functie, salariu, adMag, util, parola});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    ArrayList<String> getOrase() {
        ArrayList<String> orase = new ArrayList<>();
        try {

            String sql = "SELECT oras FROM dvdmania.magazin";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                orase.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orase;
    }
}
