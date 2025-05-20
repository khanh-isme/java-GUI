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
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
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

        subjectNameField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateFields() {
                String subjectName = subjectNameField.getText().trim();
                if (subjectName.isEmpty()) {
                    examTimeField.setText("");
                    examTimeEnterField.setText("");
                    return;
                }

                Subject existingSubject = SubjectDAO.getSubjectFromDB(subjectName);
                if (existingSubject != null) {
                    examTimeField.setText(existingSubject.getNgayThiStr());
                    examTimeEnterField.setText(existingSubject.getTimeStr());
                } else {
                    // Nếu không tìm thấy, có thể cũng nên xóa dữ liệu cũ (tuỳ logic bạn muốn)
                    examTimeField.setText("");
                    examTimeEnterField.setText("");
                }
            }


            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFields();
            }
        });


        JPanel subjectPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        subjectPanel.setBorder(BorderFactory.createTitledBorder("Thông tin môn học"));
        subjectPanel.setFont(font);

        subjectPanel.add(new JLabel("Tên môn:"));
        subjectPanel.add(subjectNameField);


        subjectPanel.add(new JLabel("Ngày thi (yyyy-MM-dd HH:mm):"));
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

        quizNameField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateFields() {
                String quizName = quizNameField.getText().trim();
                String subjectName = subjectNameField.getText().trim();

                if (quizName.isEmpty() || subjectName.isEmpty()) {
                    quizTimeField.setText("");
                    return;
                }

                Quiz existingQuiz = QuizDAO.getQuizByNameAndSubject(quizName, subjectName);
                if (existingQuiz != null) {
                    quiz = existingQuiz; // Gán quiz để có thể thêm câu hỏi
                    quizTimeField.setText(String.valueOf(existingQuiz.getthoiGianLamBai()));

                    // Tùy chọn: hiện thông báo 1 lần duy nhất
                    quizNameField.putClientProperty("quizFound", true);
                } else {
                    quizTimeField.setText("");
                    quiz = null;
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFields();
            }
        });


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


        JButton saveBtn = createButton("Lưu", font);

        addQuizBtn.addActionListener(e -> {
            int lastId = QuizDAO.getLastQuizIdFromDB() + 1;

            String quizName = quizNameField.getText().trim();
            String subjectName = subjectNameField.getText().trim();

            Quiz existingQuiz = QuizDAO.getQuizByNameAndSubject(quizName, subjectName);

            if (existingQuiz != null) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "Quiz '" + quizName + "' cho môn '" + subjectName + "' đã tồn tại.\nBạn có muốn thêm câu hỏi vào quiz này không?",
                        "Quiz đã tồn tại", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (choice == JOptionPane.YES_OPTION) {
                    this.quiz = existingQuiz; // Gán quiz để thêm câu hỏi
                    JOptionPane.showMessageDialog(this, "Đã chuyển sang chế độ thêm câu hỏi cho quiz hiện có.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
                // Nếu chọn NO thì không làm gì (không tạo mới), chỉ return
                return;
            }

            // Nếu chưa có quiz thì tạo mới
            Quiz newQuiz = QuizValidator.validateQuiz(lastId, quizName, teacher.getUsername(), subjectName,
                    null, null, quizTimeField.getText());
            QuizDAO.saveQuizToDB(newQuiz);

            if (newQuiz == null) return;

            this.quiz = newQuiz;
            JOptionPane.showMessageDialog(this, "Thêm quiz mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
            QuestionDAO.saveQuestionToDB(q);
            if (q != null) {
                this.questions.add(q);
                resetFields();
            }
        });

        saveBtn.addActionListener(e -> {
            if (quiz == null) {
                JOptionPane.showMessageDialog(this, "Bạn cần tạo quiz trước khi thêm câu hỏi!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate subject
            String subjectName = subjectNameField.getText().trim();
            if (subjectName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên môn học.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Subject existingSubject = SubjectDAO.getSubjectFromDB(subjectName);
            if (existingSubject == null) {
                int newSubjectId = SubjectDAO.getLastSubjectIdFromDB() + 1;
                subject = SubjectBUS.validateSubject(
                        newSubjectId,
                        subjectName,
                        teacher.getUsername(),
                        null,
                        examTimeField.getText(),
                        examTimeEnterField.getText()
                );

                if (subject == null) {
                    JOptionPane.showMessageDialog(this, "Dữ liệu môn học không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Thêm quiz mới vào subject
                subject.addQuiz(quiz);
                bank.addSubject(subject);
                SubjectDAO.saveSubjectToDB(subject);
            } else {
                subject = existingSubject;
                SubjectDAO.updateSubject(subject);
                subject.addQuiz(quiz);
            }

            // Gán danh sách câu hỏi cho quiz
            quiz.addListQuestion(questions);

            JOptionPane.showMessageDialog(this, "Đã lưu quiz và câu hỏi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Reset form nếu muốn:
            questions.clear();
            resetFields();
            quiz = null;
        });


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(saveBtn);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(quizPanel, BorderLayout.NORTH);
        centerPanel.add(questionPanel, BorderLayout.CENTER);

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
