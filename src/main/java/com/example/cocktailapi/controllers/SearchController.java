package com.example.cocktailapi.controllers;

import com.example.cocktailapi.MainApplication;
import com.example.cocktailapi.constants.FilePath;
import com.example.cocktailapi.helpers.ApiClient;
import com.example.cocktailapi.models.CocktailListModel;
import com.example.cocktailapi.models.DrinkModel;
import com.example.cocktailapi.services.CocktailService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.google.gson.Gson;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchController {
    private final CocktailService cocktailService = new CocktailService();

    public VBox mainContainer;
    public TextField searchField;
    public Button searchButton;
    public ProgressIndicator progressIndicator;
    public ImageView selectedDrinkImage;

    @FXML
    private Text noDrinksMessage;
    @FXML
    private ListView<DrinkModel> drinksListView;

    public void initialize() {
        // Selection listener for ListView
        drinksListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadDrinkImage(newSelection.getStrDrinkThumb());
            }
        });
    }

    // Load and set the drink image on selection of any drink
    private void loadDrinkImage(String drinkImage) {
        Image image = new Image(drinkImage);
        selectedDrinkImage.setImage(image);
    }

    @FXML
    protected void onSearchButtonClick() {
        selectedDrinkImage.setImage(null);
        progressIndicator.setVisible(true);
        drinksListView.getItems().clear();

        new Thread(() -> {
            try {
                var query = searchField.getText();
                var cocktails = cocktailService.searchCocktailByName(query);

                Platform.runLater(() -> {
                    if (cocktails.getDrinks() == null || cocktails.getDrinks().isEmpty()) {
                        // Display message if no drinks are found
                        noDrinksMessage.setText("No drinks available against the searched query: " + query);
                        noDrinksMessage.setVisible(true);
                    } else {
                        // Add the cocktails to the ListView
                        noDrinksMessage.setVisible(false);
                        drinksListView.getItems().addAll(cocktails.getDrinks());
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

    public void onDetailsButtonClick(ActionEvent event) {
        // Assuming drinksListView is your ListView
        DrinkModel selectedDrink = drinksListView.getSelectionModel().getSelectedItem();
        if (selectedDrink != null) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(FilePath.DETAIL_VIEW));
                Parent detailView = loader.load();
                DetailController controller = loader.getController();
                controller.setDrink(selectedDrink);

                mainContainer.getChildren().setAll(detailView); // Replace the content of the root element
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions
            }
        }
    }
}