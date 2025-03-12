import project.*;

import javax.swing.*;
import java.util.List;




public class Main {
    private Ssystem system; // Hệ thống quản lý người dùng và bài thi
    private Bank bank;

    public static void main(String[] args) {
        // Đường dẫn đến file chứa dữ liệu
        String fileName = "data.txt"; // Đảm bảo rằng file này tồn tại và có dữ liệu đúng định dạng

        String fileName1="data1.txt";
        // Tạo project.Bank từ dữ liệu trong file
        Bank bank = BankDataLoader.loadBankFromFile(fileName);

        // Kiểm tra nếu bank đã được tải thành công
        if (bank != null) {
            System.out.println("Dữ liệu đã được tải thành công từ file:");
            System.out.println(bank.toString());
        } else {
            System.out.println("Không thể tải dữ liệu từ file.");
        }

        List<User> users =BankDataLoader.readFileAndParseUsers(fileName1);
        Ssystem ssystem = new Ssystem( users);
        Student demo;
        if (users != null) {
            System.out.println("Dữ liệu đã được tải thành công từ file:");
            for (User user : users) {
                System.out.println(user);
            }
        } else {
            System.out.println("Không thể tải dữ liệu từ file.");
        }

        // Chạy JFrame trên luồng sự kiện Swing
        SwingUtilities.invokeLater(() -> {
            Login loginFrame = new Login(ssystem, bank);
            loginFrame.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
            loginFrame.setVisible(true); // Hiển thị cửa sổ
        });



    }



}
