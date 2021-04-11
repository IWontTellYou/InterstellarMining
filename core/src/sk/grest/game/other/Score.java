package sk.grest.game.other;

public class Score implements Comparable<Score> {

    private String name;
    private float progress;

    public Score(String name, float progress) {
        this.name = name;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public float getProgress() {
        return progress;
    }

    @Override
    public int compareTo(Score s) {
        if(progress == s.getProgress())
            return 0;
        else if(progress > s.getProgress())
            return -1;
        else
            return 1;
    }
}
