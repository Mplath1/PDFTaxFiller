package michael.plath.model;

import michael.plath.core.dao.FileSource;

import java.io.File;
import java.util.List;

public abstract class PreparedForm {

    String name;
    String stateCode;
    File PDFfile;
    List<Form> formList;
    Constants constants;

    public PreparedForm(){
        Constants constants = new Constants();
    }

    public abstract void build();


}
