package com.example.cocktailapi.controllers;

import com.example.cocktailapi.MainApplication;
import com.example.cocktailapi.constants.FilePath;
import com.example.cocktailapi.constants.Messages;
import com.example.cocktailapi.models.DrinkModel;
import com.example.cocktailapi.services.CocktailService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;

public class SearchController {
    private final CocktailService cocktailService = new CocktailService();

    @FXML
    private VBox mainContainer;
    @FXML
    private TextField searchField;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private ImageView selectedDrinkImage;
    @FXML
    private Text errorMessage;
    @FXML
    private ListView<DrinkModel> drinksListView;

    public void initialize() {
        // Set the progress indicator default size
        progressIndicator.setMaxSize(90, 90);

        // show default image
        loadDrinkImage(null);

        // Selection listener for ListView
        drinksListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadDrinkImage(newSelection.getStrDrinkThumb());
            }
        });
    }

    // Load and set the drink image on selection of any drink
    private void loadDrinkImage(String drinkImage) {
       if(drinkImage != null){
           Image image = new Image(drinkImage);
           selectedDrinkImage.setImage(image);
       } else {
           URL inputStream = MainApplication.class.getResource(FilePath.WINDOW_TITLE_IMG);
           if (inputStream != null) {
               Image icon = new Image(inputStream.toString());
               selectedDrinkImage.setImage(icon);
           } else {
               System.err.println("Icon resource not found: " + FilePath.WINDOW_TITLE_IMG);
           }
       }
    }

    @FXML
    protected void onSearchButtonClick() {
        progressIndicator.setVisible(true);
        drinksListView.getItems().clear();

        new Thread(() -> {
            try {
                var query = searchField.getText();
                var cocktails = cocktailService.searchCocktailByName(query);

                Platform.runLater(() -> {
                    if (cocktails.getDrinks() == null || cocktails.getDrinks().isEmpty()) {
                        // Display message if no drinks are found
                        errorMessage.setText(Messages.NO_DRINKS_AVAILABLE + ": " + query);
                        errorMessage.setVisible(true);
                        loadDrinkImage(null);
                    } else {
                        // Add the cocktails to the ListView
                        errorMessage.setVisible(false);
                        drinksListView.getItems().addAll(cocktails.getDrinks());
                    }
                    progressIndicator.setVisible(false);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    progressIndicator.setVisible(false);
                    errorMessage.setText(Messages.ERROR_OCCURRED);
                    errorMessage.setVisible(true);
                });
            }
        }).start();
    }

    public void onDetailsButtonClick(ActionEvent event) {
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
                errorMessage.setText(Messages.ERROR_OCCURRED);
                errorMessage.setVisible(true);
            }
        } else {
            errorMessage.setText(Messages.NO_DRINK_SELECTED);
            errorMessage.setVisible(true);
        }
    }
}