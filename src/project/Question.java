package project;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int id;
    private int quizId;
    private String text;
    private String answerA, answerB, answerC, answerD;
    private String correctAnswer;

    public Question(int id,int quizId, String text, String a, String b, String c, String d, String correctAnswer) {
        this.id = id;
        this.text = text;
        this.quizId= quizId;
        this.answerA = a;
        this.answerB = b;
        this.answerC = c;
        this.answerD = d;
        this.correctAnswer = correctAnswer;
    }


    public List<String> getOptions() {
        List<String> options = new ArrayList<>();
        options.add(answerA);
        options.add(answerB);
        options.add(answerC);
        options.add(answerD);
        return options;
    }

    // Getters
    public int getId() {
        return id;
    }
    public int getQuizId(){
        return  quizId;
    }

    public String getCorrectAnswerText() {
        switch (correctAnswer.toUpperCase()) {
            case "A":
                return answerA;
            case "B":
                return answerB;
            case "C":
                return answerC;
            case "D":
                return answerD;
            default:
                return "";
        }
    }




    public String getText() {
        return text;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
