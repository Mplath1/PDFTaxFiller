package michael.plath.core;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;

import static michael.plath.core.Constants.*;

public class R12 extends Form{
    public R12(){
        resourceFileName = "r12.pdf";
        stateCode = "NJ";
    }




    void build(){
        String schedule = "H"; //Seller,InTransit,Returns,ReturnsUntaxed
        PDField field;
        try {
            form.getField("Sheet." + schedule + ".Number").setValue(String.valueOf(1));
            form.getField("Sheet." + schedule + ".Total").setValue(String.valueOf(2));

            form.getField("Licensee.Name").setValue("USA Wine Imports,Inc.");
            form.getField("Licensee.Number").setValue("3404-23-943-001");
            form.getField("Month.Start").setValue("Jan");
            form.getField("Month.End").setValue("Feb");

            PDTextField textField = (PDTextField) form.getField("Seller.Name.0");
            textField.setMultiline(true);
            textField.setDefaultAppearance("/Helv 0 Tf 0 g");
            form.getField("Seller.Name.0").setValue(licenseeName + "\n" + licenseeStreetAddress + "\n"
                    + licenseeCityStateZip);

            form.getField("Seller.BeerMalt.0"); //retail + wholesale totals
            form.getField("Seller.Liquor.0");
            form.getField("Seller.StillWine.0");
            form.getField("Seller.Vermouth.0");
            form.getField("Seller.SparklingWine.0");
            form.getField("Seller.AppleCider.0");

            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
