package DAO;


import project.Subject;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;

public class SubjectDAO {
    public static int getLastSubjectIdFromDB() {
        String sql = "SELECT id FROM subjects ORDER BY id DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Đã xảy ra lỗi khi truy cập dữ liệu:\n" + e.getMessage(),
                    "Lỗi Hệ Thống",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return -1;
    }

    public static boolean subjectExists(String subjectName) {
        String sql = "SELECT 1 FROM subjects WHERE subjectName = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, subjectName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static Subject getSubjectFromDB(String subjectName) {
        String sql = "SELECT * " +
                "FROM subjects s " + //
                "JOIN teachers t ON s.teacherId = t.teacherId " + //
                "WHERE s.subjectName = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, subjectName); // đặt sau khi tạo stmt
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("subjectName");
                String teacher = rs.getString("name");
                LocalDateTime examDate = rs.getTimestamp("thoiGianMoDe").toLocalDateTime();
                int time = rs.getInt("thoiGianChoVao");


                return new Subject(id,name, teacher, null, examDate, time); // chưa có quizzes, có thể load sau
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Đã xảy ra lỗi khi truy cập dữ liệu:\n" + e.getMessage(),
                    "Lỗi Hệ Thống",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return null;
    }

    public static boolean saveSubjectToDB(Subject subject) {
        String checkSql = "SELECT COUNT(*) FROM subjects WHERE subjectName = ?";
        String insertSql = "INSERT INTO subjects (id,subjectName, teacherId, thoiGianMoDe, thoiGianChoVao) " +
                "VALUES (?,?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            int teacherId = TeacherDAO.getTeacherIdByName(subject.getTeacher());
            if (teacherId == -1) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy giáo viên: " + subject.getTeacher(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Kiểm tra subject đã tồn tại chưa
            checkStmt.setString(1, subject.getName());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Môn học đã tồn tại trong cơ sở dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }

            // Nếu chưa có thì insert
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1,subject.getId());
                insertStmt.setString(2, subject.getName());
                insertStmt.setInt(3, teacherId);
                insertStmt.setTimestamp(4, Timestamp.valueOf(subject.getNgayThi()));
                insertStmt.setInt(5, subject.getTime());

                int rowsAffected = insertStmt.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi môn học vào DB:\n" + e.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }



    public static boolean deleteSubject(String subjectName) {
        String sql = "DELETE FROM subjects WHERE subjectName = ?";
        boolean deleted = false;


        try (Connection conn = DBConnection.getConnection(); // Lấy kết nối từ lớp tiện ích
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Kiểm tra đầu vào cơ bản
            if (subjectName == null || subjectName.trim().isEmpty()) {
                System.err.println("Tên môn học không hợp lệ.");
                return false;
            }

            pstmt.setString(1, subjectName);
            int rowsAffected = pstmt.executeUpdate();

            // Kiểm tra xem có hàng nào bị xóa không
            if (rowsAffected > 0) {
                deleted = true;
                System.out.println("Đã xóa thành công môn học: " + subjectName + " và các dữ liệu liên quan theo ràng buộc.");
            } else {
                // Không có hàng nào bị xóa, có thể do không tìm thấy subjectName
                System.out.println("Không tìm thấy môn học có tên: " + subjectName + " để xóa.");
            }

        } catch (SQLException e) {
            // Xử lý lỗi SQL (ví dụ: không kết nối được DB, lỗi cú pháp,...)
            System.err.println("Lỗi SQL khi xóa môn học '" + subjectName + "': " + e.getMessage());
            // Trong ứng dụng thực tế, bạn nên sử dụng logging framework
            e.printStackTrace(); // In chi tiết lỗi (hữu ích khi gỡ lỗi)
        } catch (Exception ex) {
            // Bắt các lỗi khác (ví dụ: lỗi khi lấy connection)
            System.err.println("Lỗi không xác định khi xóa môn học '" + subjectName + "': " + ex.getMessage());
            ex.printStackTrace();
        }

        return deleted;
    }


    public static boolean updateSubject(Subject subject) {
        // Kiểm tra đầu vào cơ bản
        if (subject == null || subject.getId() <= 0) {
            System.err.println("Dữ liệu môn học không hợp lệ để cập nhật.");
            return false;
        }

        // Câu lệnh SQL UPDATE
        String sql = "UPDATE subjects SET subjectName = ?, thoiGianMoDe = ?, teacherId = ?, thoiGianChoVao = ? WHERE id = ?";
        boolean updated = false;

        // Sử dụng try-with-resources
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int teacherId = TeacherDAO.getTeacherIdByName(subject.getTeacher());

            pstmt.setString(1, subject.getName());
            pstmt.setTimestamp(2, Timestamp.valueOf(subject.getNgayThi()));
            pstmt.setInt(3,teacherId);

            // 4. thoiGianChoVao
            pstmt.setInt(4, subject.getTime());

            // 5. id (Cho mệnh đề WHERE)
            pstmt.setInt(5, subject.getId());

            // Thực thi cập nhật
            int rowsAffected = pstmt.executeUpdate();

            // Kiểm tra kết quả
            if (rowsAffected > 0) {
                updated = true;
                System.out.println("Đã cập nhật thành công môn học có ID: " + subject.getId());
            } else {
                // Không có hàng nào được cập nhật, có thể do ID không tồn tại
                System.out.println("Không tìm thấy môn học có ID: " + subject.getId() + " để cập nhật.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật môn học ID " + subject.getId() + ": " + e.getMessage());
            e.printStackTrace(); // Nên dùng logging
        } catch (Exception ex) {
            System.err.println("Lỗi không xác định khi cập nhật môn học ID " + subject.getId() + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }



}
