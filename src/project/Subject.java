package project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class Subject {
    private int id;
    private String name;
    private String teacher;
    private List<Quiz> quizzes;
    private LocalDateTime ngaythi;
    private int time;// thời gian cho vào

    public Subject(int id,String name, String teacher,List<Quiz> quizzes,LocalDateTime ngaythi, int time) {
        this.name = name;
        this.id=id;
        this.quizzes = quizzes != null ? quizzes : new ArrayList<>();
        this.ngaythi= ngaythi;
        this.time=time;
        this.teacher=teacher;
    }

    public int getId(){
        return this.id;
    }
    public String getNgayThiStr() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return this.ngaythi.format(formatter);
    }

    public String getTimeStr(){
        return String.valueOf(this.time);
    }

    // Getter cho name
    public String getName() {
        return this.name;
    }
    public  String getTeacher(){
        return this.teacher;
    }
    // Setter cho name
    public void setName(String name) {
        this.name = name;
    }


    public LocalDateTime getNgayThi() {
        return this.ngaythi;
    }

    public  void setNgayThi(LocalDateTime ngaythi) {
        this.ngaythi=ngaythi;
    }

    public int getTime() {
        return this.time;
    }
    public void setTime(int time) {
        this.time=time;
    }

    // Getter cho quizzes
    public List<Quiz> getQuizzes() {
        return this.quizzes;
    }

    // Setter cho quizzes
    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    // Phương thức thêm project.Quiz vào danh sách
    public void addQuiz(Quiz quiz) {
        if (quiz != null) {
            this.quizzes.add(quiz);
        }
    }

    // Phương thức xoá project.Quiz khỏi danh sách
    public void removeQuiz(Quiz quiz) {
        this.quizzes.remove(quiz);
    }





    public Quiz getRandomQuiz() {
        if (quizzes == null || quizzes.isEmpty()) {
            System.out.println("No quizzes available for this subject.");
            return null; // Không có quiz để random
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(quizzes.size()); // Lấy index ngẫu nhiên
        return quizzes.get(randomIndex); // Trả về quiz ngẫu nhiên
    }


    // Override phương thức toString() để dễ dàng in thông tin của project.Subject
    @Override
    public String toString() {
        return this.name;
    }



    public void setTeacher(String teacher) {
        this.teacher=teacher;
    }
}
