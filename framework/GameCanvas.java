/**
    A GameCanvas class that handles all the game's animation
    and non-animation updates by constantly calling their
    draw and update methods.
    @author Angelo Joaquin B. Alvarez (210295)
    @author 
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
import gameobjects.*;

public class GameCanvas {

    public GameCanvas() {
        addGameObject(new TestObject(50, 50, GameObjectID.Test));
    }
    
    //Declaration of Variables
    public ArrayList<GameObject> gameObject = new ArrayList<GameObject>();
    private GameObject temp;

    /** Iterates through every gameObject and calls its update method */
    public void update() {
        for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            temp.update(gameObject);
        }
    }

    /** Iterates through every gameObject and calls its draw method */
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            temp.draw(g2d);
        }
    }

    public void addGameObject(GameObject object) {
        this.gameObject.add(object);
    }
    public void removeGameObject(GameObject object) {
        this.gameObject.remove(object);
    }


}
