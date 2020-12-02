package User_Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HowToPlay extends JPanel{
    private MyContainer myContainer;
    private JLabel lbBackground;
    private JLabel lbOK;
    private ImageIcon backgroundIcon;

    public HowToPlay (MyContainer mContainer) {
        this.myContainer = mContainer;
        setLayout(null);
        setBackground(Color.YELLOW);
        initComps();
    }

    private MouseAdapter myMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == lbOK) {
                ImageIcon playIcon = new ImageIcon(getClass().getResource("/Images/ok2.png"));
                lbOK.setIcon(playIcon);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == lbOK) {
                ImageIcon playIcon = new ImageIcon(getClass().getResource("/Images/ok1.png"));
                lbOK.setIcon(playIcon);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() == lbOK) {
                myContainer.showMenu();
            }
        }
    };
    
    public void initComps(){
        lbOK = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/ok1.png"));
        lbOK.setBounds(350, 515, icon.getIconWidth(), icon.getIconHeight());
        lbOK.setIcon(icon);
        lbOK.addMouseListener(myMouseAdapter);
        add(lbOK);

        lbBackground = new JLabel();
        lbBackground.setBounds(-9, 0, GUI.WIDTH, GUI.HEIGHT);
        backgroundIcon = new ImageIcon(getClass().getResource("/Images/rsz_how_to_play.png"));
        lbBackground.setIcon(backgroundIcon);
        add(lbBackground);

    }
}
