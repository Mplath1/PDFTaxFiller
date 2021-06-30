package michael.plath.model;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;

import static michael.plath.model.Constants.*;

public class R02 extends Form{
    public R02(){
        resourceFileName = "r02s.pdf";
        stateCode = "NJ";
    }




    void build(){
        PDField field;
        try {

            form.getField("Info.Licensee.Name").setValue(licenseeName);
            form.getField("Info.Licensee.Fed").setValue(licenseeFederalIdNumber);
            //form.getField("Info.Licensee.Trade").setValue();
            form.getField("Info.Licensee.Address").setValue(licenseeStreetAddress);
            form.getField("Info.Licensee.CityStateZip").setValue(licenseeCityStateZip);
            form.getField("Info.Licensee.County").setValue(licenseeCounty);
            form.getField("Info.Licensee.Type").setValue(licenseeType);
            form.getField("Info.Licensee.Number").setValue(licenseeNumber);
            //form.getField("Info.Month.Start").setValue("Jan");
            //form.getField("Info.Month.End").setValue("Feb");
            //form.getField("Year").setValue(String.valueOf(1999));

            String[] categories = {"BeerMalt","Liquor","StillWine","Vermouth","SparklingWine","AppleCider"};
            //innerLoop?


            form.getField("BeerMalt.Inventory"); //set all to zero
            form.getField("BeerMalt.Purchases"); //H-1 set to zero
            form.getField("BeerMalt.Returns"); //H-3 & H-4
            form.getField("BeerMalt.Total"); //total of previous 3 fields
            form.getField("BeerMalt.Control");// Sch. D. Totals with Samples
            form.getField("BeerMalt.Exemption");// Sch. E Exemptions
            form.getField("BeerMalt.SubTotal"); //Previous 2 totals
            form.getField("BeerMalt.SalesReturns"); //Sch. A totals
            form.getField("BeerMalt.Overall"); // subttotal plus salesreturns
            form.getField("BeerMalt.ActualInventory"); //total -overall
            form.getField("BeerMalt.TaxDue"); //same as subttotal
            form.getField("BeerMalt.ExemptionF");
            form.getField("BeerMalt.Balance"); //taxdue - exemption F
            form.getField("BeerMalt.TaxCredit"); //from H-3
            form.getField("BeerMalt.NetTax"); //balance - taxcredit
            form.getField("BeerMalt.TaxDollars"); //calculated 0.12,5.50,0.875,0.875,0.875,0.15

            form.getField("Dollars.Due");
            form.getField("Dollars.Credits");
            form.getField("Dollars.Transmitted");

            form.getField("Warehouse.Name.1").setValue(warehouseName);
            form.getField("Warehouse.Address.1").setValue(warehouseStreetAddress);
            form.getField("Warehouse.Address.2").setValue(warehouseCityStateZip);

            form.getField("Warehouse.Name.2").setValue(warehouseName);
            form.getField("Warehouse.Address.3").setValue(warehouseStreetAddress);
            form.getField("Warehouse.Address.4").setValue(warehouseCityStateZip);


            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
