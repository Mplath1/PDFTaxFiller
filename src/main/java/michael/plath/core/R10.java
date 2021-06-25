package michael.plath.core;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;

public class R10 extends Form{
    public R10(){
        resourceFileName = "r10.pdf";
        stateCode = "NJ";
    }

    void build(){
        String schedule = "F";
        PDField field;
        try {
            form.getField("Sheet." + schedule + ".Number").setValue(String.valueOf(1));
            form.getField("Sheet." + schedule + ".Total").setValue(String.valueOf(1));

            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
