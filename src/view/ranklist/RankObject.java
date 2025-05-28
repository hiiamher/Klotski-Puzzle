package view.ranklist;

public class RankObject {
    private String username;
    private String time;
    private String step;

    public RankObject(String username, String time, String step) {
        this.username = username;
        this.time = time;
        this.step = step;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Time: " + time + ", Step: " + step;
    }
}
