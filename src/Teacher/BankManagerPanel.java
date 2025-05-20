package Teacher;

import BUS.QuestionValidator;
import BUS.QuizValidator;
import DAO.QuestionDAO;
import DAO.QuizDAO;
import DAO.SubjectDAO;
import project.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.text.Normalizer;

public class BankManagerPanel extends JPanel {
    private Bank bank;

    private JList<Subject> subjectList;
    private JList<Quiz> quizList;
    private JList<Question> questionList;

    private JTextField subjectSearchField = new JTextField(15);
    private JTextField quizSearchField = new JTextField(15);
    private JTextField questionSearchField = new JTextField(15);

    private Subject subjectSelected;
    private Quiz quizSelected;
    private Question questionSelected;

    private DefaultListModel<Subject> subjectModel = new DefaultListModel<>();
    private DefaultListModel<Quiz> quizModel = new DefaultListModel<>();
    private DefaultListModel<Question> questionModel = new DefaultListModel<>();

    public BankManagerPanel(Bank bank) {
        this.bank = bank;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initLists();
        add(createListPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadSubjects("");

        initListeners();
    }

    private void initLists() {
        subjectList = new JList<>(subjectModel);
        quizList = new JList<>(quizModel);
        questionList = new JList<>(questionModel);

        subjectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private JPanel createListPanel() {
        JPanel subjectPanel = new JPanel(new BorderLayout());
        subjectPanel.add(subjectSearchField, BorderLayout.NORTH);
        subjectPanel.add(new JScrollPane(subjectList), BorderLayout.CENTER);
        subjectPanel.setBorder(BorderFactory.createTitledBorder("Subjects"));

        JPanel quizPanel = new JPanel(new BorderLayout());
        quizPanel.add(quizSearchField, BorderLayout.NORTH);
        quizPanel.add(new JScrollPane(quizList), BorderLayout.CENTER);
        quizPanel.setBorder(BorderFactory.createTitledBorder("Quizzes"));

        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.add(questionSearchField, BorderLayout.NORTH);
        questionPanel.add(new JScrollPane(questionList), BorderLayout.CENTER);
        questionPanel.setBorder(BorderFactory.createTitledBorder("Questions"));

        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.add(subjectPanel);
        panel.add(quizPanel);
        panel.add(questionPanel);

        return panel;
    }

    private void initListeners() {
        subjectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                subjectSelected = subjectList.getSelectedValue();
                loadQuizzes(subjectSelected, quizSearchField.getText().trim());
            }
        });

