package com.example.cocktailapi;

import com.example.cocktailapi.constants.FilePath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(FilePath.SEARCH_VIEW));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        URL inputStream = MainApplication.class.getResource(FilePath.WINDOW_TITLE_IMG);
        if (inputStream != null) {
            Image icon = new Image(inputStream.toString());
            stage.getIcons().add(icon);
        } else {
            System.err.println("Icon resource not found: " + FilePath.WINDOW_TITLE_IMG);
        }
        stage.setTitle("Search Cocktails!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}