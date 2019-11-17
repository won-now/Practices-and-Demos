import java.io.*;

public class BufferedByteStream {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("Track 12.mp3");
            BufferedInputStream bis = new BufferedInputStream(fis);
            FileOutputStream fos = new FileOutputStream("newTrack.mp3");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte []input = new byte[2000];
            long before = System.currentTimeMillis();
            int count = 0;
            while (bis.read(input) != -1){
                bos.write(input);
                count++;
            }
//            System.out.println("byte:1000,buffer:default " + (System.currentTimeMillis() - before) + "ms");
//            System.out.println("count: " + count);
//byte:1000,buffer:default 26ms
//count: 7947
            System.out.println("byte:1000,buffer:20000 " + (System.currentTimeMillis() - before) + "ms");
            System.out.println("count: " + count);
//            byte:2000,buffer:20000 15ms
//            count: 3974

//            byte:1000,buffer:default 24ms
//            count: 3974
            bis.close();
            fis.close();
            bos.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
