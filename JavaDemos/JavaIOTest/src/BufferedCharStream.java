import java.io.*;

public class BufferedCharStream {
    public static void main(String[] args) {
        try {
//            File file = new File("copytest2.txt");
            FileInputStream fis = new FileInputStream("test2.txt");
            FileOutputStream fos = new FileOutputStream("chartest_buff.txt");
            InputStreamReader inReader = new InputStreamReader(fis,"GBK");
            OutputStreamWriter outWriter = new OutputStreamWriter(fos,"utf-8");
            BufferedReader br = new BufferedReader(inReader);
            BufferedWriter bw = new BufferedWriter(outWriter);
            PrintWriter pw = new PrintWriter(outWriter,true);
            String line;
            while ((line = br.readLine()) != null){
//                bw.write(line);
//                bw.newLine();
                pw.println(line);
            }
//            bw.flush();
            bw.close();
            br.close();
            inReader.close();
            fis.close();
            outWriter.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
