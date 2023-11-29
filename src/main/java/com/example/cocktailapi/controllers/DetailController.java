package com.example.cocktailapi.controllers;

import com.example.cocktailapi.MainApplication;
import com.example.cocktailapi.constants.FilePath;
import com.example.cocktailapi.models.DrinkModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;

public class DetailController {
    @FXML
    private ImageView drinkImageView;
    @FXML
    private Text titleLabel;
    @FXML
    private Text instructionLabel;
    @FXML
    private Text categoryLabel;
    @FXML
    private Text tagLabel;
    private DrinkModel drink;

    public void setDrink(DrinkModel drink) {
        this.drink = drink;
        drinkImageView.setImage(new Image(drink.getStrDrinkThumb()));
        titleLabel.setText(drink.getStrDrink());
        instructionLabel.setText(drink.getStrInstructions());
        categoryLabel.setText(drink.getStrCategory());
        tagLabel.setText(drink.getStrTags());
    }

    @FXML
    private void onBackButtonClick() {
        // Load the home view FXML file again
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(FilePath.SEARCH_VIEW));
            Parent homeView = loader.load();
            drinkImageView.getScene().setRoot(homeView);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
}
