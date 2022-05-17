/**
    A GameCanvas class that handles all the game's animation
    and non-animation updates by constantly calling their
    draw and update methods.
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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import gameobjects.*;

public class GameCanvas extends JComponent {
    //Declaration of Variables
    public ArrayList<GameObject> gameObject = new ArrayList<GameObject>();
    private GameObject temp;

    public GameCanvas(int w, int h) {
        
    }
    
    public void newPlayer(int n) {
        if (n == 1) {
            addGameObject(new Player(50, 50, 50, 50, GameObjectID.PlayerOne));
            addGameObject(new Player(200, 200, 50, 50, GameObjectID.PlayerTwo));
        } else if (n == 2) {
            addGameObject(new Player(50, 50, 50, 50, GameObjectID.PlayerTwo));
            addGameObject(new Player(200, 200, 50, 50, GameObjectID.PlayerOne));
        }
    }

    /** Iterates through every gameObject and calls its update method */
    public void update() {
        for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            temp.update(gameObject);
        }
    }

    /** Iterates through every gameObject and calls its draw method */
    public void draw(Graphics2D g2d) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        if (MouseEventListener.mode == 0) {
            g2d.setColor(new Color(255,0,0));
            g2d.fillRect(450, 390, 250, 150); 
        } else if (MouseEventListener.mode == 1) {
            g2d.setColor(new Color(0,255,0));
            g2d.fillRect(100, 195, 250, 250); 
            g2d.setColor(new Color(255,0,0));
            g2d.fillRect(450, 195, 250, 250); 
            g2d.setColor(new Color(0,0,255));
            g2d.fillRect(775, 195, 250, 250); 
        }     
        
        if (MouseEventListener.mode == 2) {
            for (int i = 0; i < gameObject.size(); i++) {
                temp = gameObject.get(i);
                temp.draw(g2d);
            }
        }

        if (MouseEventListener.landscape == 1) {
            System.out.println("First Map");
        } else if (MouseEventListener.landscape == 2) {
            System.out.println("Second Map");
        } else if (MouseEventListener.landscape == 3) {
            System.out.println("Third Map");
        }
    }

    public void addGameObject(GameObject object) {
        this.gameObject.add(object);
    }
    public void removeGameObject(GameObject object) {
        this.gameObject.remove(object);
    }

    public GameObject getP1() {
        for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            if (temp.getID() == GameObjectID.PlayerOne) {
                return temp;
            }
        }
        return null;
     }
 
    public GameObject getP2() {
        for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            if (temp.getID() == GameObjectID.PlayerTwo) {
                return temp;
            }
        }
        return null;
    }
}
