package michael.plath.model;

import michael.plath.core.Main;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public abstract class Form {
    protected String resourceFileName;
    protected String stateCode;
    protected InputStream in;
    protected PDDocument doc;
    protected PDDocumentCatalog catalog;
    protected PDAcroForm form;
    protected File completedFile;

    //structure of fields
    //list of root field names as array?

    public void loadForm(){
        String resourcePath = "forms/" + stateCode + "/" + resourceFileName;
        in = Main.class.getClassLoader().getResourceAsStream("forms/" + stateCode + "/" + resourceFileName);
        try {
            doc = PDDocument.load(in);
            catalog = doc.getDocumentCatalog();
            form = catalog.getAcroForm();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public File getCompletedFile(){
        return completedFile;
    }


    abstract void build();

    void displayAllFields(){
        Iterator<PDField> iterator = form.getFieldIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next().getFullyQualifiedName());
        }
    }

    //build additional page()
    //merge pages
}
