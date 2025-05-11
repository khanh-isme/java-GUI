package Teacher;

import BUS.QuestionValidator;
import BUS.QuizValidator;
import BUS.SubjectBUS;
import DAO.QuestionDAO;
import DAO.QuizDAO;
import DAO.SubjectDAO;
import project.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BankManagerPanel extends JPanel {
    private Bank bank;
    private JList<Subject> subjectList;
    private JList<Quiz> quizList;
    private JList<Question> questionList;

    private DefaultListModel<Subject> subjectModel = new DefaultListModel<>();
    private DefaultListModel<Quiz> quizModel = new DefaultListModel<>();
    private DefaultListModel<Question> questionModel = new DefaultListModel<>();

    public BankManagerPanel(Bank bank) {
        this.bank = bank;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // SUBJECT LIST
        subjectList = new JList<>(subjectModel);
        subjectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane subjectScroll = new JScrollPane(subjectList);
        subjectScroll.setBorder(BorderFactory.createTitledBorder("Subjects"));

        // QUIZ LIST
        quizList = new JList<>(quizModel);
        quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane quizScroll = new JScrollPane(quizList);
        quizScroll.setBorder(BorderFactory.createTitledBorder("Quizzes"));

        // QUESTION LIST
        questionList = new JList<>(questionModel);
        questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane questionScroll = new JScrollPane(questionList);
        questionScroll.setBorder(BorderFactory.createTitledBorder("Questions"));

        // PANELs
        JPanel listPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        listPanel.add(subjectScroll);
        listPanel.add(quizScroll);
        listPanel.add(questionScroll);

        add(listPanel, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadSubjects();

        subjectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadQuizzes(subjectList.getSelectedValue());
            }
        });

        quizList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadQuestions(quizList.getSelectedValue());
            }
        });
    }

    private void loadSubjects() {
        subjectModel.clear();
        for (Subject s : bank.getSubjects()) {
            subjectModel.addElement(s);
        }
    }

    private void loadQuizzes(Subject subject) {
        quizModel.clear();
        questionModel.clear();
        if (subject != null) {
            for (Quiz q : subject.getQuizzes()) {
                quizModel.addElement(q);
            }
        }
    }

    private void loadQuestions(Quiz quiz) {
        questionModel.clear();
        if (quiz != null) {
            for (Question q : quiz.getQuestions()) {
                questionModel.addElement(q);
            }
        }
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton editSubject = new JButton("Edit Subject");
        JButton deleteSubject = new JButton("Delete Subject");

        JButton editQuiz = new JButton("Edit Quiz");
        JButton deleteQuiz = new JButton("Delete Quiz");

        JButton editQuestion = new JButton("Edit Question");
        JButton deleteQuestion = new JButton("Delete Question");

        JButton saveBtn = new JButton("save");

        panel.add(editSubject);
        panel.add(deleteSubject);
        panel.add(editQuiz);
        panel.add(deleteQuiz);
        panel.add(editQuestion);
        panel.add(deleteQuestion);
        panel.add(saveBtn);

        // SUBJECT HANDLERS
        deleteSubject.addActionListener(e -> {
            Subject selected = subjectList.getSelectedValue();
            if (selected != null) {
                bank.getSubjects().remove(selected);

                if(SubjectDAO.deleteSubject(selected.getName())){
                    JOptionPane.showMessageDialog(null,"đã xóa Subject thành công","hệ thống",JOptionPane.INFORMATION_MESSAGE);
                }

                loadSubjects();
                quizModel.clear();
                questionModel.clear();
            }
        });




        editSubject.addActionListener(e -> {
            Subject selected = subjectList.getSelectedValue();
            if (selected != null) {
                // Tạo các ô nhập trước
                JTextField nameField = new JTextField(selected.getName());
                JTextField teacherField = new JTextField(selected.getTeacher());
                JTextField dateField = new JTextField(selected.getNgayThi().toString()); // LocalDateTime -> String
                JTextField thoiGianChoVaoField = new JTextField(String.valueOf(selected.getTime()));


                JPanel subjectpanel = new JPanel();
                subjectpanel.setLayout(new BoxLayout(subjectpanel, BoxLayout.Y_AXIS));
                subjectpanel.add(new JLabel("Tên môn học:"));
                subjectpanel.add(nameField);
                subjectpanel.add(new JLabel("Giáo viên phụ trách:"));
                subjectpanel.add(teacherField);
                subjectpanel.add(new JLabel("Ngày thi (yyyy-MM-ddTHH:mm):")); // định dạng ISO
                subjectpanel.add(dateField);
                subjectpanel.add(new JLabel("thời gian cho vào"));
                subjectpanel.add(thoiGianChoVaoField);

                int result = JOptionPane.showConfirmDialog(
                        null, subjectpanel, "Chỉnh sửa môn học", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // Gán lại giá trị nếu người dùng OK
                    selected.setName(nameField.getText());
                    selected.setTeacher(teacherField.getText());
                    int time = Integer.parseInt(thoiGianChoVaoField.getText());
                    selected.setTime(time);

                    try {
                        LocalDateTime newDate = LocalDateTime.parse(dateField.getText());
                        selected.setNgayThi(newDate);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Ngày thi không hợp lệ. Định dạng đúng là yyyy-MM-ddTHH:mm", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }

                    //lưu vào data base
                    Subject subjectTemp = SubjectBUS.validateSubject(selected.getId(),selected.getName(),selected.getTeacher(),selected.getQuizzes(),
                            selected.getNgayThiStr(),selected.getTimeStr());
                    if (subjectTemp != null){
                        SubjectDAO.updateSubject(subjectTemp);
                    }
                    subjectList.repaint();
                }
            }
        });




        // QUIZ HANDLERS
        deleteQuiz.addActionListener(e -> {
            Subject subject = subjectList.getSelectedValue();
            Quiz quiz = quizList.getSelectedValue();
            if (subject != null && quiz != null) {
                subject.getQuizzes().remove(quiz);

                if(QuizDAO.deleteQuiz(quiz.getId())){
                    JOptionPane.showMessageDialog(null,"đã xóa quiz","thông báo từ hệ thống",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                loadQuizzes(subject);
                questionModel.clear();
            }
        });

        editQuiz.addActionListener(e -> {
            Quiz quiz = quizList.getSelectedValue();
            if (quiz != null) {
                JPanel quizPanel = new JPanel(new GridLayout(0, 1, 5, 5));

                JTextField nameField = new JTextField(quiz.getName());
                JTextField creatorField = new JTextField(quiz.getCreateBy());
                JTextField subjectField = new JTextField(quiz.getSubjectName());
                JTextField dateField = new JTextField(quiz.getNgayTaoStr());
                JTextField timeField = new JTextField(String.valueOf(quiz.getthoiGianLamBai()));

                quizPanel.add(new JLabel("Tên Quiz:"));
                quizPanel.add(nameField);
                quizPanel.add(new JLabel("người tạo:"));
                quizPanel.add(creatorField);
                quizPanel.add(new JLabel("Tên môn học:"));
                quizPanel.add(subjectField);
                quizPanel.add(new JLabel("Ngày tạo (yyyy-MM-dd HH:mm:ss):"));
                quizPanel.add(dateField);
                quizPanel.add(new JLabel("Thời gian làm bài (phút):"));
                quizPanel.add(timeField);



                int result = JOptionPane.showConfirmDialog(null, quizPanel, "Chỉnh sửa Quiz", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {


                    Quiz quizTemp = QuizValidator.validateQuiz(quiz.getId(),nameField.getText(),creatorField.getText(),subjectField.getText(),
                            dateField.getText(),null,timeField.getText());
                    if(quizTemp == null){
                        return;
                    }else {
                        QuizDAO.updateQuizt(quizTemp);
                    }
                    try {
                        quiz.setName(nameField.getText());
                        quiz.setCreateBy(creatorField.getText());
                        quiz.setSubjectName(subjectField.getText());
                        quiz.setCreatedDate(quizTemp.getCreatedDate());
                        quiz.setThoiGianLamBai(Integer.parseInt(timeField.getText()));

                        quizList.repaint();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật thông tin Quiz:\n" + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        // QUESTION HANDLERS
        deleteQuestion.addActionListener(e -> {
            Quiz quiz = quizList.getSelectedValue();
            Question question = questionList.getSelectedValue();
            if (quiz != null && question != null) {
                QuestionDAO.deleteQuestion(quiz.getId());
                quiz.getQuestions().remove(question);
                loadQuestions(quiz);
            }
        });



        editQuestion.addActionListener(e -> {
            Question question = questionList.getSelectedValue();
            if (question != null) {
                JPanel questionPanel = new JPanel(new GridLayout(0, 1, 5, 5));

                JTextField textField = new JTextField(question.getText());
                JTextField aField = new JTextField(question.getAnswerA());
                JTextField bField = new JTextField(question.getAnswerB());
                JTextField cField = new JTextField(question.getAnswerC());
                JTextField dField = new JTextField(question.getAnswerD());
                JTextField correctField = new JTextField(question.getCorrectAnswer());

                questionPanel.add(new JLabel("Câu hỏi:"));
                questionPanel.add(textField);
                questionPanel.add(new JLabel("Đáp án A:"));
                questionPanel.add(aField);
                questionPanel.add(new JLabel("Đáp án B:"));
                questionPanel.add(bField);
                questionPanel.add(new JLabel("Đáp án C:"));
                questionPanel.add(cField);
                questionPanel.add(new JLabel("Đáp án D:"));
                questionPanel.add(dField);
                questionPanel.add(new JLabel("Đáp án đúng (A/B/C/D):"));
                questionPanel.add(correctField);

                int result = JOptionPane.showConfirmDialog(null, questionPanel, "Chỉnh sửa Câu hỏi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {

                    Question questionsTemp =QuestionValidator.validateQuestion(question.getId(), question.getQuizId(),question.getText(),
                            question.getAnswerA(), question.getAnswerB(), question.getAnswerC(), question.getAnswerD(), question.getCorrectAnswer());

                    if (questionsTemp ==null){
                        return;
                    }else
                    {
                        QuestionDAO.updateQuestion(questionsTemp);
                    }
                    question.setText(textField.getText());
                    question.setAnswerA(aField.getText());
                    question.setAnswerB(bField.getText());
                    question.setAnswerC(cField.getText());
                    question.setAnswerD(dField.getText());
                    question.setCorrectAnswer(correctField.getText().toUpperCase()); // Viết hoa đáp án đúng

                    questionList.repaint();
                }
            }
        });


        return panel;
    }
}
