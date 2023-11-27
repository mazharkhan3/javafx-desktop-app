module com.example.cocktailapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.cocktailapi to javafx.fxml;
    exports com.example.cocktailapi;
    exports com.example.cocktailapi.controllers;
    opens com.example.cocktailapi.controllers to javafx.fxml;
    opens com.example.cocktailapi.models to com.google.gson;
}