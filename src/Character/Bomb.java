package Character;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Bomb extends Actor {
    protected int size, timeLine;

    public Bomb(int x, int y, int size, int timeLine) {
        // round up (down) the coordinate of the bomb.
        this.x = (x / 45) * 45;
        this.y = (y / 45) * 45;

        this.size = size;
        this.timeLine = timeLine;
        this.orient = 0;
        this.type = Actor.BOMB;
        img = new ImageIcon(getClass().getResource("/Images/bomb.png")).getImage();
        this.width= img.getWidth(null);
        this.height= img.getHeight(null);
    }

    public int getSize() {
        return size;
    }

    public boolean setRun(Bomber myBomber) {
        Rectangle rec2 = new Rectangle(x, y, 45, 45);
        Rectangle rec3 = new Rectangle(myBomber.getX(), myBomber.getY(), myBomber.getWidth(), myBomber.getHeight());
        return rec2.intersects(rec3);
    }
    public boolean setRun2(Bomber2 myBomber) {
        Rectangle rec2 = new Rectangle(x, y, 45, 45);
        Rectangle rec3 = new Rectangle(myBomber.getX(), myBomber.getY(), myBomber.getWidth(), myBomber.getHeight());
        return rec2.intersects(rec3);
    }

    public boolean isImpact(int xNewBomb, int yNewBomb){
        Rectangle rec1 = new Rectangle(x, y, 45, 45);
        Rectangle rec2 = new Rectangle(xNewBomb, yNewBomb, 45, 45);
        return rec1.intersects(rec2);
    }

    public void bombExplode(){
        this.timeLine--;
    }

    public int getTimeline() {
        return timeLine;
    }

    public int isImpactBombVsActor(Actor actor) {
        if (actor.getRunBomb() == Bomber.ALLOW_RUN) {
            return 0;
        }
        Rectangle rec2 = new Rectangle(x, y, 45, 45);
        Rectangle rec3 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        if (rec2.intersects(rec3)) {
            if (actor.getType() == Bomber.BOSS) {
                return 2;
            }
            return 1;
        }
        return 0;
    }
}

