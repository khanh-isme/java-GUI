import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student extends User implements Reviewable {


    private String mssv;
    private List<Result> quizHistory;// Lưu trữ lịch sử bài thi
    private List<Subject> registeredSubjects;

    public Student(String username, String password, String role, String mssv,List<Result> quizHistory,List<Subject> registeredSubjects) {
        super(username, password, role);
        this.mssv=mssv;
        this.quizHistory= quizHistory!= null ? quizHistory  : new ArrayList<>();
        this.registeredSubjects= registeredSubjects!= null ? registeredSubjects  : new ArrayList<>();
    }


    // Thêm kết quả mới vào lịch sử làm bài
    public void addResult(Result result) {
        quizHistory.add(result);
    }



    // Hiển thị lịch sử làm bài chi tiết, bao gồm các câu hỏi đã chọn
    public void viewDetailedQuizHistory() {
        System.out.println("Detailed Quiz History for: " + username);

        if (quizHistory.isEmpty()) {
            System.out.println("No quiz history available.");
            return;
        }

        for (Result result : quizHistory) {
            result.displayDetailedResult();
        }
    }


    @Override
    public void reviewInfo() {
        System.out.println("MSSV: " + mssv);
        System.out.println("Quiz History:");

        if (quizHistory.isEmpty()) {
            System.out.println("No quiz history available.");
        } else {
            for (Result result : quizHistory) {
                System.out.println("môn: " + result.getSubjectName());
                System.out.println("Quiz: " + result.getQuizName());
                System.out.println("Score: " + result.getScore());
                System.out.println("Date Taken: " + result.getDateTaken());

                System.out.println("-----------------------------");
            }
        }
        if(registeredSubjects.isEmpty()) {
            System.out.println("No registered Subjects available.");
        } else {
            System.out.println("registered Subjects:");
            for(Subject subject:registeredSubjects) {
                System.out.print(subject.getName()+","+ " ");
            }
        }
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
    public List<Subject> getRegisteredSubjects() {
        return this.registeredSubjects;
    }

    public void setRegisteredSubjects(List<Subject> registeredSubjects) {
        this.registeredSubjects=registeredSubjects;
    }
    public List<Result> getQuizHistory() {
        return this.quizHistory;
    }


    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }


    public void setQuizHistory(List<Result> quizHistory) {
        this.quizHistory = quizHistory;
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
