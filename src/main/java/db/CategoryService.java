package db;

import gui.GradableType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private CategoryService(){};

    public static void insert(GradableType gradableType, int classId){
        Connection conn = MySqlConnection.getConnection();
        String query = "INSERT INTO Categories (name,class_id,ugrad_weight,grad_weight) VALUES(?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, gradableType.getType());
            statement.setInt(2,classId);
            statement.setInt(3, gradableType.getUndergradWeight());
            statement.setInt(4,gradableType.getGraduateWeight());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                gradableType.setId(rs.getInt(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<GradableType> getAll(int classId) {
        Connection conn = MySqlConnection.getConnection();
        ArrayList<GradableType>res = new ArrayList<GradableType>();
        String query = "SELECT * FROM Categories WHERE class_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,classId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String type = rs.getString("name");
                int upgradWeight = rs.getInt("ugrad_weight");
                int gradWeight = rs.getInt("grad_weight");
                int id = rs.getInt("category_id");
                GradableType gradableType = new GradableType(type, upgradWeight, gradWeight);
                gradableType.setId(id);
                res.add(gradableType);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public static GradableType getById(int categoryId) {
        Connection conn = MySqlConnection.getConnection();
        GradableType gradableType = null;
        String query = "SELECT * FROM Categories WHERE category_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,categoryId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String type = rs.getString("name");
                int upgradWeight = rs.getInt("ugrad_weight");
                int gradWeight = rs.getInt("grad_weight");
                int id = rs.getInt("category_id");
                gradableType = new GradableType(type, upgradWeight, gradWeight);
                gradableType.setId(id);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return gradableType;
    }
}
