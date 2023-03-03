package michael.plath.model;

import michael.plath.core.Main;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class Form {
    protected String resourceFileName;
    protected String stateCode;
    protected InputStream in;
    protected PDDocument doc;
    protected List<PDField> fieldRootNames;
    protected PDDocumentCatalog catalog;
    protected PDAcroForm form;
    protected int extraPagesNeeded;
    protected File completedFile;
    protected String outputDirectoryPath; //TODO:LOAD FROM PROPERTIES

    //structure of fields
    //list of root field names as array?

    //LOAD -> BUILD -> MERGE -> FILL -> CLEAN


    public void loadForm(){
        String resourcePath = "forms/" + stateCode + "/" + resourceFileName;
        in = Main.class.getClassLoader().getResourceAsStream("forms/" + stateCode + "/" + resourceFileName);
        try {
            doc = PDDocument.load(in);
            catalog = doc.getDocumentCatalog();
            form = catalog.getAcroForm();
            fieldRootNames = doc.getDocumentCatalog().getAcroForm().getFields();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }finally {

        }

    }

    public File getCompletedFile(){
        return completedFile;
    }


    abstract void build();

    protected void merge(){
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        int extraPagesNeeded; //FIX THIS
        int extraRows = 59;
        try {
            mergerUtility.addSource(new File("src\\main\\resources\\forms\\" + stateCode + "\\temp\\original.pdf"));
            extraPagesNeeded = (extraRows/24) + 1;
            for(int i = extraPagesNeeded; i > 0; i--) {
                mergerUtility.addSource(new File("src\\main\\resources\\forms\\" + stateCode + "\\temp\\" + i + ".pdf"));
            }
            mergerUtility.setDestinationFileName(String.valueOf(new File("src\\main\\resources\\forms\\" + stateCode + "\\temp\\" + resourceFileName)));
            mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void clean(){
        File directory = new File("src\\main\\resources\\forms\\" + stateCode + "\\temp\\");
        List<File> filesInDirectory = Arrays.asList(directory.listFiles());
        boolean success = false;
        for (File current : filesInDirectory) {
            if (!current.getName().equalsIgnoreCase(resourceFileName)) {
                success = current.delete();
            }
        }
    }





    abstract void fill();

    void displayAllFields(){
        Iterator<PDField> iterator = form.getFieldIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next().getFullyQualifiedName());
        }
    }

    //build additional page()
    //merge pages
}
