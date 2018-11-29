package db;

import gui.Gradable;
import gui.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeService {

    private GradeService(){};
    //insert into grades table with a student and a gradable (with full fields set)
    public static void insert(Gradable gradable, Student student) {
        int studentId = StudentService.getId(student);
        Connection conn = MySqlConnection.getConnection();
        String query = "INSERT INTO Grades (student_id, gradable_id, points_lost, student_weight, comment)" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,studentId);
            statement.setInt(2,gradable.getGradableId());
            statement.setInt(3,gradable.getPointsLost());
            statement.setInt(4,gradable.getStudentWeight());
            statement.setString(5,gradable.getNote());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public static void drop(Gradable gradable) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM Grades WHERE gradable_id=?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,gradable.getGradableId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    public static ArrayList<Gradable> getAllGradablesForStudent(Student student, int classId) {
        int studentId = StudentService.getId(student);
        ArrayList<Gradable>res = GradableService.getAll(classId);
        Connection conn = MySqlConnection.getConnection();
        for (Gradable gradable : res) {
            String query = "SELECT * FROM Grades WHERE student_id = ? and gradable_id = ?";
            try {
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, studentId);
                statement.setInt(2, gradable.getGradableId());
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

	public static void updatePointsLost(int gradable_id, int student_id, int pl) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Grades SET points_lost = ? WHERE gradable_id = ? AND student_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, pl);
            statement.setInt(2,gradable_id);
			statement.setInt(3,student_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static void updateStudentWeight(int gradable_id, int student_id, int sw) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Grades SET student_weight = ? WHERE gradable_id = ? AND student_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, sw);
            statement.setInt(2,gradable_id);
			statement.setInt(3,student_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static void updateComment(int gradable_id, int student_id, String c) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Grades SET comment = ? WHERE gradable_id = ? AND student_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, c);
            statement.setInt(2,gradable_id);
			statement.setInt(3,student_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	

}
