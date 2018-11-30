package db;

import gui.Student;

import java.sql.*;

public class StudentService {
    public static int insertStudent(Student student){
        Connection conn = MySqlConnection.getConnection();
        String sql = "INSERT INTO Students (school_id,name,type) VALUES(?,?,?)";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, student.getSchoolID());
            statement.setString(2, student.getName());
            statement.setString(3, student.getYear());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                res = rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void dropStudent(Student student) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM Students WHERE school_id = ?";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, student.getSchoolID());
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getId(Student student) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT student_id FROM Students WHERE school_id = ?";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, student.getSchoolID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res = rs.getInt("student_id");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Student getStudentById(int id) {
        Connection conn = MySqlConnection.getConnection();
        Student student = new Student();
        String sql = "SELECT * FROM Students WHERE student_id = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String[]strs = name.split(" ");
                if (strs.length == 2) {
                    student.setFirstName(strs[0]);
                    student.setLastName(strs[1]);
                } else {
                    student.setFirstName(name);
                }
                student.setSchoolID(rs.getString("school_id"));
                student.setYear(rs.getString("type"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public static int studentInDb(String id) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT * FROM Students WHERE school_id = ?";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);
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
