module com.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.projet to javafx.graphics, javafx.fxml;
    opens com.projet.controllers to javafx.graphics, javafx.fxml;

    exports com.projet;
}
