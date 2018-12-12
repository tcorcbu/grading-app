package db;

import objects.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Globals;

public class StudentClassService {
    public static PreparedStatement insertStudentClass(String school_id){
        Connection conn = MySqlConnection.getConnection();
        String sql = "INSERT INTO StudentsClasses (class_id, school_id) VALUES (?,?)";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, Globals.class_id());
            statement.setString(2, school_id);
            // statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
    }

    public static PreparedStatement deleteStudentClass(String school_id){
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM StudentsClasses WHERE class_id = ? and school_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, Globals.class_id());
            statement.setString(2, school_id);
            // statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
    }

    public static List<String> getAllStudentsId(int classId){
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT school_id FROM StudentsClasses WHERE class_id = ?";
        List<String>res = new ArrayList<String>();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, classId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("school_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean containsStudent(String school_id) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT school_id FROM StudentsClasses WHERE class_id = ? AND school_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, Globals.class_id());
            statement.setString(2,school_id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
