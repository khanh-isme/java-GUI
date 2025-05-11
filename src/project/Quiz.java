package project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int id;
    private String name;
    private String createBy;
    private String subjectName;
    private LocalDateTime createdDate;
    private List<Question> questions;
    private int thoiGianLamBai;

    public Quiz(int id, String name, String createBy, String subjectName,LocalDateTime createdDate, List<Question> questions, int thoiGianLamBai) {
        this.id = id;
        this.name = name;
        this.createBy = createBy;
        this.createdDate = createdDate;
        this.subjectName=subjectName;
        this.questions = questions != null ? questions : new ArrayList<>();
        this.thoiGianLamBai = thoiGianLamBai;
    }

    public void addListQuestion(List<Question> questions){
        this.questions=questions;
    }
    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreateBy() {
        return createBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getthoiGianLamBai() {
        return thoiGianLamBai;
    }

    public String getNgayTaoStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.createdDate.format(formatter);
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setThoiGianLamBai(int timelambai) {
        this.thoiGianLamBai = timelambai;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public String getSubjectName() {
        return subjectName;
    }

    @Override
    public String toString() {
        return this.name;
    }


    public void setSubjectName(String subjectName) {
        this.subjectName=subjectName;
    }


}
