package main.java.kpiCycle.model;

import javafx.scene.control.Alert;

public class AlertGenerator {

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oops...");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void startMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("mini FAQ");
        alert.setHeaderText(null);
        alert.setContentText("Перед началом убедись, что все параграфы (кроме Нелегальный... и Активность) начинаются с *\n" +
                "Приготовь код из запроса, который нужно будет вставить в поле Код из БД\n" +
                "После генерации проверь корректность полученной функции\n" +
                "Дополнительная информация описана в ReadMe файле");
        alert.showAndWait();
    }
}


