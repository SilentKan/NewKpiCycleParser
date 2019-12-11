package main.java.kpiCicle.model;

import javafx.scene.control.Alert;

public class AlertGenerator {

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oops...");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


