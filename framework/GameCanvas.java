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
    private Wall w;
    private int playerID;
    private Boolean gameStart = false;
    private Boolean waitP1 = true;
    private Boolean waitP2 = true;

    public GameCanvas(int w, int h) {

    }

    public void newPlayer(int n) {
        if (n == 1) {
            addGameObject(new Player(248, 282, 50, 50, GameObjectID.PlayerOne, n));
            addGameObject(new Player(856, 282, 50, 50, GameObjectID.PlayerTwo, n-1));
            playerID = n;
        } else if (n == 2) {
            addGameObject(new Player(248, 282, 50, 50, GameObjectID.PlayerTwo, n-1));
            addGameObject(new Player(856, 282, 50, 50, GameObjectID.PlayerOne, n));
        }
        playerID = n;
        w = new Wall(171, 79, 815, 407, GameObjectID.Wall);
        addGameObject(new PushButton(15, 642, 120, 60, GameObjectID.Button, (Player) getP1()));
    }

    /** Iterates through every gameObject and calls its update method */
    public void update() {
        for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            temp.update(gameObject);
        }

        if (MouseEventListener.mode == 1) {
            if (playerID == 1) {
                waitP1 = false;
            } else {
                waitP2 = false;
            }
        } 
        if(gameStart == true) {
            if (w.isOut(getP1())) {
                if (playerID == 1) {
                    scoreP2++;
                } else {
                    scoreP1++;
                }
                respawn();
            }
        }
    }

    /** Iterates through every gameObject and calls its draw method */
    public void draw(Graphics2D g2d) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        
        if (gameStart == true) {
            for (int i = 0; i < gameObject.size(); i++) {
                temp = gameObject.get(i);
                temp.draw(g2d);
            }
            w.draw(g2d);
            g2d.setFont(new Font("Karla", Font.BOLD | Font.ITALIC, 25));
            g2d.setPaint(Color.YELLOW);
            g2d.drawString("P1: " + (int) scoreP1 + "||" + " P2:" + (int) scoreP2, 118, 55);
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

    public void respawn() {
        Player P1 = ((Player) getP1());
        P1.setX(P1.getSpawnProps()[0]);
        P1.setY(P1.getSpawnProps()[1]);
        if (P1.getSpawnProps()[2] == 1) P1.setDir("Right"); else P1.setDir("Left");
        P1.setPushSpeed(0);
    }


    public void gameStart(Boolean n) {
        gameStart = n;
    }
    public Boolean getP1Wait() {
        return waitP1;
    }
    public Boolean getP2Wait() {
        return waitP2;
    }
}
