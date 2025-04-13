package Snake;

import Libraries.General.Directions;
import Libraries.General.Grid;
import Libraries.General.Position;
import Libraries.General.Size;
import Libraries.Sprites.RectSprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Screen extends JPanel implements ActionListener {

    public static Grid grid;
    public static Head head;
    public static int score = 0;
    public static int speed = 400;
    public static boolean sight = false;
    public static boolean dangerCake = true;
    public static boolean running = true;
    public static Timer timer;

    public Screen() throws IOException {
        grid = new Grid(new Size(15, 15), new Size(49, 49), Color.GRAY);
        head = new Head(new Position(grid.getXs().get(7), grid.getYs().get(7)), grid.getRectSize(), new Color(0, 200, 0), Directions.EAST);
        for (int n = 0; n < 19; n++) {
            new Head(new Position(grid.getXs().get(7), grid.getYs().get(7)), grid.getRectSize(), new Color(0, 200, 0), Directions.EAST);
        }

        Training.training2nd();
        //timer = new Timer(speed, this);
        //timer.start();

        //LogCompact.logInit();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            if (dangerCake) {
                for (Head heads : Head.heads) {
                    DangerCake.cake(heads, 3, 3, 1);
                }
            }
            for (Head heads : Head.heads) {
                if (heads.isAlive()) {
                    heads.move();
                }
            }
            /*try {
                LogCompact.addState();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }*/
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        grid.draw((Graphics2D) g);

        for (Head heads : Head.heads) {
            if (heads.isAlive()) {
                for (RectSprite body : heads.getBody()) {
                    body.draw((Graphics2D) g);
                }
            }
        }

        if (sight) {
            g.setColor(Color.ORANGE);
            for (Position pos : DangerCake.availableSquares(head, head.getPos())) {
                g.fillRect(pos.getX(), pos.getY(), 49, 49);
            }
        }

        for (Head heads : Head.heads) {
            if (heads.isAlive()) {
                heads.draw((Graphics2D) g);
            }
        }
    }

    public static class Keyboard extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (running) {
                head.keyPressed(e);
            }
            if (e.getExtendedKeyCode() == KeyEvent.VK_EQUALS) {
                speed -= 10;
                if (speed < 1) {
                    speed = 1;
                }
                timer.setDelay(speed);
                System.out.println("Speed: " + speed);
            }
            if (e.getExtendedKeyCode() == KeyEvent.VK_MINUS) {
                speed += 10;
                if (speed == 11) {
                    speed = 10;
                }
                timer.setDelay(speed);
                System.out.println("Speed: " + speed);
            }
            if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE) {
                if (running) {
                    running = false;
                    timer.setDelay(20);
                } else {
                    /*try {
                        LogCompact.callState(LogCompact.totalStates);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }*/
                    running = true;
                    timer.setDelay(speed);
                }
            }
            if (e.getExtendedKeyCode() == KeyEvent.VK_E) {
                sight = !sight;
            }
            if (e.isControlDown() && e.isAltDown() && e.getExtendedKeyCode() == KeyEvent.VK_C) {
                dangerCake = !dangerCake;
            }
            /*if (!running) {
                if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT) {
                    try {
                        LogCompact.callState(LogCompact.stateShown - 1);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {
                    try {
                        LogCompact.callState(LogCompact.stateShown + 1);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }*/
        }
    }
}
