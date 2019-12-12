package main.java.kpiCycle.model;

import javafx.stage.FileChooser;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentExtractor {
    private CycleTemplate cycleTemplate = CycleTemplate.getInstance();

    public XWPFDocument getDocFromFs() {
        File file;
        XWPFDocument document = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("docx", "*.docx"));
        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                Pattern pattern = Pattern.compile("\\d{2}");
                Matcher matcher = pattern.matcher(file.getName());
                int cycleNum = 0;
                while (matcher.find()) {cycleNum = Integer.valueOf(file.getName().substring(matcher.start(), matcher.end()));}
                cycleTemplate.setNumberOfCycle(cycleNum);
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
