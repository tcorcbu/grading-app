package db;

import gui.Student;

import java.sql.*;

public class StudentService {
    public static PreparedStatement insertStudent(Student student){
        Connection conn = MySqlConnection.getConnection();
        String sql = "INSERT INTO Students (school_id,name,type) VALUES(?,?,?)";
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(sql);
            statement.setString(1, student.getSchoolID());
            statement.setString(2, student.getName());
            statement.setString(3, student.getYear());
            // statement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static PreparedStatement dropStudent(Student student) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM Students WHERE school_id = ?";
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(sql);
            statement.setString(1, student.getSchoolID());
            // statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
    }

    // public static int getId(Student student) {
        // Connection conn = MySqlConnection.getConnection();
        // String sql = "SELECT school_id FROM Students WHERE school_id = ?";
        // int res = -1;
        // try{
            // PreparedStatement statement = conn.prepareStatement(sql);
            // statement.setString(1, student.getSchoolID());
            // ResultSet rs = statement.executeQuery();
            // while (rs.next()) {
                // res = rs.getInt("student_id");
            // }
        // }catch (SQLException e) {
            // e.printStackTrace();
        // }
        // return res;
    // }

    public static Student getStudentById(String id) {
        Connection conn = MySqlConnection.getConnection();
        Student student = new Student();
        String sql = "SELECT * FROM Students WHERE school_id = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);
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
                student.setSchoolID(id);
                student.setYear(rs.getString("type"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public static boolean studentInDb(Student s) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT * FROM Students WHERE school_id = ?";
        boolean res = false;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, s.getSchoolID());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                res = true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res; 
    }

}
