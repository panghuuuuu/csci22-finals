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
    // Declaration of Variables
    public ArrayList<GameObject> gameObject = new ArrayList<GameObject>();
    private GameObject temp;
    private double scoreP1 = 0;
    private double scoreP2 = 0;
    private int playerID;

    public GameCanvas(int w, int h) {

    }

    public void newPlayer(int n) {
        if (n == 1) {
            addGameObject(new Player(250, 250, 50, 50, GameObjectID.PlayerOne));
            addGameObject(new Player(200, 200, 50, 50, GameObjectID.PlayerTwo));
            playerID = n;
        } else if (n == 2) {
            addGameObject(new Player(250, 250, 50, 50, GameObjectID.PlayerTwo));
            addGameObject(new Player(200, 200, 50, 50, GameObjectID.PlayerOne));
        }
        playerID = n;
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
        for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            temp.draw(g2d);
        }
        Wall w = new Wall(171, 79, 815, 471, GameObjectID.Wall);
        if (w.isOut(getP1())) {
            if (playerID == 1) {
                scoreP2++;
            } else {
                scoreP1++;
            }
            respawnP1();
        }
        if (w.isOut(getP2())) {
            if (playerID == 1) {
                scoreP1++;
            } else {
                scoreP2++;
            }
            respawnP2();
        }
        w.draw(g2d);
        g2d.setFont(new Font("Karla", Font.BOLD | Font.ITALIC, 25));
        g2d.setPaint(Color.YELLOW);
        g2d.drawString("P1: " + (int) scoreP1 + "||" + " P2:" + (int) scoreP2, 118, 55);
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

    public void respawnP1() {
        getP1().setX(500);
        getP1().setY(350);
    }

    public void respawnP2() {
        getP2().setX(500);
        getP2().setY(350);
    }
}
