package Dao;

import Models.GoddessModel;
import Utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoddessDaoImpl implements GoddessDao {
    @Override
    public List<GoddessModel> getAll() {
        ArrayList<GoddessModel> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        String sql = "select * from imooc_goddess";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                GoddessModel goddess = new GoddessModel();
                goddess.setId(rs.getInt("id"));
                goddess.setUser_name(rs.getString("user_name"));
                goddess.setSex(rs.getInt("sex"));
                goddess.setAge(rs.getInt("age"));
                goddess.setEmail(rs.getString("email"));
                goddess.setBirthday(rs.getDate("birthday"));
                goddess.setMobile(rs.getString("mobile"));
                list.add(goddess);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,rs);
        }
        return list;
    }

    //根据id查询
    @Override
    public GoddessModel get(int id) {
        Connection conn = DBUtil.getConnection();
        String sql = "select * from imooc_goddess where id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        GoddessModel goddess = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                goddess = new GoddessModel();
                goddess.setId(rs.getInt("id"));
                goddess.setUser_name(rs.getString("user_name"));
                goddess.setSex(rs.getInt("sex"));
                goddess.setAge(rs.getInt("age"));
                goddess.setEmail(rs.getString("email"));
                goddess.setBirthday(rs.getDate("birthday"));
                goddess.setMobile(rs.getString("mobile"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,rs);
        }
        return goddess;
    }

    @Override
    public int add(GoddessModel goddess) {
        Connection conn = null;
        String sql = "insert into imooc_goddess(user_name,sex,age," +
                "birthday,email,mobile,create_user,create_date," +
                "update_user,update_date,isdel) values" +
                "(?,?,?,?,?,?,?,current_date(),?,current_date(),?)";
        PreparedStatement pstmt = null;
        int count = -1;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,goddess.getUser_name());
            pstmt.setInt(2,goddess.getSex());
            pstmt.setInt(3,goddess.getAge());
            pstmt.setDate(4,new Date(goddess.getBirthday().getTime()));
            pstmt.setString(5,goddess.getEmail());
            pstmt.setString(6,goddess.getMobile());
            pstmt.setString(7,goddess.getCreate_user());
            pstmt.setString(8,goddess.getUpdate_user());
            pstmt.setInt(9,goddess.getId());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(conn,pstmt,null);
        }
        return count;
    }

    @Override
    public int update(String user_name,int id) {
        String sql = "update imooc_goddess set user_name=?,update_date=current_date() where id=?";
         return DBUtil.executeDML(sql,user_name,id);
    }

    @Override
    public int delete(int id)
    {
        String sql = "delete from imooc_goddess where id=?";
        return DBUtil.executeDML(sql,id);
    }
}
