package project;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User   {
    private List<Quiz> quizzesCreated;
    private int teacherid;

    public Teacher(String username, String password,List<Quiz> quizzesCreated, int teacherid) {
        super(username, password);
        this.quizzesCreated= quizzesCreated!= null ? quizzesCreated  : new ArrayList<>();
        this.teacherid= teacherid;
    }


    public int getTeacherid(){
        return teacherid;
    }
    public void viewResults(Quiz quiz) {
        // Code to view results of students
    }

    public void addquizzesCreated(Quiz quiz) {
        this.quizzesCreated.add(quiz);
    }

    // Getters and Setters
    public List<Quiz> getQuizzesCreated() {
        return quizzesCreated;
    }

    public void setQuizzesCreated(List<Quiz> quizzesCreated) {
        this.quizzesCreated = quizzesCreated;
    }

    public void updateUserAttributes(String username, String password, String role) {
        if (username != null && !username.isEmpty()) {
            this.username = username;
        }
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
    }

    public void setTeacherid(int id) {
        this.teacherid=id;
    }
}
