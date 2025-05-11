package Teacher;

import project.*;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Dùng cho Objects.requireNonNullElse

public class BankInfoPanel extends JPanel {

    private final Bank bank;
    private JComboBox<Subject> subjectFilterBox;
    private JTextPane infoPane; // Thay JTextArea bằng JTextPane để hỗ trợ HTML
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Định dạng ngày giờ

    public BankInfoPanel(Bank bank) {
        // Khởi tạo các thuộc tính cơ bản của panel
        this.bank = Objects.requireNonNull(bank, "Bank object cannot be null");
        setLayout(new BorderLayout(5, 5)); // BorderLayout với khoảng cách 5px
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding xung quanh panel

        // --- Panel Bộ lọc (Phía trên) ---
        setupFilterPanel();

        // --- Panel Hiển thị Thông tin (Trung tâm) ---
        setupInfoPanel();

        // --- Tải dữ liệu ban đầu (hiển thị tất cả) ---
        refreshDisplay();
    }

    // Thiết lập ComboBox lọc môn học
    private void setupFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Lọc theo môn học:"));

        subjectFilterBox = new JComboBox<>();
        // Thêm tùy chọn "Tất cả môn học" (đại diện bằng null)
        subjectFilterBox.addItem(null);

        // Thêm các môn học từ Bank vào ComboBox
        if (bank.getSubjects() != null) {
            for (Subject subject : bank.getSubjects()) {
                subjectFilterBox.addItem(subject);
            }
        }

