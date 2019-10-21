import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class FileDemo {
    public static void main(String[] args) throws Exception{
        File file = new File("../Test");
        if(!file.exists()){
            if(file.mkdir())
                System.out.println("Successfully created directory!");
        }
        //过滤文件
        String[] files = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith("doc");
            }
        });
        for (String s : files){
            System.out.println(s);
        }

        File[] files1 = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return !file.canWrite();
            }
        });
        for (File f : files1)
            System.out.println(f);

        listAllFiles(new File("D:" + File.separator + "ffmpeg"));
    }

    static void listAllFiles(File dir){
//        File dir = new File("D:" + File.separator + "ffmpeg");
        if(!dir.exists())
            throw new IllegalArgumentException(dir + "不存在");
        if(!dir.isDirectory())
            throw new IllegalArgumentException(dir + "不是目录");

        File[] files = dir.listFiles();
        if(files != null && files.length > 0){
            for (File directory : files) {
                if (directory.isDirectory())
                    listAllFiles(directory);
                else
                    System.out.println(directory);
            }
        }
    }
}
