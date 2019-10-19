import java.io.FileInputStream;

import java.io.IOException;

public class ReadByteStream {
    public static void main(String[] args){
        try {
            FileInputStream fis = new FileInputStream("test.txt");
            byte []input = new byte[1024];
            fis.read(input);
            String inputString = new String(input);
            System.out.println(inputString);
            fis.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
