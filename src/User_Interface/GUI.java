package User_Interface;

import java.awt.CardLayout;
import javax.swing.JFrame;

public class GUI extends JFrame {
    public static final int WIDTH = 905;
    public static final int HEIGHT = 615;
    private MyContainer myContainer;

    public GUI() {
        super("Bomberman");
        setSize(WIDTH, HEIGHT);
        setLayout(new CardLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        myContainer = new MyContainer(this);
        add(myContainer);
    }

}
