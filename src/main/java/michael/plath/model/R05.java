package michael.plath.model;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static michael.plath.model.Constants.licenseeName;
import static michael.plath.model.Constants.licenseeNumber;
import static michael.plath.model.DataSet.getOrganizationListByType;

public class R05 extends Form{
        public R05(){
            resourceFileName = "r05.pdf";
            stateCode = "NJ";
        }



    @Override
    void build() {
        try {
            String schedule = "A";
            PDField field;

            displayAllFields();

            form.getField("Sheet." + schedule + ".Number").setValue(String.valueOf(1));
            form.getField("Sheet." + schedule + ".Total").setValue(String.valueOf(1));
            form.getField("Info.Licensee.Name").setValue(licenseeName);
            form.getField("Info.Licensee.Number").setValue(licenseeNumber);
            //form.getField("Sheet.Number").setPartialName(schedule + String.valueOf(1));

            /*
            //check if extra page is needed. Will need to rename fields and add page if required
            // void createCorrectPage(int numberOfWholesalers){
            int totalRetailers = getOrganizationListByType("Wholesale").size();
            PDDocument otherDoc = PDDocument.load(new File("C:\\USAWine\\New Jersey\\NJForms\\r05.pdf"));

            //load extra page and set field name&numbers
            PDAcroForm extraFields = otherDoc.getDocumentCatalog().getAcroForm();
            int extraCounter = 31; //DEFINE FINAL ELSEWHERE
            int fieldCounter = 0; //only 24 per page DEFINE FINAL ELSEWHERE
            int numberOfExtraPages = 1; //create a clever algorithm for this
            PDField extraField = null;
            while(extraCounter < totalWholesalers){ //DEFINE FIELD NAMES AS FINAL ELSEWHERE

                extraField = extraFields.getField("Wholesaler.Name." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());


                extraField = extraFields.getField("Wholesaler.Liquor." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                extraField = extraFields.getField("Wholesaler.StillWine." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                extraField = extraFields.getField("Wholesaler.Vermouth." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                extraField = extraFields.getField("Wholesaler.SparklingWine." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                extraField = extraFields.getField("Wholesaler.AppleCider." + fieldCounter);
                extraField.setPartialName(String.valueOf(extraCounter));
                extraField.setValue(extraField.getFullyQualifiedName());

                fieldCounter++;
                extraCounter++;
                if(fieldCounter == 24){ //end of extra page.save.close.reset field counter.load new doc anc acroform
                    extraFields.getField("Sheet.Number").setValue(String.valueOf(numberOfExtraPages + 2));
                    otherDoc.save(new File("C:\\USAWine\\New Jersey\\ExtraPage" + numberOfExtraPages + ".pdf"));
                    otherDoc.close();
                    numberOfExtraPages++;
                    fieldCounter = 0;
                    otherDoc = PDDocument.load(new File("C:\\USAWine\\New Jersey\\NJForms\\r08ExtraPage.pdf"));

                    extraFields = otherDoc.getDocumentCatalog().getAcroForm();

                }
                if(extraCounter == totalRetailers){//end of wholesalers.change remaining fields to unused and save
                    extraFields.getField("Sheet.Number").setValue(String.valueOf(numberOfExtraPages + 2));
                    for(int j = 0; j < (24 - fieldCounter); j++){
                        extraField = extraFields.getField("Wholesaler.Name." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());


                        extraField = extraFields.getField("Wholesaler.Liquor." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraField = extraFields.getField("Wholesaler.StillWine." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraField = extraFields.getField("Wholesaler.Vermouth." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraField = extraFields.getField("Wholesaler.SparklingWine." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraField = extraFields.getField("Wholesaler.AppleCider." + (fieldCounter + j));
                        //extraField.setPartialName("UNUSED" + String.valueOf(extraCounter));
                        //extraField.setValue(extraField.getFullyQualifiedName());

                        extraCounter++;
                    }
                    otherDoc.save(new File("C:\\USAWine\\New Jersey\\ExtraPage" + numberOfExtraPages + ".pdf"));
                }

            }
            //}

            //create mergeUtility and add first page plus any extra pages
            PDFMergerUtility mergerUtility = new PDFMergerUtility();
            mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\NJForms\\r05.pdf"));
            for(int i = 1; i< (numberOfExtraPages + 1) ;i++) {
                mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\ExtraPage" + i + ".pdf"));
            }
            //set destination of merge. merge. load merged document with all combined pages/fields
            mergerUtility.setDestinationFileName("C:\\USAWine\\New Jersey\\TESTMERGE.pdf");
            mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly()); //merge works
            doc = PDDocument.load(new File("C:\\USAWine\\New Jersey\\TESTMERGE.pdf"));
            form = doc.getDocumentCatalog().getAcroForm();
            form.getField("Sheet.Number").setValue(String.valueOf(1));


            //rename auto-named "dummyFieldName" root/parent fields in additional pages to correct names
            int dummyFieldCounter = 1; //fix this and use an array
            for(int i = 0; i<numberOfExtraPages; i++) {
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
                field.setPartialName("Wholesaler");
                //field.setValue("WORKED " + field.getFullyQualifiedName());
                dummyFieldCounter++;
            }
            //}
            */
            /*
            Info.Month. (Start/End)
            Info.Licensee. (Name/Number)
            Year
            Sheet. (Number/Total)


            Wholesaler.Name.#
            Wholesaler.BeerAndMalt.#
            Wholesaler.Liquor.#
            Wholesaler.StillWine.#
            Wholesaler.Vermouth.#
            Wholesaler.SparklingWine.#
            Wholesaler.AppleCider.#
            */

            //form.getField("Sheet.Total").setValue(String.valueOf(numberOfExtraPages + 2));//R08(2 pages) + extra

            List<Organization> wholesalers = getOrganizationListByType("Wholesale");
            double totalGallons[] = {0,0,0,0,0,0};

            //populate fields with wholesaler info
            for(int i = 0;i< wholesalers.size(); i++){
                PDTextField textField = (PDTextField) form.getField("Wholesaler.Name." + i);
                textField.setMultiline(true); //set multiline to true for addresses
                textField.setDefaultAppearance("/Helv 0 Tf 0 g"); //autoSize CHANGE FORM FIELD SIZE USING ADOBE
                form.getField("Wholesaler.Name." + i).setValue(wholesalers.get(i).getName() + "\n"
                        + wholesalers.get(i).getAddress() + "\n" + wholesalers.get(i).getLicense());
                //form.getField("Retailer.BeerAndMalt." + i).setValue(0.0);
                form.getField("Wholesaler.Liquor." + i).setValue(String.valueOf(wholesalers.get(i).totalGallonsLiquor));
                totalGallons[1] += wholesalers.get(i).totalGallonsLiquor;
                form.getField("Wholesaler.StillWine." + i).setValue(String.valueOf(wholesalers.get(i).totalGallonsStill));
                totalGallons[2] += wholesalers.get(i).totalGallonsStill;
                //form.getField("Retailer.Vermouth." + i).setValue(String.valueOf(0.0));
                form.getField("Wholesaler.SparklingWine." + i).setValue(String.valueOf(wholesalers.get(i).totalGallonsSparkling));
                totalGallons[4] += wholesalers.get(i).totalGallonsSparkling;

            }

            System.out.println(totalGallons[1] + " " + totalGallons[2] + " " + totalGallons[4]);

            //save final document and close
            doc.save(new File("C:\\USAWine\\New Jersey\\Tester\\" + resourceFileName));
            doc.close();

        }catch (IOException e){
            System.out.println(e.getMessage());

        }
    }

    public String[] getWholesaleTotals(){
        String[] wholesaleTotals = {"0","0","0","0","0","0"};
        double[] wholesaleDoubleTotals = {0,0,0,0,0,0};
        for (Organization organization: getOrganizationListByType("Wholesale")) {
            wholesaleDoubleTotals[1] += organization.totalGallonsLiquor;
            wholesaleDoubleTotals[2] += organization.totalGallonsStill;
            wholesaleDoubleTotals[4] += organization.totalGallonsSparkling;
        }
        for(int i=0; i<wholesaleTotals.length;i++){
            wholesaleTotals[i] = String.valueOf(wholesaleDoubleTotals[i]);
        }

        return  wholesaleTotals;
    }




}
