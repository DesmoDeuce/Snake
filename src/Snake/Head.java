package Snake;

import Libraries.General.Directions;
import Libraries.General.Position;
import Libraries.General.Size;
import Libraries.Sprites.RectSprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Head extends RectSprite {

    private String dir;
    private ArrayList<RectSprite> body;
    private String next;


    public Head(Position pos, Size size, Color color, String dir) {
        super(pos, size, false, color);
        this.dir = dir;
        body = new ArrayList<>();
        next = dir;
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

        if (getPos().getX() == Screen.apple.getPos().getX() && getPos().getY() == Screen.apple.getPos().getY()) {
            grow();
            Screen.score++;
            Snake.snake.setTitle("Snake | Score: " + Screen.score);

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
            Screen.apple.setPos(open.get(new Random().nextInt(open.size())));
        }
    }

    public void die() {
        Screen.running = false;
        Screen.dead = true;
        /*
        Screen.score = 0;
        setPos(new Position(Screen.grid.getPoss().get(112).getX(), Screen.grid.getPoss().get(112).getY()));
        setDir(Directions.EAST);
        body.clear();
        */
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

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setBody(ArrayList<RectSprite> body) {
        this.body = body;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
