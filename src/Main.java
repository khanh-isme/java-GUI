

import project.*;
import DAO.*;
import javax.swing.*;
import java.sql.Connection;



public class Main {
    private static Ssystem system; // Hệ thống quản lý người dùng và bài thi
    private static Bank bank;

    public static void main(String[] args) {


        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("✅ Kết nối thành công với database!");

            // Load toàn bộ users (students + teachers)
            SystemDAO systemDAO = new SystemDAO();
            system = systemDAO.loadAllUsers(conn);


            // Load toàn bộ ngân hàng đề thi
            BankDAO bankDAO = new BankDAO();
            bank = bankDAO.loadBankData(conn);


            System.out.println("\n📚 Danh sách môn học trong ngân hàng đề:");
            for (Subject subject : bank.getSubjects()) {
                System.out.println("📘 Môn: " + subject.getName() + " | Giáo viên: " + subject.getTeacher());
                for (Quiz quiz : subject.getQuizzes()) {
                    System.out.println("   📝 Quiz: " + quiz.getName() + " | Tạo bởi: " + quiz.getCreateBy());
                    for (Question q : quiz.getQuestions()) {
                        System.out.println("     ❓ Question: " + q.getText());
                    }
                }
            }


            // Chạy JFrame trên luồng sự kiện Swing
            SwingUtilities.invokeLater(() -> {
                Login loginFrame = new Login(system, bank);
                loginFrame.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
                loginFrame.setVisible(true); // Hiển thị cửa sổ
            });

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi kết nối hoặc load dữ liệu:");
            e.printStackTrace();
        }
    }
}
