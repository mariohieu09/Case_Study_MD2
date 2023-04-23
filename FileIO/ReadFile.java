package FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadFile<T> {
    public List<T> readFile(File file){
        List<T> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            if(fis.available() == 0){
                 return list;
            }else{
                ObjectInputStream ois = new ObjectInputStream(fis);
                list = (List<T>) ois.readObject();
                ois.close();
            }
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
