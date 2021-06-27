package michael.plath.core;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;

import static michael.plath.core.Constants.licenseeName;
import static michael.plath.core.Constants.licenseeNumber;

public class R09 extends Form{
    public R09(){
        resourceFileName = "r09.pdf";
        stateCode = "NJ";
    }

    void build() {

        try {
            String schedule = "E";
            PDField field;

            form.getField("Sheet."+ schedule + ".Number").setValue(String.valueOf(1));
            form.getField("Sheet."+ schedule + ".Total").setValue(String.valueOf(1));
            form.getField("Info.Licensee.Name").setValue(licenseeName);
            form.getField("Info.Licensee.Number").setValue(licenseeNumber);






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
