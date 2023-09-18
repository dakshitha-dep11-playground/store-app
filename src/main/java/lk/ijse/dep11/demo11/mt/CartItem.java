package lk.ijse.dep11.demo11.mt;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String barCode;
    private String description;
    private int qty;
    private double price;
    private double total;

    public CartItem(String barCode, String description, int qty, double price) {
        this.barCode = barCode;
        this.description = description;
        this.qty = qty;
        this.price = price;
        this.total = price*qty;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }
//
//    public void setTotal(double total) {
//        this.total = total;
//    }

    public void changeQTY(int qty){
        this.qty = this.qty+qty;
        this.total = this.qty*this.price;
    }

    public  void changeQTYandPrice(int qty, double price){
        this.qty = qty;
        this.total = this.qty*price;
    }
}
