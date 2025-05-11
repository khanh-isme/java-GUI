package DAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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



}
