package org.example.util.database;

import org.example.util.constant.TreeColor;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://binary-db:5432/BinaryTree";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertNode(String treeType, Integer value, Integer height, String color, Long parentId, Long leftId, Long rightId) {
        String sql = "INSERT INTO binary_tree_nodes (tree_type, value, height, color, parent_id, Left_id, right_id) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, treeType);
            preparedStatement.setInt(2, value);
            preparedStatement.setInt(3, height != null ? height : 1);
            preparedStatement.setString(4, color != null ? color : String.valueOf(TreeColor.BLACK));
            if (parentId != null) preparedStatement.setLong(5, parentId);
            else preparedStatement.setNull(5, Types.BIGINT);
            if (leftId != null) preparedStatement.setLong(6, leftId);
            else preparedStatement.setNull(6, Types.BIGINT);
            if (rightId != null) preparedStatement.setLong(7, rightId);
            else preparedStatement.setNull(7, Types.BIGINT);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Node inserted successfully");
            } else {
                System.err.println("Insert failed, no rows affected");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateNode(long id, Integer height, String color, Long parentId, Long leftId, Long rightId) {
        String sql = "UPDATE binary_tree_nodes SET height = ?, color = ?, parent_id = ?, left_id = ?, right_id = ? WHERE id = ?";

        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, height != null ? height : 1);
            preparedStatement.setString(2, color != null ? color : String.valueOf(TreeColor.BLACK));
            if (parentId != null) preparedStatement.setLong(3, parentId);
            else preparedStatement.setNull(3, Types.BIGINT);
            if (leftId != null) preparedStatement.setLong(4, leftId);
            else preparedStatement.setNull(4, Types.BIGINT);
            if (rightId != null) preparedStatement.setLong(5, rightId);
            else preparedStatement.setNull(5, Types.BIGINT);
            preparedStatement.setLong(6, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated node with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteNode(long id) {
        String sql = "DELETE FROM binary_tree_nodes WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Deleted node with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
