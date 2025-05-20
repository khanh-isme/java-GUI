import project.*;
import project.Ssystem;
import project.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    public Login(Ssystem ssystem , Bank bank){
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setResizable(false);

        // Panel chính
        JPanel panel = new JPanel();
        panel.setBackground(new Color(44, 62, 80));
        panel.setLayout(null);

        // Tiêu đề
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(170, 20, 100, 30);
        panel.add(titleLabel);

        // Label Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 70, 80, 25);
        panel.add(userLabel);

        // Ô nhập Username
        JTextField userText = new JTextField();
        userText.setBounds(150, 70, 180, 30);
        userText.setBackground(new Color(236, 240, 241));
        userText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(userText);

        // Label Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 120, 80, 25);
        panel.add(passLabel);

        // Ô nhập Password
        JPasswordField passText = new JPasswordField();
        passText.setBounds(150, 120, 180, 30);
        passText.setBackground(new Color(236, 240, 241));
        passText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(passText);

        // Nút Đăng nhập
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 180, 180, 40);
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setFocusPainted(false);

        // Hiệu ứng hover nút đăng nhập
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(52, 152, 219));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(41, 128, 185));
            }
        });

        panel.add(loginButton);
        add(panel);


        loginButton.addActionListener(e -> {
            String mssv = userText.getText();
            int mssv1;
            mssv1 = Integer.parseInt(mssv);
            String password = new String(passText.getPassword());

            // Gọi phương thức đăng nhập
            User user = ssystem.loginUser(mssv1, password);


            if (user instanceof Student student) {
                // Nếu là sinh viên, mở giao diện project.Student.ShowQuizPanel
                this.dispose();
                SwingUtilities.invokeLater(() -> new StudentGUI(student, bank,ssystem));
            }else{
                if(user instanceof  Teacher teacher){
                    this.dispose();
                    SwingUtilities.invokeLater(() -> new TeacherGUI(ssystem, bank, teacher));
                }
                else {
                    // Hiển thị thông báo lỗi nếu đăng nhập thất bại
                    JOptionPane.showMessageDialog(null,
                            "Tên đăng nhập hoặc mật khẩu không chính xác!",
                            "Lỗi đăng nhập",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });


        JButton registerButton = new JButton("Đăng ký");
        registerButton.setBounds(150, 220, 180, 30);
        registerButton.setBackground(new Color(39, 174, 96));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBorder(BorderFactory.createEmptyBorder());
        registerButton.setFocusPainted(false);

        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(new Color(46, 204, 113));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(new Color(39, 174, 96));
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở form đăng ký
                new RegisterFrame(ssystem);  // 👉 Bạn cần tạo class này
            }
        });

        panel.add(registerButton);
        add(panel);

    }
}
