package PlayMode;

import Character.*;
import User_Interface.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.BitSet;

import javax.swing.*;


public class PlayVsMonster extends JPanel implements Runnable{
    public static boolean IS_RUNNING = true;
    public static int timePlaceBomb = 0;

    private MyContainer myContainer;
    private BitSet traceKey = new BitSet();
    private JLabel lbMenu;

    private Manager myManager = new Manager();

    private int count = 0;
    private int timeMonsterChangeOrient = 0;
    private int timeDead = 0;
    private int timeLose = 0;
    private int timeNext = 0;

    public PlayVsMonster(MyContainer myContainer) {
        this.myContainer = myContainer;
        setBackground(Color.WHITE);
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
        innitComps();
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
                myManager.setRound(1);
                myManager.initManager();
                myContainer.showMenu();
            }
        }
    };

    private void innitComps() {
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
        myManager.drawPortal(g2d);
        myManager.drawAllItems(g2d);
        myManager.drawAllBombs(g2d);
        myManager.drawAllBoxes(g2d);
        myManager.drawAllMonster(g2d);
        myManager.getBomber().drawActor(g2d);
        myManager.drawAllShadows(g2d);
        myManager.drawInfo(g2d);
        myManager.drawBoss(g2d);

        if (myManager.getStatus() == Manager.LOSE) {
            myManager.drawMessage(g2d, Manager.LOSE);
        }
        if (myManager.getStatus() == Manager.NEXT_ROUND) {
            myManager.drawMessage(g2d, Manager.NEXT_ROUND);
        }
        if (myManager.getStatus() == Manager.WIN) {
            myManager.drawMessage(g2d, Manager.WIN);
        }

    }

    @Override
    public void run () {
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
            myManager.setBombStuck();

            myManager.allBombExplode();

            myManager.checkDead();

            myManager.checkImpactItem();

            myManager.checkWinAndLose();

            if (myManager.getStatus() == Manager.LOSE) {
                timeLose++;
                if (timeLose == 3000) {
                    myManager.initManager();
                    myContainer.showMenu();
                    timeLose = 0;
                }
            }

            if (myManager.getStatus() == Manager.NEXT_ROUND) {
                timeNext++;
                if (timeNext == 2000) {
                    myManager.initManager();
                    timeNext = 0;
                }
            }

            if (myManager.getStatus() == Manager.WIN) {
                timeNext++;
                if (timeNext == 4500) {
                    myManager.initManager();
                    myContainer.showMenu();
                    timeNext = 0;
                }
            }

            if (myManager.getBomber().getStatus() == Bomber.DEAD) {
                timeDead++;
                if (timeDead == 1000 && myManager.getBomber().getHeart() != 0) {
                    if (myManager.getRound() == 1) {
                        myManager.getBomber().Respawn(0, 540);
                        timeDead = 0;
                    }
                    if (myManager.getRound() == 2) {
                        myManager.getBomber().Respawn(315, 270);
                        timeDead = 0;
                    }
                    if (myManager.getRound() == 3) {
                        myManager.getBomber().Respawn(315, 495);
                        timeDead = 0;
                    }
                }
            }

            if (timeMonsterChangeOrient == 0) {
                myManager.changeOrientAll();
                timeMonsterChangeOrient = 3000;
            }

            if (timeMonsterChangeOrient > 0) {
                timeMonsterChangeOrient--;
            }


            timePlaceBomb++;
            myManager.moveBoss(count);
            if (timePlaceBomb > 3000) timePlaceBomb = 0;
            myManager.moveAllMonster(count);

            repaint();

            count++;
            if (count == 1000000) {
                count = 0;
            }
        }

    }
}

