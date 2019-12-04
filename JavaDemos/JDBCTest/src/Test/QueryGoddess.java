package Test;

import Dao.GoddessDaoImpl;
import Models.GoddessModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QueryGoddess {
    public static void main(String[] args) {
        GoddessModel goddess = new GoddessModel();
        List<GoddessModel> list = null;
        GoddessDaoImpl daoImpl = new GoddessDaoImpl();
        list = daoImpl.getAll();
        for(GoddessModel g : list){
            System.out.println(g.getUser_name());
        }
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse("1997-05-12");
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        goddess.setUser_name("小k");
//        goddess.setBirthday(date);
//        goddess.setMobile("13568716901");
//        goddess.setEmail("8687121@163.com");
//        goddess.setCreate_user("ADMIN");
//        goddess.setUpdate_user("ADMIN");
//        goddess.setAge(13);
//        goddess.setSex(0);
//        goddess.setId(4);
//        System.out.println(daoImpl.add(goddess));
        System.out.println(daoImpl.delete(2));
        daoImpl.update("小k",4);

    }
}
