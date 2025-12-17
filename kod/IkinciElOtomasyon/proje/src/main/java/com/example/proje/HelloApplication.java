package com.example.proje;

import com.example.proje.service.IkinciElOtomasyon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    public static IkinciElOtomasyon ikinciElOtomasyon = new IkinciElOtomasyon();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(loader.load(), 1300, 800);
        stage.setTitle("İkinci El Otomasyon");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
