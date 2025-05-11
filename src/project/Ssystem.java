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
    public User loginUser(int userid, String password) {

        for (User user : users) {
            if( user instanceof Student student){
                if (student.getMssv()== userid) {
                    if (user.getPassword().equals(password)) {
                        System.out.println("Đăng nhập thành công.");
                        return user;
                    } else {
                        System.out.println("Sai mật khẩu.");
                        return null;
                    }
                }
            }
            if( user instanceof Teacher teacher){
                if(teacher.getTeacherid()== userid){
                    if (user.getPassword().equals(password)) {
                        System.out.println("Đăng nhập thành công.");
                        return user;
                    } else {
                        System.out.println("Sai mật khẩu.");
                        return null;
                    }
                }
            }

        }
        System.out.println("Không tìm thấy người dùng.");
        return null;
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






}
