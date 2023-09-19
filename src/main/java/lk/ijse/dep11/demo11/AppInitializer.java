package lk.ijse.dep11.demo11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = getClass().getResource("/view/TestScene.fxml");
        System.out.println(url);
        AnchorPane root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Order");
        primaryStage.centerOnScreen();
        primaryStage.alwaysOnTopProperty();
        primaryStage.show();


    }
}
