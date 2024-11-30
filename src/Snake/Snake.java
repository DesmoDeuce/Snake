package Snake;

import javax.swing.*;
import java.io.IOException;

public class Snake extends JFrame {

    public static Snake snake;

    public Snake() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(765, 765 + 23);
        setLocationRelativeTo(null);
        setTitle("Snake | Score: " + Screen.score);
        add(new Screen());
        addKeyListener(new Screen.Keyboard());
    }

    public static void main(String[] args) throws IOException {
        snake = new Snake();
        snake.setVisible(true);
    }
}
