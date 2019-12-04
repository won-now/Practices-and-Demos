package Dao;

import Models.GoddessModel;

import java.util.List;

public interface GoddessDao {
    //查询全部
    public List<GoddessModel> getAll();

    //根据id查询
    public GoddessModel get(int id);
    //添加
    public int add(GoddessModel goddess);
    //更新
    public int update(String user_name,int id);
    //删除
    public int delete(int id);
}
