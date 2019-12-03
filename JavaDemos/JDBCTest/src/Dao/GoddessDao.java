package Dao;

import Models.GoddessModel;

import java.util.List;

public interface GoddessDao {
    //
    public List<GoddessModel> getAll();

    public GoddessModel get(int id);

    public int add(GoddessModel goddess);

    public int update();

    public int delete();
}
