package com.example.cocktailapi.services;

import com.example.cocktailapi.helpers.ApiClient;
import com.example.cocktailapi.models.CocktailListModel;
import com.google.gson.Gson;

public class CocktailService {
    public CocktailListModel searchCocktailByName(String name) throws Exception {
        ApiClient apiClient = new ApiClient();
        String json = apiClient.getJsonFromApi("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + name);

        Gson gson = new Gson();

        return gson.fromJson(json, CocktailListModel.class);
    }
}
