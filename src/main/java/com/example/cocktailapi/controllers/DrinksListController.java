package com.example.cocktailapi.controllers;

import com.example.cocktailapi.models.DrinkModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class DrinksListController {
    @FXML private ImageView imageView;
    @FXML private Label titleLabel;
    @FXML private Label tagLabel;
    @FXML private Label categoryLabel;
    @FXML private Label instructionLabel;

    public void setData(DrinkModel drink) {

        var title = drink.getStrDrink() != null ? drink.getStrDrink() : "N/A";
        var tags = drink.getStrTags() != null ? "Tags: " + drink.getStrTags() : "Tags: N/A";
        var category = drink.getStrCategory() != null ? "Category: " + drink.getStrCategory() : "Category: N/A";
        var instructions = drink.getStrInstructions() != null ? drink.getStrInstructions() : "N/A";
        var cocktailImage = drink.getStrDrinkThumb();

        titleLabel.setText(title);
        tagLabel.setText(tags);
        categoryLabel.setText(category);
        instructionLabel.setText(instructions);

        Image image = new Image(cocktailImage, true); // true for background loading
        imageView.setImage(image);
    }
}
