package michael.plath.model;

import michael.plath.core.dao.FileSource;

import java.io.File;
import java.util.List;
import org.json.JSONObject;

public abstract class PreparedForm {

    String name;
    String stateCode;
    File PDFfile;
    List<Form> formList;
    Constants constants;
    //TODO:ADD OVERALL TOTALS TO GLOBAL SCOPE FOR FILLING IN FIELDS APPEARING ON MULTIPLE FORMS
    JSONObject tallies;

    public PreparedForm(){
        Constants constants = new Constants();
        tallies = new JSONObject();
    }

    public abstract void build();

    public abstract void loadTallies();


}
