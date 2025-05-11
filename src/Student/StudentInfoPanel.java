package Student;
import project.*;
import javax.swing.*;
import java.awt.*;

public class StudentInfoPanel extends JPanel {
    public StudentInfoPanel(Student student) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Tiêu đề
        JLabel titleLabel = new JLabel("Thông tin Sinh viên", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        // Tạo panel chứa thông tin
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        Font textFont = new Font("Arial", Font.PLAIN, 16);

        // Thêm thông tin MSSV
        JLabel mssvLabel = new JLabel("MSSV: " + student.getMssv());
        mssvLabel.setFont(textFont);
        infoPanel.add(mssvLabel);

        JLabel usernameLabel = new JLabel("Tên tài khoản: " + student.getUsername());
        usernameLabel.setFont(textFont);
        infoPanel.add(usernameLabel);

        // Thêm lịch sử bài thi
        JLabel quizHistoryLabel = new JLabel("Lịch sử bài thi:");
        quizHistoryLabel.setFont(textFont);
        infoPanel.add(quizHistoryLabel);

        if (student.getQuizHistory().isEmpty()) {
            JLabel noHistoryLabel = new JLabel("Không có lịch sử bài thi.");
            noHistoryLabel.setFont(textFont);
            infoPanel.add(noHistoryLabel);
        } else {
            for (Result result : student.getQuizHistory()) {
                JLabel subjectLabel = new JLabel("Môn: " + result   .getSubjectName());
                subjectLabel.setFont(textFont);
                infoPanel.add(subjectLabel);

                JLabel quizLabel = new JLabel("Quiz: " + result.getQuizId());
                quizLabel.setFont(textFont);
                infoPanel.add(quizLabel);

                JLabel scoreLabel = new JLabel("Điểm: " + result.getScore());
                scoreLabel.setFont(textFont);
                infoPanel.add(scoreLabel);

                JLabel dateLabel = new JLabel("Ngày thi: " + result.getDateTaken());
                dateLabel.setFont(textFont);
                infoPanel.add(dateLabel);

                JLabel separator = new JLabel("-------------------------");
                separator.setFont(textFont);
                infoPanel.add(separator);
            }
        }

        // Thêm danh sách môn học đã đăng ký
        JLabel registeredSubjectsLabel = new JLabel("Môn học đã đăng ký:");
        registeredSubjectsLabel.setFont(textFont);
        infoPanel.add(registeredSubjectsLabel);

        if (student.getRegisteredSubjects().isEmpty()) {
            JLabel noSubjectsLabel = new JLabel("Không có môn học đã đăng ký.");
            noSubjectsLabel.setFont(textFont);
            infoPanel.add(noSubjectsLabel);
        } else {
            for (Subject subject : student.getRegisteredSubjects()) {
                JLabel subjectNameLabel = new JLabel(subject.getName());
                subjectNameLabel.setFont(textFont);
                infoPanel.add(subjectNameLabel);
            }
        }

        add(infoPanel, BorderLayout.CENTER);
    }
}