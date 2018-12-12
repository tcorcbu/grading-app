package db;

import objects.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.Globals;

public class CategoryService {
    private CategoryService(){};
	
    public static PreparedStatement insert(Category category){

        Connection conn = MySqlConnection.getConnection();
        String query = "INSERT INTO Categories (name,class_id,ugrad_weight,grad_weight) VALUES(?,?,?,?)";
        PreparedStatement statement = null;
		try {
            statement = conn.prepareStatement(query);
            statement.setString(1, category.getType());
            statement.setInt(2,Globals.class_id());
            statement.setInt(3, category.getUndergradWeight());
            statement.setInt(4,category.getGraduateWeight());

            // statement.executeUpdate();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
		return statement;
    }

    public static PreparedStatement drop(String name) {
        Connection conn = MySqlConnection.getConnection();
        String sql = "DELETE FROM categories WHERE name = ? AND class_id = ?";
		PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
			statement.setInt(2, Globals.class_id());
			
            // statement.executeUpdate();
			
        }catch (SQLException e) {
            e.printStackTrace();
        }
		return statement;
    }
	
	public static ArrayList<Category> getAll(int class_id) {
        Connection conn = MySqlConnection.getConnection();
        ArrayList<Category>res = new ArrayList<Category>();
        String query = "SELECT * FROM Categories WHERE class_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,class_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String type = rs.getString("name");
                int ugradWeight = rs.getInt("ugrad_weight");
                int gradWeight = rs.getInt("grad_weight");
                // int id = rs.getInt("category_id");
                Category category = new Category(type, gradWeight, ugradWeight);
                // category.setId(id);
                res.add(category);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public static Category getByName(String category_name) {
        Connection conn = MySqlConnection.getConnection();
        Category category = null;
        String query = "SELECT * FROM Categories WHERE name = ? AND class_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,category_name);
			statement.setInt(2,Globals.class_id());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String type = rs.getString("name");
                int ugradWeight = rs.getInt("ugrad_weight");
                int gradWeight = rs.getInt("grad_weight");
                // int id = rs.getInt("category_id");
                category = new Category(type, gradWeight, ugradWeight);
                // category.setId(id);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return category;
    }

	public static PreparedStatement updateUgradWeight(Category gt, int uw) {
		Connection conn = MySqlConnection.getConnection();
		String query = "UPDATE Categories SET ugrad_weight = ? WHERE name = ? AND class_id = ?";
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, uw);
			statement.setString(2,gt.getType());
			statement.setInt(3,Globals.class_id());
			
			// statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}	
	
	public static PreparedStatement updateGradWeight(Category gt, int gw) {
		Connection conn = MySqlConnection.getConnection();
		String query = "UPDATE Categories SET grad_weight = ? WHERE name = ? AND class_id = ?";
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, gw);
			statement.setString(2,gt.getType());
			statement.setInt(3,Globals.class_id());
			// statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}	
	
	}
