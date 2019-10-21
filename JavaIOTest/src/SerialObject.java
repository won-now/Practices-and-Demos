import java.io.*;

public class SerialObject {
    public static void main(String[] args) throws Exception{
        File file = new File("serialtest.txt");
            //序列化
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
//            Person p = new Person("001", "张三",20);
//            oos.writeObject(p);
//            oos.flush();
//            oos.close();
            //反序列化
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Person p = (Person) ois.readObject();
            System.out.println(p);
    }
}
