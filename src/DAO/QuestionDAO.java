package DAO;

import project.*;
import javax.swing.*;
import java.sql.*;

public class QuestionDAO {
    public static int getLastQuestionIdFromDb(){
        String sql = "SELECT questionId FROM questions ORDER BY questionId DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("questionId");
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


    public static boolean saveQuestionToDB(Question question) {
        String sql = "INSERT INTO questions (questionId, quizId, questionText, answerA, answerB, answerC, answerD, correctAnswer) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, question.getId());
            stmt.setInt(2, question.getQuizId());
            stmt.setString(3, question.getText());
            stmt.setString(4, question.getAnswerA());
            stmt.setString(5, question.getAnswerB());
            stmt.setString(6, question.getAnswerC());
            stmt.setString(7, question.getAnswerD());
            stmt.setString(8, question.getCorrectAnswer());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi câu hỏi vào DB:\n" + e.getMessage(), "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    public static boolean deleteQuestion(int questionId) {
        // Câu lệnh SQL DELETE dựa trên khoa chinh
        String sql = "DELETE FROM questions WHERE questionId = ?";
        boolean deleted = false;

        if (questionId <= 0) {
            System.err.println("Quiz ID không hợp lệ: " + questionId);
            return false;
        }

        // Sử dụng try-with-resources để đảm bảo kết nối và statement được đóng
        try (Connection conn = DBConnection.getConnection(); // Lấy kết nối
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Thiết lập tham số cho PreparedStatement
            pstmt.setInt(1, questionId);

            // Thực thi câu lệnh DELETE
            int rowsAffected = pstmt.executeUpdate();

            // Kiểm tra xem có hàng nào bị xóa không
            if (rowsAffected > 0) {
                deleted = true;
                System.out.println("Đã xóa thành công Quiz có ID: " + questionId);
                System.out.println(" -> Các câu hỏi và kết quả liên quan cũng đã được xóa (do ON DELETE CASCADE).");
            } else {
                // Không có hàng nào bị xóa, có thể do không tìm thấy quizId
                System.out.println("Không tìm thấy Quiz có ID: " + questionId + " để xóa.");
            }

        } catch (SQLException e) {
            // Xử lý lỗi SQL
            System.err.println("Lỗi SQL khi xóa Quiz có ID '" + questionId + "': " + e.getMessage());
            e.printStackTrace(); // Nên dùng logging trong ứng dụng thực tế
        } catch (Exception ex) {
            // Bắt các lỗi khác
            System.err.println("Lỗi không xác định khi xóa Quiz có ID '" + questionId + "': " + ex.getMessage());
            ex.printStackTrace();
        }

        return deleted;
    }

    public static boolean updateQuestion(Question question){
        if(question == null || question.getId() < 0){
            JOptionPane.showMessageDialog(null,"id khong hợp lệ","thông báo hệ thông",JOptionPane.INFORMATION_MESSAGE);
        }

        // Câu lệnh SQL UPDATE
        String sql = "UPDATE questions SET quizId = ?, questionText = ?, answerA = ?, answerB = ?, answerC = ?,answerD = ?,correctAnswer = ? " +
                " WHERE questionId = ?";
        boolean updated = false;

        // Sử dụng try-with-resources
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, question.getQuizId());
            pstmt.setString(2,question.getText());
            pstmt.setString(3,question.getAnswerA());
            pstmt.setString(4,question.getAnswerB());
            pstmt.setString(5, question.getAnswerC());
            pstmt.setString(6,question.getAnswerD());
            pstmt.setString(7,question.getCorrectAnswer());

            pstmt.setInt(8,question.getId());
            // Thực thi cập nhật
            int rowsAffected = pstmt.executeUpdate();

            // Kiểm tra kết quả
            if (rowsAffected > 0) {
                updated = true;
                System.out.println("Đã cập nhật thành công môn học có ID: " + question.getId());
            } else {
                // Không có hàng nào được cập nhật, có thể do ID không tồn tại
                System.out.println("Không tìm thấy môn học có ID: " + question.getId() + " để cập nhật.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi cập nhật môn học ID " + question.getId() + ": " + e.getMessage());
            e.printStackTrace(); // Nên dùng logging
        } catch (Exception ex) {
            System.err.println("Lỗi không xác định khi cập nhật môn học ID " + question.getId() + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        return updated;
    }

}
