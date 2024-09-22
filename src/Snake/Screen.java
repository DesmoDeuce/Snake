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
import java.util.ArrayList;
import java.util.Random;

public class Screen extends JPanel implements ActionListener {

    public static Grid grid;
    public static Head head;
    public static RectSprite apple;
    public static int score = 0;
    public static int speed = 400;
    public static ArrayList<Position> kurt = new ArrayList<>();
    public static Timer timer;

    public Screen() {
        grid = new Grid(new Size(15, 15), new Size(49, 49), Color.GRAY);
        head = new Head(new Position(grid.getPoss().get(112).getX(), grid.getPoss().get(112).getY()), grid.getRectSize(), new Color(0, 200, 0), Directions.EAST);
        apple = new RectSprite(grid.getPoss().get(new Random().nextInt(grid.getPoss().size())), grid.getRectSize(), false, Color.RED);

        timer = new Timer(speed, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DangerCake.cake();
        head.move();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        grid.draw((Graphics2D) g);
        g.setColor(Color.ORANGE);
        for (Position pos : kurt) {
            g.fillRect(pos.getX(), pos.getY(), grid.getRectSize().getWidth(), grid.getRectSize().getHeight());
        }
        apple.draw((Graphics2D) g);
        head.draw((Graphics2D) g);
    }

    public static class Keyboard extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            head.keyPressed(e);
            if (e.getExtendedKeyCode() == KeyEvent.VK_MINUS) {
                speed -= 10;
                if (speed < 1) {
                    speed = 1;
                }
                timer.setDelay(speed);
                System.out.println("Speed: " + speed);
            }
            if (e.getExtendedKeyCode() == KeyEvent.VK_EQUALS) {
                speed += 10;
                if (speed == 11) {
                    speed = 10;
                }
                timer.setDelay(speed);
                System.out.println("Speed: " + speed);
            }
            if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE) {
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }
            }
        }
    }
}
