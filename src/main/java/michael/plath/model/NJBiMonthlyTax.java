package michael.plath.model;

import michael.plath.core.dao.FileSource;
import michael.plath.core.dao.FileSourceImpl;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class NJBiMonthlyTax extends PreparedForm{

    public NJBiMonthlyTax(){
        name = "New Jersey Bi-Monthly Tax";
        stateCode = "NJ";
        formList = new ArrayList<Form>(); //use to add all forms and cycle to build
    }

    @Override
    public void build() {
        //load properties
        Constants constants = new Constants();
        String outputDirectoryPath = "N:\\MichaelProjects\\TaxOutput\\"; //TODO:LOAD FROM PROPERTIES
        //String formTemplateDirectoryPath = "N:\\MichaelProjects\\PDFTaxFiller\\src\\main\\resources\\forms\\NJ\\";
/*
        //create new source
        FileSource ourData = new FileSourceImpl();
        //get data from source
        ourData.setSource(new File("C:\\USAWine\\New Jersey\\NJ Tax Report Jan-Feb 2021 Raw Data.xlsx"));
        ourData.connect();
        FileInputStream dataStream = ourData.getData();
        //arrange and calculate data
        NJDataSorter dataSorter = new NJDataSorter(dataStream);
        DataSet dataSet = new DataSetImpl(dataSorter);
        dataSet.getDataSorter().sort();
*/

        //use builder for overall NJ Bi-Monthly Tax Prep
        //FIX ROUNDING. ALSO ACCOUNT FOR NEGATIVES. if total is negative set to zero and move negative number to returns.
        // only calculate invoices for retailers. credits should be calculated returns
        //correct dates. build GUI

        //should be form factory. abstract factory once states are added
        //R02(last) R12 R08 R05 R09 R10 R57

        Form R08 = new R08(); //works, terrible design though
        Form R05 = new R05(); //works
        Form R09 = new R09(); //works
        Form R10 = new R10(); //works
        Form R57 = new R57(); //works
        Form R02 = new R02(); //work in progress
        Form R12 = new R12(); //works. correct sheetTotal to 2 Returns go on H3 form. complete this after R08 & R05 & R02
        R08.loadForm();
        R08.build();
        R08.clean();
        R08.fill();
        R05.loadForm();
        R05.build();
        R09.loadForm();
        R09.build();
        R10.loadForm();
        R10.build();
        R57.loadForm();
        R57.build();
        R02.loadForm();
        R02.build();
        R12.loadForm();
        R12.build();
        //merge complete forms
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        try {
            mergerUtility.addSource(new File(outputDirectoryPath + R02.resourceFileName));
            mergerUtility.addSource(new File(outputDirectoryPath + R12.resourceFileName));
            mergerUtility.addSource(new File(outputDirectoryPath + R08.resourceFileName));
            mergerUtility.addSource(new File(outputDirectoryPath + R05.resourceFileName));
            mergerUtility.addSource(new File(outputDirectoryPath + R09.resourceFileName));
            mergerUtility.addSource(new File(outputDirectoryPath + R10.resourceFileName));
            mergerUtility.addSource(new File(outputDirectoryPath + R57.resourceFileName));
            mergerUtility.setDestinationFileName(outputDirectoryPath + "CompleteTaxForm.pdf");
            mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            PDFfile = new File(outputDirectoryPath + "CompleteTaxForm.pdf");
            //need to replace dummy fields

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
