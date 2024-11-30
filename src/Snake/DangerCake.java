package Snake;

import Libraries.General.Directions;
import Libraries.General.Position;
import Libraries.Sprites.RectSprite;

import java.util.ArrayList;

public class DangerCake {

    public static final double munch = 0.6;
    public static final double death = 9;
    public static final double surrounded = 3;
    public static final double split = 3;
    public static final double edge = 1;
    public static double bordering = .01 * Screen.head.getBody().size();
    public static final double change = .125;

    //manages the DangerCake algorithm.
    public static void cake() {
        bordering = (munch / 60) * Screen.head.getBody().size();
        if (bordering > munch * ((double) 5 / 3)) {
            bordering = munch * ((double) 5 / 3) - (munch / 60);
        }
        double nDanger = 0;
        double sDanger = 0;
        double eDanger = 0;
        double wDanger = 0;

        nDanger += munch(Directions.NORTH);
        sDanger += munch(Directions.SOUTH);
        eDanger += munch(Directions.EAST);
        wDanger += munch(Directions.WEST);

        nDanger += edge(Directions.NORTH);
        sDanger += edge(Directions.SOUTH);
        eDanger += edge(Directions.EAST);
        wDanger += edge(Directions.WEST);

        nDanger += bordering(Directions.NORTH);
        sDanger += bordering(Directions.SOUTH);
        eDanger += bordering(Directions.EAST);
        wDanger += bordering(Directions.WEST);

        nDanger += change(Directions.NORTH);
        sDanger += change(Directions.SOUTH);
        eDanger += change(Directions.EAST);
        wDanger += change(Directions.WEST);

        nDanger += split(Directions.NORTH);
        sDanger += split(Directions.SOUTH);
        eDanger += split(Directions.EAST);
        wDanger += split(Directions.WEST);

        nDanger += surrounded(Directions.NORTH);
        sDanger += surrounded(Directions.SOUTH);
        eDanger += surrounded(Directions.EAST);
        wDanger += surrounded(Directions.WEST);

        nDanger += death(Directions.NORTH);
        sDanger += death(Directions.SOUTH);
        eDanger += death(Directions.EAST);
        wDanger += death(Directions.WEST);

        if (Screen.head.getDir().equals(Directions.NORTH)) {
            sDanger = 100;
        }
        if (Screen.head.getDir().equals(Directions.SOUTH)) {
            nDanger = 100;
        }
        if (Screen.head.getDir().equals(Directions.EAST)) {
            wDanger = 100;
        }
        if (Screen.head.getDir().equals(Directions.WEST)) {
            eDanger = 100;
        }

        //checks if everywhere is surrounded
        if (nDanger >= surrounded
                && sDanger >= surrounded
                && eDanger >= surrounded
                && wDanger >= surrounded) {
            int highestSize = 0;
            String highestDir = "NONE";
            if (nDanger < death
                    && availableSquares(Screen.head.getPos().add(Directions.NORTH, Screen.grid)).size() > highestSize) {
                highestSize = availableSquares(Screen.head.getPos().add(Directions.NORTH, Screen.grid)).size();
                highestDir = Directions.NORTH;
            }
            if (sDanger < death) {
                if (availableSquares(Screen.head.getPos().add(Directions.SOUTH, Screen.grid)).size() > highestSize) {
                    highestSize = availableSquares(Screen.head.getPos().add(Directions.SOUTH, Screen.grid)).size();
                    highestDir = Directions.SOUTH;
                } else if (availableSquares(Screen.head.getPos().add(Directions.SOUTH, Screen.grid)).size() == highestSize
                        && !highestDir.equals("NONE")) {
                    if (edge(highestDir) > edge(Directions.SOUTH)) {
                        highestDir = Directions.SOUTH;
                    }
                }
            }
            if (eDanger < death) {
                if (availableSquares(Screen.head.getPos().add(Directions.EAST, Screen.grid)).size() > highestSize) {
                    highestSize = availableSquares(Screen.head.getPos().add(Directions.EAST, Screen.grid)).size();
                    highestDir = Directions.EAST;
                } else if (availableSquares(Screen.head.getPos().add(Directions.EAST, Screen.grid)).size() == highestSize
                        && !highestDir.equals("NONE")) {
                    if (edge(highestDir) > edge(Directions.EAST)) {
                        highestDir = Directions.EAST;
                    }
                }
            }
            if (wDanger < death) {
                if (availableSquares(Screen.head.getPos().add(Directions.WEST, Screen.grid)).size() > highestSize) {
                    highestSize = availableSquares(Screen.head.getPos().add(Directions.WEST, Screen.grid)).size();
                    highestDir = Directions.WEST;
                } else if (availableSquares(Screen.head.getPos().add(Directions.WEST, Screen.grid)).size() == highestSize
                        && !highestDir.equals("NONE")) {
                    if (edge(highestDir) > edge(Directions.WEST)) {
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

        Screen.head.setNext(least);
    }

    //determines if a given direction is towards the apple
    public static double munch(String dir) {
        if (Screen.apple.getPos().getY() == Screen.grid.getYs().get(0)) {
            for (RectSprite body : Screen.head.getBody()) {
                if (body.getPos().getY() == Screen.grid.getYs().get(14)) {
                    return 0;
                }
            }
        }
        if (Screen.apple.getPos().getY() == Screen.grid.getYs().get(14)) {
            for (RectSprite body : Screen.head.getBody()) {
                if (body.getPos().getY() == Screen.grid.getYs().get(0)) {
                    return 0;
                }
            }
        }
        if (Screen.apple.getPos().getX() == Screen.grid.getXs().get(14)) {
            for (RectSprite body : Screen.head.getBody()) {
                if (body.getPos().getX() == Screen.grid.getXs().get(0)) {
                    return 0;
                }
            }
        }
        if (Screen.apple.getPos().getX() == Screen.grid.getXs().get(0)) {
            for (RectSprite body : Screen.head.getBody()) {
                if (body.getPos().getX() == Screen.grid.getXs().get(14)) {
                    return 0;
                }
            }
        }
        if (Screen.head.getPos().getY() >= Screen.apple.getPos().getY()
                && dir.equals(Directions.SOUTH)) {
            return munch;
        }
        if (Screen.head.getPos().getY() <= Screen.apple.getPos().getY()
                && dir.equals(Directions.NORTH)) {
            return munch;
        }
        if (Screen.head.getPos().getX() <= Screen.apple.getPos().getX()
                && dir.equals(Directions.WEST)) {
            return munch;
        }
        if (Screen.head.getPos().getX() >= Screen.apple.getPos().getX()
                && dir.equals(Directions.EAST)) {
            return munch;
        }
        for (RectSprite body : Screen.head.getBody()) {
            if (body.getPos().intsEqual(Screen.apple.getPos())) {
                return munch;
            }
        }
        return 0;
    }

    //determines if the head would be on the edge in a given direction
    public static double edge(String dir) {
        Position pos = Screen.head.getPos().add(dir, Screen.grid);
        if (pos.getX() == Screen.grid.getXs().get(0)
                && Screen.apple.getPos().getX() != Screen.grid.getXs().get(0)) {
            return edge;
        }
        if (pos.getX() == Screen.grid.getXs().get(14)
                && Screen.apple.getPos().getX() != Screen.grid.getXs().get(14)) {
            return edge;
        }
        if (pos.getY() == Screen.grid.getYs().get(0)
                && Screen.apple.getPos().getY() != Screen.grid.getYs().get(0)) {
            return edge;
        }
        if (pos.getY() == Screen.grid.getYs().get(14)
                && Screen.apple.getPos().getY() != Screen.grid.getYs().get(14)) {
            return edge;
        }
        return 0;
    }

    //checks if the head would be next to a snake body
    public static double bordering(String dir) {
        Position pos = Screen.head.getPos().add(dir, Screen.grid);
        Position nPos = pos.add(Directions.NORTH, Screen.grid);
        Position sPos = pos.add(Directions.SOUTH, Screen.grid);
        Position ePos = pos.add(Directions.EAST, Screen.grid);
        Position wPos = pos.add(Directions.WEST, Screen.grid);

        for (RectSprite body : Screen.head.getBody()) {
            Position bodyPos = body.getPos();
            if (Screen.head.getBody().indexOf(body) != Screen.head.getBody().size() - 1) {
                if (nPos.intsEqual(bodyPos)) {
                    return 0;
                }
                if (sPos.intsEqual(bodyPos)) {
                    return 0;
                }
                if (ePos.intsEqual(bodyPos)) {
                    return 0;
                }
                if (wPos.intsEqual(bodyPos)) {
                    return 0;
                }
            }
        }
        return bordering;
    }

    //checks if the given direction is different then the previous direction
    public static double change(String dir) {
        if (Screen.head.getDir().equals(dir)) {
            return change;
        }
        return 0;
    }

    //determines if the snake would split their area in half in a given direction
    public static double split(String dir) {
        Position pos1 = Screen.head.getPos().add(dir, Screen.grid).add(dir, Screen.grid);
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
        for (RectSprite body : Screen.head.getBody()) {
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
                return split;
            }
            if (pos2.getX() < Screen.grid.getXs().get(0)
                    || pos2.getX() > Screen.grid.getXs().get(14)) {
                return split;
            }
            if (pos3.getX() < Screen.grid.getXs().get(0)
                    || pos3.getX() > Screen.grid.getXs().get(14)) {
                return split;
            }
            if (pos1.getY() < Screen.grid.getYs().get(0)
                    || pos1.getY() > Screen.grid.getYs().get(14)) {
                return split;
            }
            if (pos2.getY() < Screen.grid.getYs().get(0)
                    || pos2.getY() > Screen.grid.getYs().get(14)) {
                return split;
            }
            if (pos3.getY() < Screen.grid.getYs().get(0)
                    || pos3.getY() > Screen.grid.getYs().get(14)) {
                return split;
            }
            for (RectSprite body : Screen.head.getBody()) {
                if (pos1.intsEqual(body.getPos()) && Screen.head.getBody().size() - Screen.head.getBody().indexOf(body) > 1) {
                    return split;
                }
                if (pos2.intsEqual(body.getPos()) && Screen.head.getBody().size() - Screen.head.getBody().indexOf(body) > 1) {
                    return split;
                }
                if (pos3.intsEqual(body.getPos()) && Screen.head.getBody().size() - Screen.head.getBody().indexOf(body) > 1) {
                    return split;
                }
            }
        }
        return 0;
    }

    //determines if the snake would be surrounded in a given direction
    public static double surrounded(String dir) {
        if (availableSquares(Screen.head.getPos().add(dir, Screen.grid)).size() < (224 - Screen.head.getBody().size()) * .5) {
            return surrounded;
        }
        return 0;
    }

    //returns the available squares from a given location
    public static ArrayList<Position> availableSquares(Position start) {
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
                if (nPos.intsEqual(Screen.head.getPos())) {
                    nOK = false;
                }
                if (sPos.intsEqual(Screen.head.getPos())) {
                    sOK = false;
                }
                if (ePos.intsEqual(Screen.head.getPos())) {
                    eOK = false;
                }
                if (wPos.intsEqual(Screen.head.getPos())) {
                    wOK = false;
                }

                //checks if direction is in the body
                for (RectSprite bodies : Screen.head.getBody()) {
                    Position pos = bodies.getPos();
                    int distance = ((Math.abs(pos.getX() - Screen.head.getPos().getX()) + Math.abs(pos.getY() - Screen.head.getPos().getY())) / 50);
                    if (pos.getX() != Screen.head.getPos().getX() && pos.getY() != Screen.head.getPos().getY()) {
                        distance--;
                    }
                    if (!(distance >= Screen.head.getBody().size() - Screen.head.getBody().indexOf(bodies))) {
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
    public static double death(String dir) {
        Position pos = Screen.head.getPos().add(dir, Screen.grid);

        if (pos.getX() < Screen.grid.getXs().get(0)
                || pos.getX() > Screen.grid.getXs().get(14)) {
            return death;
        }
        if (pos.getY() < Screen.grid.getYs().get(0)
                || pos.getY() > Screen.grid.getYs().get(14)) {
            return death;
        }
        for (RectSprite body : Screen.head.getBody()) {
            if (pos.intsEqual(body.getPos()) && Screen.head.getBody().size() - Screen.head.getBody().indexOf(body) > 1) {
                return death;
            }
        }
        return 0;
    }
}
