import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;



public class Admin extends User {
    private List<User> users;

    public Admin(String username, String password) {
        super(username, password, "Admin");

    }
    public void addlistUser(List<User> users) {
        this.users=users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void showUser(List<User>users) {
        for(User user : users) {
            System.out.println("name "+ user.getUsername());
            System.out.println("pass: " +user.getPassword());
        }
    }

    public void editforStudent(Student student,Ssystem system) {
        Editor editor = new Editor();
        Scanner sc = new Scanner(System.in);
        System.out.println("1 dete Quizes taken");
        System.out.println("2 edit MSSV");
        System.out.println("3 delete Result");
        System.out.println("4 delete Registered Subjects");
        System.out.println("5 update Student");
        System.out.println("6 exit");
        System.out.print("chọn chức năng: ");

        int option = Integer.parseInt(sc.nextLine());
        switch(option) {
            case 1:
                editor.deleteQuiz(student);
                break;
            case 2:
                editor.editMSSV(student);
                break;
            case 3:
                editor.deleteResult(student);
                break;
            case 4:
                editor.deleteRegisteredSubjects(student);
                break;
            case 5:
                editor.updateStudent(student,system);
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    public void editforTeacher(Teacher teacher,Ssystem system) {
        Editor editor = new Editor();
        Scanner sc = new Scanner(System.in);
        System.out.println("1 delete Quizes created");
        System.out.println("2 update Teacher");
        System.out.println("3 exit");
        System.out.print("chọn chức năng: ");

        int option = Integer.parseInt(sc.nextLine());
        switch(option) {
            case 1:
                editor.deleteQuiz(teacher);
                break;
            case 2:
                editor.updateTeacher(teacher,system);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    // Getters and Setters
}
