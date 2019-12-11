package main.java.kpiCicle.model;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


import java.util.ArrayList;
import java.util.List;


public class DocumentParser {
    private XWPFDocument document;
    public DocumentParser(XWPFDocument doc) {
        this.document = doc;
    }
    private CycleTemplate cycleTemplate = CycleTemplate.getInstance();
    private  String codeFromBD = "Null";

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

    public String generateProcedure() {
        int condId = 1;
        String result = cycleTemplate.buildHeader() + "\n";
        result = result + cycleTemplate.buildBeginPart(codeFromBD) + "\n\n";
        String activeBlocks = "---------ACTIVITIES----------------\n";

        List<StringBuilder> blocks = parseDocOnBlocks();
        for (StringBuilder block: blocks) {
            String[] blockMass = block.toString().split(";");
            String comment = blockMass[0].split("-")[0].trim();

                for (int i = 1; i < blockMass.length; i++) {
                        String[] subBlock = blockMass[i].split("=");
                        String condName = subBlock[0].trim();
                        String attribValue = subBlock[2].trim();
                        if (!comment.startsWith("Активность")) {
                            result = result + cycleTemplate.buildMainBlock(condId, comment, condName, attribValue, blocks.indexOf(block) + 1) + "\n\n";
                            condId++;
                        } else {
                            activeBlocks = activeBlocks + cycleTemplate.buildActBlock(condId,condName, blocks.indexOf(block) + 1) + "\n\n";
                            condId++;
                        }
                }
        }
        result = result + activeBlocks + "\n";
        result = result + cycleTemplate.buildEndBlock() + "\n";






// System.out.println(condId + ") " + comment + "\n" + condName + "\n" + attribValue + "\n" + (blocks.indexOf(block) + 1));
        return result;
        /*for (StringBuilder st: blocks) {
            System.out.println(st);
            System.out.println("------");
        }*/

        // вот это надо внедрить чуть по выше. сейчас для тестов ок






        // прочитать элементы коллекции blocks. каждая строка это блок, состоящий из нескольких условий.
        // attrib_xxx_value xxx - имеет одну цифру на блок. т.е. каждый элемент блока имеет одинаковые цифры
        // и наоборот счетчик cond_id общий на всю процедуру.
        // заменить нужные значения в шаблонах. собрать процедуру и вывести на экран.
    }

    public String getCodeFromBD() {
        return codeFromBD;
    }

    public void setCodeFromBD(String codeFromBD) {
        this.codeFromBD = codeFromBD;
    }
}
