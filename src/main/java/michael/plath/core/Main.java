package michael.plath.core;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class Main {

    public static void main(String[] args){

        //load properties
        Constants constants = new Constants();

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
        R57.displayAllFields();

        //merge complete forms
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        try {
            mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\Tester\\" + R02.resourceFileName));
            mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\Tester\\" + R12.resourceFileName));
            mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\Tester\\" + R08.resourceFileName));
            mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\Tester\\" + R05.resourceFileName));
            mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\Tester\\" + R09.resourceFileName));
            mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\Tester\\" + R10.resourceFileName));
            mergerUtility.addSource(new File("C:\\USAWine\\New Jersey\\Tester\\" + R57.resourceFileName));
            mergerUtility.setDestinationFileName("C:\\USAWine\\New Jersey\\Tester\\CompleteTaxForm.pdf");
            mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            //need to replace dummy fields

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        /*


        //create new PDF from source
        static PDAcroForm form;
        PDDocument doc = new PDDocument();
        doc.getDocumentCatalog().setAcroForm(form);

        //create new page from source. check that acroforms and titles still work
        File nextFile = new File();
        PDDocument otherDoc = PDDocument.load(nextFile)
        PDPage page = otherDoc.importPage();
        doc.addPage(page);
        //fill in widgets from data using dataSet

         */


    }

    public static Properties readPropertiesFile(String filename) throws IOException {
        FileInputStream fileInputStream = null;
        Properties properties = null;
        try{
            fileInputStream = new FileInputStream(filename);
            properties = new Properties();
            properties.load(fileInputStream);
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());

        }finally {
            fileInputStream.close();
        }

        return properties;



    }


}
