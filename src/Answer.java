
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void displayQuestionDetails() {
        System.out.println("Question: " + questionText);
        System.out.println("Choices: " + choices);
        System.out.println("Correct Answer: " + correctAnswer);
        System.out.println("Student's Answer: " + studentAnswer);
        System.out.println("---------------------------------------------------");
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