package db;

import gui.Gradable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradableService {
    private GradableService(){};

    public static void insert(Gradable gradable, int classId) {
        Connection conn = MySqlConnection.getConnection();
        String query = "INSERT INTO Gradables (name,class_id,total_points,relative_weight,category_id) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, gradable.getName());
            statement.setInt(2, classId);
            statement.setInt(3, gradable.getPoints());
            statement.setInt(4, gradable.getIntraCategoryWeight());
            statement.setInt(5, gradable.getType().getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                gradable.setGradableId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Gradable> getAll(int classId) {
        Connection conn = MySqlConnection.getConnection();
        String query = "SELECT * FROM Gradables WHERE class_id=?";
        ArrayList<Gradable>res = new ArrayList<Gradable>();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, classId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int gradableId = rs.getInt("gradable_id");
                String name = rs.getString("name");
                int points = rs.getInt("total_points");
                int intraCategoryWeight = rs.getInt("relative_weight");
                int categoryId = rs.getInt("category_id");
                Gradable gradable = new Gradable();
                gradable.setGradableId(gradableId);
                gradable.setName(name);
                gradable.setTotal(points);
                gradable.setIntraCategoryWeight(intraCategoryWeight);
                gradable.setType(CategoryService.getById(categoryId));
                res.add(gradable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void drop(Gradable gradable) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM Gradables WHERE gradable_id=?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,gradable.getGradableId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static void updatePoints(Gradable gradable, int tp) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Gradables SET total_points = ? WHERE gradable_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, tp);
            statement.setInt(2,gradable.getGradableId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static void updateWeight(Gradable gradable, int rw) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Gradables SET relative_weight = ? WHERE gradable_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, rw);
            statement.setInt(2,gradable.getGradableId());
            statement.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
