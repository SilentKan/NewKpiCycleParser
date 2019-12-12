package main.java.kpiCycle.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import main.java.kpiCycle.model.AlertGenerator;
import main.java.kpiCycle.model.DocumentExtractor;
import main.java.kpiCycle.model.DocumentParser;

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
        AlertGenerator.startMessage();
        uploadFile.setOnAction(event -> {
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
