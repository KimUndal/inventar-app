module com.undal.inventarapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.undal.inventarapp.client;
    opens com.undal.inventarapp.client to javafx.fxml;
}