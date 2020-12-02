package Character;

import PlayMode.PlayVsMonster;
import User_Interface.GameSound;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;

public class Manager {
    public static final int LOSE = 1;
    public static final int NEXT_ROUND = 2;
    public static final int WIN = 3;

    private Bomber myBomber;
    private Bomber2 myBomber2;

    private ArrayList<Bomb> arrBomb;
    private ArrayList<Bomb> arrBomb2;
    private ArrayList<Bomb> arrBombMonster;

    private ArrayList<BombExplode> arrBombExplode;
    private ArrayList<BombExplode> arrBombExplode2;
    private ArrayList<BombExplode> arrBombExplodeMonster;

    private ArrayList<Item> arrItem;
    private ArrayList<Box> arrBox;
    private ArrayList<Box> arrShadow;
    private ArrayList<Monster> arrMonster;

    private Random random = new Random();

    private int round = 1;
    private int nextRound = 0;
    private int status = 0;

    public Manager() {
        initManager();
    }

    public Manager(int nothing) {
        initManagerPvP();
    }

    /**
     * Init methods: these methods initialize all entities in the game.
     */
    public void initManagerPvP() {
        myBomber = new Bomber(0, 540, Actor.BOMBER, Actor.DOWN, 4, 1, 1);
        myBomber2 = new Bomber2(631, 90, Actor.BOMBER2, Actor.DOWN, 4, 1, 1);
        initAllMap("BOX_PvP.txt", "SHADOW_PvP.txt", "MONSTER_PvP.txt", "ITEM_PvP.txt");
    }

    public void initManager() {
        switch (round) {
            case 1:
                myBomber = new Bomber(0, 540, Actor.BOMBER, Actor.DOWN, 4, 1, 1);
                initAllMap("BOX.txt", "SHADOW.txt", "MONSTER.txt", "ITEM.txt");
                nextRound = 0;
                status = 0;
                break;
            case 2:
                myBomber.Respawn(315, 270);
                initAllMap("BOX1.txt", "SHADOW1.txt", "MONSTER1.txt", "ITEM1.txt");
                nextRound = 0;
                status = 0;
                break;
            case 3:
                myBomber.Respawn(315, 495);
                initAllMap("BOX2.txt", "SHADOW2.txt", "MONSTER2.txt", "ITEM2.txt");
                nextRound = 0;
                status = 0;
                break;

            default:
                break;
        }

    }

    public void initAllMap(String pathBox, String pathShadow, String pathMonster, String pathItem) {
        arrBomb = new ArrayList<>();
        arrBomb2 = new ArrayList<>();
        arrBombMonster = new ArrayList<>();

        arrBombExplode = new ArrayList<>();
        arrBombExplode2 = new ArrayList<>();
        arrBombExplodeMonster = new ArrayList<>();

        arrItem = new ArrayList<>();
        arrBox = new ArrayList<>();
        arrShadow = new ArrayList<>();
        arrMonster = new ArrayList<>();

        initBox(pathBox);
        initShadow(pathShadow);
        initMonster(pathMonster);
        initItem(pathItem);
    }

