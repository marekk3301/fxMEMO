package game;

public class Player {
    private int points;

    public Player(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void givePoints() {
        points += 1;
    }
}
