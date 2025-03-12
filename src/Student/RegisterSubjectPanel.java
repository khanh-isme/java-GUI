package Student;
import project.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class RegisterSubjectPanel extends JPanel {
    private JList<String> subjectList;
    private DefaultListModel<String> listModel;
    private JButton registerButton;
    private Student student;
    private Bank bank;

    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(46, 134, 222); // Blue
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94); // Dark Gray
    private static final Color BACKGROUND_COLOR = new Color(245, 246, 250); // Light Gray
    private static final Color TEXT_COLOR = new Color(44, 62, 80); // Dark Text

    public RegisterSubjectPanel(Bank bank, Student student) {
        this.bank = bank;
        this.student = student;

        // Setup panel
        setLayout(new BorderLayout(10, 10));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        // Title
        JLabel title = createTitleLabel();
        add(title, BorderLayout.NORTH);

        // project.Subject list panel
        add(createSubjectListPanel(), BorderLayout.CENTER);

        // Button panel
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JLabel createTitleLabel() {
        JLabel title = new JLabel("Register for Subjects", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(SECONDARY_COLOR);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        return title;
    }

    private JPanel createSubjectListPanel() {
        listModel = new DefaultListModel<>();
        for (Subject subject : bank.getSubjects()) {
            listModel.addElement(subject.getName());
        }

        subjectList = new JList<>(listModel) {
            @Override
            public void updateUI() {
                super.updateUI();
                setFont(new Font("Arial", Font.PLAIN, 14));
                setForeground(TEXT_COLOR);
            }
        };

        subjectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subjectList.setCellRenderer(new CustomListCellRenderer());
        subjectList.setFixedCellHeight(40);

        // Add hover effect
        subjectList.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = subjectList.locationToIndex(e.getPoint());
                subjectList.repaint();
            }
        });

        JScrollPane scrollPane = new JScrollPane(subjectList);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                "Available Subjects",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                PRIMARY_COLOR
        ));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonPanel() {
        registerButton = createStyledButton("Register");

        // Add hover effect for button
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(PRIMARY_COLOR.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(PRIMARY_COLOR);
            }
        });

        registerButton.addActionListener(e -> registerSelectedSubject());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(registerButton);
        return buttonPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            public void updateUI() {
                super.updateUI();
                setFont(new Font("Arial", Font.BOLD, 16));
                setForeground(Color.WHITE);
            }
        };

        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add shadow effect
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 20)),
                new EmptyBorder(12, 25, 12, 25)
        ));

        return button;
    }

    // Custom cell renderer for the list
    private class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setBorder(new EmptyBorder(0, 10, 0, 10));

                if (isSelected) {
                    c.setBackground(PRIMARY_COLOR);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(BACKGROUND_COLOR);
                    c.setForeground(TEXT_COLOR);
                }
            }
            return c;
        }
    }

    private void registerSelectedSubject() {
        String selectedSubjectName = subjectList.getSelectedValue();
        if (selectedSubjectName == null) {
            showErrorDialog("Please select a subject to register.");
            return;
        }

        Subject selectedSubject = bank.getSubjects().stream()
                .filter(subject -> subject.getName().equalsIgnoreCase(selectedSubjectName))
                .findFirst()
                .orElse(null);


        /*
        Subject selectedSubject = null;
        for (Subject subject : bank.getSubjects()) {
            if (subject.getName().equalsIgnoreCase(selectedSubjectName)) {
                selectedSubject = subject;
                break; // Dừng vòng lặp ngay khi tìm thấy
            }
        }
         */



        if (selectedSubject == null) {
            showErrorDialog("project.Subject not found. Please try again.");
            return;
        }

        if (student.getRegisteredSubjects().contains(selectedSubject)) {
            showInfoDialog("You have already registered for this subject.");
            return;
        }

        student.getRegisteredSubjects().add(selectedSubject);
        showSuccessDialog("Successfully registered for: " + selectedSubject.getName());
    }

    // Helper methods for dialogs with custom styling
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE, getCustomIcon(UIManager.getIcon("OptionPane.errorIcon")));
    }

    private void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Information",
                JOptionPane.INFORMATION_MESSAGE, getCustomIcon(UIManager.getIcon("OptionPane.informationIcon")));
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Success",
                JOptionPane.INFORMATION_MESSAGE, getCustomIcon(UIManager.getIcon("OptionPane.informationIcon")));
    }

    private Icon getCustomIcon(Icon original) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                original.paintIcon(c, g2, x, y);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return original.getIconWidth();
            }

            @Override
            public int getIconHeight() {
                return original.getIconHeight();
            }
        };
    }
}