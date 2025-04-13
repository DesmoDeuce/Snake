package Snake;

import java.util.ArrayList;

public class Trainee {

    public static ArrayList<Trainee> trainees = new ArrayList<>();

    private Head head;
    private int[] ints;
    private int moves;

    public Trainee(Head head, int[] ints) {
        this.head = head;
        this.ints = ints;
        moves = 0;
        trainees.add(this);
    }

    public Head getHead() {
        return head;
    }

    public int[] getInts() {
        return ints;
    }

    public int getMoves() {
        return moves;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public void setInts(int[] ints) {
        this.ints = ints;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
}
