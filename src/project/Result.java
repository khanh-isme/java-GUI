package project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Result {
    private int mssv;
    private String subjectName;
    private int quizId;
    private double score;
    private LocalDateTime dateTaken;

    public Result( int mssv,String subjectName, int quizId, double score, LocalDateTime dateTaken) {
        this.subjectName = subjectName;
        this.quizId = quizId;
        this.score = score;
        this.dateTaken = dateTaken;
        this.mssv= mssv;
    }

    public int getMssv(){
        return this.mssv;
    }
    // Getters
    public String getSubjectName() {
        return subjectName;
    }

    public int getQuizId() {
        return quizId;
    }

    public double getScore() {
        return score;
    }

    public LocalDateTime getDateTaken() {
        return dateTaken;
    }

    public String getLocalDateTimeStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTaken.format(formatter);
    }


    // Setters
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDateTaken(LocalDateTime dateTaken) {
        this.dateTaken = dateTaken;
    }
}
