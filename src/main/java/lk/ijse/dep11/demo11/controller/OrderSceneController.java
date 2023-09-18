package lk.ijse.dep11.demo11.controller;

import com.sun.scenario.effect.Crop;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dep11.demo11.mt.CartItem;
import lk.ijse.dep11.demo11.mt.PreviousOrders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class OrderSceneController {
    public AnchorPane root;

    public static AnchorPane root2 ;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtStock;
    public TextField txtBuyPrice;
    public TextField txtSellprice;
    public Spinner<Integer> txtQTY;
    public Button btnAdd;
    public  TableView<CartItem> tblCart;
    public static TableView<CartItem> tblCart2;
    public Button btnNewOrder;
    public Label lblTotal;
    public Label lblProfit;

    public ObservableList<CartItem> cartList;
    public Button btnCompleteOrder;
    public Button btnRemove;
    public Button bntEdit;
    public static Button bntEdit2;

    ArrayList<Item> itemList = new ArrayList<>();

    public Item selectedItem;

    public double total;
    public double profit;

    public ArrayList<PreviousOrders> previousOrderList = new ArrayList<>();

    //edit cart item
    public static CartItem selectedCartItem;
    public static CartItem editingCartItem;

    public void initialize(){

        {bntEdit2 = bntEdit;
            root2 = root;
            tblCart2 = tblCart;}


        total = 0;
        profit =0;
        btnAdd.setDisable(true);
        txtQTY.setDisable(true);
        btnAdd.setDefaultButton(true);
        txtQTY.setEditable(true);
        //bntEdit.setDisable(true);
        txtQTY.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,0,1));

        Item item1 = new Item(/*"4796009869767"*/"500", "Promate = book 80pgs", 4, 250, 300);
        Item item2 = new Item(/*"4796009869768"*/"501", "Promate # book 80pgs", 3, 290, 350);

        itemList.add(item1);
        itemList.add(item2);

        txtQTY.valueProperty().addListener(e->{
            int stock = selectedItem.getStock();
            btnAdd.setDisable((txtQTY.getValue()>stock));
        });

        tblCart.getSelectionModel().selectedIndexProperty().addListener(e->{
            //System.out.println(e);
            int selectedIndex = tblCart.getSelectionModel().getSelectedIndex();
            if(selectedIndex>=0){
                bntEdit.setDisable(false);
                selectedCartItem = cartList.get(selectedIndex);
                System.out.println(selectedCartItem);
                System.out.println(selectedCartItem.getBarCode());
            }
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

                setTotalProfit(txtQTY.getValue(), selectedItem.getSellingPrice(), selectedItem.getBuyingPrice());
                clearAndRequestFocus();
                return;
            }
        }

        setTotalProfit(txtQTY.getValue(), selectedItem.getSellingPrice(), selectedItem.getBuyingPrice());
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
        //selectedItem=null;
        txtCode.requestFocus();
    }

    public void setTotalProfit(int qty, double sellingPrice, double buyingPrice){
        double newTotal;
        double newProfit;

        newTotal = qty*sellingPrice;
        newProfit = qty*(sellingPrice-buyingPrice);

        total += newTotal;
        profit += newProfit;

        lblProfit.setText("Order Pofit : Rs. "+String.format("%.2f",profit));
        lblTotal.setText("Order Total : Rs. "+String.format("%.2f",total));
    }

    public void btnCompleteOrderSetOnAction(ActionEvent actionEvent) {
        PreviousOrders previousOrders = new PreviousOrders(cartList,total,profit);
        previousOrderList.add(previousOrders);


        cartList.clear();
        tblCart.refresh();
        total =0;
        profit = 0;
        clearAndRequestFocus();
        lblProfit.setText("Order Pofit : Rs. "+String.format("%.2f",profit));
        lblTotal.setText("Order Total : Rs. "+String.format("%.2f",total));

        System.out.println(previousOrderList.size());
    }

    public void btnRemoveSetOnAction(ActionEvent actionEvent) {
    }

    public void bntEditSetOnAction(ActionEvent actionEvent) throws IOException {
        editingCartItem = new CartItem(selectedItem.getBarCode(), selectedItem.getDescription(), selectedCartItem.getQty(), selectedItem.getSellingPrice());
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/EditCartItemScene.fxml"));

        Stage editCartItemStage = new Stage();
        editCartItemStage.setScene(new Scene(fxmlLoader.load()));

//        EditCartItemSceneController ctrl = fxmlLoader.getController();
        //        ctrl.initData(10);
//        Response response = new Response();


        // editCartItemStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EditCartItemScene.fxml"))));
        editCartItemStage.setTitle("Edit Cart Item");
        editCartItemStage.setResizable(false);
        editCartItemStage.centerOnScreen();
        editCartItemStage.alwaysOnTopProperty();
        editCartItemStage.initModality(Modality.APPLICATION_MODAL);


        editCartItemStage.setOnCloseRequest(e -> {
            int getCatIndex = cartList.indexOf(selectedCartItem);
            cartList.remove(selectedCartItem);
            cartList.add(getCatIndex,editingCartItem);


            tblCart.refresh();
            tblCart.refresh();
            System.out.println("hi");

        });
        editCartItemStage.show();
        tblCart.refresh();

    }

    public  void rootOnMouseClicked(MouseEvent mouseEvent) {

        bntEdit.setDisable(true);
        tblCart.getSelectionModel().clearSelection();

        // : Todo have to click here to reload table and add items, cause dknw how to do it after ediitng

//        System.out.println(selectedCartItem);
//        System.out.println(editingCartItem);
//        int getCatIndex = cartList.indexOf(selectedCartItem);
//        cartList.remove(selectedCartItem);
//        cartList.add(getCatIndex,editingCartItem);


        tblCart.refresh();
//        remakeTotalProfit(selectedCartItem,editingCartItem);
        selectedCartItem = null;

    }

    public static void clearTabelSelection(){
        //selectedCartItem = null;
        bntEdit2.setDisable(true);
        tblCart2.getSelectionModel().clearSelection();
    }

    public  void remakeTotalProfit(CartItem selectedCartItem,CartItem editingCartItem){
        double newTotal;
        double newProfit ;

        double changeOfTotal = editingCartItem.getTotal() -  selectedCartItem.getTotal();
        total += changeOfTotal;

        //:Todo update profit also

        lblProfit.setText("Order Pofit : Rs. "+String.format("%.2f",profit));
        lblTotal.setText("Order Total : Rs. "+String.format("%.2f",total));


    }
}

class Response {
    int qty;
    double price;
}





