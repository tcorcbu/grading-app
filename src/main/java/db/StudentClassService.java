package db;

import gui.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentClassService {
    public static void insertStudentClass(int classId, int studentId){
        Connection conn = MySqlConnection.getConnection();
        String sql = "INSERT INTO StudentsClasses (class_id, student_id) VALUES (?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, classId);
            statement.setInt(2, studentId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudentClass(int classId, int studentId){
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM StudentsClasses WHERE class_id = ? and student_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, classId);
            statement.setInt(2, studentId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> getAllStudentsId(int classId){
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT student_id FROM StudentsClasses WHERE class_id = ?";
        List<Integer>res = new ArrayList<Integer>();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, classId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res.add(rs.getInt("student_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int studentInClass(int class_id, int student_id) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT * FROM StudentsClasses WHERE class_id = ? and student_id = ?";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, class_id);
            statement.setInt(2, student_id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                res = rs.getInt("student_id");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res; 
    }

}
