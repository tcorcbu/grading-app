package db;

import gui.Gradable;
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

    public static PreparedStatement insert(Gradable gradable, Student student) {

        Connection conn = MySqlConnection.getConnection();
        String query = "INSERT INTO Grades (school_id, gradable_name, class_id, points_lost, student_weight, comment) VALUES(?,?,?,?,?,?)";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1,student.getSchoolID());
            statement.setString(2,gradable.getName());
			statement.setInt(3,Globals.class_id());
            statement.setInt(4,gradable.getPointsLost());
            statement.setInt(5,gradable.getStudentWeight());
            statement.setString(6,gradable.getNote());
            // statement.executeUpdate();
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
            // statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
    }
	
    public static ArrayList<Gradable> getAllGradablesForStudent(Student student) {
        ArrayList<Gradable>res = GradableService.getAll(Globals.class_id());
        Connection conn = MySqlConnection.getConnection();
        for (Gradable gradable : res) {
            String query = "SELECT * FROM Grades WHERE school_id = ? AND gradable_name = ? AND class_id = ?";
            try {
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, student.getSchoolID());
                statement.setString(2, gradable.getName());
				statement.setInt(3, Globals.class_id());
				
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    gradable.setPointsLost(rs.getInt("points_lost"));
                    gradable.setStudentWeight(rs.getInt("student_weight"));
                    gradable.setNote(rs.getString("comment"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

	public static PreparedStatement updatePointsLost(Gradable gradable, String school_id, int pl) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Grades SET points_lost = ? WHERE gradable_name = ? AND school_id = ? AND class_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, pl);
            statement.setString(2,gradable.getName());
			statement.setString(3,school_id);
			statement.setInt(4,Globals.class_id());
            // statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
	}
	
	public static PreparedStatement updateStudentWeight(Gradable gradable, String school_id, int sw) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Grades SET student_weight = ? WHERE gradable_name = ? AND school_id = ? AND class_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, sw);
            statement.setString(2,gradable.getName());
			statement.setString(3,school_id);
			statement.setInt(4,Globals.class_id());
            // statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
	}
	
	public static PreparedStatement updateComment(Gradable gradable, String school_id, String c) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Grades SET comment = ? WHERE gradable_name = ? AND school_id = ? AND class_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, c);
            statement.setString(2,gradable.getName());
			statement.setString(3,school_id);
			statement.setInt(4,Globals.class_id());
            // statement.executeUpdate();
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
            // statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
    }
}
