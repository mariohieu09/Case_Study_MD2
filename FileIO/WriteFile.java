package FileIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class WriteFile <T>{
    public void writeFile(File file, List<T> list){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