    public void initItem(String pathItem) {
        try {
            File f = new File(pathItem);
            Scanner sc = new Scanner(new BufferedReader(new FileReader(f)));
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] str = line.split(":");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                int type = Integer.parseInt(str[2]);
                String images = str[3];
                Item item = new Item(x, y, type, images);
                arrItem.add(item);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initBox(String pathBox) {
        try {
            File f = new File(pathBox);
            Scanner sc = new Scanner(new BufferedReader(new FileReader(f)));
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] str = line.split(":");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                int type = Integer.parseInt(str[2]);
                String img = str[3];
                Box box = new Box(x, y, type, img);
                arrBox.add(box);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initShadow(String pathShadow) {
        try {
            File f = new File(pathShadow);
            Scanner sc = new Scanner(new BufferedReader(new FileReader(f)));
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] str = line.split(":");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                int type = Integer.parseInt(str[2]);
                String img = str[3];
                Box box = new Box(x, y, type, img);
                arrShadow.add(box);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initMonster(String pathMonster) {
        try {
            File f = new File(pathMonster);
            Scanner sc = new Scanner(new BufferedReader(new FileReader(f)));
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] str = line.split(":");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                int type = Integer.parseInt(str[2]);
                int orient = Integer.parseInt(str[3]);
                int speed = Integer.parseInt(str[4]);
                int heart = Integer.parseInt(str[5]);
                String images = str[6];
                Monster monster = new Monster(x, y, type, orient, speed, heart, images);
                arrMonster.add(monster);
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // for player1.
    public void initBomb() {
        if (myBomber.getStatus() == Bomber.DEAD) {
            return;
        }
        int x = myBomber.getX() + myBomber.getWidth() / 2;
        int y = myBomber.getY() + myBomber.getHeight() / 2;

        // Disallow set bombs in same place.
        for (Bomb bomb : arrBomb) {
            if (bomb.isImpact(x, y)) return;
        }


        if (arrBomb.size() >= myBomber.getBombQuantity()) return;

        GameSound.getInstance().getClip(GameSound.BOMB).setFramePosition(0);
        GameSound.getInstance().getClip(GameSound.BOMB).start();
        Bomb myBomb = new Bomb(x, y, myBomber.getBombSize(), 1800);
        arrBomb.add(myBomb);
    }

    // for player2.
    public void initBomb2() {
        if (myBomber2.getStatus() == Bomber.DEAD) {
            return;
        }
        int x = myBomber2.getX() + myBomber2.getWidth() / 2;
        int y = myBomber2.getY() + myBomber2.getHeight() / 2;

        // Disallow set bombs in same place.
        for (Bomb bomb : arrBomb2) {
            if (bomb.isImpact(x, y)) return;
        }

        if (arrBomb2.size() >= myBomber2.getBombQuantity()) return;

        GameSound.getInstance().getClip(GameSound.BOMB).setFramePosition(0);
        GameSound.getInstance().getClip(GameSound.BOMB).start();
        Bomb myBomb = new Bomb(x, y, myBomber2.getBombSize(), 1800);
        arrBomb2.add(myBomb);
    }

    /**
     * Draw methods: these methods draw all entities in the game.
     */
    public void drawBackground(Graphics2D g2d) {
        Image img = new ImageIcon(getClass().getResource("/Images/grass.png")).getImage();
        g2d.drawImage(img, 0, 0, null);
    }

    public void drawAllItems(Graphics2D g2d) {
        for (int i = 0; i < arrItem.size(); i++) {
            arrItem.get(i).drawItem(g2d);
        }
    }

    public void drawAllBoxes(Graphics2D g2d) {
        for (int i = 0; i < arrBox.size(); i++) {
            arrBox.get(i).drawBox(g2d);
        }
    }

    public void drawAllShadows(Graphics2D g2d) {
        for (int i = 0; i < arrShadow.size(); i++) {
            arrShadow.get(i).drawBox(g2d);
        }
    }

    public void drawPvPWin(Graphics2D g2d) {
        if (myBomber.getHeart() == 0) {
            Image img = new ImageIcon(getClass().getResource("/Images/player2_win.jpg")).getImage();
            g2d.drawImage(img, 45, 50, null);
        }

        if (myBomber2.getHeart() == 0) {
            Image img = new ImageIcon(getClass().getResource("/Images/player1_win.jpg")).getImage();
            g2d.drawImage(img, 45, 50, null);
        }
        GameSound.getInstance().getClip(GameSound.BOMBER_DIE).stop();
        GameSound.getInstance().getClip(GameSound.PLAY_GAME).stop();
        GameSound.getInstance().getClip(GameSound.WIN).start();
    }

    public void drawAllBombs(Graphics2D g2d) {
        for (int i = 0; i < arrBomb.size(); i++) {
            arrBomb.get(i).drawActor(g2d);
        }
        for (int i = 0; i < arrBomb2.size(); i++) {
            arrBomb2.get(i).drawActor(g2d);
        }
        for (int i = 0; i < arrBombMonster.size(); i++) {
            arrBombMonster.get(i).drawActor(g2d);
        }
        for (int i = 0; i < arrBombExplode.size(); i++) {
            arrBombExplode.get(i).drawBombExplode(g2d);
        }
        for (int i = 0; i < arrBombExplode2.size(); i++) {
            arrBombExplode2.get(i).drawBombExplode(g2d);
        }
        for (int i = 0; i < arrBombExplodeMonster.size(); i++) {
            arrBombExplodeMonster.get(i).drawBombExplode(g2d);
        }
    }

    public void drawInfo(Graphics2D g2d) {
        Image imgInfo = new ImageIcon(getClass().getResource("/Images/background_Info.png")).getImage();
        g2d.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        g2d.setColor(Color.RED);
        g2d.drawImage(imgInfo, 675, 0, null);
        g2d.drawString("HEART", 755, 100);
        Image heart = new ImageIcon(getClass().getResource("/Images/heart_1.png")).getImage();
        if (myBomber.getHeart() == 3) {
            g2d.drawImage(heart, 750, 120, null);
            g2d.drawImage(heart, 775, 120, null);
            g2d.drawImage(heart, 800, 120, null);
        }
        if (myBomber.getHeart() == 2) {
            g2d.drawImage(heart, 760, 120, null);
            g2d.drawImage(heart, 790, 120, null);
        }
        if (myBomber.getHeart() == 1) {
            g2d.drawImage(heart, 775, 120, null);
        }
    }

    public void drawInfoPvP(Graphics2D g2d) {
        Image imgInfo = new ImageIcon(getClass().getResource("/Images/background_Info.png")).getImage();
        g2d.setFont(new Font("Showcard Gothic", Font.BOLD, 20));
        g2d.setColor(Color.RED);
        g2d.drawImage(imgInfo, 675, 0, null);
        g2d.drawString("PLAYER 1", 742, 100);
        Image heart = new ImageIcon(getClass().getResource("/Images/heart_1.png")).getImage();
        if (myBomber.getHeart() == 3) {
            g2d.drawImage(heart, 750, 120, null);
            g2d.drawImage(heart, 775, 120, null);
            g2d.drawImage(heart, 800, 120, null);
        }
        if (myBomber.getHeart() == 2) {
            g2d.drawImage(heart, 760, 120, null);
            g2d.drawImage(heart, 790, 120, null);
        }
        if (myBomber.getHeart() == 1) {
            g2d.drawImage(heart, 775, 120, null);
        }

        g2d.drawString("PLAYER 2", 742, 200);
        if (myBomber2.getHeart() == 3) {
            g2d.drawImage(heart, 750, 220, null);
            g2d.drawImage(heart, 775, 220, null);
            g2d.drawImage(heart, 800, 220, null);
        }
        if (myBomber2.getHeart() == 2) {
            g2d.drawImage(heart, 760, 220, null);
            g2d.drawImage(heart, 790, 220, null);
        }
        if (myBomber2.getHeart() == 1) {
            g2d.drawImage(heart, 775, 220, null);
        }
    }

    public void drawAllMonster(Graphics2D g2d) {
        for (int i = 0; i < arrMonster.size(); i++) {
            arrMonster.get(i).drawActor(g2d);
        }
    }

    public void drawBoss(Graphics2D g2d) {
        for (int i = 0; i < arrMonster.size(); i++) {
            arrMonster.get(i).drawBoss(g2d);
        }
    }

    public void drawPortal(Graphics2D g2d) {
        Image portal = new ImageIcon(getClass().getResource("/Images/portal.v1.png")).getImage();
        g2d.drawImage(portal, 631, 0, null);
    }

    public void drawMessage(Graphics2D g2d, int type) {
        if (type == 1) {
            Image img = new ImageIcon(getClass().getResource("/Images/Lose.jpg")).getImage();
            g2d.drawImage(img, 160, 45, null);
        } else {
            if (type == 2) {
                if (round == 2) {
                    Image img = new ImageIcon(getClass().getResource("/Images/round2.png")).getImage();
                    g2d.drawImage(img, 50, 45, null);
                }
                if (round == 3) {
                    Image img = new ImageIcon(getClass().getResource("/Images/round3.png")).getImage();
                    g2d.drawImage(img, 50, 45, null);
                }
            } else {
                Image img = new ImageIcon(getClass().getResource("/Images/Win.jpg")).getImage();
                g2d.drawImage(img, 160, 45, null);
            }
        }
    }

    public void checkDead() {
        for (int i = 0; i < arrBombExplode.size(); i++) {
            if (arrBombExplode.get(i).isImpactBombExplodeVsActor(myBomber) && myBomber.getStatus() == Bomber.ALIVE) {
                Image icon = new ImageIcon(getClass().getResource("/Images/bomber_dead.png")).getImage();
                myBomber.setImg(icon);

                if (myBomber.getStatus() == Bomber.DEAD) return;

                myBomber.setHeart(myBomber.getHeart() - 1);
                myBomber.setStatus(Bomber.DEAD);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).setFramePosition(0);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).start();
            }
        }
        for (int i = 0; i < arrMonster.size(); i++) {
            if (myBomber.isImpactBomberVsActor(arrMonster.get(i))) {
                Image icon = new ImageIcon(getClass().getResource("/Images/ghost.png")).getImage();
                myBomber.setImg(icon);

                if (myBomber.getStatus() == Bomber.DEAD) return;

                myBomber.setHeart(myBomber.getHeart() - 1);
                myBomber.setStatus(Bomber.DEAD);
                GameSound.getInstance().getClip(GameSound.BOMBER_DIE).setFramePosition(0);
                GameSound.getInstance().getClip(GameSound.BOMBER_DIE).start();
            }
        }
    }

    public void checkDeadPvP() {
        // bomb1 kill player1.
        for (int i = 0; i < arrBombExplode.size(); i++) {
            if (arrBombExplode.get(i).isImpactBombExplodeVsActor(myBomber) && myBomber.getStatus() == Bomber.ALIVE) {
                Image icon = new ImageIcon(getClass().getResource("/Images/bomber_dead.png")).getImage();
                myBomber.setImg(icon);
                if (myBomber.getStatus() == Bomber.DEAD) {
                    return;
                }
                myBomber.setHeart(myBomber.getHeart() - 1);
                myBomber.setStatus(Bomber.DEAD);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).setFramePosition(0);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).start();
            }
        }
        // bomb1 kill player2.
        for (int i = 0; i < arrBombExplode.size(); i++) {
            if (arrBombExplode.get(i).isImpactBombExplodeVsActor(myBomber2) && myBomber2.getStatus() == Bomber.ALIVE) {
                Image icon = new ImageIcon(getClass().getResource("/Images/bomber_dead_01.png")).getImage();
                myBomber2.setImg(icon);
                if (myBomber2.getStatus() == Bomber.DEAD) {
                    return;
                }
                myBomber2.setHeart(myBomber2.getHeart() - 1);
                myBomber2.setStatus(Bomber.DEAD);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).setFramePosition(0);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).start();
            }
        }
        //bomb2 kill player1.
        for (int i = 0; i < arrBombExplode2.size(); i++) {
            if (arrBombExplode2.get(i).isImpactBombExplodeVsActor(myBomber) && myBomber.getStatus() == Bomber.ALIVE) {
                Image icon = new ImageIcon(getClass().getResource("/Images/bomber_dead.png")).getImage();
                myBomber.setImg(icon);
                if (myBomber.getStatus() == Bomber.DEAD) {
                    return;
                }
                myBomber.setHeart(myBomber.getHeart() - 1);
                myBomber.setStatus(Bomber.DEAD);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).setFramePosition(0);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).start();
            }
        }
        //bomb2 kill player2.
        for (int i = 0; i < arrBombExplode2.size(); i++) {
            if (arrBombExplode2.get(i).isImpactBombExplodeVsActor(myBomber2) && myBomber2.getStatus() == Bomber.ALIVE) {
                Image icon = new ImageIcon(getClass().getResource("/Images/bomber_dead_01.png")).getImage();
                myBomber2.setImg(icon);
                if (myBomber2.getStatus() == Bomber.DEAD) {
                    return;
                }
                myBomber2.setHeart(myBomber2.getHeart() - 1);
                myBomber2.setStatus(Bomber.DEAD);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).setFramePosition(0);
                GameSound.instance.getClip(GameSound.BOMBER_DIE).start();
            }
        }

    }

    public void checkWinAndLose() {
            if (myBomber.getHeart() == 0 && nextRound == 0) {
                round = 1;
                status = LOSE;
                nextRound++;
                GameSound.getInstance().getClip(GameSound.PLAY_GAME).stop();
                GameSound.getInstance().getClip(GameSound.LOSE).start();
            }
            if (myBomber.getX() == 631 && myBomber.getY() == 0) {
                if (arrMonster.size() == 0 && nextRound == 0) {
                    if (round == 3) {
                        status = WIN;
                        nextRound++;
                        GameSound.getInstance().getClip(GameSound.PLAY_GAME).stop();
                        GameSound.getInstance().getClip(GameSound.WIN).start();
                        round = 1;
                        return;
                    }
                    round = round + 1;
                    nextRound++;
                    status = NEXT_ROUND;
                }
            }
    }

    public void allBombExplode() {
        // Make Bomb explodes.
        Explode(arrBomb, arrBombExplode);
        Explode(arrBomb2, arrBombExplode2);

        // Interact between Bomb and BOSS.
        for (int j = 0; j < arrMonster.size(); j++) {
            for (int i = 0; i < arrBomb.size(); i++) {
                if (arrBomb.get(i).isImpactBombVsActor(arrMonster.get(j)) == 2) {
                    BombExplode bombExplode = new BombExplode(arrBomb.get(i).getX(),
                                                              arrBomb.get(i).getY(),
                                                              arrBomb.get(i).getSize(),
                                                              arrBox);
                    arrBombExplode.add(bombExplode);
                    GameSound.getInstance().getClip(GameSound.BOMB_EXPLODE).setFramePosition(0);
                    GameSound.getInstance().getClip(GameSound.BOMB_EXPLODE).start();
                    arrBomb.remove(i);
                }
            }
        }

        // Interact between Bomb and Monster.
        for (int k = 0; k < arrBombExplode.size(); k++) {
            arrBombExplode.get(k).deadlineBomb();
            for (int j = 0; j < arrMonster.size(); j++) {
                if (arrBombExplode.get(k).isImpactBombExplodeVsActor(arrMonster.get(j))) {
                    if (arrMonster.get(j).getHeart() > 1) {
                        arrMonster.get(j).setHeart(arrMonster.get(j).getHeart() - 1);
                    } else {
                        GameSound.getInstance().getClip(GameSound.MONSTER_DIE).setFramePosition(0);
                        GameSound.getInstance().getClip(GameSound.MONSTER_DIE).start();
                        arrMonster.remove(j);
                    }
                }
            }
        }

        // Interact between Bomb and Box.
        for (BombExplode bombExplode : arrBombExplode) {
            for (int j = 0; j < arrBox.size(); j++) {
                if (bombExplode.isImpactBombExplodeVsBox(arrBox.get(j))) {
                    arrBox.remove(j);
                    arrShadow.remove(j);
                }
            }
        }
        for (BombExplode bombExplode : arrBombExplode2) {
            for (int j = 0; j < arrBox.size(); j++) {
                if (bombExplode.isImpactBombExplodeVsBox(arrBox.get(j))) {
                    arrBox.remove(j);
                    arrShadow.remove(j);
                }
            }
        }


        // Interact between Bomb and Item.
        for (BombExplode bombExplode : arrBombExplode) {
            for (int j = 0; j < arrItem.size(); j++) {
                if (bombExplode.isImpactBombExplodeVsItem(arrItem.get(j))) {
                    arrItem.remove(j);
                }
            }
        }
        for (BombExplode bombExplode : arrBombExplode2) {
            for (int j = 0; j < arrItem.size(); j++) {
                if (bombExplode.isImpactBombExplodeVsItem(arrItem.get(j))) {
                    arrItem.remove(j);
                }
            }
        }

        // If bombs are placed next to each other, make it explode simultaneously.
        ExplodeSimultaneously(arrBombExplode, arrBomb);
        ExplodeSimultaneously(arrBombExplode2, arrBomb2);

        // Remove the remain of bomb.
        for (int k = 0; k < arrBombExplode.size(); k++) {
            arrBombExplode.get(k).deadlineBomb();
            if (arrBombExplode.get(k).getTimeLine() == 0) {
                arrBombExplode.remove(k);
            }
        }
        for (int k = 0; k < arrBombExplode2.size(); k++) {
            arrBombExplode2.get(k).deadlineBomb();
            if (arrBombExplode2.get(k).getTimeLine() == 125) {
                arrBombExplode2.remove(k);
            }
        }
    }

    private void Explode(ArrayList<Bomb> arrBomb, ArrayList<BombExplode> arrBombExplode) {
        for (int i = 0; i < arrBomb.size(); i++) {
            arrBomb.get(i).bombExplode();
            if (arrBomb.get(i).getTimeline() == 250) {
                BombExplode bombExplode = new BombExplode(arrBomb.get(i).getX(),
                                                          arrBomb.get(i).getY(),
                                                          arrBomb.get(i).getSize(),
                                                          arrBox);
                arrBombExplode.add(bombExplode);
                GameSound.getInstance().getClip(GameSound.BOMB_EXPLODE).setFramePosition(0);
                GameSound.getInstance().getClip(GameSound.BOMB_EXPLODE).start();
                arrBomb.remove(i);
            }
        }
    }

    private void ExplodeSimultaneously(ArrayList<BombExplode> arrBombExplode, ArrayList<Bomb> arrBomb) {
        for (int i = 0; i < arrBombExplode.size(); i++) {
            for (int j = 0; j < arrBomb.size(); j++) {
                if (arrBombExplode.get(i).isImpactBombExplodeVsBomb(arrBomb.get(j))) {
                    BombExplode bombExplode = new BombExplode(arrBomb.get(j).getX(),
                                                              arrBomb.get(j).getY(),
                                                              arrBomb.get(j).getSize(),
                                                              arrBox);
                    arrBombExplode.add(bombExplode);
                    arrBomb.remove(j);
                }
            }
        }
    }

    public void setBombStuck() {
        if (arrBomb.size() > 0) {
            if (!arrBomb.get(arrBomb.size() - 1).setRun(myBomber)) {
                myBomber.setRunBomb(Bomber.DISALLOW_RUN);
            }
        }
    }

    public void setBombStuck2() {
        if (arrBomb2.size() > 0) {
            if (!arrBomb2.get(arrBomb2.size() - 1).setRun2(myBomber2)) {
                myBomber2.setRunBomb(Bomber.DISALLOW_RUN);
            }
        }
    }

    public void checkImpactItem() {
        for (int i = 0; i < arrItem.size(); i++) {
            if (arrItem.get(i).isImpactItemVsBomber(myBomber)) {
                GameSound.getInstance().getClip(GameSound.ITEM).setFramePosition(0);
                GameSound.getInstance().getClip(GameSound.ITEM).start();
                if (arrItem.get(i).getType() == Item.Item_Bomb) {
                    myBomber.setBombQuantity(myBomber.getBombQuantity() + 1);
                    arrItem.remove(i);
                    break;
                }
                if (arrItem.get(i).getType() == Item.Item_BombSize) {
                    myBomber.setBombSize(myBomber.getBombSize() + 1);
                    arrItem.remove(i);
                    break;
                }
                if (arrItem.get(i).getType() == Item.Item_Shoe) {
                    myBomber.setSpeed(myBomber.getSpeed() - 1);
                    arrItem.remove(i);
                    break;
                }
            }
        }
    }

    public void checkImpactItem2() {
        for (int i = 0; i < arrItem.size(); i++) {
            if (arrItem.get(i).isImpactItemVsBomber2(myBomber2)) {
                GameSound.getInstance().getClip(GameSound.ITEM).setFramePosition(0);
                GameSound.getInstance().getClip(GameSound.ITEM).start();
                if (arrItem.get(i).getType() == Item.Item_Bomb) {
                    myBomber2.setBombQuantity(myBomber2.getBombQuantity() + 1);
                    arrItem.remove(i);
                    break;
                }
                if (arrItem.get(i).getType() == Item.Item_BombSize) {
                    myBomber2.setBombSize(myBomber2.getBombSize() + 1);
                    arrItem.remove(i);
                    break;
                }
                if (arrItem.get(i).getType() == Item.Item_Shoe) {
                    myBomber2.setSpeed(myBomber2.getSpeed() - 1);
                    arrItem.remove(i);
                    break;
                }
            }
        }
    }

    public void changeOrientAll() {
        for (int i = 0; i < arrMonster.size(); i++) {
            if (arrMonster.get(i).getType() != Actor.BOSS) {
                int orient = random.nextInt(4) + 1;
                arrMonster.get(i).changeOrient(orient);
            }
        }
    }

    public void moveBoss(int count) {
        for (int i = 0; i < arrMonster.size(); i++) {
            if (arrMonster.get(i).getType() == Actor.BOSS) {
                if (myBomber.getStatus() == Bomber.DEAD) return;

                arrMonster.get(i).move(count, arrBomb, arrBox);

                int xBmb = myBomber.getX();
                int yBmb = myBomber.getY();
                int xB = arrMonster.get(i).getX();
                int yB = arrMonster.get(i).getY();

                if (yBmb + 20 < yB) arrMonster.get(i).changeOrient(Actor.UP);
                else if (yBmb - 20 > yB) arrMonster.get(i).changeOrient(Actor.DOWN);
                if (yBmb - 20 < yB && yB < yBmb + 20) {
                    if (xBmb < xB) arrMonster.get(i).changeOrient(Actor.LEFT);
                    else arrMonster.get(i).changeOrient(Actor.RIGHT);
                }

                if (PlayVsMonster.timePlaceBomb == 3000) {
                    int bombx1 = xB;
                    int bomby1 = yB;

                    int bombx2 = xB + arrMonster.get(i).getWidth();
                    int bomby2 = yB + arrMonster.get(i).getHeight();

                    int bombx3 = xB;
                    int bomby3 = yB + arrMonster.get(i).getHeight();

                    int bombx4 = xB + arrMonster.get(i).getWidth();
                    int bomby4 = yB;

                    Bomb myBomb1 = new Bomb(bombx1, bomby1, 2, 1500);
                    arrBombMonster.add(myBomb1);
                    Bomb myBomb2 = new Bomb(bombx2, bomby2, 2, 1500);
                    arrBombMonster.add(myBomb2);
                    Bomb myBomb3 = new Bomb(bombx3, bomby3, 2, 1500);
                    arrBombMonster.add(myBomb3);
                    Bomb myBomb4 = new Bomb(bombx4, bomby4, 2, 1500);
                    arrBombMonster.add(myBomb4);
                }

                for (int r = 0; r < arrBombMonster.size(); r++) {
                    arrBombMonster.get(r).bombExplode();
                    if (arrBombMonster.get(r).getTimeline() == 250) {
                        BombExplode bombExplode = new BombExplode(arrBombMonster.get(r).getX(),
                                arrBombMonster.get(r).getY(),
                                arrBombMonster.get(r).getSize(),
                                arrBox);
                        arrBombExplodeMonster.add(bombExplode);
                        if (r == 1) {
                            GameSound.getInstance().getClip(GameSound.BOMB_EXPLODE).setFramePosition(0);
                            GameSound.getInstance().getClip(GameSound.BOMB_EXPLODE).start();
                        }
                        arrBombMonster.remove(r);
                    }
                }

                for (int z = 0; z < arrBombExplodeMonster.size(); z++) {
                    if (arrBombExplodeMonster.get(z).isImpactBombExplodeVsActor(myBomber) && myBomber.getStatus() == Bomber.ALIVE) {
                        Image icon = new ImageIcon(getClass().getResource("/Images/bomber_dead.png")).getImage();
                        myBomber.setImg(icon);
                        if (myBomber.getStatus() == Bomber.DEAD) {
                            return;
                        }
                        GameSound.getInstance().getClip(GameSound.BOMBER_DIE).setFramePosition(0);
                        GameSound.getInstance().getClip(GameSound.BOMBER_DIE).start();
                        myBomber.setHeart(myBomber.getHeart() - 1);
                        myBomber.setStatus(Bomber.DEAD);
                    }
                }
                for (int k = 0; k < arrBombExplodeMonster.size(); k++) {
                    arrBombExplodeMonster.get(k).deadlineBomb();
                    if (arrBombExplodeMonster.get(k).getTimeLine() == 0) {
                        arrBombExplodeMonster.remove(k);
                    }
                }
            }
        }
    }

    public void moveAllMonster(int count) {
        for (int i = 0; i < arrMonster.size(); i++) {
            if (arrMonster.get(i).getType() != Actor.BOSS && !arrMonster.get(i).move(count, arrBomb, arrBox)) {
                int orient = random.nextInt(4) + 1;
                arrMonster.get(i).changeOrient(orient);
            }
        }
    }

    public ArrayList<Box> getArrBox() {
        return arrBox;
    }

    public ArrayList<Bomb> getArrBomb() {
        return arrBomb;
    }

    public Bomber getBomber() {
        return myBomber;
    }

    public Bomber2 getBomber2() {
        return myBomber2;
    }

    public int getStatus() {
        return status;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = 1;
    }
}

