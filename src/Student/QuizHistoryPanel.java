
package Student;
import project.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizHistoryPanel extends JPanel {
    private Student student;
    private Bank bank;

    public QuizHistoryPanel(Student student, Bank bank) {
        this.student = student;
        this.bank = bank;
        setLayout(new BorderLayout());
        displayQuizHistory();
    }

    private void displayQuizHistory() {
        List<Result>quizHistory = student.getQuizHistory();

        // Tiêu đề
        JLabel titleLabel = new JLabel("Detailed Quiz History for: " + student.getUsername(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // Kiểm tra nếu không có lịch sử
        if (quizHistory.isEmpty()) {
            JLabel emptyLabel = new JLabel("No quiz history available.", JLabel.CENTER);
            add(emptyLabel, BorderLayout.CENTER);
            return;
        }

        // Tạo panel chứa nội dung chi tiết
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Duyệt qua từng Result để hiển thị
        for (Result result : quizHistory) {
            JTextArea resultText = new JTextArea();
            resultText.setEditable(false);
            resultText.append("Quiz Name: " + result.getQuizName() + "\n");
            resultText.append("Score: " + result.getScore() + "\n");
            resultText.append("Date Taken: " + result.getDateTaken() + "\n");
            resultText.append("Questions and Answers:\n");

            List<Answer> answers = result.getAnswers();
            for (Answer answer : answers) {
                if (answer != null) {
                    resultText.append("- " + answer.displayQuestionDetails() + "\n");
                } else {
                    resultText.append("- No answer provided for this question.\n");
                }
            }
            resultText.append("-------------------------\n");

            contentPanel.add(resultText);
        }

        // Thêm scroll để xem nội dung dài
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    }
}