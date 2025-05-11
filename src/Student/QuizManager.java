package Student;
// đang hơi vô dụng
public class QuizManager {
    private static QuizManager instance;
    private boolean isQuizActive = false;

    private QuizManager() {} // Constructor private để đảm bảo Singleton

    public static QuizManager getInstance() {
        if (instance == null) {
            instance = new QuizManager();
        }
        return instance;
    }

    public boolean isQuizActive() {
        return isQuizActive;
    }

    public void setQuizActive(boolean active) {
        this.isQuizActive = active;
    }
}
