package project;

import java.util.List;


public class Answer {

    private String questionText;
    private List<String> choices;
    private String correctAnswer;
    private String studentAnswer;



    public Answer(String questionText, List<String> choices, String correctAnswer, String studentAnswer) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
    }


    // Hiển thị chi tiết câu hỏi và câu trả lời
    public String displayQuestionDetails() {
        return "Question: " + questionText + "\n" +
                "Choices: " + choices + "\n" +
                "Correct Answer: " + correctAnswer + "\n" +
                "Student's Answer: " + studentAnswer + "\n" +
                "---------------------------------------------------";
    }

    public boolean isCorrect() {
        return studentAnswer != null && studentAnswer.equalsIgnoreCase(correctAnswer);
    }
    // Getters
    public String getQuestionText() { return questionText; }
    public List<String> getChoices() { return choices; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getStudentAnswer() { return studentAnswer; }


}