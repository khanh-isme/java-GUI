import DAO.StudentDAO;
import DAO.SubjectDAO;
import DAO.TeacherDAO;
import project.*;
import BUS.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterFrame extends JFrame {
    public RegisterFrame(Ssystem ssystem) {
        setTitle("Đăng ký tài khoản");
        setSize(400, 460);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(44, 62, 80));
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Đăng ký");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(140, 20, 200, 30);
        panel.add(titleLabel);



        // Họ tên
        JLabel nameLabel = new JLabel("Họ tên:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(50, 120, 80, 25);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 120, 180, 30);
        nameField.setBackground(new Color(236, 240, 241));
        panel.add(nameField);

        // Mật khẩu
        JLabel passLabel = new JLabel("Mật khẩu:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 170, 80, 25);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 170, 180, 30);
        passField.setBackground(new Color(236, 240, 241));
        panel.add(passField);

        // Loại người dùng
        JLabel typeLabel = new JLabel("Loại:");
        typeLabel.setForeground(Color.WHITE);
        typeLabel.setBounds(50, 220, 80, 25);
        panel.add(typeLabel);

        String[] userTypes = {"Student", "Teacher"};
        JComboBox<String> typeBox = new JComboBox<>(userTypes);
        typeBox.setBounds(150, 220, 180, 30);
        panel.add(typeBox);

        // Ngày sinh (cho học sinh)
        JLabel dobLabel = new JLabel("Ngày sinh:");
        dobLabel.setForeground(Color.WHITE);
        dobLabel.setBounds(50, 260, 80, 25);
        panel.add(dobLabel);

        JTextField dobField = new JTextField();
        dobField.setBounds(150, 260, 180, 30);
        dobField.setBackground(new Color(236, 240, 241));
        panel.add(dobField);

        // Lớp (cho học sinh)
        JLabel classLabel = new JLabel("Lớp:");
        classLabel.setForeground(Color.WHITE);
        classLabel.setBounds(50, 300, 80, 25);
        panel.add(classLabel);

        JTextField classField = new JTextField();
        classField.setBounds(150, 300, 180, 30);
        classField.setBackground(new Color(236, 240, 241));
        panel.add(classField);

        // Nút Đăng ký
        JButton registerBtn = new JButton("Đăng ký");
        registerBtn.setBounds(150, 360, 180, 40);
        registerBtn.setBackground(new Color(39, 174, 96));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        registerBtn.setBorder(BorderFactory.createEmptyBorder());
        registerBtn.setFocusPainted(false);

        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerBtn.setBackground(new Color(46, 204, 113));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerBtn.setBackground(new Color(39, 174, 96));
            }
        });

        panel.add(registerBtn);
        add(panel);

        // Ẩn/hiện trường ngày sinh và lớp tùy loại người dùng
        dobLabel.setVisible(false);
        dobField.setVisible(false);
        classLabel.setVisible(false);
        classField.setVisible(false);

        typeBox.addActionListener(e -> {
            boolean isStudent = "Student".equals(typeBox.getSelectedItem());
            dobLabel.setVisible(isStudent);
            dobField.setVisible(isStudent);
            classLabel.setVisible(isStudent);
            classField.setVisible(isStudent);
        });

        // Xử lý đăng ký
        registerBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String password = new String(passField.getPassword());
                String userType = (String) typeBox.getSelectedItem();

                if (name.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userType.equals("Student")) {
                    String dob = dobField.getText().trim();
                    String className = classField.getText().trim();

                    if (dob.isEmpty() || className.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày sinh và lớp cho sinh viên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Student student = StudentValidator.validateStudent(0,name,password,dob,className);
                    if(student== null){
                        return;
                    }
                    StudentDAO.saveStudentToDB(student);
                    ssystem.addUser(student);
                    JOptionPane.showMessageDialog(this, "Đăng ký sinh viên thành công!\nMSSV: " + student.getMssv());

                } else {
                    Teacher teacher = TeacherBUS.validateTeacher(name,password);
                    if(teacher== null){
                        return;
                    }
                    TeacherDAO.saveTeacherToDB(teacher);
                    ssystem.addUser(teacher);
                    JOptionPane.showMessageDialog(this, "Đăng ký giáo viên thành công! \n id: "+teacher.getTeacherid() );
                }

                this.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi đăng ký.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
