package main.java.kpiCicle.model;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DocumentParser {
    private XWPFDocument document;
    public DocumentParser(XWPFDocument doc) {
        this.document = doc;
    }

    // парсим документ на блоки
    public List<StringBuilder> parseDocOnBlocks() {
        List<String> lines = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        List<StringBuilder> blocks = new ArrayList<>();
        XWPFWordExtractor fileExtract = new XWPFWordExtractor(document);
        stringBuilder.append(fileExtract.getText());
        String[] docToLine = stringBuilder.toString().split("\n");
        for (String st: docToLine) {
            if (st.length() == 0 || st.length() == 1 || st.contains("Visited Route Outlets")) {
                continue;
            }
            lines.add(st);
        }
        int index = lines.indexOf("GrCoSp");
        lines  = lines.subList(index + 1, lines.size());
        StringBuilder block = new StringBuilder();
        for (String st: lines) {
            if(st.startsWith("*") || st.startsWith("Активность")) {

                blocks.add(block);
                block = new StringBuilder(st + "; ");
            } else {
                block.append(st + ";");
            }
        }
        // добавляем последний блок в коллекцию
        blocks.add(block);
        return blocks;
    }

    public List<CycleElement> generateProcedure() {
        List<CycleElement> cycleElements = new ArrayList<>();
        List<StringBuilder> blocks = parseDocOnBlocks();
        for (StringBuilder st: blocks) {
            System.out.println(st);
            System.out.println("------");
        }
        // прочитать элементы коллекции blocks. каждая строка это блок, состоящий из нескольких условий.
        // attrib_xxx_value xxx - имеет одну цифру на блок. т.е. каждый элемент блока имеет одинаковые цифры
        // и наоборот счетчик cond_id общий на всю процедуру.
        // заменить нужные значения в шаблонах. собрать процедуру и вывести на экран.

        return cycleElements;
    }
}
