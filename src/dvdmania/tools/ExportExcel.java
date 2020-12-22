package dvdmania.tools;

import dvdmania.management.Stock;
import dvdmania.management.Store;
import dvdmania.products.Album;
import dvdmania.products.Game;
import dvdmania.products.Movie;
import dvdmania.products.Order;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExportExcel {

    private static ExportExcel instance = null;

    private ExportExcel() {
    }

    public static ExportExcel getInstance() {
        if (instance == null) {
            instance = new ExportExcel();
        }

        return instance;
    }

    public void writeStockToExcel(ArrayList<Stock> stocks, Date date) {
        //Create Blank workbook
        XSSFWorkbook workbook = XSSFWorkbookFactory.createWorkbook();

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet("Stock " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900));

        //Create row object
        XSSFRow row;

        //create data that needs to be written
        Map<String, Object[]> info = new TreeMap<String, Object[]>();
        info.put("1", new Object[]{"ID", "Film", "Joc", "Album", "Magazin", "Cantitate", "Pret"});
        int i = 2;
        for (Stock stock : stocks) {
            Movie movie = null;
            Game game = null;
            Album album = null;
            Store store = null;
            if (stock.getMovie() != null) {
                movie = stock.getMovie();
            }
            if (stock.getGame() != null) {
                game = stock.getGame();
            }
            if (stock.getAlbum() != null) {
                album = stock.getAlbum();
            }
            if (stock.getStore() != null) {
                store = stock.getStore();
            }

            String movieStr = (movie == null) ? "" : movie.getTitle();
            String gameStr = (game == null) ? "" : game.getTitle();
            String albumStr = (album == null) ? "" : album.getArtist() + " - " + album.getTitle();
            String storeStr = (store == null) ? "" : store.getOras() + " " + store.getAdresa();
            info.put(i + "", new Object[]{stock.getIdProduct() + "", movieStr, gameStr, albumStr, storeStr, stock.getQuantity() + "", stock.getPrice() + ""});
            i++;
        }

        //iterate data and write it to sheet
        Set<String> keyId = info.keySet();
        int rowId = 0;

        for (String key : keyId) {
            row = spreadsheet.createRow(rowId++);
            Object[] objectArr = info.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }

        //Create file system using specific name
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("Stock " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900) + ".xlsx"));
            workbook.write(out);
            System.out.println("Created Stock " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900) + ".xlsx successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeOrdersToExcel(ArrayList<Order> orders) {

        Date date = new Date();

        //Create Blank workbook
        XSSFWorkbook workbook = XSSFWorkbookFactory.createWorkbook();

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet("Orders " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900));

        //Create row object
        XSSFRow row;

        //create data that needs to be written
        Map<String, Object[]> info = new TreeMap<String, Object[]>();
        info.put("1", new Object[]{"Produs", "Client", "Adresa client", "Angajat", "Magazin", "Data imprumutului", "Data returului", "Pret"});
        int i = 2;
        for (Order order : orders) {
            Stock stock = order.getStock();
            Store store = null;
            String prodStr = null;
            if (stock.getMovie() != null) {
                Movie movie = stock.getMovie();
                prodStr = movie.getTitle();
            }
            if (stock.getGame() != null) {
                Game game = stock.getGame();
                prodStr = game.getTitle();
            }
            if (stock.getAlbum() != null) {
                Album album = stock.getAlbum();
                prodStr = album.getArtist() + " - " + album.getTitle();
            }
            if (stock.getStore() != null) {
                store = stock.getStore();
            }

            String storeStr = (store == null) ? "" : store.getOras() + " " + store.getAdresa();
            String clientStr = order.getClient().getNume() + " " + order.getClient().getPrenume();
            String clientAdrStr = order.getClient().getAdresa();
            String empStr = order.getEmployee().getNume() + " " + order.getEmployee().getPrenume();
            String orderDate = order.getBorrowDate().toString();
            String retDate = null;
            if (order.getReturnDate() != null) {
                retDate = order.getReturnDate().toString();
            } else {
                retDate = "";
            }
            info.put(i + "", new Object[]{prodStr, clientStr, clientAdrStr, empStr, storeStr, orderDate, retDate, order.getPrice() + ""});
            i++;
        }

        //iterate data and write it to sheet
        Set<String> keyId = info.keySet();
        int rowId = 0;

        for (String key : keyId) {
            row = spreadsheet.createRow(rowId++);
            Object[] objectArr = info.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }

        //Create file system using specific name
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("Orders " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900) + ".xlsx"));
            workbook.write(out);
            System.out.println("Created Orders " + date.getDate() + "." + date.getMonth() + "." + (date.getYear() + 1900) + ".xlsx successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
