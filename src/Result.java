
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Result {
    private String subjectName;
    private String quizName;
    private	float score;
    private int answeredQuestions;
    private int totalQuestions;
    private String dateTaken;
    private List<Answer> answers;

    // Constructor
    public Result( String subjectName,String quizName, float score,int answeredQuestions ,int totalQuestions, String dateTaken, List<Answer> answers) {
        this.subjectName=  subjectName;
        this.quizName = quizName;
        this.score = score;
        this.answeredQuestions= answeredQuestions;
        this.totalQuestions = totalQuestions;
        this.dateTaken = dateTaken;
        this.answers = answers;
    }

    // Hiển thị chi tiết kết quả, bao gồm danh sách câu hỏi
    public void displayDetailedResult() {
        System.out.println("Quiz Name: " + quizName);
        System.out.println("Score: " + score );
        System.out.println("Date Taken: " + dateTaken);
        System.out.println("Questions and Answers:");

        for (Answer answer : answers) {
            if (answer != null) {
                answer.displayQuestionDetails();
            } else {
                System.out.println("No answer provided for this question.");
            }

        }

        System.out.println("-------------------------");
    }

    // Getters và setters nếu cần
    public String getQuizName() {
        return quizName;
    }

    public float getScore() {
        return score;
    }
    public int getansweredQuestion() {
        return answeredQuestions;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public String getDateTaken() {
        return dateTaken;
    }
    public String getSubjectName() {
        return this.subjectName;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Result Details:\n");
        resultBuilder.append("Subject Name: ").append(subjectName).append("\n");
        resultBuilder.append("Quiz Name: ").append(quizName).append("\n");
        resultBuilder.append("Score: ").append(score).append(" / ").append(totalQuestions).append("\n");
        resultBuilder.append("Answered Questions: ").append(answeredQuestions).append(" / ").append(totalQuestions).append("\n");
        resultBuilder.append("Date Taken: ").append(dateTaken).append("\n");

        return resultBuilder.toString();
    }

}
