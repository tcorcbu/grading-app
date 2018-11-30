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

    public static boolean containsStudent(int studentId, int classId) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT student_id FROM StudentsClasses WHERE class_id = ? AND student_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, classId);
            statement.setInt(2,studentId);
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
