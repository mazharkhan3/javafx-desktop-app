package com.example.cocktailapi.controllers;

import com.example.cocktailapi.MainApplication;
import com.example.cocktailapi.helpers.ApiClient;
import com.example.cocktailapi.models.CocktailListModel;
import com.example.cocktailapi.models.DrinkModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.google.gson.Gson;
import javafx.scene.text.Text;

import java.io.IOException;

public class SearchController {
    public TextField searchField;
    public Button searchButton;
    public VBox drinksContainer;
    public ProgressIndicator progressIndicator;
    @FXML private Text noDrinksMessage;

    @FXML
    protected void onSearchButtonClick() {
        progressIndicator.setVisible(true);

        new Thread(() -> {
            try {
                var query = searchField.getText();
                ApiClient apiClient = new ApiClient();
                String json = apiClient.getJsonFromApi("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + query);

                Gson gson = new Gson();
                CocktailListModel cocktailListModel = gson.fromJson(json, CocktailListModel.class);

                Platform.runLater(() -> {
                    drinksContainer.getChildren().clear();
                    if (cocktailListModel.getDrinks() == null || cocktailListModel.getDrinks().isEmpty()) {
                        // Display message if no drinks are found
                        noDrinksMessage.setText("No drinks available against the searched query: " + query);
                        noDrinksMessage.setVisible(true);
                    } else {
                        noDrinksMessage.setVisible(false);
                        for (DrinkModel drink : cocktailListModel.getDrinks()) {
                            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("drink-list-view.fxml"));
                            HBox card = null;
                            try {
                                card = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            DrinksListController controller = loader.getController();
                            controller.setData(drink);
                            drinksContainer.getChildren().add(card);
                        }
                    }
                    progressIndicator.setVisible(false);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    progressIndicator.setVisible(false);
                    noDrinksMessage.setText("An error occurred during the search.");
                    noDrinksMessage.setVisible(true);
                });
            }
        }).start();
    }
}