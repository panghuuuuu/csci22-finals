/**
    A GameObject abstract class that handles all the common
    properties of the objects present in the game. It also
    contains the Update and Draw methods that will handle
    the Animation and non-animation updates of the game.
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
    protected double x, y, width, height;
    protected double xSpeed = 0, ySpeed = 0;

    /**
    Constructs a new GameObject instance.
        @param xPos The Initial x Position of the Object {@code double}
        @param yPos The Initial y Position of the Object {@code double}
        @param w The Initial width of the Object {@code double}
        @param h The initial height of the Object {@cod double}
        @param ObjectID The Object ID of an Object {@code GameObjectID}
    **/
    public GameObject(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        this.x = xPos;
        this.y = yPos;
        this.width = w;
        this.height = h;
        this.id = objectID;
    }

    /** Handles the updates per second of all the properties 
     * @param gameObject Array list of GameObjects**/
    public abstract void update(ArrayList<GameObject> gameObject);
    
    /** Handles the frames per second of all the properties 
     * @param g2d Graphics2D object**/
    public abstract void draw(Graphics2D g2d);


    /** Moves the GameObject instance's X position by its Speed
     * @param speed Speed of the GameObject instance**/
    public void moveX(double speed) {
        this.x += speed;
    }
    /** Moves the GameObject instance's Y position by its Speed
     * @param speed Speed of the GameObject instance**/
    public void moveY(double speed) {
        this.y += speed;
    }

    ////////////////////
    //ACCESSOR METHODS//
    ////////////////////

    /** Returns the current X Position of the GameObject instance
     * @return Current x Value
     */
    public double getX() {
        return this.x;
    }
    /** Returns the current Y Position of the GameObject instance
     * @return Current y value
     */
    public double getY() {
        return this.y;
    }
    /** Returns the current width of the GameObject instance
     * @return current width value
     */
    public double getWidth() {
        return this.width;
    }
    /** Returns the current height of the GameObject instance
     * @return current height value
     */
    public double getHeight() {
        return this.height;
    }

    /** Returns the GameObject instance ID of the GameObject instance
     * @return GameObject instance ID
     */
    public GameObjectID getID() {
        return this.id;
    }

    /** Returns the GameObject instance's Horizontal Speed
     * @return Current xSpeed value
     */
    public double getXSpeed() {
        return this.xSpeed;
    }
    /** Returns the Game Object instance's Vertical Speed
     * @return Current ySpeed value
     */
    public double getYSpeed() {
        return this.ySpeed;
    }

    /** Returns the Game Object instance's Horizontal Bounds
     * @return {@code Rectangle} of Horizontal Bounds
     */
    public Rectangle getHBounds() {
        double hx = this.x + this.xSpeed;
        double hy = this.y + 2;
        double hw = this.width + this.xSpeed/2;
        double hh = this.height - 4;

        return new Rectangle((int) hx, (int) hy, (int) hw, (int) hh);
    }
    /** Returns the Game Object instance's Vertical Bounds
     * @return {@code Rectangle} of Vertical Bounds
     */
    public Rectangle getVBounds() {
        double vx = this.x + 2;
        double vy = this.y + this.ySpeed;
        double vw = this.width - 4;
        double vh = this.height + this.ySpeed/2;

        return new Rectangle((int) vx, (int) vy, (int) vw, (int) vh);
    }

    ///////////////////
    //MUTATOR METHODS//
    ///////////////////

    /** Sets the X value of GameObject Instance
     * @param xPos New X Position
     */
    public void setX(double xPos) {
        this.x = xPos;
    }
    /** Sets the Y Value of the GameObject Instance
     * @param yPos New Y Position
     */
    public void setY(double yPos) {
        this.y = yPos;
    }

    /** Sets the Horizontal Speed of the GameObject Instance
     * @param xSpd New X Speed value
     */
    public void setXSpeed(double xSpd) {
        this.xSpeed = xSpd;
    }
    /** Sets the Vertical Speed of the GameObject Instance
     * @param ySpd New Y Speed Value
     */
    public void setYSpeed(double ySpd) {
        this.ySpeed = ySpd;
    }

    /** Sets the Width of the GameObject Instance
     * @param w New Width Value
     */
    public void setWidth(double w) {
        this.width = w;
    }
    /** Sets the Height of the GameObject Instance
     * @param h New Height Value
     */
    public void setHeight(double h) {
        this.height = h;
    }
}
