import java.util.List;

public class Question {
    private String content;
    private List<String> options;
    private String correctAnswer;
    private String difficulty;

    public Question(String content, List<String> options, String correctAnswer, String difficulty) {
        this.content = content;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    public boolean isCorrect(String answer) {
        return this.correctAnswer.equals(answer);
    }

    // Getters and Setters

    public String getContent() {
        return this.content;
    }
    public List<String> getOptions(){
        return this.options;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

}
