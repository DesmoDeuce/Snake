package Snake;

import Libraries.General.Directions;
import Libraries.General.Position;
import Libraries.General.Size;
import Libraries.Sprites.RectSprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Head extends RectSprite {

    public static ArrayList<Head> heads = new ArrayList<>();

    private String dir;
    private ArrayList<RectSprite> body;
    private String next;
    private RectSprite apple;
    private int score;
    private boolean alive;


    public Head(Position pos, Size size, Color color, String dir) {
        super(pos, size, false, color);
        this.dir = dir;
        body = new ArrayList<>();
        next = dir;
        apple = new RectSprite(Screen.grid.getPoss().get(new Random().nextInt(Screen.grid.getPoss().size())), Screen.grid.getRectSize(), false, Color.RED);
        score = 0;
        alive = true;
        heads.add(this);
    }

    @Override
    public void draw(Graphics2D graphics) {
        apple.draw(graphics);
        super.draw(graphics);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_W && !dir.equals(Directions.SOUTH)) {
            next = Directions.NORTH;
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_S && !dir.equals(Directions.NORTH)) {
            next = Directions.SOUTH;
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_D && !dir.equals(Directions.WEST)) {
            next = Directions.EAST;
        }
        if (e.getExtendedKeyCode() == KeyEvent.VK_A && !dir.equals(Directions.EAST)) {
            next = Directions.WEST;
        }
    }

    public void move() {
        dir = next;
        for (int n = body.size() - 1; n >= 0; n--) {
            Position position;
            if (n == 0) {
                position = getPos();
            } else {
                position = body.get(n - 1).getPos();
            }
            body.get(n).setPos(new Position(position.getX(), position.getY()));
        }
        if (dir.equals(Directions.NORTH) || dir.equals("\"NORTH\"")) {
            getPos().setY(getPos().getY() - Screen.grid.getRectSize().getHeight() - Screen.grid.getGapSize());
        }
        if (dir.equals(Directions.SOUTH) || dir.equals("\"SOUTH\"")) {
            getPos().setY(getPos().getY() + Screen.grid.getRectSize().getHeight() + Screen.grid.getGapSize());
        }
        if (dir.equals(Directions.EAST) || dir.equals("\"EAST\"")) {
            getPos().setX(getPos().getX() + Screen.grid.getRectSize().getWidth() + Screen.grid.getGapSize());
        }
        if (dir.equals(Directions.WEST) || dir.equals("\"WEST\"")) {
            getPos().setX(getPos().getX() - Screen.grid.getRectSize().getWidth() - Screen.grid.getGapSize());
        }

        for (RectSprite b : body) {
            if (b.getPos().getX() == getPos().getX() && b.getPos().getY() == getPos().getY()) {
                die();
                break;
            }
        }

        if (getPos().getX() < 0
                || getPos().getX() > 750
                || getPos().getY() < 0
                || getPos().getY() > 750) {
            die();
        }

        if (getPos().getX() == apple.getPos().getX() && getPos().getY() == apple.getPos().getY()) {
            grow();
            Screen.score++;
            //Snake.snake.setTitle("Snake | Score: " + Screen.score);
            score++;

            ArrayList<Position> open = Screen.grid.getPoss();
            ArrayList<Position> remove = new ArrayList<>();
            open.remove(getPos());
            for (RectSprite b : body) {
                for (Position position : open) {
                    if (b.getPos().intsEqual(position)) {
                        remove.add(position);
                    }
                }
            }
            for (Position position : remove) {
                open.remove(position);
            }
            apple.setPos(open.get(new Random().nextInt(open.size())));
        }
    }

    public void die() {
        alive = false;
    }

    public void revive() {
        Screen.score = 0;
        score = 0;
        setPos(new Position(Screen.grid.getXs().get(7), Screen.grid.getYs().get(7)));
        setDir(Directions.EAST);
        body.clear();
        alive = true;
        apple.setPos(Screen.grid.getPoss().get(new Random().nextInt(Screen.grid.getPoss().size())));
    }

    public void grow() {
        if (body.isEmpty()) {
            body.add(new RectSprite(getPos(), Screen.grid.getRectSize(), false, Color.GREEN));
        } else {
            body.add(new RectSprite(body.get(body.size() - 1).getPos(), Screen.grid.getRectSize(), false, Color.GREEN));
        }
    }

    public String getDir() {
        return dir;
    }

    public ArrayList<RectSprite> getBody() {
        return body;
    }

    public String getNext() {
        return next;
    }

    public RectSprite getApple() {
        return apple;
    }

    public int getScore() {
        return score;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setBody(ArrayList<RectSprite> body) {
        this.body = body;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setApple(RectSprite apple) {
        this.apple = apple;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
