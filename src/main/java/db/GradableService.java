package db;

import gui.Gradable;
import db.GradableTypeService;

import java.sql.*;
import java.util.ArrayList;

public class GradableService {
    public static int insertGradable(Gradable gradable,int class_id){
        Connection conn = MySqlConnection.getConnection();
        String sql = "INSERT INTO Gradables (name,class_id,total_points,relative_weight,category_id) VALUES(?,?,?,?,?)";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, gradable.getName());
			statement.setInt(2, class_id);
            statement.setInt(3, gradable.getPoints());
            statement.setInt(4, gradable.getIntraCategoryWeight());
			statement.setInt(5, GradableTypeService.getId(gradable.getType(),class_id));
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

    public static void dropGradable(Gradable gradable,int class_id) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM Gradables WHERE name = ? AND class_id = ?";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, gradable.getName());
			statement.setInt(2, class_id);
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getId(Gradable gradable, int class_id) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT gradable_id FROM Gradables WHERE name = ? AND class_id = ?";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, gradable.getName());
			statement.setInt(2, class_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res = rs.getInt("gradable_id");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Gradable getGradableById(int id) {
        Connection conn = MySqlConnection.getConnection();
        Gradable gradable = new Gradable();
        String sql = "SELECT * FROM Gradables WHERE gradable_id = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                gradable.setName(rs.getString("name"));
				gradable.setPoints(rs.getInt("total_points"));
                gradable.setID(rs.getInt("gradable_id"));
                gradable.setType(GradableTypeService.getGradableTypeById(rs.getInt("category_id")));
				gradable.setIntraCategoryWeight(rs.getInt("relative_weight"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return gradable;
    }

	public static ArrayList<Integer> getAllGradableId(int classId){
	Connection conn = MySqlConnection.getConnection();
	String sql = "SELECT gradable_id FROM gradables WHERE class_id = ?";
	ArrayList<Integer>res = new ArrayList<Integer>();
	try {
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, classId);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			res.add(rs.getInt("gradable_id"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return res;
    }
}