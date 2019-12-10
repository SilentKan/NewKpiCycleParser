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

    // в получении параграфов нет нужды
   /* public List<String> getParagraph() {
        List<String> paragraph = new ArrayList<>();
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph p : paragraphs) {
            if ((paragraph.isEmpty() && !p.getText().startsWith("Нелегальный продукт в точке")) || p.getText().length() == 0 || p.getText().length() == 1) {
                continue;
            } else {
                paragraph.add(p.getText());
            }
        }
        return paragraph;*/
   // }

    public List<StringBuilder> getAllDocument() {
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
        blocks.add(block);
        System.out.println(blocks.size());
        for (StringBuilder st: blocks) {
            System.out.println(st);
            System.out.println("-------");
        }
        return blocks;
    }

    public List<CycleElement> parse() {
        List<CycleElement> cycleElements = new ArrayList<>();

        return cycleElements;
    }
}
