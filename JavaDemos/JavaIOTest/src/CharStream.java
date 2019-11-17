import java.io.*;

public class CharStream {
    public static void main(String[] args) {
        try {
            File file = new File("copytest2.txt");
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream("chartest.txt");

            InputStreamReader inReader = new InputStreamReader(fis);

            OutputStreamWriter outWriter = new OutputStreamWriter(fos);
            char[] input = new char[100];
            inReader.read(input);
            outWriter.write(input);
            System.out.println(new String(input));
            inReader.close();
            fis.close();
            outWriter.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
