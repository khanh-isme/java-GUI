package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Subject {
    private String name;
    //private project.Subject monhoctruoc; // test cho vui
    private List<Quiz> quizzes;
    private String ngaythi;
    private int time;// thời gian mở đề

    public Subject(String name, List<Quiz> quizzes,String ngaythi, int time) {
        this.name = name;
        this.quizzes = quizzes != null ? quizzes : new ArrayList<>();
        this.ngaythi= ngaythi;
        this.time=time;
    }

    // Getter cho name
    public String getName() {
        return this.name;
    }

    // Setter cho name
    public void setName(String name) {
        this.name = name;
    }
    public  String getNgayThi() {
        return this.ngaythi;
    }
    public  void setNgayThi(String ngaythi) {
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


    // Phương thức lấy bài thi theo tiêu đề
    public Quiz getQuizByTitle(String title) {
        for (Quiz quiz : quizzes) {
            if (quiz.getTitle().equalsIgnoreCase(title)) {
                return quiz;
            }
        }
        return null; // Trả về null nếu không tìm thấy bài thi
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
        return "Subject{name='" + name + "', quizzes=" + quizzes.toString() + "}";
    }

    public void printInfo() {
        for (Quiz quiz : quizzes) {
            System.out.println("\n  Quiz Title: " + quiz.getTitle());
            System.out.println("  Time Limit: " + quiz.getTimeLimit() + " minutes");
            quiz.printInfo();
        }
    }
}
