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

        if (!file.equals(null)) {
            try {
                cycleTemplate.setNumberOfCycle(Integer.valueOf(file.getName().substring(12, 14)));
                document = new XWPFDocument(OPCPackage.open(file));
            } catch (IOException e) {
                e.printStackTrace();

            } catch (InvalidFormatException e) {
                AlertGenerator.showAlert("При открытии файла что то пошло не так :(");

            } catch (NumberFormatException e) {
                AlertGenerator.showAlert("Произошла ошибка при считывании имени файла\n " +
                        "Убедитесь что цифры находится между 13 и 15 символами\n " +
                        "например: Reporting C'12.docx");

            } catch (Exception e) {
                AlertGenerator.showAlert("Произошла ошибка при загрузке файла :(");
            }
        }
        return document;
    }
}

//TODO при возникающих ошибках вывести сообщение пользователю +
//TODO сделать проверку если парсинг имени файла завершиться неудачей, вывести сообщение с подсказкой +