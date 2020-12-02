package PlayMode;

import Character.*;
import User_Interface.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.BitSet;

import javax.swing.*;

public class PlayPvP extends JPanel implements Runnable {
    public static boolean IS_RUNNING = true;

    private MyContainer myContainer;
    private BitSet traceKey = new BitSet();
    private JLabel lbMenu;

    int nothing = 0;
    private Manager myManager = new Manager(nothing);

    private int count = 0;
    private int timeDead = 0;
    private int timeWin = 0;

    public PlayPvP(MyContainer myContainer) {
        this.myContainer = myContainer;
        setLayout(null);
        setFocusable(true);
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                traceKey.set(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                traceKey.clear(e.getKeyCode());
            }
        };
        addKeyListener(keyAdapter);
        Thread myThread = new Thread(this);
        myThread.start();
        initComps();
    }

    private MouseAdapter myMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == lbMenu) {
                ImageIcon playIcon = new ImageIcon(getClass().getResource("/Images/menu2.png"));
                lbMenu.setIcon(playIcon);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == lbMenu) {
                ImageIcon playIcon = new ImageIcon(getClass().getResource("/Images/menu1.png"));
                lbMenu.setIcon(playIcon);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() == lbMenu) {
                myManager.initManagerPvP();
                myContainer.showMenu();
            }
        }
    };

    public void initComps() {
        lbMenu = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/menu1.png"));
        lbMenu.setBounds(740, 500, icon.getIconWidth(), icon.getIconHeight());
        lbMenu.setIcon(icon);
        lbMenu.addMouseListener(myMouseAdapter);
        add(lbMenu);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new java.awt.BasicStroke(2));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        myManager.drawBackground(g2d);
        myManager.drawAllItems(g2d);
        myManager.drawAllBombs(g2d);
        myManager.drawAllBoxes(g2d);
        myManager.getBomber().drawActor(g2d);
        myManager.getBomber2().drawActor(g2d);
        myManager.drawAllShadows(g2d);
        myManager.drawInfoPvP(g2d);

        if (myManager.getBomber().getHeart() == 0 || myManager.getBomber2().getHeart() == 0) {
            myManager.drawPvPWin(g2d);
        }
    }

    // Runnable interface.
    @Override
    public void run() {
        while (IS_RUNNING) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (traceKey.get(KeyEvent.VK_LEFT)) {
                myManager.getBomber().changeOrient(Bomber.LEFT);
                myManager.getBomber().move(count, myManager.getArrBomb(), myManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_RIGHT)) {
                myManager.getBomber().changeOrient(Bomber.RIGHT);
                myManager.getBomber().move(count, myManager.getArrBomb(), myManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_UP)) {
                myManager.getBomber().changeOrient(Bomber.UP);
                myManager.getBomber().move(count, myManager.getArrBomb(), myManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_DOWN)) {
                myManager.getBomber().changeOrient(Bomber.DOWN);
                myManager.getBomber().move(count, myManager.getArrBomb(), myManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_SPACE)) {
                myManager.initBomb();
                myManager.getBomber().setRunBomb(Bomber.ALLOW_RUN);
            }

            if (traceKey.get(KeyEvent.VK_A)) {
                myManager.getBomber2().changeOrient(Bomber2.LEFT);
                myManager.getBomber2().move(count, myManager.getArrBomb(), myManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_D)) {
                myManager.getBomber2().changeOrient(Bomber2.RIGHT);
                myManager.getBomber2().move(count, myManager.getArrBomb(), myManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_W)) {
                myManager.getBomber2().changeOrient(Bomber2.UP);
                myManager.getBomber2().move(count, myManager.getArrBomb(), myManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_S)) {
                myManager.getBomber2().changeOrient(Bomber2.DOWN);
                myManager.getBomber2().move(count, myManager.getArrBomb(), myManager.getArrBox());
            }
            if (traceKey.get(KeyEvent.VK_F)) {
                myManager.initBomb2();
                myManager.getBomber2().setRunBomb(Bomber2.ALLOW_RUN);
            }

            myManager.setBombStuck();
            myManager.setBombStuck2();

            myManager.allBombExplode();

            myManager.checkDeadPvP();

            myManager.checkImpactItem();
            myManager.checkImpactItem2();

            if(myManager.getBomber().getStatus() == Bomber.DEAD){
                timeDead++;
                if (timeDead == 1000) {
                    myManager.getBomber().Respawn(0, 540);
                    timeDead = 0;
                }
            }

            if(myManager.getBomber2().getStatus() == Bomber.DEAD){
                timeDead++;
                if (timeDead == 1000) {
                    myManager.getBomber2().Respawn(631, 90);
                    timeDead = 0;
                }
            }

            if (myManager.getBomber().getHeart() == 0 || myManager.getBomber2().getHeart() == 0) {
                timeWin++;
                if (timeWin == 4500) {
                    myManager.initManagerPvP();
                    myContainer.showMenu();
                    timeWin = 0;
                }
            }

            repaint();

            count++;
            if (count == 1000000) {
                count = 0;
            }
        }
    }
}
