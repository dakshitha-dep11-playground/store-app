package lk.ijse.dep11.demo11.controller;

class Item {
    private String barCode;
    private String description;
    private int stock;
    private double buyingPrice;
    private double sellingPrice;


    public Item(String barCode, String description, int stock, double buyingPrice, double sellingPrice) {
        this.barCode = barCode;
        this.description = description;
        this.stock = stock;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    double getProfit() {
        return (sellingPrice - buyingPrice);
    }
}