        quizList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                quizSelected = quizList.getSelectedValue();
                loadQuestions(quizSelected, questionSearchField.getText().trim());
            }
        });

        subjectSearchField.getDocument().addDocumentListener(new SearchListener(() -> {
            loadSubjects(subjectSearchField.getText().trim());
        }));

        quizSearchField.getDocument().addDocumentListener(new SearchListener(() -> {
            loadQuizzes(subjectSelected, quizSearchField.getText().trim());
        }));

        questionSearchField.getDocument().addDocumentListener(new SearchListener(() -> {
            loadQuestions(quizSelected, questionSearchField.getText().trim());
        }));
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton editSubject = new JButton("Edit Subject");
        JButton deleteSubject = new JButton("Delete Subject");
        JButton editQuiz = new JButton("Edit Quiz");
        JButton deleteQuiz = new JButton("Delete Quiz");
        JButton editQuestion = new JButton("Edit Question");
        JButton deleteQuestion = new JButton("Delete Question");

        panel.add(editSubject);
        panel.add(deleteSubject);
        panel.add(editQuiz);
        panel.add(deleteQuiz);
        panel.add(editQuestion);
        panel.add(deleteQuestion);

        deleteSubject.addActionListener(e -> deleteSelectedSubject());
        deleteQuiz.addActionListener(e -> deleteSelectedQuiz());
        deleteQuestion.addActionListener(e -> deleteSelectedQuestion());

        editSubject.addActionListener(e -> editSubjectDialog());
        editQuiz.addActionListener(e -> editQuizDialog());
        editQuestion.addActionListener(e -> editQuestionDialog());

        return panel;
    }

    private void loadSubjects(String filter) {
        subjectModel.clear();
        bank.getSubjects().stream()
                .filter(s -> matchesFilter(s.getName(), filter))
                .forEach(subjectModel::addElement);
    }

    private void loadQuizzes(Subject subject, String filter) {
        quizModel.clear();
        questionModel.clear();
        if (subject != null) {
            subject.getQuizzes().stream()
                    .filter(q -> matchesFilter(q.getName(), filter))
                    .forEach(quizModel::addElement);
        }
    }

    private void loadQuestions(Quiz quiz, String filter) {
        questionModel.clear();
        if (quiz != null) {
            quiz.getQuestions().stream()
                    .filter(q -> matchesFilter(q.getText(), filter))
                    .forEach(questionModel::addElement);
        }
    }

    private void deleteSelectedSubject() {
        subjectSelected = subjectList.getSelectedValue();
        if (subjectSelected != null && confirmDelete("Subject", subjectSelected.getName())) {
            if (SubjectDAO.deleteSubject(subjectSelected.getName())) {
                bank.getSubjects().remove(subjectSelected);
                loadSubjects(subjectSearchField.getText().trim());
                quizModel.clear();
                questionModel.clear();
                showInfo("Đã xoá Subject.");
            } else {
                showError("Xoá Subject thất bại.");
            }
        }
    }

    private void deleteSelectedQuiz() {
        quizSelected = quizList.getSelectedValue();
        if (quizSelected != null && confirmDelete("Quiz", quizSelected.getName())) {
            if (QuizDAO.deleteQuiz(quizSelected.getId())) {
                subjectSelected.getQuizzes().remove(quizSelected);
                loadQuizzes(subjectSelected, quizSearchField.getText().trim());
                questionModel.clear();
                showInfo("Đã xoá Quiz.");
            } else {
                showError("Xoá Quiz thất bại.");
            }
        }
    }

    private void deleteSelectedQuestion() {
        questionSelected = questionList.getSelectedValue();
        if (questionSelected != null && confirmDelete("Question", questionSelected.getText())) {
            if (QuestionDAO.deleteQuestion(questionSelected.getId())) {
                quizSelected.getQuestions().remove(questionSelected);
                loadQuestions(quizSelected, questionSearchField.getText().trim());
                showInfo("Đã xoá Question.");
            } else {
                showError("Xoá Question thất bại.");
            }
        }
    }

    private boolean confirmDelete(String type, String name) {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc muốn xoá " + type + " \"" + name + "\" không?",
                "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION
        );
        return confirm == JOptionPane.YES_OPTION;
    }

    private void editSubjectDialog() {
        Subject s = subjectList.getSelectedValue();
        if (s == null) return;

        JTextField nameField = new JTextField(s.getName());
        JTextField teacherField = new JTextField(s.getTeacher());
        JTextField dateField = new JTextField(s.getNgayThi().toString());
        JTextField timeField = new JTextField(String.valueOf(s.getTime()));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Tên môn học:")); panel.add(nameField);
        panel.add(new JLabel("Giáo viên phụ trách:")); panel.add(teacherField);
        panel.add(new JLabel("Ngày thi (yyyy-MM-ddTHH:mm):")); panel.add(dateField);
        panel.add(new JLabel("Thời gian thi (phút):")); panel.add(timeField);

        if (JOptionPane.showConfirmDialog(null, panel, "Chỉnh sửa môn học", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                s.setName(nameField.getText());
                s.setTeacher(teacherField.getText());
                s.setTime(Integer.parseInt(timeField.getText()));
                s.setNgayThi(LocalDateTime.parse(dateField.getText()));
                SubjectDAO.updateSubject(s);
                subjectList.repaint();
            } catch (Exception ex) {
                showError("Lỗi định dạng dữ liệu.");
            }
        }
    }

    private void editQuizDialog() {
        Quiz q = quizList.getSelectedValue();
        if (q == null) return;

        JTextField nameField = new JTextField(q.getName());
        JTextField creatorField = new JTextField(q.getCreateBy());
        JTextField subjectField = new JTextField(q.getSubjectName());
        JTextField dateField = new JTextField(q.getNgayTaoStr());
        JTextField timeField = new JTextField(String.valueOf(q.getthoiGianLamBai()));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Tên Quiz:")); panel.add(nameField);
        panel.add(new JLabel("Người tạo:")); panel.add(creatorField);
        panel.add(new JLabel("Môn học:")); panel.add(subjectField);
        panel.add(new JLabel("Ngày tạo (yyyy-MM-dd HH:mm:ss):")); panel.add(dateField);
        panel.add(new JLabel("Thời gian làm bài:")); panel.add(timeField);

        if (JOptionPane.showConfirmDialog(null, panel, "Chỉnh sửa Quiz", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Quiz validated = QuizValidator.validateQuiz(
                    q.getId(), nameField.getText(), creatorField.getText(),
                    subjectField.getText(), dateField.getText(), null, timeField.getText());

            if (validated != null) {
                q.setName(validated.getName());
                q.setCreateBy(validated.getCreateBy());
                q.setSubjectName(validated.getSubjectName());
                q.setCreatedDate(validated.getCreatedDate());
                q.setThoiGianLamBai(validated.getthoiGianLamBai());
                QuizDAO.updateQuizt(q);
                quizList.repaint();
            }
        }
    }

    private void editQuestionDialog() {
        Question q = questionList.getSelectedValue();
        if (q == null) return;

        JTextField textField = new JTextField(q.getText());
        JTextField aField = new JTextField(q.getAnswerA());
        JTextField bField = new JTextField(q.getAnswerB());
        JTextField cField = new JTextField(q.getAnswerC());
        JTextField dField = new JTextField(q.getAnswerD());
        JTextField correctField = new JTextField(q.getCorrectAnswer());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Câu hỏi:")); panel.add(textField);
        panel.add(new JLabel("Đáp án A:")); panel.add(aField);
        panel.add(new JLabel("Đáp án B:")); panel.add(bField);
        panel.add(new JLabel("Đáp án C:")); panel.add(cField);
        panel.add(new JLabel("Đáp án D:")); panel.add(dField);
        panel.add(new JLabel("Đáp án đúng (A/B/C/D):")); panel.add(correctField);

        if (JOptionPane.showConfirmDialog(null, panel, "Chỉnh sửa câu hỏi", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            Question validated = QuestionValidator.validateQuestion(
                    q.getId(), q.getQuizId(),
                    textField.getText(), aField.getText(), bField.getText(),
                    cField.getText(), dField.getText(), correctField.getText().toUpperCase());

            if (validated != null) {
                q.setText(validated.getText());
                q.setAnswerA(validated.getAnswerA());
                q.setAnswerB(validated.getAnswerB());
                q.setAnswerC(validated.getAnswerC());
                q.setAnswerD(validated.getAnswerD());
                q.setCorrectAnswer(validated.getCorrectAnswer());
                QuestionDAO.updateQuestion(q);
                questionList.repaint();
            }
        }
    }

    private boolean matchesFilter(String source, String filter) {
        return removeVietnameseDiacritics(source.toLowerCase())
                .contains(removeVietnameseDiacritics(filter.toLowerCase()));
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Hệ thống", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public static String removeVietnameseDiacritics(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(str).replaceAll("").replace("đ", "d").replace("Đ", "D");
    }

    private static class SearchListener implements DocumentListener {
        private final Runnable callback;

        public SearchListener(Runnable callback) {
            this.callback = callback;
        }

        @Override public void insertUpdate(DocumentEvent e) { callback.run(); }
        @Override public void removeUpdate(DocumentEvent e) { callback.run(); }
        @Override public void changedUpdate(DocumentEvent e) { callback.run(); }
    }
}
