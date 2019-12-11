package main.java.kpiCicle.model;

import javafx.stage.FileChooser;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.IOException;


// извлекает документ из файловой системы
public class DocumentExtractor {
    private CycleTemplate cycleTemplate = CycleTemplate.getInstance();

    public XWPFDocument getDocFromFs() {
        File file;
        XWPFDocument document = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("docx", "*.docx"));
        file = fileChooser.showOpenDialog(null);
        // сделать проверку если парсинг имени файла завершиться неудачей, вывести сообщение с подсказкой
        cycleTemplate.setNumberOfCycle(Integer.valueOf(file.getName().substring(12, 14)));
        if (!file.equals(null)) {
            try {
                document = new XWPFDocument(OPCPackage.open(file));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                // вывести сообщение об ошибке в представлении, если поймали исключение
                e.printStackTrace();
            }
        }
        return document;
    }
}

//TODO при возникающих ошибках вывести сообщение пользователю
//TODO сделать проверку если парсинг имени файла завершиться неудачей, вывести сообщение с подсказкой