package Snake;

import Libraries.General.Directions;
import Libraries.General.Position;
import Libraries.Sprites.RectSprite;

import java.util.ArrayList;

public class DangerCake {

    //manages the DangerCake algorithm.
    public static void cake(Head head, double surrounded, double split, double edge) {
        double munch = 0.6;
        double death = 100;
        double bordering = (munch / 60) * head.getBody().size();
        if (bordering > munch * ((double) 5 / 3)) {
            bordering = munch * ((double) 5 / 3) - (munch / 60);
        }
        double nDanger = 0;
        double sDanger = 0;
        double eDanger = 0;
        double wDanger = 0;

        if (munch(head, Directions.NORTH)) {
            nDanger += munch;
        }
        if (munch(head, Directions.SOUTH)) {
            sDanger += munch;
        }
        if (munch(head, Directions.EAST)) {
            eDanger += munch;
        }
        if (munch(head, Directions.WEST)) {
            wDanger += munch;
        }

        if (edge(head, Directions.NORTH)) {
            nDanger += edge;
        }
        if (edge(head, Directions.SOUTH)) {
            sDanger += edge;
        }
        if (edge(head, Directions.EAST)) {
            eDanger += edge;
        }
        if (edge(head, Directions.WEST)) {
            wDanger += edge;
        }

        if (bordering(head, Directions.NORTH)) {
            nDanger += bordering;
        }
        if (bordering(head, Directions.SOUTH)) {
            sDanger += bordering;
        }
        if (bordering(head, Directions.EAST)) {
            eDanger += bordering;
        }
        if (bordering(head, Directions.WEST)) {
            wDanger += bordering;
        }

        if (split(head, Directions.NORTH)) {
            nDanger += split;
        }
        if (split(head, Directions.SOUTH)) {
            sDanger += split;
        }
        if (split(head, Directions.EAST)) {
            eDanger += split;
        }
        if (split(head, Directions.WEST)) {
            wDanger += split;
        }

        if (surrounded(head, Directions.NORTH)) {
            nDanger += surrounded;
        }
        if (surrounded(head, Directions.SOUTH)) {
            sDanger += surrounded;
        }
        if (surrounded(head, Directions.EAST)) {
            eDanger += surrounded;
        }
        if (surrounded(head, Directions.WEST)) {
            wDanger += surrounded;
        }

        if (death(head, Directions.NORTH)) {
            nDanger += death;
        }
        if (death(head, Directions.SOUTH)) {
            sDanger += death;
        }
        if (death(head, Directions.EAST)) {
            eDanger += death;
        }
        if (death(head, Directions.WEST)) {
            wDanger += death;
        }

        if (head.getDir().equals(Directions.NORTH)) {
            sDanger = 10000;
        }
        if (head.getDir().equals(Directions.SOUTH)) {
            nDanger = 10000;
        }
        if (head.getDir().equals(Directions.EAST)) {
            wDanger = 10000;
        }
        if (head.getDir().equals(Directions.WEST)) {
            eDanger = 10000;
        }

        //checks if everywhere is surrounded
        if (nDanger >= surrounded
                && sDanger >= surrounded
                && eDanger >= surrounded
                && wDanger >= surrounded) {
            int highestSize = 0;
            String highestDir = "NONE";
            if (nDanger < death
                    && availableSquares(head, head.getPos().add(Directions.NORTH, Screen.grid)).size() > highestSize) {
                highestSize = availableSquares(head, head.getPos().add(Directions.NORTH, Screen.grid)).size();
                highestDir = Directions.NORTH;
            }
            if (sDanger < death) {
                if (availableSquares(head, head.getPos().add(Directions.SOUTH, Screen.grid)).size() > highestSize) {
                    highestSize = availableSquares(head, head.getPos().add(Directions.SOUTH, Screen.grid)).size();
                    highestDir = Directions.SOUTH;
                } else if (availableSquares(head, head.getPos().add(Directions.SOUTH, Screen.grid)).size() == highestSize
                        && !highestDir.equals("NONE")) {
                    if (edge(head, highestDir) && !edge(head, Directions.SOUTH)) {
                        highestDir = Directions.SOUTH;
                    }
                }
            }
            if (eDanger < death) {
                if (availableSquares(head, head.getPos().add(Directions.EAST, Screen.grid)).size() > highestSize) {
                    highestSize = availableSquares(head, head.getPos().add(Directions.EAST, Screen.grid)).size();
                    highestDir = Directions.EAST;
                } else if (availableSquares(head, head.getPos().add(Directions.EAST, Screen.grid)).size() == highestSize
                        && !highestDir.equals("NONE")) {
                    if (edge(head, highestDir) && !edge(head, Directions.EAST)) {
                        highestDir = Directions.EAST;
                    }
                }
            }
            if (wDanger < death) {
                if (availableSquares(head, head.getPos().add(Directions.WEST, Screen.grid)).size() > highestSize) {
                    highestSize = availableSquares(head, head.getPos().add(Directions.WEST, Screen.grid)).size();
                    highestDir = Directions.WEST;
                } else if (availableSquares(head, head.getPos().add(Directions.WEST, Screen.grid)).size() == highestSize
                        && !highestDir.equals("NONE")) {
                    if (edge(head, highestDir) && !edge(head, Directions.WEST)) {
                        highestDir = Directions.WEST;
                    }
                }
            }
            if (highestDir.equals(Directions.NORTH)) {
                nDanger = 0;
            }
            if (highestDir.equals(Directions.SOUTH)) {
                sDanger = 0;
            }
            if (highestDir.equals(Directions.EAST)) {
                eDanger = 0;
            }
            if (highestDir.equals(Directions.WEST)) {
                wDanger = 0;
            }
        }

        /* Logs danger values of all four directions
        System.out.println("nDanger: " + nDanger);
        System.out.println("sDanger: " + sDanger);
        System.out.println("eDanger: " + eDanger);
        System.out.println("wDanger: " + wDanger);
        */

        String least = Directions.NORTH;
        if (sDanger < nDanger) {
            //South is least dangerous.
            least = Directions.SOUTH;
        }
        if (eDanger < nDanger && eDanger < sDanger) {
            //East is least dangerous.
            least = Directions.EAST;
        }
        if (wDanger < nDanger && wDanger < sDanger && wDanger < eDanger) {
            //West is least dangerous.
            least = Directions.WEST;
        }

        head.setNext(least);
    }

