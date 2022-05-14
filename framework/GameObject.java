/**
    A GameObject abstract class that handles all the common
    properties of the object present in the game.
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


package framework;

import java.util.*;
import java.awt.*;

public abstract class GameObject {

    protected GameObjectID id;
    protected double x, y;
    protected double xSpeed = 0, ySpeed = 0;


    /**
    Constructor Method for a GameObject instance
        @param xPos The Initial x Position of the Object {@code Double}
        @param yPos The Initial y Position of the Object {@code Double}
        @param ObjectID The Object ID of an Object {@code GameObjectID}
    **/
    public GameObject(double xPos, double yPos, GameObjectID objectID) {
        this.x = xPos;
        this.y = yPos;
        this.id = objectID;
    }

    /** Handles the updates per second of all the properties 
     * @param gameObject Array list of GameObjects**/
    public abstract void update(ArrayList<GameObject> gameObject);
    
    /** Handles the frames per second of all the properties 
     * @param g2d Graphics2D object**/
    public abstract void draw(Graphics2D g2d);

    //Accessor Methods
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }

    public GameObjectID getID() {
        return this.id;
    }

    public double getXSpeed() {
        return this.xSpeed;
    }
    public double getYSpeed() {
        return this.ySpeed;
    }

    //Mutator Methods
    public void setX(double xPos) {
        this.x = xPos;
    }
    public void setY(double yPos) {
        this.y = yPos;
    }

    public void setXSpeed(double xSpd) {
        this.xSpeed = xSpd;
    }
    public void setYSpeed(double ySpd) {
        this.ySpeed = ySpd;
    }

}
