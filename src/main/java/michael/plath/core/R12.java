package michael.plath.core;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;

public class R12 extends Form{
    public R12(){
        resourceFileName = "r12.pdf";
        stateCode = "NJ";
    }




    void build(){
        String schedule = "H";
        PDField field;
        try {
            form.getField("Sheet." + schedule + ".Number").setValue(String.valueOf(1));
            form.getField("Sheet." + schedule + ".Total").setValue(String.valueOf(2));

            form.getField("Licensee.Name").setValue("USA Wine Imports,Inc.");
            form.getField("Licensee.Number").setValue("3404-23-943-001");
            form.getField("Month.Start").setValue("Jan");
            form.getField("Month.End").setValue("Feb");

            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
