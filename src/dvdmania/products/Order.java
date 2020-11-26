package dvdmania.products;

import dvdmania.management.Client;
import dvdmania.management.Employee;
import dvdmania.management.Stock;
import dvdmania.management.Store;

import java.time.LocalDate;

public class Order {
    Stock stock;
    Client client;
    Employee employee;
    Store store;
    LocalDate borrowDate;
    LocalDate returnDate;
    double price;

    public Order() {
    }

    public Order(Stock stock, Client client, Employee employee, Store store, LocalDate borrowDate, LocalDate returnDate, double price) {
        this.stock = stock;
        this.client = client;
        this.employee = employee;
        this.store = store;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.price = price;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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