        // Custom renderer để hiển thị tên môn học hoặc "Tất cả môn học"
        subjectFilterBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Subject) {
                    // Giả sử Subject có phương thức getName() trả về tên môn học
                    setText(((Subject) value).getName());
                } else if (value == null) {
                    setText("Tất cả môn học"); // Hiển thị cho giá trị null
                }
                return this;
            }
        });

        // Thêm ActionListener để cập nhật hiển thị khi lựa chọn thay đổi
        subjectFilterBox.addActionListener(e -> refreshDisplay());

        filterPanel.add(subjectFilterBox);
        add(filterPanel, BorderLayout.NORTH); // Thêm panel lọc vào phía trên
    }

    // Thiết lập JTextPane để hiển thị thông tin
    private void setupInfoPanel() {
        infoPane = new JTextPane();
        infoPane.setEditable(false); // Không cho phép chỉnh sửa
        infoPane.setContentType("text/html"); // Đặt kiểu nội dung là HTML
        // Cài đặt bộ editor kit cơ bản cho HTML (hỗ trợ CSS nội tuyến đơn giản nếu muốn)
        infoPane.setEditorKit(new HTMLEditorKit());

        JScrollPane scrollPane = new JScrollPane(infoPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER); // Thêm vào vùng trung tâm
    }

    // Cập nhật nội dung hiển thị dựa trên bộ lọc
    private void refreshDisplay() {
        Subject selectedSubject = (Subject) subjectFilterBox.getSelectedItem();
        List<Subject> subjectsToDisplay = new ArrayList<>();

        // Xác định danh sách môn học cần hiển thị
        if (selectedSubject == null) {
            // Nếu chọn "Tất cả môn học", lấy toàn bộ danh sách từ bank
            if (bank.getSubjects() != null) {
                subjectsToDisplay.addAll(bank.getSubjects());
            }
        } else {
            // Nếu chọn một môn cụ thể, chỉ thêm môn đó vào danh sách
            subjectsToDisplay.add(selectedSubject);
        }

        // Xây dựng nội dung HTML
        StringBuilder htmlContent = new StringBuilder("<html><body style='font-family: sans-serif; padding: 5px;'>");

        // Thêm thông tin chung của Bank (nếu muốn, ví dụ: chỉ hiển thị khi chọn "Tất cả")
        if (selectedSubject == null && bank.getDate() != null) {
            htmlContent.append("<h2>THÔNG TIN NGÂN HÀNG ĐỀ</h2>");
            htmlContent.append("<b>Ngày tạo ngân hàng:</b> ").append(bank.getDate()).append("<hr>"); // Sử dụng formatter đã định nghĩa
        }

        // Lặp qua danh sách môn học cần hiển thị
        if (subjectsToDisplay.isEmpty() && selectedSubject != null) {
            htmlContent.append("<h3>Môn học: ").append(selectedSubject.getName()).append("</h3>");
            htmlContent.append("<p><i>(Không có thông tin chi tiết hoặc quiz nào cho môn này)</i></p>");
        } else if (subjectsToDisplay.isEmpty()) {
            htmlContent.append("<p><i>(Không có môn học nào trong ngân hàng đề)</i></p>");
        }


        for (Subject subject : subjectsToDisplay) {
            // --- Thông tin Môn học ---
            htmlContent.append("<h3>Môn học: ").append(escapeHtml(subject.getName())).append("</h3>"); // Dùng escapeHtml cho tên
            // Sử dụng Objects.requireNonNullElse để tránh NullPointerException nếu getTeacher trả về null
            htmlContent.append("<b>Giáo viên:</b> ").append(escapeHtml(Objects.requireNonNullElse(subject.getTeacher(), "N/A").toString())).append("<br>");
            if (subject.getNgayThi() != null) {
                htmlContent.append("<b>Ngày thi:</b> ").append(subject.getNgayThi()).append("<br>");
            } else {
                htmlContent.append("<b>Ngày thi:</b> N/A<br>");
            }
            htmlContent.append("<b>Thời gian chờ vào thi:</b> ").append(subject.getTime()).append(" phút<br>");

            // --- Danh sách Quiz của Môn học ---
            List<Quiz> quizzes = subject.getQuizzes();
            if (quizzes == null || quizzes.isEmpty()) {
                htmlContent.append("<p><i>(Không có quiz nào cho môn học này)</i></p>");
            } else {
                htmlContent.append("<h4>Danh sách Quiz:</h4>");
                htmlContent.append("<ul>"); // Bắt đầu danh sách không thứ tự cho quizzes

                for (Quiz quiz : quizzes) {
                    htmlContent.append("<li>"); // Bắt đầu một mục quiz
                    htmlContent.append("<b>Tên Quiz:</b> ").append(escapeHtml(quiz.getName())).append("<br>");
                    htmlContent.append("  <b>Tạo bởi:</b> ").append(escapeHtml(Objects.requireNonNullElse(quiz.getCreateBy(), "N/A").toString())).append("<br>");
                    if (quiz.getCreatedDate() != null) {
                        htmlContent.append("  <b>Ngày tạo:</b> ").append(quiz.getCreatedDate().format(formatter)).append("<br>");
                    } else {
                        htmlContent.append("  <b>Ngày tạo:</b> N/A<br>");
                    }
                    htmlContent.append("  <b>Thời gian làm bài:</b> ").append(quiz.getthoiGianLamBai()).append(" phút");

                    // --- Danh sách Câu hỏi của Quiz ---
                    List<Question> questions = quiz.getQuestions();
                    if (questions != null && !questions.isEmpty()) {
                        htmlContent.append("<div style='margin-left: 20px; margin-top: 5px; border-left: 2px solid #eee; padding-left: 10px;'>"); // Khối câu hỏi thụt vào
                        htmlContent.append("<b>Câu hỏi:</b><br>");
                        for (int i = 0; i < questions.size(); i++) {
                            Question q = questions.get(i);
                            htmlContent.append("<div style='margin-bottom: 10px;'>"); // Khối cho từng câu hỏi
                            htmlContent.append("  <b>").append(i + 1).append(". ").append(escapeHtml(q.getText())).append("</b><br>");
                            htmlContent.append("  <span style='margin-left: 15px;'>A. ").append(escapeHtml(q.getAnswerA())).append("</span><br>");
                            htmlContent.append("  <span style='margin-left: 15px;'>B. ").append(escapeHtml(q.getAnswerB())).append("</span><br>");
                            htmlContent.append("  <span style='margin-left: 15px;'>C. ").append(escapeHtml(q.getAnswerC())).append("</span><br>");
                            htmlContent.append("  <span style='margin-left: 15px;'>D. ").append(escapeHtml(q.getAnswerD())).append("</span><br>");
                            htmlContent.append("  <span style='margin-left: 15px;'><b>Đáp án đúng:</b> ").append(escapeHtml(String.valueOf(q.getCorrectAnswer()))).append("</span><br>");
                            htmlContent.append("</div>"); // Kết thúc khối câu hỏi
                        }
                        htmlContent.append("</div>"); // Kết thúc khối danh sách câu hỏi
                    } else {
                        htmlContent.append("<br><i>(Không có câu hỏi nào cho quiz này)</i>");
                    }
                    htmlContent.append("</li><br>"); // Kết thúc mục quiz, thêm khoảng cách
                }
                htmlContent.append("</ul>"); // Kết thúc danh sách quizzes
            }
            htmlContent.append("<hr>"); // Đường kẻ ngang phân cách các môn học
        }

        htmlContent.append("</body></html>");

        // Đặt nội dung HTML cho JTextPane và cuộn lên đầu
        infoPane.setText(htmlContent.toString());
        infoPane.setCaretPosition(0); // Đưa thanh cuộn về đầu
    }


    private String escapeHtml(String s) {
        if (s == null) return "";
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '>' || c == '<' || c == '&' || c == '"' || c == '\'') {
                out.append('&');
                out.append('#');
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}