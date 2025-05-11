package Student;

import project.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShowQuizPanel extends JPanel {
    private Student student;
    private AtomicBoolean flag;

    public ShowQuizPanel(Student student, Bank bank, AtomicBoolean flag) {
        this.student = student;
        this.flag = flag;

        // Thiết lập layout chính
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        setBackground(new Color(245, 245, 247)); // Màu nền nhẹ nhàng

        // Panel chứa các nút môn học
        JPanel containerPanel = new JPanel();
        List<Subject> subjects = student.getRegisteredSubjects();
        int rows = (subjects.size() + 1) / 2; // Số hàng = (số môn + 1) / 2 để đủ chỗ
        containerPanel.setLayout(new GridLayout(rows, 2, 15, 15)); // 2 cột, khoảng cách 15px
        containerPanel.setBackground(new Color(255, 255, 255));
        containerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tạo nút cho từng môn học
        for (Subject subject : subjects) {
            JButton button = new JButton(subject.getName() +"  " + subject.getNgayThi());
            button.setBackground(Color.WHITE); // Màu nền trắng
            button.setForeground(Color.BLACK); // Chữ màu đen cho dễ đọc
            button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            button.setPreferredSize(new Dimension(200, 60)); // Kích thước cố định
            button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2)); // Viền xám
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Hiệu ứng hover
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(240, 240, 240)); // Màu xám nhạt khi hover
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(Color.WHITE); // Trở lại màu trắng
                }
            });

            // Sự kiện click
            button.addActionListener((ActionEvent e) -> {
                flag.set(true);
                removeAll();
                add(new QuizPanel(subject.getRandomQuiz(), student, flag, subject.getName()), BorderLayout.CENTER);
                revalidate();
                repaint();
            });

            containerPanel.add(button);
        }

        // Nếu số nút lẻ, thêm một panel trống để giữ layout
        if (subjects.size() % 2 != 0) {
            containerPanel.add(new JPanel());
        }

        // ScrollPane để cuộn danh sách
        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        // Tùy chỉnh thanh cuộn
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(160, 160, 160);
                this.trackColor = new Color(240, 240, 240);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }
        });

        add(scrollPane, BorderLayout.CENTER);
    }
}