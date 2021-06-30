package michael.plath.core.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public interface FileSource {

    void setSource(File file);
    boolean connect();
    FileInputStream getData();
}
