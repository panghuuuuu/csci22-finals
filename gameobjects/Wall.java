/**
    A Wall class that creates the platform that the two players will 
    play on. It contains collision detection which determines if a 
    player steps out of bounds.
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

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

import framework.*;

public class Wall extends GameObject {

    /**
     * Constructor method of the Wall Class
     * instantiaing the x and y positions, width, 
     * and height of the wall
     */
    public Wall(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        super(xPos, yPos, w, h, objectID);
    }

    /**
     * Overrides the update method of GameObject.
     * @param gameObject {@code ArrayList<GameObject>} ArrayList of GameObjects
     */
    @Override
    public void update(ArrayList<GameObject> gameObject) {

    }

    /**
     * Overrides the draw method of GameObject.
     * @param g2d {@code Graphics2D} Graphics2D object
     */
    @Override
    public void draw(Graphics2D g2d) {
        // Display platform
        g2d.setColor(new Color(1f,0f,0f,0f ));
        g2d.fillRect((int) x, (int) y, (int) width, (int) height);
    }
 
    /**
     * Collision detection between player and platform
     * @return Determines if the player is out of bounds. 
     */
    public Boolean isOut(GameObject player) {
        return (this.x + this.width <= player.getX() ||
                this.x >= player.getX() + player.getWidth() ||
                this.y + this.height <= player.getY() ||
                this.y >= player.getY() + player.getHeight());
    }

}
