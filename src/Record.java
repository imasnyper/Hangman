public class Record {
    public int wins;
    public int losses;

    public Record() {
        this.wins = 0;
        this.losses = 0;
    }

    public Record(int wins, int losses) {
        this.wins = wins;
        this.losses = losses;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementLosses() {
        this.losses++;
    }
}
