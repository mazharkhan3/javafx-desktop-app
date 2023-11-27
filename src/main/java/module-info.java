module com.example.cocktailapi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cocktailapi to javafx.fxml;
    exports com.example.cocktailapi;
}