package Snake;

import Libraries.General.Directions;
import Libraries.General.Position;
import Libraries.Sprites.RectSprite;

import java.util.ArrayList;

public class DangerCake {

    public static final double munch = 0.5;
    public static final double death = 5;
    public static final double surrounded = 3;

    //evaluates the danger values and decides direction
    public static void cake() {
        ArrayList<Double> ns = new ArrayList<>();
        double nDanger = 0;
        double sDanger = 0;
        double eDanger = 0;
        double wDanger = 0;

        nDanger += fromApple(Screen.head, Screen.apple, Directions.NORTH);
        sDanger += fromApple(Screen.head, Screen.apple, Directions.SOUTH);
        eDanger += fromApple(Screen.head, Screen.apple, Directions.EAST);
        wDanger += fromApple(Screen.head, Screen.apple, Directions.WEST);
        ;
        nDanger += isDeath(Screen.head, Screen.head.getPos(), Directions.NORTH);
        sDanger += isDeath(Screen.head, Screen.head.getPos(), Directions.SOUTH);
        eDanger += isDeath(Screen.head, Screen.head.getPos(), Directions.EAST);
        wDanger += isDeath(Screen.head, Screen.head.getPos(), Directions.WEST);

        nDanger += checkSurround(Screen.head, Directions.NORTH);
        sDanger += checkSurround(Screen.head, Directions.SOUTH);
        eDanger += checkSurround(Screen.head, Directions.EAST);
        wDanger += checkSurround(Screen.head, Directions.WEST);

        ns.add(nDanger);
        ns.add(sDanger);
        ns.add(eDanger);
        ns.add(wDanger);
        double lowest = nDanger;
        for (Double n : ns) {
            if (n < lowest) {
                lowest = n;
            }
        }
        System.out.println("NDanger: " + nDanger);
        System.out.println("SDanger: " + sDanger);
        System.out.println("EDanger: " + eDanger);
        System.out.println("WDanger: " + wDanger);
        System.out.println("Lowest: " + lowest);
        if (lowest == nDanger) {
            Screen.head.setNext(Directions.NORTH);
        }
        if (lowest == sDanger) {
            Screen.head.setNext(Directions.SOUTH);
        }
        if (lowest == eDanger) {
            Screen.head.setNext(Directions.EAST);
        }
        if (lowest == wDanger) {
            Screen.head.setNext(Directions.WEST);
        }
    }

    //checks if the snake moves away from the apple with that dir
    public static double fromApple(Head head, RectSprite apple, String dir) {
        if (dir.equals(Directions.NORTH)
                && head.getPos().getY() <= apple.getPos().getY()) {
            return munch;
        }
        if (dir.equals(Directions.SOUTH)
                && head.getPos().getY() >= apple.getPos().getY()) {
            return munch;
        }
        if (dir.equals(Directions.EAST)
                && head.getPos().getX() >= apple.getPos().getX()) {
            return munch;
        }
        if (dir.equals(Directions.WEST)
                && head.getPos().getX() <= apple.getPos().getX()) {
            return munch;
        }
        return 0;
    }

    //checks if the snake will die in that direction
    public static double isDeath(Head head, Position pos, String dir) {
        if (dir.equals(Directions.NORTH)) {
            if (pos.getY() == Screen.grid.getPoss().get(0).getY()) {
                return death;
            }
            for (RectSprite body : head.getBody()) {
                Position p = body.getPos();
                if (p.getX() == pos.getX()
                        && p.getY() == pos.add(Directions.NORTH, Screen.grid).getY()) {
                    return death;
                }
            }
        }
        if (dir.equals(Directions.SOUTH)) {
            if (pos.getY() == Screen.grid.getPoss().get(Screen.grid.getPoss().size() - 1).getY()) {
                return death;
            }
            for (RectSprite body : head.getBody()) {
                Position p = body.getPos();
                if (p.getX() == pos.getX()
                        && p.getY() == pos.add(Directions.SOUTH, Screen.grid).getY()) {
                    return death;
                }
            }
        }
        if (dir.equals(Directions.EAST)) {
            if (pos.getX() == Screen.grid.getPoss().get(Screen.grid.getPoss().size() - 1).getX()) {
                return death;
            }
            for (RectSprite body : head.getBody()) {
                Position p = body.getPos();
                if (p.getY() == pos.getY()
                        && p.getX() == pos.add(Directions.EAST, Screen.grid).getX()) {
                    return death;
                }
            }
        }
        if (dir.equals(Directions.WEST)) {
            if (pos.getX() == Screen.grid.getPoss().get(0).getX()) {
                return death;
            }
            for (RectSprite body : head.getBody()) {
                Position p = body.getPos();
                if (p.getY() == pos.getY()
                        && p.getX() == pos.add(Directions.WEST, Screen.grid).getX()) {
                    return death;
                }
            }
        }
        return 0;
    }

