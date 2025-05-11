import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;

class QuizPanel extends JPanel {
    private String[] questions = {
            "What is the capital of France?",
            "Which planet is known as the Red Planet?",
            "What is 2 + 2?",
            "Who wrote 'To Kill a Mockingbird'?",
            "What is the boiling point of water?",
            "Which is the largest ocean on Earth?",
            "What is the square root of 64?",
            "Who discovered gravity?",
            "What is the speed of light?",
            "Which country has the largest population?"
    };
    private String[][] options = {
            {"Berlin", "Madrid", "Paris", "Rome"},
            {"Earth", "Mars", "Jupiter", "Saturn"},
            {"3", "4", "5", "6"},
            {"Harper Lee", "J.K. Rowling", "Mark Twain", "Ernest Hemingway"},
            {"90°C", "100°C", "110°C", "120°C"},
            {"Atlantic", "Indian", "Arctic", "Pacific"},
            {"6", "7", "8", "9"},
            {"Newton", "Einstein", "Galileo", "Hawking"},
            {"300,000 km/s", "150,000 km/s", "450,000 km/s", "600,000 km/s"},
            {"USA", "India", "China", "Brazil"}
    };

    private HashMap<Integer, ButtonGroup> answerGroups = new HashMap<>();

    public QuizPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel questionContainer = new JPanel();
        questionContainer.setLayout(new GridLayout(questions.length, 1, 20, 20));

        for (int i = 0; i < questions.length; i++) {
            JPanel questionPanel = new JPanel();
            questionPanel.setLayout(new BorderLayout());
            JLabel questionLabel = new JLabel((i + 1) + ". " + questions[i]);
            questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
            questionPanel.add(questionLabel, BorderLayout.NORTH);

            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new GridLayout(4, 1));
            ButtonGroup group = new ButtonGroup();
            for (int j = 0; j < 4; j++) {
                JRadioButton optionButton = new JRadioButton(options[i][j]);
                optionButton.setFont(new Font("Arial", Font.PLAIN, 18));
                group.add(optionButton);
                optionsPanel.add(optionButton);
            }
            answerGroups.put(i, group);
            questionPanel.add(optionsPanel, BorderLayout.CENTER);
            questionContainer.add(questionPanel);
        }

        JScrollPane scrollPane = new JScrollPane(questionContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);// làm thanh cuộn cho panel
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);// dùng để chỉnh tốc đọ cuộn chuột
        add(scrollPane, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(41, 128, 185));

        submitButton.setBorder(BorderFactory.createEmptyBorder());
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelectedAnswers();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void getSelectedAnswers() {
        System.out.println("User's Answers:");
        for (int i = 0; i < questions.length; i++) {
            ButtonGroup group = answerGroups.get(i);
            if (group != null) {
                Enumeration<AbstractButton> buttons = group.getElements();
                while (buttons.hasMoreElements()) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        System.out.println((i + 1) + ". " + button.getText());
                    }
                }
            }
        }
    }
}
