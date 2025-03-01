

//bài thi làm bài tính thời gian
public abstract class TimedTest {
    private int timeLimit; // Time limit in minutes

    public TimedTest(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    // Abstract method to start the test with a timer
    public abstract void startWithTimer();
}
