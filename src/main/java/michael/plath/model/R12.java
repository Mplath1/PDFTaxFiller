package michael.plath.model;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.xpath.operations.Or;

import java.io.File;
import java.util.List;

import static michael.plath.model.Constants.*;
import static michael.plath.model.DataSet.getOrganizationListByType;

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
            //form.getField("Month.Start").setValue("Jan");
            //form.getField("Month.End").setValue("Feb");

            PDTextField textField = (PDTextField) form.getField("Seller.Name.0");
            textField.setMultiline(true);
            textField.setDefaultAppearance("/Helv 0 Tf 0 g");
            form.getField("Seller.Name.0").setValue(licenseeName + "\n" + licenseeStreetAddress + "\n"
                    + licenseeCityStateZip);

            double totalGallons[] = {0, 0, 0, 0, 0, 0};
            List<Organization> organizationList = getOrganizationListByType("Retail");
            for(Organization organization: organizationList){
               totalGallons[1] += Double.parseDouble(String.valueOf(organization.totalGallonsLiquor));
               totalGallons[2] += Double.parseDouble(String.valueOf(organization.totalGallonsStill));
               totalGallons[4] += Double.parseDouble(String.valueOf(organization.totalGallonsSparkling));
            }

            organizationList = getOrganizationListByType("Wholesale");
            for(Organization organization: organizationList){
                totalGallons[1] += Double.parseDouble(String.valueOf(organization.totalGallonsLiquor));
                totalGallons[2] += Double.parseDouble(String.valueOf(organization.totalGallonsStill));
                totalGallons[4] += Double.parseDouble(String.valueOf(organization.totalGallonsSparkling));
            }

            organizationList = getOrganizationListByType("Sample");
            for(Organization organization: organizationList){
                totalGallons[1] += Double.parseDouble(String.valueOf(organization.totalGallonsLiquor));
                totalGallons[2] += Double.parseDouble(String.valueOf(organization.totalGallonsStill));
                totalGallons[4] += Double.parseDouble(String.valueOf(organization.totalGallonsSparkling));
            }


            form.getField("Seller.BeerMalt.0"); //retail + wholesale totals
            form.getField("Seller.Liquor.0").setValue(String.valueOf(totalGallons[1]));
            form.getField("Seller.StillWine.0").setValue(String.valueOf(totalGallons[2]));
            form.getField("Seller.Vermouth.0");
            form.getField("Seller.SparklingWine.0").setValue(String.valueOf(totalGallons[4]));
            form.getField("Seller.AppleCider.0");

            int numberofRetailersWithCredits = 0;
            organizationList = getOrganizationListByType("Retail");
            for(Organization organization: organizationList){
                double[] creditGallons = {0,0,0,0,0,0};
                boolean populate = false;
                for(Transaction transaction: organization.transactions){
                     //check for negatives currently. modify to look for credits
                    double absoluteValueGallons = 0;
                    if(transaction.getGallonsLiquor() < 0) {
                        absoluteValueGallons = Double.parseDouble(String.valueOf(transaction.gallonsLiquor)) * -1;
                        creditGallons[1] += absoluteValueGallons;
                        populate = true;
                    }
                    if(transaction.getGallonsStill() < 0) {
                        absoluteValueGallons = Double.parseDouble(String.valueOf(transaction.gallonsStill)) * -1;
                        creditGallons[2] += absoluteValueGallons;
                        populate = true;
                    }
                    if(transaction.getGallonsSparkling() < 0) {
                        absoluteValueGallons = Double.parseDouble(String.valueOf(transaction.gallonsSparkling)) * -1;
                        creditGallons[4] += absoluteValueGallons;
                        populate = true;
                    }


                }
                if(populate){
                    textField = (PDTextField) form.getField("Returns.Name." + numberofRetailersWithCredits);
                    textField.setMultiline(true);
                    textField.setDefaultAppearance("/Helv 0 Tf 0 g");
                    form.getField("Returns.Name." + + numberofRetailersWithCredits).setValue(organization.getName()
                            + "\n" + organization.getAddress() + "\n" + organization.getLicense());
                    form.getField("Returns.BeerMalt." + numberofRetailersWithCredits); //returns values
                    form.getField("Returns.Liquor." + numberofRetailersWithCredits).setValue(String.valueOf(creditGallons[1]));
                    form.getField("Returns.StillWine." + numberofRetailersWithCredits).setValue(String.valueOf(creditGallons[2]));
                    form.getField("Returns.Vermouth." + numberofRetailersWithCredits);
                    form.getField("Returns.SparklingWine." + numberofRetailersWithCredits).setValue(String.valueOf(creditGallons[4]));
                    form.getField("Returns.AppleCider." + numberofRetailersWithCredits);
                    numberofRetailersWithCredits++;
                }

            }


            form.getField("Returns.BeerMalt.0"); //returns totals
            form.getField("Returns.Liquor.0");
            form.getField("Returns.StillWine.0");
            form.getField("Returns.Vermouth.0");
            form.getField("Returns.SparklingWine.0");
            form.getField("Returns.AppleCider.0");





            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void fill() {

    }
}