    //determines if a given direction is towards the apple
    public static boolean munch(Head head, String dir) {
        if (head.getApple().getPos().getY() == Screen.grid.getYs().get(0)) {
            for (RectSprite body : head.getBody()) {
                if (body.getPos().getY() == Screen.grid.getYs().get(14)) {
                    return false;
                }
            }
        }
        if (head.getApple().getPos().getY() == Screen.grid.getYs().get(14)) {
            for (RectSprite body : head.getBody()) {
                if (body.getPos().getY() == Screen.grid.getYs().get(0)) {
                    return false;
                }
            }
        }
        if (head.getApple().getPos().getX() == Screen.grid.getXs().get(14)) {
            for (RectSprite body : head.getBody()) {
                if (body.getPos().getX() == Screen.grid.getXs().get(0)) {
                    return false;
                }
            }
        }
        if (head.getApple().getPos().getX() == Screen.grid.getXs().get(0)) {
            for (RectSprite body : head.getBody()) {
                if (body.getPos().getX() == Screen.grid.getXs().get(14)) {
                    return false;
                }
            }
        }
        if (head.getPos().getY() >= head.getApple().getPos().getY()
                && dir.equals(Directions.SOUTH)) {
            return true;
        }
        if (head.getPos().getY() <= head.getApple().getPos().getY()
                && dir.equals(Directions.NORTH)) {
            return true;
        }
        if (head.getPos().getX() <= head.getApple().getPos().getX()
                && dir.equals(Directions.WEST)) {
            return true;
        }
        if (head.getPos().getX() >= head.getApple().getPos().getX()
                && dir.equals(Directions.EAST)) {
            return true;
        }
        for (RectSprite body : head.getBody()) {
            if (body.getPos().intsEqual(head.getApple().getPos())) {
                return true;
            }
        }
        return false;
    }

    //determines if the head would be on the edge in a given direction
    public static boolean edge(Head head, String dir) {
        Position pos = head.getPos().add(dir, Screen.grid);
        if (pos.getX() == Screen.grid.getXs().get(0)
                && head.getApple().getPos().getX() != Screen.grid.getXs().get(0)) {
            return true;
        }
        if (pos.getX() == Screen.grid.getXs().get(14)
                && head.getApple().getPos().getX() != Screen.grid.getXs().get(14)) {
            return true;
        }
        if (pos.getY() == Screen.grid.getYs().get(0)
                && head.getApple().getPos().getY() != Screen.grid.getYs().get(0)) {
            return true;
        }
        if (pos.getY() == Screen.grid.getYs().get(14)
                && head.getApple().getPos().getY() != Screen.grid.getYs().get(14)) {
            return true;
        }
        return false;
    }

