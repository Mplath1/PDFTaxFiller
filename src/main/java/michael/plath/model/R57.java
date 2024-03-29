package michael.plath.model;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;

import static michael.plath.model.Constants.*;

public class R57 extends Form{
    public R57(){
        resourceFileName = "r57.pdf";
        stateCode = "NJ";
        outputDirectoryPath = "N:\\MichaelProjects\\TaxOutput\\";
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
            //form.getField("Month.Start").setValue("Jan");
            //form.getField("Month.End").setValue("Feb");

            doc.save(new File(outputDirectoryPath + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void fill() {

    }
}
