// Matis Krisztian 523/1 mkim2052

package UX;

public class Score {
    private final String username;
    private final int score;

    public Score(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
