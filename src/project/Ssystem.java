package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Ssystem {
    private List<User> users;


    public Ssystem(List<User> users) {
        this.users = users;

    }

    // Phương thức đăng ký người dùng mới (học sinh hoặc giáo viên)
    public void registerUser(User user) {
        this.users.add(user);
        System.out.println("User " + user.getUsername() + " registered successfully.");
    }

    // Phương thức đăng nhập người dùng dựa trên tên đăng nhập và mật khẩu
    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {

                return user;
            }
        }

        return null; // Trả về null nếu không đăng nhập được
    }

    public boolean isUsernameExists(String username) {
        for (User user : users) { // users là danh sách chứa tất cả project.User
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true; // Tìm thấy username trùng
            }
        }
        return false; // Không tìm thấy username
    }



    // Phương thức lấy tất cả học sinh trong hệ thống (dùng để giáo viên xem kết quả)
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        return students;
    }



    public void logoutUser(User user) {
        user.logout();
    }

    // Getters and Setters
    public List<User> getUsers(){
        return this.users;
    }
    public void addUser(User user) {
        this.users.add(user);
    }



    public static void writeUsersToFile3(List<User> users, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    bw.write(formatAdmin(admin));
                    bw.newLine(); // Xuống dòng
                } else if (user instanceof Student) {
                    Student student = (Student) user;
                    bw.write(formatStudent(student));
                    bw.newLine(); // Xuống dòng

                    // Ghi các môn học đã đăng ký
                    List<Subject> registeredSubjects = student.getRegisteredSubjects();
                    bw.write("registered Subjects: ");
                    for (int i = 0; i < registeredSubjects.size(); i++) {
                        bw.write(registeredSubjects.get(i).getName());
                        if (i < registeredSubjects.size() - 1) {
                            bw.write(","); // Phân tách các môn học bằng dấu phẩy
                        }
                    }
                    bw.newLine(); // Xuống dòng

                    // Ghi lịch sử bài thi
                    for (Result result : student.getQuizHistory()) {
                        bw.write("\tSubject Name: " + result.getSubjectName());
                        bw.newLine();
                        bw.write("\tQuiz Name: " + result.getQuizName());
                        bw.newLine();
                        bw.write("\tScore: " + result.getScore());
                        bw.newLine();
                        bw.write("\tDate Taken: " + result.getDateTaken());
                        bw.newLine();
                    }
                } else if (user instanceof Teacher) {
                    Teacher teacher = (Teacher) user;
                    bw.write(formatTeacher(teacher));
                    bw.newLine(); // Xuống dòng

                    // Ghi các bài kiểm tra đã tạo
                    List<Quiz> quizzesCreated = teacher.getQuizzesCreated();
                    for (int i = 0; i < quizzesCreated.size(); i++) {
                        if (i == 0) bw.write("\tQuizzes Created: ");
                        bw.write(quizzesCreated.get(i).getTitle());
                        if (i < quizzesCreated.size() - 1) {
                            bw.write(","); // Phân tách các bài kiểm tra bằng dấu phẩy
                        }
                    }
                    bw.newLine(); // Xuống dòng
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Định dạng chuỗi cho project.Admin
    private static String formatAdmin(Admin admin) {
        return "Admin," + admin.getUsername() + "," + admin.getPassword();
    }

    // Định dạng chuỗi cho project.Student
    private static String formatStudent(Student student) {
        return "Student," + student.getUsername() + "," + student.getPassword() + "," +
                student.getRole() + "," + student.getMssv();
    }

    // Định dạng chuỗi cho project.Teacher
    private static String formatTeacher(Teacher teacher) {
        return "Teacher," + teacher.getUsername() + "," + teacher.getPassword() + "," +
                teacher.getRole();
    }


}
