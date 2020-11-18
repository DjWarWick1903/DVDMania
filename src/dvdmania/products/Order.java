package dvdmania.products;

import java.time.LocalDate;

public class Order {
    int idAsset;
    int idClient;
    int idEmployee;
    int idStore;
    LocalDate borrowDate;
    LocalDate returnDate;
    double price;

    public Order(int idAsset, int idClient, int idEmployee, int idStore, LocalDate borrowDate, LocalDate returnDate, double price) {
        this.idAsset = idAsset;
        this.idClient = idClient;
        this.idEmployee = idEmployee;
        this.idStore = idStore;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.price = price;
    }

    public Order(int idAsset, int idClient, int idEmployee, int idStore, LocalDate borrowDate, double price) {
        this.idAsset = idAsset;
        this.idClient = idClient;
        this.idEmployee = idEmployee;
        this.idStore = idStore;
        this.borrowDate = borrowDate;
        this.price = price;
    }

    public int getIdAsset() {
        return idAsset;
    }

    public void setIdAsset(int idAsset) {
        this.idAsset = idAsset;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdStore() {
        return idStore;
    }

    public void setIdStore(int idStore) {
        this.idStore = idStore;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
