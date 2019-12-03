package Test;

import Dao.GoddessDaoImpl;
import Models.GoddessModel;

import java.util.ArrayList;
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

    }
}
