package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {
    private static Connection conn;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = (Connection) DriverManager.getConnection(MySQLDBUtil.URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private MySqlConnection(){}

    public static Connection getConnection(){
        return conn;
    }

    public static void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
