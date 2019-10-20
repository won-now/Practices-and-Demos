import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyByteStream {
    public static void main(String[] args) {
        try {
//            FileInputStream fis = new FileInputStream("lu.jpg");
            FileInputStream fis = new FileInputStream("test2.txt");
//            FileOutputStream fos = new FileOutputStream("deer.jpg");
            FileOutputStream fos = new FileOutputStream("copytest2.txt");
            byte[] input = new byte[1024];
            int len;
            while ((len = fis.read(input)) != -1){
//                fos.write(input);
                String s = new String(input,0,len,"GBK");
//                String s = new String(input,"GBK");
                byte[] output = s.getBytes("UTF-8");
                System.out.println(s);
                fos.write(output);
            }
            //一定要记得输入输出流的关闭
            fos.close();
            fis.close();
        }catch (IOException e){
            e.printStackTrace(); }
    }
}
