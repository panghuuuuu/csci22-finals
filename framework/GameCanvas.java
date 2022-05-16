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
    public ArrayList<Player> p;
    private GameObject temp;
    private Player p1;
    private Player p2;
    private int width;
    private int height;


    public GameCanvas(int w, int h) {
        addGameObject(new TestObject(50, 50, 50, 50, GameObjectID.Test));
        addGameObject(new TestObject2(50, 1000, 100, 100, GameObjectID.Test2));
        p = new ArrayList<Player>();
        width = w;
        height = h;
        setPreferredSize(new Dimension(width, height));
    }
    
    public void newPlayer(int n) {
        if (n == 1) {
            p1 = new Player(10, 250); 
            p2 = new Player(300, 500);
        } else if (n == 2) {
            p1 = new Player(300, 500); 
            p2 = new Player(10, 250);
        }
        p.add(p1);
        p.add(p2);
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
        /*for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            temp.draw(g2d);
        }*/

        for (Player n: p) {
            n.draw(g2d);
        }
    }

    public void addGameObject(GameObject object) {
        this.gameObject.add(object);
    }
    public void removeGameObject(GameObject object) {
        this.gameObject.remove(object);
    }

    public Player getP1() {
        return p1;
     }
 
    public Player getP2() {
         return p2;
    }
}