    //checks if the head would be next to a snake body
    public static boolean bordering(Head head, String dir) {
        Position pos = head.getPos().add(dir, Screen.grid);
        Position nPos = pos.add(Directions.NORTH, Screen.grid);
        Position sPos = pos.add(Directions.SOUTH, Screen.grid);
        Position ePos = pos.add(Directions.EAST, Screen.grid);
        Position wPos = pos.add(Directions.WEST, Screen.grid);

        for (RectSprite body : head.getBody()) {
            Position bodyPos = body.getPos();
            if (head.getBody().indexOf(body) != head.getBody().size() - 1) {
                if (nPos.intsEqual(bodyPos)) {
                    return false;
                }
                if (sPos.intsEqual(bodyPos)) {
                    return false;
                }
                if (ePos.intsEqual(bodyPos)) {
                    return false;
                }
                if (wPos.intsEqual(bodyPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    //determines if the snake would split their area in half in a given direction
    public static boolean split(Head head, String dir) {
        Position pos1 = head.getPos().add(dir, Screen.grid).add(dir, Screen.grid);
        Position pos2 = pos1;
        Position pos3 = pos1;
        if (dir.equals(Directions.NORTH) || dir.equals(Directions.SOUTH)) {
            pos2.add(Directions.EAST, Screen.grid);
            pos3.add(Directions.WEST, Screen.grid);
        }
        if (dir.equals(Directions.EAST) || dir.equals(Directions.WEST)) {
            pos2.add(Directions.NORTH, Screen.grid);
            pos3.add(Directions.SOUTH, Screen.grid);
        }

        boolean opposite = false;
        for (RectSprite body : head.getBody()) {
            Position pos = body.getPos();
            if (dir.equals(Directions.NORTH)) {
                if (pos.getY() == Screen.grid.getYs().get(14)) {
                    opposite = true;
                    break;
                }
            }
            if (dir.equals(Directions.SOUTH)) {
                if (pos.getY() == Screen.grid.getYs().get(0)) {
                    opposite = true;
                    break;
                }
            }
            if (dir.equals(Directions.EAST)) {
                if (pos.getY() == Screen.grid.getXs().get(0)) {
                    opposite = true;
                    break;
                }
            }
            if (dir.equals(Directions.WEST)) {
                if (pos.getY() == Screen.grid.getXs().get(14)) {
                    opposite = true;
                    break;
                }
            }
        }

        if (opposite) {
            if (pos1.getX() < Screen.grid.getXs().get(0)
                    || pos1.getX() > Screen.grid.getXs().get(14)) {
                return true;
            }
            if (pos2.getX() < Screen.grid.getXs().get(0)
                    || pos2.getX() > Screen.grid.getXs().get(14)) {
                return true;
            }
            if (pos3.getX() < Screen.grid.getXs().get(0)
                    || pos3.getX() > Screen.grid.getXs().get(14)) {
                return true;
            }
            if (pos1.getY() < Screen.grid.getYs().get(0)
                    || pos1.getY() > Screen.grid.getYs().get(14)) {
                return true;
            }
            if (pos2.getY() < Screen.grid.getYs().get(0)
                    || pos2.getY() > Screen.grid.getYs().get(14)) {
                return true;
            }
            if (pos3.getY() < Screen.grid.getYs().get(0)
                    || pos3.getY() > Screen.grid.getYs().get(14)) {
                return true;
            }
            for (RectSprite body : head.getBody()) {
                if (pos1.intsEqual(body.getPos()) && head.getBody().size() - head.getBody().indexOf(body) > 1) {
                    return true;
                }
                if (pos2.intsEqual(body.getPos()) && head.getBody().size() - head.getBody().indexOf(body) > 1) {
                    return true;
                }
                if (pos3.intsEqual(body.getPos()) && head.getBody().size() - head.getBody().indexOf(body) > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    //determines if the snake would be surrounded in a given direction
    public static boolean surrounded(Head head, String dir) {
        if (availableSquares(head, head.getPos().add(dir, Screen.grid)).size() < (224 - head.getBody().size()) * .5) {
            return true;
        }
        return false;
    }

    //returns the available squares from a given location
    public static ArrayList<Position> availableSquares(Head head, Position start) {
        boolean running = true;
        ArrayList<Position> squares = new ArrayList<>();
        squares.add(start);
        ArrayList<Position> add = new ArrayList<>();
        while(running) {
            running = false;
            for (Position poss : squares) {
                Position nPos = poss.add(Directions.NORTH, Screen.grid);
                Position sPos = poss.add(Directions.SOUTH, Screen.grid);
                Position ePos = poss.add(Directions.EAST, Screen.grid);
                Position wPos = poss.add(Directions.WEST, Screen.grid);
                boolean nOK = true;
                boolean sOK = true;
                boolean eOK = true;
                boolean wOK = true;

                //checks if the direction is off the map
                if (nPos.getY() < Screen.grid.getYs().get(0)) {
                    nOK = false;
                }
                if (sPos.getY() > Screen.grid.getYs().get(14)) {
                    sOK = false;
                }
                if (ePos.getX() > Screen.grid.getXs().get(14)) {
                    eOK = false;
                }
                if (wPos.getX() < Screen.grid.getXs().get(0)) {
                    wOK = false;
                }

                //checks if direction is in the head
                if (nPos.intsEqual(head.getPos())) {
                    nOK = false;
                }
                if (sPos.intsEqual(head.getPos())) {
                    sOK = false;
                }
                if (ePos.intsEqual(head.getPos())) {
                    eOK = false;
                }
                if (wPos.intsEqual(head.getPos())) {
                    wOK = false;
                }

                //checks if direction is in the body
                for (RectSprite bodies : head.getBody()) {
                    Position pos = bodies.getPos();
                    int distance = ((Math.abs(pos.getX() - head.getPos().getX()) + Math.abs(pos.getY() - head.getPos().getY())) / 50);
                    if (pos.getX() != head.getPos().getX() && pos.getY() != head.getPos().getY()) {
                        distance--;
                    }
                    if (!(distance >= head.getBody().size() - head.getBody().indexOf(bodies))) {
                        if (nPos.intsEqual(pos)) {
                            nOK = false;
                        }
                        if (sPos.intsEqual(pos)) {
                            sOK = false;
                        }
                        if (ePos.intsEqual(pos)) {
                            eOK = false;
                        }
                        if (wPos.intsEqual(pos)) {
                            wOK = false;
                        }
                    }
                }

                //checks if direction is already in square list
                for (Position pos : squares) {
                    if (nPos.intsEqual(pos)) {
                        nOK = false;
                    }
                    if (sPos.intsEqual(pos)) {
                        sOK = false;
                    }
                    if (ePos.intsEqual(pos)) {
                        eOK = false;
                    }
                    if (wPos.intsEqual(pos)) {
                        wOK = false;
                    }
                }
                for (Position pos : add) {
                    if (nPos.intsEqual(pos)) {
                        nOK = false;
                    }
                    if (sPos.intsEqual(pos)) {
                        sOK = false;
                    }
                    if (ePos.intsEqual(pos)) {
                        eOK = false;
                    }
                    if (wPos.intsEqual(pos)) {
                        wOK = false;
                    }
                }

                if (nOK) {
                    add.add(nPos);
                    running = true;
                }
                if (sOK) {
                    add.add(sPos);
                    running = true;
                }
                if (eOK) {
                    add.add(ePos);
                    running = true;
                }
                if (wOK) {
                    add.add(wPos);
                    running = true;
                }
            }
            //adds the squares to the square list
            squares.addAll(add);
            //resets the add list
            add.clear();
        }
        return squares;
    }

    //determines if the snake will die in a given direction
    public static boolean death(Head head, String dir) {
        Position pos = head.getPos().add(dir, Screen.grid);

        if (pos.getX() < Screen.grid.getXs().get(0)
                || pos.getX() > Screen.grid.getXs().get(14)) {
            return true;
        }
        if (pos.getY() < Screen.grid.getYs().get(0)
                || pos.getY() > Screen.grid.getYs().get(14)) {
            return true;
        }
        for (RectSprite body : head.getBody()) {
            if (pos.intsEqual(body.getPos()) && head.getBody().size() - head.getBody().indexOf(body) > 1) {
                return true;
            }
        }
        return false;
    }
}
