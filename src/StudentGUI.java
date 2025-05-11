import project.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;
import Student.*;
public class StudentGUI {
    private JFrame frame;
    private Student student;
    private Ssystem system;
    private Bank bank;
    private JPanel contentPanel;
    private AtomicBoolean flag = new AtomicBoolean(false);

    public StudentGUI(Student student, Bank bank ,Ssystem system) {
        this.student = student;
        this.bank = bank;
        this.system=system;
        initialize();
    }

    private void initialize() {
        // Cấu hình Frame chính
        frame = new JFrame("Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Căn giữa màn hình

        // Sidebar panel với màu xám đậm
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(64, 64, 64)); // Màu xám đậm
        sidebar.setPreferredSize(new Dimension(250, frame.getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Padding

        // Content panel với border
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(245, 246, 250)); // Màu nền sáng
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tạo header cho sidebar
        JLabel header = new JLabel("Menu", SwingConstants.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        sidebar.add(header);

        // Menu items
        String[] menuItems = {"Take Quiz", "Review Info", "View Exam Schedule",
                "View Detailed Quiz History", "Register Subject",
                "Edit Account", "Logout"};

        for (String item : menuItems) {
            JButton button = new JButton(item);
            // Style cơ bản
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            button.setForeground(Color.WHITE);
            button.setOpaque(false); // Loại bỏ màu nền
            button.setContentAreaFilled(false); // Loại bỏ vùng nền
            button.setBorder(BorderFactory.createEmptyBorder(25, 15, 25, 15));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Hiệu ứng hover
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setForeground(new Color(180, 180, 180)); // Chữ sáng hơn khi hover
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setForeground(Color.WHITE); // Trở lại màu trắng
                }
            });

            button.addActionListener(new MenuActionListener(item));
            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách giữa các nút
        }

        // Thêm các panel vào frame
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        // Tối ưu giao diện
        frame.getContentPane().setBackground(new Color(236, 240, 241));
        frame.setVisible(true);
    }

    private class MenuActionListener implements ActionListener {
        private String menuName;

        public MenuActionListener(String menuName) {
            this.menuName = menuName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (menuName.equals("Take Quiz") && !flag.get()) {
                contentPanel.removeAll();
                contentPanel.add(new ShowQuizPanel(student, bank, flag), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            if (menuName.equals("Review Info") && !flag.get()) {
                contentPanel.removeAll();
                contentPanel.add(new StudentInfoPanel(student), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            if (menuName.equals("View Exam Schedule") && !flag.get()) {
                contentPanel.removeAll();
                contentPanel.add(new ExamSchedulePanel(student), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            if (menuName.equals("View Detailed Quiz History") && !flag.get()) {
                contentPanel.removeAll();
                contentPanel.add(new QuizHistoryPanel(student, bank), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            if (menuName.equals("Register Subject") && !flag.get()) {
                contentPanel.removeAll();
                contentPanel.add(new RegisterSubjectPanel(bank, student), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            if (menuName.equals("Edit Account") && !flag.get()) {
                contentPanel.removeAll();
                contentPanel.add(new StudentEditPanel(student), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            if (menuName.equals("Logout") && !flag.get()) {
                frame.dispose(); // Đóng frame hiện tại

                // Mở lại form Login
                SwingUtilities.invokeLater(() -> {
                    Login loginFrame = new Login(system, bank);
                    loginFrame.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
                    loginFrame.setVisible(true); // Hiển thị cửa sổ
                });
            }
        }
    }
}