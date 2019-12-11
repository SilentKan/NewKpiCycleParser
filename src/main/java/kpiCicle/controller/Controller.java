package main.java.kpiCicle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import main.java.kpiCicle.model.DocumentExtractor;
import main.java.kpiCicle.model.DocumentParser;

public class Controller {
    DocumentExtractor documentExtractor = new DocumentExtractor();

    @FXML
    private Button uploadFile;

    @FXML
    private TextField textField;

    @FXML
    private TextArea resultTextArea;

    @FXML
    void initialize() {
        uploadFile.setOnAction(event -> {
            // передаем полученный документ в класс парсера
            DocumentParser documentParser = new DocumentParser(documentExtractor.getDocFromFs());
            // если поле пустое вывести предупреждение.
            if (!textField.getText().isEmpty()) {
                documentParser.setCodeFromBD(textField.getText());
            }
            resultTextArea.appendText(documentParser.generateProcedure());
            textField.clear();
        });
    }
}

//TODO если поле пустое вывести предупреждение.