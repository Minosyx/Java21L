package com.project.converter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ConverterApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        Platform.exit();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main-scene.fxml"));
        Scene scene = new Scene(root, 804, 700);
        stage.setTitle("Konwerter walut");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/dollar.png")));
        stage.show();
    }
}
