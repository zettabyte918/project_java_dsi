module com.projet {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires okhttp3;
    requires com.google.gson;

    opens com.projet to javafx.graphics, javafx.fxml;
    opens com.projet.controllers to javafx.graphics, javafx.fxml;
    opens com.projet.utils to com.google.gson;
    opens com.projet.utils.OrangeSMS.responses to com.google.gson;

    exports com.projet;
    exports com.projet.models;
}
