import java.io.FileOutputStream;
import java.io.IOException;

public class WriteByteStream {
    public static void main(String[] args) {
        try {
            FileOutputStream fos = new FileOutputStream("wtest.txt");
            String outString = "输出这个字符串";
            byte []output = outString.getBytes();
            fos.write(output);
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
