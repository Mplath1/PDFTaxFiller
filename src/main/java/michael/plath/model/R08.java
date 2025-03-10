package michael.plath.model;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static michael.plath.model.Constants.licenseeName;
import static michael.plath.model.Constants.licenseeNumber;
import static michael.plath.model.DataSet.getOrganizationListByType;

public class R08 extends Form{
    public R08(){
        resourceFileName = "r08.pdf";
        stateCode = "NJ";
        outputDirectoryPath = "N:\\MichaelProjects\\TaxOutput\\";
    }

    public void build() {
        try {
            String schedule = "D";
            PDDocument producedDoc = doc;
            PDField field;


            //save original root names and count here in an array for use when renaming

            // void createCorrectPage(int numberOfRetailers){
            int totalRetailers = getOrganizationListByType("Retail").size();

            //IF MORE THEN 44 AN ADDITIONAL SHEET NEEDED. totalRetailers - 20 - (24 * extraPagesNeeded) = 0
            int extraRetailers = (totalRetailers - 20 - 24);
            int extraPagesNeeded = 0;
            PDFMergerUtility mergerUtility = new PDFMergerUtility();
            if(extraRetailers > 0) {

                if ((extraRetailers % 24) != 0) {
                    extraPagesNeeded = (extraRetailers/24) + 1;
                }else{
                    extraPagesNeeded = extraRetailers/24;
                }
            }

            producedDoc.save(new File("src\\main\\resources\\forms\\NJ\\temp\\original.pdf"));

            PDDocument otherDoc = doc;
            PDAcroForm fields = otherDoc.getDocumentCatalog().getAcroForm();

            int counter = 44;
            int fieldCounter = 0;
            while(counter < totalRetailers){
                field = fields.getField("Retailer.License." + fieldCounter);
                field.setPartialName(String.valueOf(counter));
                //field.setValue(field.getFullyQualifiedName());

                field = fields.getField("Retailer.Name." + fieldCounter);
                field.setPartialName(String.valueOf(counter));
                field.setValue(field.getFullyQualifiedName());


                field = fields.getField("Retailer.Liquor." + fieldCounter);
                field.setPartialName(String.valueOf(counter));
                //field.setValue(field.getFullyQualifiedName());

                field = fields.getField("Retailer.StillWine." + fieldCounter);
                field.setPartialName(String.valueOf(counter));
                //field.setValue(field.getFullyQualifiedName());

                field = fields.getField("Retailer.Vermouth." + fieldCounter);
                field.setPartialName(String.valueOf(counter));
                //field.setValue(field.getFullyQualifiedName());

                field = fields.getField("Retailer.SparklingWine." + fieldCounter);
                field.setPartialName(String.valueOf(counter));
                //field.setValue(field.getFullyQualifiedName());

                field = fields.getField("Retailer.AppleCider." + fieldCounter);
                field.setPartialName(String.valueOf(counter));
                //field.setValue(field.getFullyQualifiedName());

                fieldCounter++;
                counter++;

                if (fieldCounter == 24) { //end of extra page.save.close.reset field counter.load new doc anc acroform
                    //extraFields.getField("Sheet."+ schedule + ".Number").setValue(String.valueOf(numberOfExtraPages + 2));
                    //otherDoc.addPage(otherDoc.getPage(0)); //use add page not import always
                    otherDoc.removePage(1);
                    otherDoc.save(new File("src\\main\\resources\\forms\\NJ\\temp\\" + extraPagesNeeded + ".pdf"));
                    extraPagesNeeded--;
                    fieldCounter = 0;

                    otherDoc = PDDocument.load(new File("src\\main\\resources\\forms\\NJ\\temp\\original.pdf")); //loads with fields starting at 44 no matter what!!!!!!!
                    fields = otherDoc.getDocumentCatalog().getAcroForm();

                }
                if (counter == totalRetailers) {//end of retailers.change remaining fields to unused and save
                    //extraFields.getField("Sheet."+ schedule + ".Number").setValue(String.valueOf(numberOfExtraPages + 2));
                    for (int j = 0; j < (24 - fieldCounter); j++) {
                        field = fields.getField("Retailer.License." + (fieldCounter + j));
                        field.setPartialName("UNUSED" + String.valueOf(counter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        field = fields.getField("Retailer.Name." + (fieldCounter + j));
                        field.setPartialName("UNUSED" + String.valueOf(counter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        field = fields.getField("Retailer.Liquor." + (fieldCounter + j));
                        field.setPartialName("UNUSED" + String.valueOf(counter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        field = fields.getField("Retailer.StillWine." + (fieldCounter + j));
                        field.setPartialName("UNUSED" + String.valueOf(counter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        field = fields.getField("Retailer.Vermouth." + (fieldCounter + j));
                        field.setPartialName("UNUSED" + String.valueOf(counter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        field = fields.getField("Retailer.SparklingWine." + (fieldCounter + j));
                        field.setPartialName("UNUSED" + String.valueOf(counter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        field = fields.getField("Retailer.AppleCider." + (fieldCounter + j));
                        field.setPartialName("UNUSED" + String.valueOf(counter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        counter++;
                    }
                    otherDoc.removePage(1);
                    otherDoc.save(new File("src\\main\\resources\\forms\\NJ\\temp\\" + extraPagesNeeded + ".pdf"));
                    //otherDoc.save(new File("C:\\USAWine\\New Jersey\\ExtraPageTEST.pdf"));
                }
            }
            //end of create form method here

            //turn this into merge utility private function
            merge();
            /*try {
                mergerUtility.addSource(new File("src\\main\\resources\\forms\\" + stateCode + "\\temp\\original.pdf"));
                extraPagesNeeded = (extraRetailers/24) + 1;
                for(int i = extraPagesNeeded; i > 0; i--) {
                    mergerUtility.addSource(new File("src\\main\\resources\\forms\\" + stateCode + "\\temp\\" + i + ".pdf"));
                }
                mergerUtility.setDestinationFileName(String.valueOf(new File("src\\main\\resources\\forms\\" + stateCode + "\\temp\\" + resourceFileName)));
                mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            }catch(IOException e){
                System.out.println(e.getMessage());
            }*/

            /*
            Info.Month. (Start/End)
            Info.Licensee. (Name/Number)
            Year
            Sheet. (Number/Total)

            (0-23 on page 0. 20 on page 1. if more then 44 you will need additional pages)
            Retailer.License.#
            Retailer.Name.#
            Retailer.BeerAndMalt.# (or AllRetail/Theft/Samples/Breakage/Other/Adjustment/Control)
            Retailer.Liquor.# (or AllRetail/Theft/Samples/Breakage/Other/Adjustment/Control)
            Retailer.StillWine.# (or AllRetail/Theft/Samples/Breakage/Other/Adjustment/Control)
            Retailer.Vermouth.# (or AllRetail/Theft/Samples/Breakage/Other/Adjustment/Control)
            Retailer.SparklingWine.# (or AllRetail/Theft/Samples/Breakage/Other/Adjustment/Control)
            Retailer.AppleCider.# (or AllRetail/Theft/Samples/Breakage/Other/Adjustment/Control)
            */

            //field rename private method
            doc = PDDocument.load(new File("src\\main\\resources\\forms\\NJ\\temp\\R08.pdf"));
            form = doc.getDocumentCatalog().getAcroForm();
            fieldRootNames = doc.getDocumentCatalog().getAcroForm().getFields();
            Iterator<PDField> iterator = fieldRootNames.listIterator();
            while(iterator.hasNext()){
                int dummyFieldNumber = 4;
                field = iterator.next();
                if(field.getFullyQualifiedName().contains("dummyFieldName")){
                    switch(dummyFieldNumber % 4) {
                        case 0:
                        field.setPartialName("Retailer");
                        break;
                        case 1:
                            field.setPartialName("Info");
                            break;
                        case 2:
                            field.setPartialName("Year");
                            break;
                        case 3:
                            field.setPartialName("Sheet");
                            break;
                        default:
                            break;
                    }

                }
                dummyFieldNumber++;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }

    //should be deprecated but due to doc merge creating dummyFields is needed
    public String[] getRetailTotals(){
        String[] retailTotals = {"0","0","0","0","0","0"};
        retailTotals[1] = form.getField("Retailer.Liquor.AllRetail").getValueAsString();
        retailTotals[2] = form.getField("Retailer.StillWine.AllRetail").getValueAsString();
        retailTotals[4] = form.getField("Retailer.SparklingWine.AllRetail").getValueAsString();

        return  retailTotals;
    }

    //should be deprecated but due to doc merge creating dummyFields is needed
    public String[] getSampleTotals(){
        String[] sampleTotals = {"0","0","0","0","0","0"};
        sampleTotals[1] = form.getField("Retailer.Liquor.Samples").getValueAsString();
        sampleTotals[2] = form.getField("Retailer.StillWine.Samples").getValueAsString();
        sampleTotals[4] = form.getField("Retailer.SparklingWine.Samples").getValueAsString();
        return  sampleTotals;
    }

    //WORKS. REMOVE FROM BUILD SECTION
    public void fill(){
        try{
        doc = PDDocument.load(new File("src\\main\\resources\\forms\\NJ\\temp\\R08.pdf"));
        form = doc.getDocumentCatalog().getAcroForm();
        fieldRootNames = doc.getDocumentCatalog().getAcroForm().getFields();
        Iterator<PDField> iterator = fieldRootNames.listIterator();
        PDField field = null;
        while(iterator.hasNext()){
            int dummyFieldNumber = 4;
            field = iterator.next();
            if(field.getFullyQualifiedName().contains("dummyFieldName")){
                switch(dummyFieldNumber % 4) {
                    case 0:
                        field.setPartialName("Retailer");
                        break;
                    case 1:
                        field.setPartialName("Info");
                        break;
                    case 2:
                        field.setPartialName("Year");
                        break;
                    case 3:
                        field.setPartialName("Sheet");
                        break;
                    default:
                        break;
                }

            }
            dummyFieldNumber++;
        }
        //form.getField("Sheet."+ schedule + ".Total").setValue(String.valueOf(numberOfExtraPages + 2));//R08(2 pages) + extra

        form.getField("Info.Licensee.Name").setValue(licenseeName);
        form.getField("Info.Licensee.Number").setValue(licenseeNumber);

        List<Organization> retailers = getOrganizationListByType("Retail");
        double totalGallons[] = {0, 0, 0, 0, 0, 0};

        //populate fields with retailer info
        for (int i = 0; i < retailers.size(); i++) {
            field = form.getField("Retailer.License." + i);
            form.getField("Retailer.License." + i).setValue(retailers.get(i).getLicense());
            PDTextField textField = (PDTextField) form.getField("Retailer.Name." + i);
            textField.setMultiline(true); //set multiline to true for addresses
            textField.setDefaultAppearance("/Helv 0 Tf 0 g"); //autoSize CHANGE FORM FIELD SIZE USING ADOBE
            form.getField("Retailer.Name." + i).setValue(retailers.get(i).getName() + "\n" + retailers.get(i).getAddress());
            //form.getField("Retailer.BeerAndMalt." + i).setValue(0.0);
            //ADD CHECK FOR NEGATIVE VALUES HERE
            form.getField("Retailer.Liquor." + i).setValue(String.valueOf(retailers.get(i).totalGallonsLiquor));
            totalGallons[1] += retailers.get(i).totalGallonsLiquor;
            form.getField("Retailer.StillWine." + i).setValue(String.valueOf(retailers.get(i).totalGallonsStill));
            totalGallons[2] += retailers.get(i).totalGallonsStill;
            //form.getField("Retailer.Vermouth." + i).setValue(String.valueOf(0.0));
            form.getField("Retailer.SparklingWine." + i).setValue(String.valueOf(retailers.get(i).totalGallonsSparkling));
            totalGallons[4] += retailers.get(i).totalGallonsSparkling;
            System.out.println("\n" + retailers.get(i).getName() + "\n\tL:" + retailers.get(i).totalGallonsLiquor + "\n\tSt:"
                    + retailers.get(i).totalGallonsStill + "\n\tSp:" + retailers.get(i).totalGallonsSparkling);

        }
        //populate totals
        form.getField("Retailer.Liquor.AllRetail").setValue(String.valueOf(totalGallons[1]));
        form.getField("Retailer.StillWine.AllRetail").setValue(String.valueOf(totalGallons[2]));
        form.getField("Retailer.SparklingWine.AllRetail").setValue(String.valueOf(totalGallons[4]));

        double sampleGallons[] = {0, 0, 0, 0, 0, 0};
        for (Organization organization : getOrganizationListByType("Sample")) {
            sampleGallons[1] += organization.totalGallonsLiquor;
            sampleGallons[2] += organization.totalGallonsStill;
            sampleGallons[4] += organization.totalGallonsSparkling;
        }
        form.getField("Retailer.Liquor.Samples").setValue(String.valueOf(sampleGallons[1]));
        form.getField("Retailer.StillWine.Samples").setValue(String.valueOf(sampleGallons[2]));
        form.getField("Retailer.SparklingWine.Samples").setValue(String.valueOf(sampleGallons[4]));

        form.getField("Retailer.Liquor.Control").setValue(String.valueOf(totalGallons[1] + sampleGallons[1]));
        form.getField("Retailer.StillWine.Control").setValue(String.valueOf(totalGallons[2] + sampleGallons[2]));
        form.getField("Retailer.SparklingWine.Control").setValue(String.valueOf(totalGallons[4] + sampleGallons[4]));


        System.out.println(totalGallons[1] + " " + totalGallons[2] + " " + totalGallons[4]);

        //save final document and close
        doc.save(new File(outputDirectoryPath + resourceFileName));
        doc.close();

    } catch (IOException e) {
        System.out.println(e.getMessage());

    }
    }


}
