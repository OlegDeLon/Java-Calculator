module com.example.eweraz {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens com.example.calc to javafx.fxml;
    exports com.example.calc;
    exports com.example.calc.Model;
    opens com.example.calc.Model to javafx.fxml;
}