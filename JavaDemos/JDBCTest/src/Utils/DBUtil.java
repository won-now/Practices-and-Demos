package Utils;

import java.sql.*;

public class DBUtil {

    public static final String URL = "jdbc:mysql://127.0.0.1:3306/imooc?serverTimezone=UTC ";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeAll(Connection conn, Statement stmt, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       if(conn != null){
           try {
               conn.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

    }
}
