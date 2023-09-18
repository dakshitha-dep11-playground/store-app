package lk.ijse.dep11.demo11.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.dep11.demo11.mt.CartItem;

public class EditCartItemSceneController {

    public AnchorPane rootEditCartItem;
    public TextField txtBarCode;
    public TextField txtQTY;
    public TextField txtSellingPrice;
    public Button btnApply;
    public Button btnCancel;
    CartItem editingCartItem;

    int qty;
    public void initialize(){

        btnApply.setDisable(true);

        editingCartItem = OrderSceneController.editingCartItem;

        txtBarCode.setText(editingCartItem.getBarCode());
        txtQTY.setText(String.valueOf(editingCartItem.getQty()));
        txtSellingPrice.setText(String.valueOf(editingCartItem.getPrice()));
        //:Todo add spinner to txtQTy

        txtQTY.onInputMethodTextChangedProperty().addListener(e->{
            if(editingCartItem.getQty() == Integer.valueOf(txtQTY.getText())){
            btnApply.setDisable(false);
            //: Todo this apply button disable code is not working properly
        }

        });

    }

    public void initData(int qty){
//        this.qty = qty;
//        txtQTY.setText(qty + "");
    }

    public void btnApplyOnAction(ActionEvent actionEvent) {

        //:Todo spiner or txtQTY value cannot be exceed stock, that may gives an error

        int qty = Integer.valueOf(txtQTY.getText());
        double sellingPrice = Double.valueOf(txtSellingPrice.getText());

        editingCartItem.changeQTYandPrice(qty,sellingPrice);

        System.out.println(editingCartItem.getTotal());

        Stage editCartItmStage = (Stage) rootEditCartItem.getScene().getWindow();
        WindowEvent closeRequest = new WindowEvent(editCartItmStage, WindowEvent.WINDOW_CLOSE_REQUEST);

        editCartItmStage.close();
        editCartItmStage.fireEvent(closeRequest);
//        OrderSceneController.clearTabelSelection();





    }

    public void btnCancelOnAction(ActionEvent actionEvent) {

    }
}
