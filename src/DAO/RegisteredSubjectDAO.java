package DAO;

import java.sql.*;
import java.util.*;

public class RegisteredSubjectDAO {

    // THÊM đăng ký môn học
    public static void addRegistration(int mssv, String subjectName) {
        String sql = "INSERT INTO registered_subjects (mssv, subjectName) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mssv);
            stmt.setString(2, subjectName);
            stmt.executeUpdate();
            System.out.println("✔ Đăng ký thành công!");

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi đăng ký: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SỬA tên môn học đã đăng ký (nếu muốn đổi sang môn khác)
    public void updateRegistration(int mssv, String oldSubject, String newSubject) {
        String sql = "UPDATE registered_subjects SET subjectName = ? WHERE mssv = ? AND subjectName = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newSubject);
            stmt.setInt(2, mssv);
            stmt.setString(3, oldSubject);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✔ Cập nhật thành công!");
            } else {
                System.out.println("⚠ Không tìm thấy bản ghi để cập nhật.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi cập nhật: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // XOÁ đăng ký môn học
    public static void deleteRegistration(int mssv, String subjectName) {
        String sql = "DELETE FROM registered_subjects WHERE mssv = ? AND subjectName = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mssv);
            stmt.setString(2, subjectName);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✔ Xoá thành công!");
            } else {
                System.out.println("⚠ Không tìm thấy bản ghi để xoá.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi xoá: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
