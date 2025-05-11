import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    public Login( Ssystem system){
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


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username= userText.getText();
                String password= new String (passText.getPassword());
                if( system.loginUser(username,password)!= null){

                }else{

                }


            }
        });
    }
}
