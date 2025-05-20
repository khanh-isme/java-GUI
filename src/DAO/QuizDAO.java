package DAO;

import project.*;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class QuizDAO {

    public static int getLastQuizIdFromDB() {
        String sql = "SELECT quizId FROM quizzes ORDER BY quizId DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("quizId");
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

    public static boolean saveQuizToDB(Quiz quiz) {
        List<Question> questions = quiz.getQuestions();
        String sql = "INSERT INTO quizzes (quizId, quizName, subjectName, dateCreated, createdBy, thoiGianLamBai) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int temp = TeacherDAO.getTeacherIdByName(quiz.getCreateBy());
            // Ghi thông tin quiz vào DB
            stmt.setInt(1, quiz.getId());
            stmt.setString(2, quiz.getName());
            stmt.setString(3, quiz.getSubjectName());
            stmt.setInt(5, temp );
            stmt.setTimestamp(4, Timestamp.valueOf(quiz.getCreatedDate()));
            stmt.setInt(6, quiz.getthoiGianLamBai());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                for (Question question : questions) {
                    boolean success = QuestionDAO.saveQuestionToDB(question);
                    if (!success) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi lưu câu hỏi: " + question.getText(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return false; // Dừng lại nếu có lỗi ở câu hỏi
                    }
                }
                return true; // Thành công hoàn toàn
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi quiz vào DB:\n" + e.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    public static boolean deleteQuiz(int quizId) {
        // Câu lệnh SQL DELETE dựa trên khóa chính quizId
        String sql = "DELETE FROM quizzes WHERE quizId = ?";
        boolean deleted = false;

        // Kiểm tra đầu vào cơ bản (quizId thường là số dương)
        if (quizId <= 0) {
            System.err.println("Quiz ID không hợp lệ: " + quizId);
            return false;
        }

        // Sử dụng try-with-resources để đảm bảo kết nối và statement được đóng
        try (Connection conn = DBConnection.getConnection(); // Lấy kết nối
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Thiết lập tham số cho PreparedStatement
            pstmt.setInt(1, quizId);

            // Thực thi câu lệnh DELETE
            int rowsAffected = pstmt.executeUpdate();

            // Kiểm tra xem có hàng nào bị xóa không
            if (rowsAffected > 0) {
                deleted = true;
                System.out.println("Đã xóa thành công Quiz có ID: " + quizId);
                System.out.println(" -> Các câu hỏi và kết quả liên quan cũng đã được xóa (do ON DELETE CASCADE).");
            } else {
                // Không có hàng nào bị xóa, có thể do không tìm thấy quizId
                System.out.println("Không tìm thấy Quiz có ID: " + quizId + " để xóa.");
            }

        } catch (SQLException e) {
            // Xử lý lỗi SQL
            System.err.println("Lỗi SQL khi xóa Quiz có ID '" + quizId + "': " + e.getMessage());
            e.printStackTrace(); // Nên dùng logging trong ứng dụng thực tế
        } catch (Exception ex) {
            // Bắt các lỗi khác
            System.err.println("Lỗi không xác định khi xóa Quiz có ID '" + quizId + "': " + ex.getMessage());
            ex.printStackTrace();
        }

        return deleted;
    }

    public static boolean updateQuizt(Quiz quiz) {
        // Kiểm tra đầu vào cơ bản
        if (quiz == null || quiz.getId() <= 0) {
            System.err.println("Dữ liệu môn học không hợp lệ để cập nhật.");

            return false;
        }
        // Câu lệnh SQL UPDATE
        String sql = "UPDATE quizzes SET quizName = ?, subjectName = ?, dateCreated = ?, createdBy = ?, thoiGianLamBai = ? " +
                " WHERE quizId = ?";
        boolean updated = false;

        // Sử dụng try-with-resources
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int teacherId = TeacherDAO.getTeacherIdByName(quiz.getCreateBy());

            pstmt.setString(1, quiz.getName());
            pstmt.setString(2, quiz.getSubjectName());
            pstmt.setTimestamp(3, Timestamp.valueOf(quiz.getCreatedDate()));
            pstmt.setInt(4,teacherId);

            // 4. thoiGianLam bai
            pstmt.setInt(5, quiz.getthoiGianLamBai());

            // 5. id (Cho mệnh đề WHERE)
            pstmt.setInt(6, quiz.getId());

            // Thực thi cập nhật
            int rowsAffected = pstmt.executeUpdate();

            // Kiểm tra kết quả
            if (rowsAffected > 0) {
                updated = true;
                System.out.println("Đã cập nhật thành công môn học có ID: " + quiz.getId());
            } else {
                // Không có hàng nào được cập nhật, có thể do ID không tồn tại
                System.out.println("Không tìm thấy môn học có ID: " + quiz.getId() + " để cập nhật.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật môn học ID " + quiz.getId() + ": " + e.getMessage());
            e.printStackTrace(); // Nên dùng logging
        } catch (Exception ex) {
            System.err.println("Lỗi không xác định khi cập nhật môn học ID " + quiz.getId() + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }


    public static Quiz getQuizByNameAndSubject(String quizName, String subjectName) {
        String sql = "SELECT * FROM quizzes WHERE quizName = ? AND subjectName = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, quizName);
            stmt.setString(2, subjectName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int quizId = rs.getInt("quizId");
                String name = rs.getString("quizName");
                String subject = rs.getString("subjectName");
                LocalDateTime dateCreated = rs.getTimestamp("dateCreated").toLocalDateTime();
                int createdBy = rs.getInt("createdBy");
                int thoiGianLamBai = rs.getInt("thoiGianLamBai");

                String teacherName =TeacherDAO.getTeacherNameById(createdBy);
                List<Question> questions = QuestionDAO.getQuestionsByQuizId(quizId); // Phải có DAO này

                return new Quiz(quizId, name, teacherName, subject, dateCreated, questions, thoiGianLamBai);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }





}
