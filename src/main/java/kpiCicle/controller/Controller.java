package main.java.kpiCicle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import main.java.kpiCicle.model.AlertGenerator;
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
            if (!textField.getText().isEmpty()) {
                documentParser.setCodeFromBD(textField.getText());
                resultTextArea.appendText(documentParser.generateProcedure());
                textField.clear();
            } else {
                AlertGenerator.showAlert("Ты забыл ввести код в поле\n " +
                        "SELECT ID_ASSESS_TEMPLATE from LDWH.D_ASSESS_TEMPLATE where SIEBEL_ASSESS_TMPL_ID = '%TEMPLATE_ID%'");
            }
        });
    }
}

//TODO если поле пустое вывести предупреждение. +