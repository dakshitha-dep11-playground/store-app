package lk.ijse.dep11.demo11.mt;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class PreviousOrders {

    private String custMobileNumber;
    private LocalDateTime locatTime;
    private ObservableList<CartItem> cartItemList;
    private double total;
    private double profit;

    public PreviousOrders(String custMobileNumber, ObservableList<CartItem> cartItemList, double total, double profit) {
        this.custMobileNumber = custMobileNumber;
        this.cartItemList = cartItemList;
        this.total = total;
        this.profit = profit;
        this.locatTime = LocalDateTime.now();
    }

    public PreviousOrders(ObservableList<CartItem> cartItemList, double total, double profit) {
        this.custMobileNumber = "~Not given~";
        this.cartItemList = cartItemList;
        this.total = total;
        this.profit = profit;
        this.locatTime = LocalDateTime.now();
    }

    public String getCustMobileNumber() {
        return custMobileNumber;
    }

    public void setCustMobileNumber(String custMobileNumber) {
        this.custMobileNumber = custMobileNumber;
    }

    public LocalDateTime getLocatTime() {
        return locatTime;
    }

    public void setLocatTime(LocalDateTime locatTime) {
        this.locatTime = locatTime;
    }

    public ObservableList<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(ObservableList<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
