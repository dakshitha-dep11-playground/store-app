package lk.ijse.dep11.demo11.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class OrderSceneController {
    public AnchorPane root;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtStock;
    public TextField txtBuyPrice;
    public TextField txtSellprice;
    public Spinner<Integer> txtQTY;
    public Button btnAdd;
    public TableView<CartItem> tblCart;
    public Button btnNewOrder;
    public Label lblTotal;
    public Label lblProfit;

    public ObservableList<CartItem> cartList;

    ArrayList<Item> itemList = new ArrayList<>();

    public Item selectedItem;

    public void initialize(){
        btnAdd.setDisable(true);
        txtQTY.setDisable(true);
        btnAdd.setDefaultButton(true);
        txtQTY.setEditable(true);
        txtQTY.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,0,1));

        Item item1 = new Item(/*"4796009869767"*/"500", "Promate = book 80pgs", 4, 250, 300);
        Item item2 = new Item(/*"4796009869768"*/"501", "Promate # book 80pgs", 3, 290, 350);

        itemList.add(item1);
        itemList.add(item2);

        txtQTY.valueProperty().addListener(e->{
            int stock = selectedItem.getStock();
            btnAdd.setDisable((txtQTY.getValue()>stock));
        });


        cartList = tblCart.getItems();
        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("barCode"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));


    }

    public void btnNewOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        CartItem addToCartItem = new CartItem(selectedItem.getBarCode(), selectedItem.getDescription(), txtQTY.getValue(), selectedItem.getSellingPrice());
        for (CartItem cartItem :
                cartList) {
            if(cartItem.getBarCode().equals(addToCartItem.getBarCode())){

                cartItem.changeQTY(txtQTY.getValue());
                selectedItem.setStock(selectedItem.getStock()-txtQTY.getValue());
                tblCart.refresh();
                clearAndRequestFocus();
                return;
            }
        }

        cartList.add(addToCartItem);
        selectedItem.setStock(selectedItem.getStock()-txtQTY.getValue());
       clearAndRequestFocus();
        System.out.println();
    }


    public void txtCodeSetOnAction(ActionEvent actionEvent) {
        String inoutCode = txtCode.getText().strip();

        //Part that I have added to clear the existing txt fields when txt coe is blank
        if(inoutCode.isBlank()){
            txtDescription.clear();
            txtStock.clear();
            txtBuyPrice.clear();
            txtSellprice.clear();
            btnAdd.setDisable(true);
            txtQTY.setDisable(true);
            selectedItem = null;
            return;
        }

        for (Item item :
                itemList) {
            if(item.getBarCode().equals(inoutCode)){
                txtDescription.setText(item.getDescription());
                txtStock.setText(item.getStock()+"");
                txtBuyPrice.setText(item.getBuyingPrice()+"");
                txtSellprice.setText(item.getSellingPrice()+"");
                txtQTY.requestFocus();

                txtQTY.setDisable(item.getStock()==0);
                btnAdd.setDisable(txtQTY.isDisable());

                if(item.getStock()==0){
                    txtCode.selectAll();
                    txtCode.requestFocus();
                }


                selectedItem = item;
                txtQTY.requestFocus();
                return;
            }
        }
        new Alert(Alert.AlertType.ERROR,"Item does not exist.!").show();
    }


    public void clearAndRequestFocus(){
        txtCode.clear();
        txtDescription.clear();
        txtStock.clear();
        txtBuyPrice.clear();
        txtSellprice.clear();
        btnAdd.setDisable(true);
        txtQTY.setDisable(true);
        txtQTY.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,0,1));
        selectedItem=null;
        txtCode.requestFocus();
    }
}







class Item{
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

    double getProfit(){
        return (sellingPrice-buyingPrice);
    }
}
