package main.java.kpiCicle.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    void initialize() {
        uploadFile.setOnAction(event -> {
            //передаем полученный документ в класс парсера
            DocumentParser documentParser = new DocumentParser(documentExtractor.getDocFromFs());
            if (!textField.getText().isEmpty()) {
                documentParser.setCodeFromBD(textField.getText());
            }
            // далее в documentParser преобразуем данные в обьекты/блоки процедуры CycleElement
            System.out.println(documentParser.generateProcedure());
            textField.clear();
        });
    }

}
