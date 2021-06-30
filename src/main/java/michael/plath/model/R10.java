package michael.plath.model;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;

import static michael.plath.model.Constants.licenseeName;
import static michael.plath.model.Constants.licenseeNumber;

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
            form.getField("Licensee.Name").setValue(licenseeName);
            form.getField("Licensee.Number").setValue(licenseeNumber);

            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
