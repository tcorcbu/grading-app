package db;

import gui.Gradable;
import gui.Grade;
import gui.Student;
import db.Globals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeService {

    private GradeService(){};

    public static PreparedStatement insert(Grade grade, Student student) {

        Connection conn = MySqlConnection.getConnection();
        String query = "INSERT INTO Grades (school_id, gradable_name, class_id, points_lost, student_weight, comment) VALUES(?,?,?,?,?,?)";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,student.getSchoolID());
            statement.setString(2,grade.getGradable().getName());
			statement.setInt(3,Globals.class_id());
            statement.setInt(4,grade.getPoints());
            statement.setInt(5,grade.getStudentWeight());
            statement.setString(6,grade.getNote());
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
    }

	public static PreparedStatement drop(Gradable gradable) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM Grades WHERE gradable_name = ? AND class_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql);
			statement.setString(1,gradable.getName());
            statement.setInt(2,Globals.class_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
    }
	
    public static ArrayList<Grade> getAllGradablesForStudent(Student student) {
        ArrayList<Gradable>res = GradableService.getAll(Globals.class_id());
		ArrayList<Grade>gradeList = new ArrayList<Grade>();
        Connection conn = MySqlConnection.getConnection();
        for (Gradable gradable : res) {
            String query = "SELECT * FROM Grades WHERE school_id = ? AND gradable_name = ? AND class_id = ?";
            try {
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, student.getSchoolID());
                statement.setString(2, gradable.getName());
				statement.setInt(3, Globals.class_id());
				
                ResultSet rs = statement.executeQuery();
				Grade grade = new Grade(gradable);
                while (rs.next()) {
                    grade.setPointsLost(rs.getInt("points_lost"));
                    grade.setStudentWeight(rs.getInt("student_weight"));
                    grade.setNote(rs.getString("comment"));
                }
				gradeList.add(grade);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return gradeList;
    }

	public static PreparedStatement updatePointsLost(Grade grade, String school_id, int pl) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Grades SET points_lost = ? WHERE gradable_name = ? AND school_id = ? AND class_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, pl);
            statement.setString(2,grade.getGradable().getName());
			statement.setString(3,school_id);
			statement.setInt(4,Globals.class_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
	}
	
	public static PreparedStatement updateStudentWeight(Grade grade, String school_id, int sw) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Grades SET student_weight = ? WHERE gradable_name = ? AND school_id = ? AND class_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, sw);
            statement.setString(2,grade.getGradable().getName());
			statement.setString(3,school_id);
			statement.setInt(4,Globals.class_id());
            // statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
	}
	
	public static PreparedStatement updateComment(Grade grade, String school_id, String c) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Grades SET comment = ? WHERE gradable_name = ? AND school_id = ? AND class_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, c);
            statement.setString(2,grade.getGradable().getName());
			statement.setString(3,school_id);
			statement.setInt(4,Globals.class_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
	}
	
	public static PreparedStatement dropStudentGrades(String school_id) {
        Connection conn = MySqlConnection.getConnection();
        String query = "DELETE FROM Grades WHERE school_id=?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, school_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
    }
}
