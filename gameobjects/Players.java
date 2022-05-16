/**
    A TestObject class to test the GameObject interface.
    @author Angelo Joaquin B. Alvarez (210295)
    @author Ysabella B. Panghulan (214521)
    @version May 14, 2022
**/
/*
    I have not discussed the Java language code in my program 
    with anyone other than my instructor or the teaching assistants 
    assigned to this course.

    I have not used Java language code obtained from another student, 
    or any other unauthorized source, either modified or unmodified.

    If any Java language code or documentation used in my program 
    was obtained from another source, such as a textbook or website, 
    that has been clearly noted with a proper citation in the comments 
    of my program.
*/

package gameobjects;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.ImageIcon;

public class Players {
    double x;
    double y;
    int width;
    int height;
    double horizontalSpeed;
    double verticalSpeed;
    private ImageIcon image;

    public Players(double x, double y) {
        this.x = x;
        this.y = y;
        //image = new ImageIcon(getClass().getResource("/sprites/samplesprite1.png"));
    }

    public void draw(Graphics2D g2d) {
        Rectangle2D.Double s = new Rectangle2D.Double(x,y, 50, 50);
        g2d.setColor(Color.RED);
        g2d.fill(s);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public double getVerticalSpeed() {
        return verticalSpeed;
    }

    public void reverseHorizontal() {
        horizontalSpeed *= -1;
    }

    public void reverseVertical() {
        verticalSpeed *= -1;
    }

    public void changeDirection() {
        reverseHorizontal();
        reverseVertical();
    }

    public void moveX(double n) {
        x += n;
    }

    public void moveY(double n) {
        y += n;
    }

    public void setX(double n) {
        x = n;
    }

    public void setY(double n) {
        y = n;
    }
   
    public Boolean isColliding(Players r) {
        return !(this.x + this.width <= r.getX() ||
                this.x >= r.getX() + r.getWidth() ||
                this.y + this.height <= r.getY() ||
                this.y >= r.getY() + r.getHeight());
    }
}