    //checks if it is surrounded by its body
    public static double checkSurround(Head head, String dir) {

        ArrayList<Position> poss = new ArrayList<>(Screen.grid.getPoss());
        ArrayList<Position> remove = new ArrayList<>();
        for (RectSprite square : head.getBody()) {
            for (Position pos : poss) {
                if (square.getPos().getX() == pos.getX()
                        && square.getPos().getY() == pos.getX()) {
                    remove.add(pos);
                }
            }
        }

        for (Position pos : remove) {
            poss.remove(pos);
        }
        if (dir.equals(Directions.NORTH)) {
            if (getPoss(head, head.getPos().add(Directions.NORTH, Screen.grid)).size() < poss.size() * .4) {
                return surrounded;
            }
        }
        if (dir.equals(Directions.SOUTH)) {
            if (getPoss(head, head.getPos().add(Directions.SOUTH, Screen.grid)).size() < poss.size() * .4) {
                return surrounded;
            }
        }
        if (dir.equals(Directions.EAST)) {
            if (getPoss(head, head.getPos().add(Directions.EAST, Screen.grid)).size() < poss.size() * .4) {
                return surrounded;
            }
        }
        if (dir.equals(Directions.WEST)) {
            if (getPoss(head, head.getPos().add(Directions.WEST, Screen.grid)).size() < poss.size() * .4) {
                return surrounded;
            }
        }

        return 0;
    }

    //gets all the available positions to the center position
    public static ArrayList<Position> getPoss(Head head, Position center) {
        ArrayList<Position> poss = new ArrayList<>();
        poss.add(center);
        for (int n = 0; n < 15; n++) {
            ArrayList<Position> add = new ArrayList<>();
            for (Position p : poss) {
                Position nPos = p.add(Directions.NORTH, Screen.grid);
                if (isDeath(head, p, Directions.NORTH) <= 0
                        && !containsPos(poss, nPos)
                        && !containsPos(add, nPos)
                        && nPos.getX() >= Screen.grid.getPoss().get(0).getY()) {
                    add.add(nPos);
                }
                Position sPos = p.add(Directions.SOUTH, Screen.grid);
                if (isDeath(head, p, Directions.SOUTH) <= 0
                        && !containsPos(poss, sPos)
                        && !containsPos(add, sPos)
                        && sPos.getX() < Screen.grid.getPoss().get(Screen.grid.getPoss().size() - 1).getY()) {
                    add.add(sPos);
                }
                Position ePos = p.add(Directions.EAST, Screen.grid);
                if (isDeath(head, p, Directions.EAST) <= 0
                        && !containsPos(poss, ePos)
                        && !containsPos(add, ePos)
                        && ePos.getY() < Screen.grid.getPoss().get(Screen.grid.getPoss().size() - 1).getX()) {
                    add.add(ePos);
                }
                Position wPos = p.add(Directions.WEST, Screen.grid);
                if (isDeath(head, p, Directions.WEST) <= 0
                        && !containsPos(poss, wPos)
                        && !containsPos(add, wPos)
                        && wPos.getY() >= Screen.grid.getPoss().get(0).getX()) {
                    add.add(wPos);
                }
            }
            poss.addAll(add);
        }
        Screen.kurt = poss;
        return poss;
    }

    public static boolean containsPos(ArrayList<Position> poss, Position pos) {
        for (Position p : poss) {
            if (p.getX() == pos.getX()
                    && p.getY() == pos.getY()) {
                return true;
            }
        }
        return false;
    }
}
