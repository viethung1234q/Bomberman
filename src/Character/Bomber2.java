package Character;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Bomber2 extends Actor {
    public static int ALLOW_RUN = 0;
    public static int DISALLOW_RUN = 1;

    protected int bombSize, bombQuantity, status, heart;

    public Bomber2(int x, int y, int type, int orient, int speed, int bombSize, int bombQuantity) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.orient = orient;
        this.speed = speed;
        this.bombSize = bombSize;
        this.bombQuantity = bombQuantity;
        this.status = Actor.ALIVE;
        this.heart = 3;
        this.img = new ImageIcon(getClass().getResource("/Images/bomber_down_01.png")).getImage();
        this.width = img.getWidth(null);
        this.height = img.getHeight(null) - 23;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBombQuantity() {
        return bombQuantity;
    }

    public void setBombQuantity(int bombQuantity) {
        if (bombQuantity > 5) {
            return;
        }
        this.bombQuantity = bombQuantity;
    }

    public void setBombSize(int bombSize) {
        if (bombSize > 3) {
            return;
        }
        this.bombSize = bombSize;
    }

    public int getBombSize() {
        return bombSize;
    }

    public int getType() {
        return type;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public void Respawn(int x, int y) {
        this.x = x;
        this.y = y;
        this.status = ALIVE;
        this.img = new ImageIcon(getClass().getResource("/Images/bomber_down_01.png")).getImage();
    }

    @Override
    public boolean move(int count, ArrayList<Bomb> arrBomb, ArrayList<Box> arrBox) {
        if (status == DEAD) {
            return false;
        }
        return super.move(count, arrBomb, arrBox);
    }

    @Override
    public void changeOrient(int orient) {
        if (this.status == DEAD) {
            return;
        }
        super.changeOrient(orient);
        switch (orient) {
            case LEFT:
                img = new ImageIcon(getClass().getResource("/Images/bomber_left_01.png")).getImage();
                break;
            case RIGHT:
                img = new ImageIcon(getClass().getResource("/Images/bomber_right_01.png")).getImage();
                break;
            case UP:
                img = new ImageIcon(getClass().getResource("/Images/bomber_up_01.png")).getImage();
                break;
            case DOWN:
                img = new ImageIcon(getClass().getResource("/Images/bomber_down_01.png")).getImage();
                break;
            default:
                break;
        }
    }

    public boolean isImpactBomberVsActor(Actor actor){
        if (status == DEAD) {
            return false;
        }
        Rectangle rec1 = new Rectangle(x, y, width, height);
        Rectangle rec2 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        return rec1.intersects(rec2);
    }
}
