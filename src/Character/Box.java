package Character;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class Box {
    public static int ALLOW_EXPLODE = 0;
    public static int DISALLOW_EXPLODE = 1;

    private int x, y, width, height, type;
    private Image img;

    public Box(int x, int y, int type, String image) {
        this.x = x;
        this.y = y;
        this.type = type;
        img = new ImageIcon(getClass().getResource(image)).getImage();
        this.width = img.getWidth(null);
        this.height = img.getWidth(null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType() {
        return type;
    }

    public void drawBox(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);
    }

    public int isImpactBoxVsActor (Actor actor) {
        if (actor.getType() == Actor.BOSS) {
            return 0;
        }
        Rectangle rec1 = new Rectangle(x, y, width, height);
        Rectangle rec2 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        Rectangle rec3 = new Rectangle();
        if (rec1.intersects(rec2)) {
            Rectangle2D.intersect(rec1, rec2, rec3);
            if (rec3.getHeight() == 1 && (actor.getOrient() == Actor.UP) || (actor.getOrient() == Actor.DOWN)) {
                if (actor.getX() == rec3.getX()) {
                    return (int)rec3.getWidth();
                }
                else {
                    return (int)-rec3.getWidth();
                }
            }
            else {
                if (actor.getY() == rec3.getY()) {
                    return (int)rec3.getHeight();
                }
                else {
                    return (int)-rec3.getHeight();
                }
            }
        }
        return 0;
    }
}

