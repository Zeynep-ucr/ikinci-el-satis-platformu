module com.example.proje {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.example.proje to javafx.fxml;
    exports com.example.proje;

    exports com.example.proje.controller;
    opens com.example.proje.controller to javafx.fxml;

    exports com.example.proje.model;
    opens com.example.proje.model to javafx.fxml;

    exports com.example.proje.service;
    opens com.example.proje.service to javafx.fxml;
}
