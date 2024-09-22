package Snake;

import javax.swing.*;

public class Snake extends JFrame {

    public static Snake snake;

    public Snake() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(765, 788 + 100);
        setLocationRelativeTo(null);
        setTitle("Snake | Score: " + Screen.score);
        add(new Screen());
        addKeyListener(new Screen.Keyboard());
    }

    public static void main(String[] args) {
        snake = new Snake();
        snake.setVisible(true);
    }
}
