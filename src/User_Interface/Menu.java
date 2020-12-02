package User_Interface;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel {
    private GUI myGui;
    private MyContainer myContainer;
    private JLabel lbPlayPvP;
    private JLabel lbPlayVsMonster;
    private JLabel lbHowToPlay;

    public Menu(MyContainer myContainer) {
        this.myContainer = myContainer;
        myGui = myContainer.getGui();

        setBackground(Color.WHITE);
        setLayout(null);

        initComps(myGui);
        initBackground();
    }

    public JLabel setLabel(int x, int y, String ImageIcon) {
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource(ImageIcon));

        label.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        label.setIcon(icon);

        return label;
    }

    private MouseAdapter myMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == lbPlayPvP) {
                ImageIcon playIcon = new ImageIcon(getClass().getResource("/Images/pvp2.png"));
                lbPlayPvP.setIcon(playIcon);
            }
            if (e.getSource() == lbPlayVsMonster) {
                ImageIcon optionIcon = new ImageIcon(getClass().getResource("/Images/playgame2.png"));
                lbPlayVsMonster.setIcon(optionIcon);
            }
            if (e.getSource() == lbHowToPlay) {
                ImageIcon htpIcon = new ImageIcon(getClass().getResource("/Images/howtoplay2.png"));
                lbHowToPlay.setIcon(htpIcon);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == lbPlayPvP) {
                ImageIcon playIcon = new ImageIcon(getClass().getResource("/Images/pvp1.png"));
                lbPlayPvP.setIcon(playIcon);
            }
            if (e.getSource() == lbPlayVsMonster) {
                ImageIcon optionIcon = new ImageIcon(getClass().getResource("/Images/playgame1.png"));
                lbPlayVsMonster.setIcon(optionIcon);
            }
            if (e.getSource() == lbHowToPlay) {
                ImageIcon htpIcon = new ImageIcon(getClass().getResource("/Images/howtoplay1.png"));
                lbHowToPlay.setIcon(htpIcon);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() == lbPlayPvP) {
                myContainer.showPlayPvP();
            }
            if (e.getSource() == lbPlayVsMonster) {
                myContainer.showPlayVsMonster();
            }
            if (e.getSource() == lbHowToPlay) {
                myContainer.showHowToPlay();
            }
        }
    };

    public void initComps(GUI myGui) {
        lbPlayPvP = setLabel(((myGui.getWidth() - 150) / 2) - 30,
                ((myGui.getHeight() - 150) / 2) - 30,
                "/Images/pvp1.png");
        lbPlayPvP.addMouseListener(myMouseAdapter);
        add(lbPlayPvP);


        lbPlayVsMonster = setLabel(lbPlayPvP.getX(),
                lbPlayPvP.getY() + lbPlayPvP.getHeight() + 15,
                "/Images/playgame1.png");
        lbPlayVsMonster.addMouseListener(myMouseAdapter);
        add(lbPlayVsMonster);

        lbHowToPlay = setLabel(lbPlayPvP.getX(),
                lbPlayVsMonster.getY() + lbPlayPvP.getHeight() + 15,
                "/Images/howtoplay1.png");
        lbHowToPlay.addMouseListener(myMouseAdapter);
        add(lbHowToPlay);
    }

    public void initBackground() {
        JLabel lbBackground = new JLabel();
        lbBackground.setBounds(0, -15, GUI.WIDTH, GUI.HEIGHT);
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/menu_background.jpg"));
        lbBackground.setIcon(icon);

        add(lbBackground);
    }
}
