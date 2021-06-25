package michael.plath.core;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;

public class R02 extends Form{
    public R02(){
        resourceFileName = "r02s.pdf";
        stateCode = "NJ";
    }




    void build(){
        PDField field;
        try {

            form.getField("Info.Licensee.Name").setValue("USA Wine Imports, Inc.");
            //form.getField("Info.Licensee.Fed").setValue();
            //form.getField("Info.Licensee.Trade").setValue();
            //form.getField("Info.Licensee.Address").setValue();
            //form.getField("Info.Licensee.CityStateZip").setValue();
            //form.getField("Info.Licensee.County").setValue();
            //form.getField("Info.Licensee.Type").setValue();
            //form.getField("Info.Licensee.Number").setValue();
            form.getField("Info.Month.Start").setValue("Jan");
            form.getField("Info.Month.End").setValue("Feb");
            form.getField("Year").setValue(String.valueOf(1999));




            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
