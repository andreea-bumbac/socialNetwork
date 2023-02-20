module com.example.appjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.appjavafx to javafx.fxml;
    exports com.example.appjavafx;
    exports socialnetwork.domain;
    exports socialnetwork.service;
}