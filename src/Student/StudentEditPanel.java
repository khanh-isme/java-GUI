package Student;

import javax.swing.*;
import java.awt.*;
import project.*;

public class StudentEditPanel extends JPanel {
    private Student student;
    private JTextField nameField;
    private JTextField mssvField;
    private JButton saveButton;

    public StudentEditPanel(Student student) {
        this.student = student;
        setLayout(new BorderLayout(10, 10)); // Layout chính
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        initializeComponents();
    }

    private void initializeComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font inputFont = new Font("Arial", Font.PLAIN, 16);

        // Panel chứa thông tin sinh viên
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground( Color.gray); // Màu xanh nhẹ
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel nameLabel = new JLabel("Username: ");
        nameLabel.setFont(labelFont);
        infoPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        JLabel currentName = new JLabel(student.getUsername());
        currentName.setFont(inputFont);
        infoPanel.add(currentName, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel newNameLabel = new JLabel("New Name:");
        newNameLabel.setFont(labelFont);
        infoPanel.add(newNameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        nameField.setFont(inputFont);
        infoPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel mssvLabel = new JLabel("pass:");
        mssvLabel.setFont(labelFont);
        infoPanel.add(mssvLabel, gbc);

        gbc.gridx = 1;
        JLabel currentMssv = new JLabel(""+student.getPassword());
        currentMssv.setFont(inputFont);
        infoPanel.add(currentMssv, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel newMssvLabel = new JLabel("New pass:");
        newMssvLabel.setFont(labelFont);
        infoPanel.add(newMssvLabel, gbc);

        gbc.gridx = 1;
        mssvField = new JTextField(20);
        mssvField.setFont(inputFont);
        infoPanel.add(mssvField, gbc);

        add(infoPanel, BorderLayout.CENTER);

        // Panel chứa nút lưu
        JPanel buttonPanel = new JPanel();


        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(52, 152, 219));
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover cho nút
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveButton.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveButton.setBackground(new Color(52, 152, 219));
            }
        });

        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
