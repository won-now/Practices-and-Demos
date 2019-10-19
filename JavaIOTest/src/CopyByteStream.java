import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyByteStream {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("lu.jpg");
            FileOutputStream fos = new FileOutputStream("deer.jpg");
            byte[] input = new byte[1024];
            while (fis.read(input) != -1){
                fos.write(input);
            }

            fos.close();
            fis.close();
        }catch (IOException e){
            e.printStackTrace(); }

    }
}
