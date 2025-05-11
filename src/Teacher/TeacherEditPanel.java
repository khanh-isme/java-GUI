package Teacher;
import project.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TeacherEditPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField teacherIdField;
    private JButton saveButton;

    private JTable quizzesTable;
    private DefaultTableModel quizzesTableModel;

    private Teacher teacher;

    public TeacherEditPanel(Teacher teacher) {
        this.teacher = teacher;

        setLayout(new BorderLayout());

        // Info Panel (top)
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setText(teacher.getUsername());
        infoPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setText(teacher.getPassword());
        infoPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(new JLabel("Teacher ID:"), gbc);
        gbc.gridx = 1;
        teacherIdField = new JTextField(10);
        teacherIdField.setText(String.valueOf(teacher.getTeacherid()));
        infoPanel.add(teacherIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        saveButton = new JButton("Save");
        infoPanel.add(saveButton, gbc);

        add(infoPanel, BorderLayout.NORTH);

        // Table Panel (center)
        quizzesTableModel = new DefaultTableModel(new Object[]{"Quiz ID", "Title"}, 0);
        quizzesTable = new JTable(quizzesTableModel);
        loadQuizzesToTable(teacher.getQuizzesCreated());

        add(new JScrollPane(quizzesTable), BorderLayout.CENTER);

        // Save button listener
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTeacherInfo();
            }
        });
    }

    private void loadQuizzesToTable(List<Quiz> quizzes) {
        quizzesTableModel.setRowCount(0);
        for (Quiz quiz : quizzes) {
            quizzesTableModel.addRow(new Object[]{quiz.getId(), quiz.getName()});
        }
    }

    private void saveTeacherInfo() {
        teacher.setUsername(usernameField.getText());
        teacher.setPassword(new String(passwordField.getPassword()));

        try {
            int id = Integer.parseInt(teacherIdField.getText());
            teacher.setTeacherid(id);
            JOptionPane.showMessageDialog(this, "Teacher info updated!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Teacher ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
