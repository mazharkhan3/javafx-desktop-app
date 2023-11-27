package com.example.cocktailapi.controllers;

import com.example.cocktailapi.models.DrinkModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DrinksListController {
    @FXML private ImageView imageView;
    @FXML private Label titleLabel;
    @FXML private Label tagLabel;
    @FXML private Label categoryLabel;
    @FXML private Label instructionLabel;

    public void setData(DrinkModel drink) {
        titleLabel.setText(drink.getStrDrink());
        tagLabel.setText(drink.getStrTags());
        categoryLabel.setText(drink.getStrCategory());
        instructionLabel.setText(drink.getStrInstructions());
        Image image = new Image(drink.getStrDrinkThumb(), true); // true for background loading
        imageView.setImage(image);
    }
}
