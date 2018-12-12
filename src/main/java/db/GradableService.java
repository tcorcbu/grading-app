package db;

import objects.Gradable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradableService {
    private GradableService(){};

    public static PreparedStatement insert(Gradable gradable) {
        Connection conn = MySqlConnection.getConnection();
        String query = "INSERT INTO Gradables (name,class_id,total_points,relative_weight,category_name) VALUES(?,?,?,?,?)";
        PreparedStatement statement = null;
		try {
            statement = conn.prepareStatement(query);
            statement.setString(1, gradable.getName());
            statement.setInt(2, Globals.class_id());
            statement.setInt(3, gradable.getPoints());
            statement.setInt(4, gradable.getIntraCategoryWeight());
            statement.setString(5, gradable.getType().getType());
            // statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
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
                String name = rs.getString("name");
                int points = rs.getInt("total_points");
                int intraCategoryWeight = rs.getInt("relative_weight");
                String category_name = rs.getString("category_name");
                Gradable gradable = new Gradable();
                gradable.setName(name);
                gradable.setTotal(points);
                gradable.setIntraCategoryWeight(intraCategoryWeight);
                gradable.setType(CategoryService.getByName(category_name));
                res.add(gradable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static PreparedStatement drop(Gradable gradable) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM Gradables WHERE name = ? AND class_id = ?";
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
	
	public static PreparedStatement updatePoints(Gradable gradable, int tp) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Gradables SET total_points = ? WHERE name = ? AND class_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, tp);
            statement.setString(2,gradable.getName());
			statement.setInt(3,Globals.class_id());
            // statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
	}
	
	public static PreparedStatement updateWeight(Gradable gradable, int rw) {
		Connection conn = MySqlConnection.getConnection();
        String query = "UPDATE Gradables SET relative_weight = ? WHERE name = ? AND class_id = ?";
		PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, rw);
            statement.setString(2,gradable.getName());
			statement.setInt(3,Globals.class_id());
            // statement.executeUpdate();            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
	}
}
