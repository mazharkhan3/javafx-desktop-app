package com.example.cocktailapi.controllers;

import com.example.cocktailapi.helpers.ApiClient;
import com.example.cocktailapi.models.CocktailListModel;
import com.example.cocktailapi.models.DrinkModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import com.google.gson.Gson;

public class SearchController {
    public TextField searchField;
    public Button searchButton;
    public Text showText;
    public VBox drinksContainer;

    @FXML
    protected void onSearchButtonClick() throws Exception {
        var query = searchField.getText();

        ApiClient apiClient = new ApiClient();
        String json = apiClient.getJsonFromApi("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + query);

        Gson gson = new Gson();
        CocktailListModel cocktailListModel = gson.fromJson(json, CocktailListModel.class);

        drinksContainer.getChildren().clear(); // Clear existing cards
        for (DrinkModel drink : cocktailListModel.getDrinks()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/card_layout.fxml"));
            VBox card = loader.load();
            DrinksListController controller = loader.getController();
            controller.setData(drink);
            drinksContainer.getChildren().add(card);
        }

    }
}