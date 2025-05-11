package Teacher;

import project.*; // Giả sử các class User, Student, Result, Subject, Ssystem, Bank nằm trong package này
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
// Bỏ import java.util.*; nếu không dùng hết
// Bỏ import java.util.List; vì đã có import project.* (nếu List nằm trong đó) hoặc giữ lại nếu cần

public class StudentScorePanel extends JPanel {
    private Ssystem ssystem;
    private Bank bank;
    private JTable scoreTable;
    private DefaultTableModel tableModel;
    private JComboBox<Subject> subjectFilterBox;

    // Thêm các JLabel để hiển thị thống kê
    private JLabel statsLabel;
    private JLabel gioiLabel;
    private JLabel khaLabel;
    private JLabel tbLabel;
    private JLabel yeuLabel;
    private JLabel totalLabel; // Hiển thị tổng số kết quả

    public StudentScorePanel(Ssystem ssystem, Bank bank) {
        this.ssystem = ssystem;
        this.bank = bank;
        setLayout(new BorderLayout(5, 5)); // Thêm khoảng cách giữa các component

        // --- Panel Lọc ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Canh lề trái
        subjectFilterBox = new JComboBox<>();
        subjectFilterBox.addItem(null); // Option "Tất cả môn học"
        // Đặt renderer để hiển thị tên môn học hoặc "Tất cả môn học"
        subjectFilterBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Subject) {
                    setText(((Subject) value).getName()); // Giả sử Subject có phương thức getName()
                } else {
                    setText("Tất cả môn học");
                }
                return this;
            }
        });

        List<Subject> allSubjects = bank.getSubjects();
        for (Subject subject : allSubjects) {
            subjectFilterBox.addItem(subject);
        }
        filterPanel.add(new JLabel("Lọc theo môn học:"));
        filterPanel.add(subjectFilterBox);
        add(filterPanel, BorderLayout.NORTH);

        // --- Bảng Điểm ---
        String[] columns = {"Tên ", "Lớp", "Môn học", "Điểm"};
        // Làm cho table không thể chỉnh sửa trực tiếp
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scoreTable = new JTable(tableModel);
        // Cho phép sắp xếp theo cột
        scoreTable.setAutoCreateRowSorter(true);
        add(new JScrollPane(scoreTable), BorderLayout.CENTER);

        // --- Panel Thống Kê ---
        JPanel statsPanel = new JPanel();
        // Sử dụng BoxLayout để các label xếp chồng lên nhau theo chiều dọc
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding

        statsLabel = new JLabel("Thống kê kết quả:");
        statsLabel.setFont(statsLabel.getFont().deriveFont(Font.BOLD)); // In đậm tiêu đề
        gioiLabel = new JLabel("Số lượng kết quả Giỏi (>= 8.0): 0");
        khaLabel = new JLabel("Số lượng kết quả Khá (>= 6.5): 0");
        tbLabel = new JLabel("Số lượng kết quả Trung Bình (>= 5.0): 0");
        yeuLabel = new JLabel("Số lượng kết quả Yếu (< 5.0): 0");
        totalLabel = new JLabel("Tổng số kết quả hiển thị: 0");

        // Canh lề trái cho các label trong BoxLayout
        statsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        gioiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        khaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tbLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        yeuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsPanel.add(statsLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Khoảng cách nhỏ
        statsPanel.add(totalLabel);
        statsPanel.add(gioiLabel);
        statsPanel.add(khaLabel);
        statsPanel.add(tbLabel);
        statsPanel.add(yeuLabel);

        add(statsPanel, BorderLayout.SOUTH); // Thêm panel thống kê vào dưới cùng

        // --- Listener ---
        subjectFilterBox.addActionListener(e -> refreshTableAndStats()); // Đổi tên phương thức

        // --- Tải dữ liệu lần đầu ---
        refreshTableAndStats(); // Gọi phương thức mới
    }

    // Đổi tên phương thức để rõ ràng hơn là nó cập nhật cả bảng và thống kê
    private void refreshTableAndStats() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        Subject selectedSubject = (Subject) subjectFilterBox.getSelectedItem();

        // Biến đếm cho thống kê
        int gioiCount = 0;
        int khaCount = 0;
        int tbCount = 0;
        int yeuCount = 0;
        int totalCount = 0;

        // Lặp qua danh sách người dùng
        for (User user : ssystem.getUsers()) {
            // Chỉ xử lý nếu người dùng là học sinh (Student)
            if (user instanceof Student student) {
                // Lặp qua lịch sử làm bài của học sinh
                for (Result result : student.getQuizHistory()) {
                    String subjectFromResult = result.getSubjectName();

                    // Kiểm tra điều kiện lọc môn học
                    // 1. Không có môn học nào được chọn (hiển thị tất cả) HOẶC
                    // 2. Môn học của kết quả khớp với môn học được chọn
                    boolean subjectMatches = (selectedSubject == null ||
                            (subjectFromResult != null && subjectFromResult.equals(selectedSubject.getName())));

                    if (subjectMatches) {
                        double score = result.getScore();

                        // Thêm dòng vào bảng
                        tableModel.addRow(new Object[]{
                                student.getUsername(),
                                student.getLop(), // Giả sử Student có getLop()
                                subjectFromResult,
                                score
                        });

                        // Cập nhật biến đếm thống kê dựa trên điểm
                        if (score >= 8.0) {
                            gioiCount++;
                        } else if (score >= 6.5) {
                            khaCount++;
                        } else if (score >= 5.0) {
                            tbCount++;
                        } else {
                            yeuCount++;
                        }
                        totalCount++; // Tăng tổng số kết quả phù hợp
                    }
                }
            }
        }

        // Cập nhật nội dung các JLabel thống kê
        String subjectFilterText = (selectedSubject != null) ? selectedSubject.getName() : "tất cả các môn";
        statsLabel.setText("Thống kê kết quả môn: " + subjectFilterText); // Cập nhật tiêu đề thống kê
        gioiLabel.setText(String.format("Số lượng kết quả Giỏi (>= 8.0): %d", gioiCount));
        khaLabel.setText(String.format("Số lượng kết quả Khá (>= 6.5): %d", khaCount));
        tbLabel.setText(String.format("Số lượng kết quả Trung Bình (>= 5.0): %d", tbCount));
        yeuLabel.setText(String.format("Số lượng kết quả Yếu (< 5.0): %d", yeuCount));
        totalLabel.setText(String.format("Tổng số kết quả hiển thị: %d", totalCount));
    }
}