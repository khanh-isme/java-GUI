package project;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User implements Reviewable {
    private List<Quiz> quizzesCreated;

    public Teacher(String username, String password, String role,List<Quiz> quizzesCreated) {
        super(username, password, role);
        this.quizzesCreated= quizzesCreated!= null ? quizzesCreated  : new ArrayList<>();
    }

    public Quiz createQuiz(String title, int timeLimit) {
        Quiz newQuiz = new Quiz(title, timeLimit);
        quizzesCreated.add(newQuiz);
        return newQuiz;
    }


    public void viewResults(Quiz quiz) {
        // Code to view results of students
    }

    public void addquizzesCreated(Quiz quiz) {
        this.quizzesCreated.add(quiz);
    }
    @Override
    public void reviewInfo() {
        System.out.println("teacher name:" + username);

        System.out.println("Quizzes Created:");

        if (quizzesCreated.isEmpty()) {
            System.out.println("No quizzes created.");
        } else {
            for (Quiz quiz : quizzesCreated) {
                System.out.println("Quiz Title: " + quiz.getTitle());

                System.out.println("-----------------------------");
            }
        }

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
        if (role != null && !role.isEmpty()) {
            this.role = role;
        }
    }
}
