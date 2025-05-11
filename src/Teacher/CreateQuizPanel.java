package Teacher;

import DAO.QuestionDAO;
import DAO.QuizDAO;
import DAO.SubjectDAO;
import project.*;
import BUS.*;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.util.UIScale;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateQuizPanel extends JPanel {
    private JTextField subjectNameField, examTimeField, examTimeEnterField, quizNameField, quizTimeField;
    private JTextField questionTextField, answerAField, answerBField, answerCField, answerDField, correctAnswerField;
    private DefaultListModel<Question> questionListModel;
    private Teacher teacher;
    private Subject subject;
    private Quiz quiz;
    private List<Question> questions;

    public CreateQuizPanel(Bank bank, Teacher teacher) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.questions = new ArrayList<>();
        this.teacher = teacher;

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        subjectNameField = createField(font);
        examTimeField = createField(font);
        examTimeEnterField = createField(font);

        JPanel subjectPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        subjectPanel.setBorder(BorderFactory.createTitledBorder("Thông tin môn học"));
        subjectPanel.setFont(font);

        subjectPanel.add(new JLabel("Tên môn:"));
        subjectPanel.add(subjectNameField);
        subjectPanel.add(new JLabel("Ngày thi (yyyy-MM-ddTHH:mm):"));
        subjectPanel.add(examTimeField);
        subjectPanel.add(new JLabel("Thời gian cho vào:"));
        subjectPanel.add(examTimeEnterField);

        quizNameField = createField(font);
        quizTimeField = createField(font);

        JPanel quizPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        quizPanel.setBorder(BorderFactory.createTitledBorder("Quiz"));
        quizPanel.setFont(font);
        quizPanel.add(new JLabel("Tên Quiz:"));
        quizPanel.add(quizNameField);
        quizPanel.add(new JLabel("Thời gian làm bài (phút):"));
        quizPanel.add(quizTimeField);

        JButton addQuizBtn = createButton("Thêm quiz", font);
        quizPanel.add(new JLabel()); // filler
        quizPanel.add(addQuizBtn);

        questionTextField = createField(font);
        answerAField = createField(font);
        answerBField = createField(font);
        answerCField = createField(font);
        answerDField = createField(font);
        correctAnswerField = createField(font);

        JPanel questionPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        questionPanel.setBorder(BorderFactory.createTitledBorder("Câu hỏi"));
        questionPanel.setFont(font);

        questionPanel.add(new JLabel("Nội dung câu hỏi:"));
        questionPanel.add(questionTextField);
        questionPanel.add(new JLabel("A:"));
        questionPanel.add(answerAField);
        questionPanel.add(new JLabel("B:"));
        questionPanel.add(answerBField);
        questionPanel.add(new JLabel("C:"));
        questionPanel.add(answerCField);
        questionPanel.add(new JLabel("D:"));
        questionPanel.add(answerDField);
        questionPanel.add(new JLabel("Đáp án đúng (A/B/C/D):"));
        questionPanel.add(correctAnswerField);

        JButton addQuestionBtn = createButton("Thêm câu hỏi", font);
        questionPanel.add(new JLabel());
        questionPanel.add(addQuestionBtn);

        questionListModel = new DefaultListModel<>();
        JList<Question> questionList = new JList<>(questionListModel);
        questionList.setFont(font);
        JScrollPane scrollPane = new JScrollPane(questionList);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        JButton saveBtn = createButton("Lưu", font);

        addQuizBtn.addActionListener(e -> {
            int lastId = QuizDAO.getLastQuizIdFromDB() + 1;
            Quiz quizdemo = QuizValidator.validateQuiz(lastId, quizNameField.getText(), teacher.getUsername(), subjectNameField.getText(), null,
                    null, quizTimeField.getText());
            if (quizdemo == null) return;
            this.quiz = quizdemo;
            JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        addQuestionBtn.addActionListener(e -> {
            if (quiz == null) {
                JOptionPane.showMessageDialog(this, "Bạn cần tạo quiz trước khi thêm câu hỏi!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int questionId = QuestionDAO.getLastQuestionIdFromDb() + 1;
            Question q = QuestionValidator.validateQuestion(questionId, quiz.getId(),
                    questionTextField.getText(), answerAField.getText(), answerBField.getText(),
                    answerCField.getText(), answerDField.getText(),
                    correctAnswerField.getText().toUpperCase());
            if (q != null) {
                this.questions.add(q);
                questionListModel.addElement(q);
                resetFields();
            }
        });

        saveBtn.addActionListener(e -> {
            if (quiz == null) {
                JOptionPane.showMessageDialog(this, "Bạn cần tạo quiz trước khi thêm câu hỏi!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = SubjectDAO.getLastSubjectIdFromDB() + 1;
            this.subject = SubjectBUS.validateSubject(id, subjectNameField.getText(), teacher.getUsername(),
                    null, examTimeField.getText(), examTimeEnterField.getText());
            if (subject == null) return;

            quiz.addListQuestion(questions);
            subject.addQuiz(quiz);
            bank.addSubject(subject);

            SubjectDAO.saveSubjectToDB(subject);
            QuizDAO.saveQuizToDB(quiz);
            JOptionPane.showMessageDialog(this, "Môn học '" + subject.getName() + "' đã được tạo");
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(saveBtn);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(quizPanel, BorderLayout.NORTH);
        centerPanel.add(questionPanel, BorderLayout.CENTER);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        add(subjectPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JTextField createField(Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập...");
        return field;
    }

    private JButton createButton(String text, Font font) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setBackground(new Color(60, 120, 200));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        return btn;
    }

    private void resetFields() {
        questionTextField.setText("");
        answerAField.setText("");
        answerBField.setText("");
        answerCField.setText("");
        answerDField.setText("");
        correctAnswerField.setText("");
    }
}
