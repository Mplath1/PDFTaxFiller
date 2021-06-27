package michael.plath.core;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;

import static michael.plath.core.Constants.*;

public class R57 extends Form{
    public R57(){
        resourceFileName = "r57.pdf";
        stateCode = "NJ";
    }




    void build(){
        String schedule = "BW";
        PDField field;
        try {
            form.getField("Sheet." + schedule + ".Number").setValue(String.valueOf(1));
            form.getField("Sheet." + schedule + ".Total").setValue(String.valueOf(1));

            form.getField("Licensee.Name").setValue(licenseeName);
            form.getField("Licensee.Address").setValue(licenseeStreetAddress);
            form.getField("Licensee.Number").setValue(licenseeNumber);
            form.getField("Month.Start").setValue("Jan");
            form.getField("Month.End").setValue("Feb");

            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
