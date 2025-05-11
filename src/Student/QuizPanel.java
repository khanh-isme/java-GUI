package Student;

import javax.swing.*;
import project.*;
import javax.swing.border.EmptyBorder;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

class QuizPanel extends JPanel {
    private Quiz quiz;
    private Student student;
    private HashMap<Integer, ButtonGroup> answerGroups = new HashMap<>();
    private JLabel timerLabel;
    private Timer timer;
    private int elapsedTime ;
    private AtomicBoolean flag;
    private String subjectName;

    public QuizPanel(Quiz quiz, Student student , AtomicBoolean  flag, String subjectName) {
        if (quiz == null || quiz.getQuestions() == null) {
            throw new IllegalArgumentException("Quiz is null or has no questions.");
        }

        this.quiz = quiz;
        this.student = student;
        this.flag = flag;
        this.elapsedTime=quiz.getthoiGianLamBai();
        this.subjectName=subjectName;
        List<Question> questions = quiz.getQuestions();

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));

        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timerPanel.setBackground(new Color(245, 245, 245));
        timerLabel = new JLabel("Time: 00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(new Color(41, 128, 185));
        timerPanel.add(timerLabel);
        add(timerPanel, BorderLayout.NORTH);

        startTimer();

        JPanel questionContainer = new JPanel();
        questionContainer.setLayout(new GridLayout(questions.size(), 1, 0, 15));
        questionContainer.setBackground(new Color(245, 245, 245));

        int cnt = 0;
        for (Question question : questions) {
            JPanel questionPanel = new JPanel(new BorderLayout(10, 10));
            questionPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    new EmptyBorder(10, 15, 10, 15)));
            questionPanel.setBackground(Color.WHITE);

            JLabel questionLabel = new JLabel((cnt + 1) + ". " + " "+ question.getText());
            questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
            questionLabel.setForeground(new Color(44, 62, 80));
            questionPanel.add(questionLabel, BorderLayout.NORTH);

            JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 0, 8));
            optionsPanel.setOpaque(false);
            ButtonGroup group = new ButtonGroup();
            List<String> options = question.getOptions();

            for (int j = 0; j < 4; j++) {
                JRadioButton optionButton = new JRadioButton(options.get(j));
                optionButton.setFont(new Font("Arial", Font.PLAIN, 18));
                optionButton.setForeground(new Color(44, 62, 80));
                optionButton.setFocusPainted(false);
                optionButton.setOpaque(false);
                optionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                group.add(optionButton);
                optionsPanel.add(optionButton);
            }
            answerGroups.put(cnt, group);
            questionPanel.add(optionsPanel, BorderLayout.CENTER);
            questionContainer.add(questionPanel);
            cnt++;
        }

        JScrollPane scrollPane = new JScrollPane(questionContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(41, 128, 185));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(52, 152, 219));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(41, 128, 185));
            }
        });
        submitButton.addActionListener(e ->{
            calculateScore();
            flag.set(false);
            this.setVisible(false);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            elapsedTime--;
            if (elapsedTime >= 0) {
                int minutes = elapsedTime / 60;
                int seconds = elapsedTime % 60;
                timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
            } else {
                timer.stop();
                timerLabel.setText("Time's up!");
                calculateScore();
                flag.set(false);
                this.setVisible(false);
            }
        });
        timer.start();
    }

    private void calculateScore() {
        int score = 0;
        int totalQuestions = quiz.getQuestions().size();

        System.out.println("User's Answers:");

        int cnt = 0;
        for (Question question : quiz.getQuestions()) {
            ButtonGroup group = answerGroups.get(cnt);
            if (group != null) {
                Enumeration<AbstractButton> buttons = group.getElements();
                while (buttons.hasMoreElements()) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        System.out.println((cnt + 1) + ". " + button.getText());
                        if (button.getText().equals(question.getCorrectAnswerText())) {
                            score++;
                        }
                        break;
                    }
                }
            }
            cnt++;
        }


        timer.stop();
        double percentage = ((double) score / totalQuestions) * 100;

        JOptionPane.showMessageDialog(this, "Your Score: " + score + "/" + totalQuestions +
                        " (" + String.format("%.2f", percentage) + "%)",
                "Quiz Result", JOptionPane.INFORMATION_MESSAGE);

        Result ans = new Result(student.getMssv(),subjectName, quiz.getId(),score, LocalDateTime.now());
        student.addResult(ans);

    }
}
