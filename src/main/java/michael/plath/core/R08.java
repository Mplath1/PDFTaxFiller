package michael.plath.core;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static michael.plath.core.DataSet.getOrganizationListByType;

public class R08 extends Form{
    public R08(){
        resourceFileName = "r08.pdf";
        stateCode = "NJ";
    }

    public void build() {
        try {
            String schedule = "D";
            PDField field;

            //check if extra page is needed. Will need to rename fields and add page if required
            // void createCorrectPage(int numberOfRetailers){
            int totalRetailers = getOrganizationListByType("Retail").size();
           // PDDocument otherDoc = PDDocument.load(new File("C:\\USAWine\\New Jersey\\NJForms\\r08ExtraPage.pdf"));
            //PDDocument otherDoc = PDDocument.load(in);
            PDDocument otherDoc = doc;

            //load extra page and set field name&numbers
            PDAcroForm extraFields = otherDoc.getDocumentCatalog().getAcroForm();
            int extraCounter = 44; //DEFINE FINAL ELSEWHERE
            int fieldCounter = 0; //only 24 per page DEFINE FINAL ELSEWHERE
            int numberOfExtraPages = 1; //create a clever algorithm for this
            PDField extraField = null;
            while (extraCounter < totalRetailers) { //DEFINE FIELD NAMES AS FINAL ELSEWHERE
                extraField = extraFields.getField("Retailer.License." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                extraField = extraFields.getField("Retailer.Name." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());


                extraField = extraFields.getField("Retailer.Liquor." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                extraField = extraFields.getField("Retailer.StillWine." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                extraField = extraFields.getField("Retailer.Vermouth." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                extraField = extraFields.getField("Retailer.SparklingWine." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                extraField = extraFields.getField("Retailer.AppleCider." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                fieldCounter++;
                extraCounter++;
                if (fieldCounter == 24) { //end of extra page.save.close.reset field counter.load new doc anc acroform
                    extraFields.getField("Sheet."+ schedule + ".Number").setValue(String.valueOf(numberOfExtraPages + 2));
                    otherDoc.save(new File("C:\\USAWine\\New Jersey\\ExtraPage" + numberOfExtraPages + ".pdf"));
                    otherDoc.close();
                    numberOfExtraPages++;
                    fieldCounter = 0;
                    otherDoc = PDDocument.load(new File("C:\\USAWine\\New Jersey\\NJForms\\r08ExtraPage.pdf"));

                    extraFields = otherDoc.getDocumentCatalog().getAcroForm();

                }
                if (extraCounter == totalRetailers) {//end of retailers.change remaining fields to unused and save
                    extraFields.getField("Sheet."+ schedule + ".Number").setValue(String.valueOf(numberOfExtraPages + 2));
                    for (int j = 0; j < (24 - fieldCounter); j++) {
                        extraField = extraFields.getField("Retailer.License." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraField = extraFields.getField("Retailer.Name." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());


                        extraField = extraFields.getField("Retailer.Liquor." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraField = extraFields.getField("Retailer.StillWine." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraField = extraFields.getField("Retailer.Vermouth." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraField = extraFields.getField("Retailer.SparklingWine." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraField = extraFields.getField("Retailer.AppleCider." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraCounter++;
                    }
                    otherDoc.save(new File("C:\\USAWine\\New Jersey\\ExtraPage" + numberOfExtraPages + ".pdf"));
                }

            }
            //}

            Iterator<PDField> iterator = otherDoc.getDocumentCatalog().getAcroForm().getFieldIterator();
            while(iterator.hasNext()){
                System.out.println(iterator.next().getFullyQualifiedName());
            }

            System.out.println("============");
            displayAllFields();

            //MUST BE FIXED TO PREVENT UNEEDED PAGES
            //create mergeUtility and add first page plus any extra pages
            PDFMergerUtility mergerUtility = new PDFMergerUtility();
            //mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\NJForms\\r08.pdf"));
            mergerUtility.addSource(new File("C:\\Users\\Michael\\Desktop\\Programming\\Projects\\PDFTaxFiller\\src\\main\\resources\\forms\\NJ\\r08.pdf"));
            for (int i = 1; i < (numberOfExtraPages + 1); i++) {
                mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\ExtraPage" + i + ".pdf"));
            }
            //set destination of merge. merge. load merged document with all combined pages/fields
            mergerUtility.setDestinationFileName("C:\\USAWine\\New Jersey\\TESTMERGE.pdf");
            mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly()); //merge works
            doc = PDDocument.load(new File("C:\\USAWine\\New Jersey\\TESTMERGE.pdf"));
            form = doc.getDocumentCatalog().getAcroForm();
            form.getField("Sheet."+ schedule + ".Number").setValue(String.valueOf(1));

            doc.save(new File("C:\\USAWine\\New Jersey\\TESTMERGE.pdf"));


            //rename auto-named "dummyFieldName" root/parent fields in additional pages to correct names
            int dummyFieldCounter = 1; //fix this and use an array
            for (int i = 0; i < numberOfExtraPages; i++) {
                field = form.getField("dummyFieldName" + dummyFieldCounter);
                field.setPartialName("Info");
                //field.setValue("WORKED " + field.getFullyQualifiedName());
                dummyFieldCounter++;
                field = form.getField("dummyFieldName" + dummyFieldCounter);
                field.setPartialName("Year");
                //field.setValue("WORKED " + field.getFullyQualifiedName());
                dummyFieldCounter++;
                //field = form.getField("dummyFieldName" + dummyFieldCounter);
                field.setPartialName("Sheet");
                //field.setValue("WORKED " + field.getFullyQualifiedName());
                dummyFieldCounter++;
                field = form.getField("dummyFieldName" + dummyFieldCounter);
                field.setPartialName("Retailer");
                //field.setValue("WORKED " + field.getFullyQualifiedName());
                dummyFieldCounter++;
            }
            //}

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

            form.getField("Sheet."+ schedule + ".Total").setValue(String.valueOf(numberOfExtraPages + 2));//R08(2 pages) + extra


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
                form.getField("Retailer.Liquor." + i).setValue(String.valueOf(retailers.get(i).totalGallonsLiquor));
                totalGallons[1] += retailers.get(i).totalGallonsLiquor;
                form.getField("Retailer.StillWine." + i).setValue(String.valueOf(retailers.get(i).totalGallonsStill));
                totalGallons[2] += retailers.get(i).totalGallonsStill;
                //form.getField("Retailer.Vermouth." + i).setValue(String.valueOf(0.0));
                form.getField("Retailer.SparklingWine." + i).setValue(String.valueOf(retailers.get(i).totalGallonsSparkling));
                totalGallons[4] += retailers.get(i).totalGallonsSparkling;

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
            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();

        } catch (IOException e) {


        }
    }


}
