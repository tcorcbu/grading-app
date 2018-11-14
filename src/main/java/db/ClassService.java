package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassService {
    private ClassService(){}
    //get All class names
    public static List<String> getClassNames(){
        Connection conn = MySqlConnection.getConnection();
        String query = "SELECT name FROM Classes";
        List<String>res = new ArrayList<String>();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("name"));
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    //insert a new class into db
    public static void insertClass(String className){
        Connection conn = MySqlConnection.getConnection();
        String query = "INSERT INTO Classes (name,status) VALUES(?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, className);
            statement.setString(2, "open");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer getId(String className){
        Connection conn = MySqlConnection.getConnection();
        String query = "SELECT class_id FROM Classes WHERE name = ?";
        Integer res = null;
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,className);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res = new Integer(rs.getInt("class_id"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }
}
