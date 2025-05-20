package DAO;
import project.*;
import DAO.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherDAO {

    public static int getTeacherIdByName(String teacherName) {
        String sql = "SELECT teacherId FROM teachers WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, teacherName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("teacherId");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn giáo viên:\n" + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return -1; // không tìm thấy
    }

    public static String getTeacherNameById(int teacherId) {
        String sql = "SELECT name FROM teachers WHERE teacherId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn giáo viên:\n" + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return null; // Trả về null nếu không tìm thấy hoặc có lỗi
    }


    public static boolean saveTeacherToDB(Teacher teacher) {
        String sql = "INSERT INTO teachers (name, pass) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, teacher.getUsername());
            stmt.setString(2, teacher.getPassword());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Đã xảy ra lỗi khi lưu giáo viên:\n" + e.getMessage(),
                    "Lỗi Hệ Thống",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return false;
    }
}
