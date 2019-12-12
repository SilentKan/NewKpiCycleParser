package main.java.kpiCycle.model;

import java.time.LocalDate;

public class CycleTemplate {

    private static CycleTemplate cycleTemplate;
    private CycleTemplate() {}
    public static CycleTemplate getInstance() {
        if (cycleTemplate == null) {
            cycleTemplate = new CycleTemplate();
        }
        return cycleTemplate;
    }

    private static int curCycle = 0;


    public String buildHeader() {
        String curYear = String.valueOf(LocalDate.now().getYear());
        String headerTemplate = "procedure p_cycle_cond_C_@_curYear(p_id_cycle integer)\n" +
                "            is\n" +
                "        cursor cc\n" +
                "            is\n" +
                "        select *\n" +
                "        from F_CYCLE_COND_TMP;\n" +
                "        TYPE t_cc_tab IS TABLE OF F_CYCLE_CONDITION%ROWTYPE\n" +
                "            INDEX BY BINARY_INTEGER;\n" +
                "        cc_tab t_cc_tab;\n" +
                "        i      INTEGER := 0;\n" +
                "        j      INTEGER := 0;";

        headerTemplate = headerTemplate.replace("@", String.valueOf(curCycle)).replace("curYear", curYear);
        return headerTemplate;
    }

    public String buildBeginPart(String codeFromDb) {
        String beginPart = "BEGIN\n" +
                "\n" +
                "            p_cycle_cond_refresh_mview(p_id_cycle => p_id_cycle,\n" +
                "                                       p_id_assess_template1 => @,\n" +
                "                                       p_id_assess_template2 => @);\n" +
                "            p_cycle_cond_delete(p_id_cycle);\n" +
                "\n" +
                "            FOR line IN cc\n" +
                "                LOOP\n" +
                "\n" +
                "                    j := j + 1;\n" +
                "\n" +
                "                    IF j >= 10000\n" +
                "                    THEN\n" +
                "                        FORALL indx IN cc_tab.FIRST .. cc_tab.LAST\n" +
                "                            INSERT INTO F_CYCLE_CONDITION\n" +
                "                            VALUES cc_tab(indx);\n" +
                "\n" +
                "                        COMMIT;\n" +
                "                        cc_tab.DELETE;\n" +
                "                        j := 1;\n" +
                "                    END IF;\n" +
                "\n" +
                "\n" +
                "                    IF line.trade_channel = 'GrCo' and line.id_assess_template = @\n" +
                "                    THEN";
        beginPart = beginPart.replaceAll("@", codeFromDb);
        return beginPart;
    }

    public String buildMainBlock(int condId,String headerComment ,String condName, String attribValue, int attribCounter) {
        String mainTemplate = "-- attribCounter) headerComment\n" +
                "\n" +
                "                        i := i + 1;\n" +
                "\n" +
                "                        cc_tab(i).id_posterr := line.id_posterr; cc_tab(i).id_customer_ent := line.id_customer_ent;\n" +
                "                        cc_tab(i).id_cycle := p_id_cycle;\n" +
                "                        cc_tab(i).cond_id := condId;\n" +
                "                        cc_tab(i).cond_type := 'GrCo';\n" +
                "                        cc_tab(i).cond_name1 := 'condName';\n" +
                "                        cc_tab(i).cond_name2 := '% of achievement';\n" +
                "\n" +
                "                        IF (line.attrib_@_value = 'attribValue')\n" +
                "                        THEN\n" +
                "                            cc_tab(i).cond_val := 1;\n" +
                "                        ELSE\n" +
                "                            cc_tab(i).cond_val := 0;\n" +
                "                        END IF;\n" +
                "\n" +
                "                        cc_tab(i).cond_perc := 1;";
        String attrbCounter = generateAttributeCounter(attribCounter);
        mainTemplate =  mainTemplate
                .replace("attribCounter", String.valueOf(attribCounter))
                .replace("headerComment", headerComment)
                .replace("condId", String.valueOf(condId))
                .replace("condName", condName)
                .replace("@", attrbCounter)
                .replace("attribValue", attribValue);

        return mainTemplate;
    }

    public String buildActBlock(int condId, String condName, int attribCounter) {
        String actTemplate = "                        i := i + 1;\n" +
                "                        cc_tab(i).id_posterr := line.id_posterr; cc_tab(i).id_customer_ent := line.id_customer_ent;\n" +
                "                        cc_tab(i).id_cycle := p_id_cycle;\n" +
                "                        cc_tab(i).cond_id := condId;\n" +
                "                        cc_tab(i).cond_type := 'GrCo';\n" +
                "                        cc_tab(i).cond_name1 := 'condName';\n" +
                "                        cc_tab(i).cond_name2 := line.attrib_@_comments;\n" +
                "\n" +
                "                        cc_tab(i).cond_val := nvl(line.attrib_@_score, 0);\n" +
                "\n" +
                "                        cc_tab(i).cond_perc := 1;\n" +
                "                        --";
        String attrbCounter = generateAttributeCounter(attribCounter);
        actTemplate = actTemplate
                .replace("condId", String.valueOf(condId))
                .replace("condName", condName)
                .replace("@", attrbCounter);
        return actTemplate;
    }

    public String buildEndBlock() {
        return "END IF;\n" +
                "                END LOOP;\n" +
                "\n" +
                "\n" +
                "            if j > 0 then\n" +
                "                FORALL indx IN cc_tab.FIRST .. cc_tab.LAST\n" +
                "                    INSERT INTO F_CYCLE_CONDITION VALUES cc_tab(indx);\n" +
                "\n" +
                "                COMMIT;\n" +
                "            end if;\n" +
                "\n" +
                "        END;";
    }

    public int getNumberOfCycle() {
        return curCycle;
    }

    public void setNumberOfCycle(int numberOfCycle) {
        this.curCycle = numberOfCycle;
    }

    private String generateAttributeCounter(int arrtribCounter) {
        String attrbCounter = String.valueOf(arrtribCounter);
        if (attrbCounter.length() == 1) {
            attrbCounter = "00" + attrbCounter;
        }
        if (attrbCounter.length() == 2) {
            attrbCounter = "0" + attrbCounter;
        }
        return attrbCounter;
    }
}
