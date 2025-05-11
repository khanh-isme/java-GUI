package project;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student extends User  {

    private int mssv;
    private String ngaysinh;
    private String lop;
    private List<Result> quizHistory;
    private List<Subject> registeredSubjects;

    public Student(String username, String password, int mssv ,String ngaysinh , String lop) {
        super(username, password);
        this.mssv = mssv;
        this.lop = lop;
        this.ngaysinh = ngaysinh;
        this.quizHistory = new ArrayList<>();
        this.registeredSubjects = new ArrayList<>();
    }


    // Thêm kết quả mới vào lịch sử làm bài
    public void addResult(Result result) {
        quizHistory.add(result);
    }

    public void registerSubject(Subject subject) {
        this.registeredSubjects.add(subject);
    }


    public static String getCurrentTimeAsString() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Định dạng thời gian theo kiểu mong muốn
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Chuyển đổi thời gian thành chuỗi
        return now.format(formatter);
    }


    // Getters and setters (optional)
    public int getMssv() {
        return mssv;
    }
    public String getNgaySinh() {
        return ngaysinh;
    }

    public String getLop() {
        return lop;
    }

    public List<Result> getQuizHistory() {
        return quizHistory;
    }

    public List<Subject> getRegisteredSubjects() {
        return registeredSubjects;
    }

    // Setters
    public void setMssv(int mssv) {
        this.mssv = mssv;
    }

    public void setNgaySinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public void setQuizHistory(List<Result> quizHistory) {
        this.quizHistory = quizHistory;
    }

    public void setRegisteredSubjects(List<Subject> registeredSubjects) {
        this.registeredSubjects = registeredSubjects;
    }



    public void updateUserAttributes(String username, String password) {
        if (username != null && !username.isEmpty()) {
            this.username = username;
        }
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
    }






}
