package michael.plath.model;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;

import static michael.plath.model.Constants.*;

public class R02 extends Form{
    public R02(){
        resourceFileName = "r02s.pdf";
        stateCode = "NJ";
        outputDirectoryPath = "N:\\MichaelProjects\\TaxOutput\\";
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
            String[] formFields = {"Inventory","Purchases","Returns","Total","Control","Exemption","SubTotal",
                    "SalesReturns","Overall","ActualInventory","TaxDue","ExemptionF","Balance","TaxCredit","NetTax",
            "TaxDollars"};

            form.getField("BeerMalt.Inventory").setValue("0.0"); //set all to zero
            form.getField("BeerMalt.Purchases").setValue("0.0"); //H-1 set to zero
            form.getField("BeerMalt.Returns").setValue("0.0"); //H-3 & H-4
            form.getField("BeerMalt.Total").setValue("0.0"); //total of previous 3 fields
            form.getField("BeerMalt.Control").setValue("0.0");// Sch. D. Totals with Samples
            form.getField("BeerMalt.Exemption").setValue("0.0");// Sch. E Exemptions
            form.getField("BeerMalt.SubTotal").setValue("0.0"); //Previous 2 totals
            form.getField("BeerMalt.SalesReturns").setValue("0.0"); //Sch. A totals
            form.getField("BeerMalt.Overall").setValue("0.0"); // subttotal plus salesreturns
            form.getField("BeerMalt.ActualInventory").setValue("0.0"); //total -overall
            form.getField("BeerMalt.TaxDue").setValue("0.0"); //same as subttotal
            form.getField("BeerMalt.ExemptionF").setValue("0.0");
            form.getField("BeerMalt.Balance").setValue("0.0"); //taxdue - exemption F
            form.getField("BeerMalt.TaxCredit").setValue("0.0"); //from H-3
            form.getField("BeerMalt.NetTax").setValue("0.0"); //balance - taxcredit
            form.getField("BeerMalt.TaxDollars").setValue("0.0"); //calculated 0.12,5.50,0.875,0.875,0.875,0.15



            form.getField("Dollars.Due");
            form.getField("Dollars.Credits");
            form.getField("Dollars.Transmitted");

            form.getField("Warehouse.Name.1").setValue(warehouseName);
            form.getField("Warehouse.Address.1").setValue(warehouseStreetAddress);
            form.getField("Warehouse.Address.2").setValue(warehouseCityStateZip);

            form.getField("Warehouse.Name.2").setValue(warehouseName);
            form.getField("Warehouse.Address.3").setValue(warehouseStreetAddress);
            form.getField("Warehouse.Address.4").setValue(warehouseCityStateZip);


            doc.save(new File(outputDirectoryPath + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void fill() {

    }
}
