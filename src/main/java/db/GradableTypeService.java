package db;

import gui.GradableType;

import java.sql.*;
import java.util.ArrayList;

public class GradableTypeService {
    public static int insertGradableType(GradableType gradableType,int class_id){
        Connection conn = MySqlConnection.getConnection();
        String sql = "INSERT INTO categories (name,class_id,grad_weight,ugrad_weight) VALUES(?,?,?,?)";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
            statement.setString(1, gradableType.getType());
			statement.setString(2, String.valueOf(class_id));
            statement.setString(3, String.valueOf(gradableType.getWeight("Undergraduate")));
            statement.setString(4, String.valueOf(gradableType.getWeight("Graduate")));
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

    public static void dropGradableType(String name,int class_id) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM categories WHERE name = ? AND class_id = ?";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
			statement.setString(2, String.valueOf(class_id));
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getId(GradableType gradableType,int class_id) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT gradableType_id FROM categories WHERE name = ? AND class_id = ?";
        int res = -1;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, gradableType.getType());
			statement.setString(2, String.valueOf(class_id));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res = rs.getInt("gradableType_id");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static GradableType getGradableTypeById(int id) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT * FROM categories WHERE category_id = ?";
		GradableType gt = new GradableType();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
				gt = new GradableType(rs.getString("name"), rs.getInt("grad_weight"), rs.getInt("ugrad_weight"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return gt;
    }
	
    public static ArrayList<Integer> getAllGradableTypeId(int classId){
        Connection conn = MySqlConnection.getConnection();
        String sql = "SELECT category_id FROM categories WHERE class_id = ?";
        ArrayList<Integer>res = new ArrayList<Integer>();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, classId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res.add(rs.getInt("category_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}