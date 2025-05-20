package Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import BUS.StudentValidator;
import DAO.StudentDAO;
import project.*;

public class StudentEditPanel extends JPanel {
    private Student student;
    private JTextField nameField;
    private JTextField passField;
    private JTextField mssvField;
    private JTextField dobField;
    private JTextField classField;
    private JButton saveButton;

    public StudentEditPanel(Student student) {
        this.student = student;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        initializeComponents();
    }

    private void initializeComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font inputFont = new Font("Arial", Font.PLAIN, 16);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.LIGHT_GRAY);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Username
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(student.getUsername(), 20);
        nameField.setFont(inputFont);
        infoPanel.add(nameField, gbc);

        // Password
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passField = new JTextField(student.getPassword(), 20);
        passField.setFont(inputFont);
        infoPanel.add(passField, gbc);



        // Ngày sinh
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1;
        dobField = new JTextField(student.getNgaySinh(), 20);
        dobField.setFont(inputFont);
        infoPanel.add(dobField, gbc);

        // Lớp
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("Lớp:"), gbc);
        gbc.gridx = 1;
        classField = new JTextField(student.getLop(), 20);
        classField.setFont(inputFont);
        infoPanel.add(classField, gbc);

        add(infoPanel, BorderLayout.CENTER);

        // Save button
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(52, 152, 219));
        saveButton.setFocusPainted(false);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        saveButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                saveButton.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(MouseEvent evt) {
                saveButton.setBackground(new Color(52, 152, 219));
            }
        });

        saveButton.addActionListener(e -> {

            Student studentdemo = StudentValidator.validateStudent(student.getMssv(),nameField.getText(),passField.getText(),dobField.getText(),classField.getText());
            if(studentdemo == null){
                return;
            }
            StudentDAO.updateStudent(studentdemo);
            student.setUsername(nameField.getText());
            student.setPassword(passField.getText());
            student.setNgaySinh(dobField.getText());
            student.setLop(classField.getText());

            JOptionPane.showMessageDialog(this, "Thông tin đã được cập nhật!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
