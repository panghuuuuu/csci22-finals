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
import java.util.*;
import framework.*;

public class TestObject extends GameObject {

    public TestObject(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        super(xPos, yPos, w, h, objectID);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void update(ArrayList<GameObject> gameObject) {
        this.y += this.ySpeed;  
        this.ySpeed = 5; // Move to The Right 5 pixels


        for (int i = 0; i < gameObject.size(); i++) {
            GameObject tempObject = gameObject.get(i);
            switch(tempObject.getID()) {
                case Test2:
                    if(getHBounds().intersects(tempObject.getHBounds())) {
                        if(tempObject.getXSpeed() < 0) {
                            if(x < tempObject.getX() + tempObject.getWidth()/2) x = tempObject.getX() - width;
                        } else if (tempObject.getXSpeed() > 0) {
                            if(x > tempObject.getX() + tempObject.getWidth()/2) x = tempObject.getX() - width;
                        }
                    }
                    if(getVBounds().intersects(tempObject.getVBounds())) {
                        if(tempObject.getYSpeed() < 0) {
                            if(x < tempObject.getY() + tempObject.getHeight()/2) y = tempObject.getY() - height;
                        } else if (tempObject.getYSpeed() > 0) {
                            if(x > tempObject.getY() + tempObject.getHeight()/2) y = tempObject.getY() - height;
                        }
                    }
                    
                default:
                    break;
            }
        } 
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(255,0,0));
        g2d.fillRect((int) x,(int) y, (int) width, (int) height); // Creates a Rectangle    
    }

}
