package httm.dao;

import java.sql.*;

public class DAO  {
    public static Connection con;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/httm?autoReconnect=true&useSSL=false";
//    private static final String DB_URL = "jdbc:mysql://26.41.147.33:3306/btl_httm?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
//    private static final String USER = "khanh";
    private static final String PASSWORD = "1234";
//    private static final String PASSWORD = "khoi21102004";
    private static final String DB_CLASS = "com.mysql.cj.jdbc.Driver";

    public DAO(){
        if(con == null){
            try {
                Class.forName(DB_CLASS);
                con = DriverManager.getConnection (DB_URL, USER, PASSWORD);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
