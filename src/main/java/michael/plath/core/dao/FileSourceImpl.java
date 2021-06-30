package michael.plath.core.dao;

import java.io.File;
import java.io.FileInputStream;

public class FileSourceImpl implements FileSource{

    File file;
    FileInputStream fileInputStream;

    @Override
    public void setSource(File file) {
        this.file = file;

    }

    @Override
    public boolean connect(){
        boolean result = true;
        try {
            fileInputStream = new FileInputStream(file);
        }catch (Exception e){
            e.getMessage();
            result = false;
        }
        return result;
    }

    @Override
    public FileInputStream getData() {
        return fileInputStream;
    }
}
