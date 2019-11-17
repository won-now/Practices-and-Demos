import java.io.*;

public class FileReaderWriter {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("chartest_buff.txt");
            FileWriter fw = new FileWriter("file_writer.txt");
            BufferedReader br = new BufferedReader(fr);
            BufferedWriter bw = new BufferedWriter(fw);
            String line;
            while ((line = br.readLine()) != null){
                bw.write(line + "\n");
            }
            bw.flush();
            bw.close();
            br.close();
            fw.close();
            fr.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
