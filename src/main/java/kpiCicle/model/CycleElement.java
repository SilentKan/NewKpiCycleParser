package main.java.kpiCicle.model;

// обьект содержит информацию об элементах и счетчики
public class CycleElement {

    private int counter = 0;

    public CycleElement() {
        this.counter++;
    }

    private String cond_name1;
    private String attrib_xxx_value;

    public String getCond_name1() {
        return cond_name1;
    }

    public void setCond_name1(String cond_name1) {
        this.cond_name1 = cond_name1;
    }

    public String getAttrib_xxx_value() {
        return attrib_xxx_value;
    }

    public void setAttrib_xxx_value(String attrib_xxx_value) {
        this.attrib_xxx_value = attrib_xxx_value;
    }
}
