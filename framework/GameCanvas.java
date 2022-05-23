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
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private Wall w;
    private int playerID;
    private Boolean gameStart = false;
    private Boolean waitP1 = true;
    private Boolean waitP2 = true;
    private int winnerPlayer = 0;
    private Boolean gameEnd = false;

    public GameCanvas() {

    }

    public void newPlayer(int n) {
        if (n == 1) {
            addGameObject(new Player(248, 282, 50, 50, GameObjectID.PlayerOne, n));
            addGameObject(new Player(856, 282, 50, 50, GameObjectID.PlayerTwo, 2));
            playerID = n;
        } else if (n == 2) {
            addGameObject(new Player(248, 282, 50, 50, GameObjectID.PlayerTwo, 1));
            addGameObject(new Player(856, 282, 50, 50, GameObjectID.PlayerOne, n));
        }
        playerID = n;
        w = new Wall(171, 79, 815, 407, GameObjectID.Wall);
        addGameObject(new PushButton(15, 642, 120, 60, GameObjectID.Button, (Player) getP1()));

        addGameObject(new Score(25, 25, 60, 90, GameObjectID.PlayerOneScore, ((Player) getP1()), this));
        addGameObject(new Score(995, 25, 60, 90, GameObjectID.PlayerTwoScore, ((Player) getP1()), this));
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
                if (((Player) getP1()).getPlayerID() == 1) scoreP2++; else if (((Player) getP1()).getPlayerID() == 2) scoreP1++;
                respawn();
            }

            if (scoreP1 == 5 || scoreP2 == 5) {
                if (playerID == 1 && scoreP1 == 5 || scoreP2 == 5 && playerID == 2) winnerPlayer = 1;
                else winnerPlayer = 2;
                gameEnd = true;
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

    public int getServerPoint(Player p) {
        if (p.getPlayerID() == 1) {
            return this.scoreP2;
        } else if (p.getPlayerID() == 2) {
            return this.scoreP1;
        }
        return 0;
    }

    public void setLocalP2Points(Player p, int i) {
        if (p.getPlayerID() == 1) {
            this.scoreP2 = i;
        } else if (p.getPlayerID() == 2) {
            this.scoreP1 = i;
        }
    }
    
    public int[] getPoints() {
        return new int[] {this.scoreP1, this.scoreP2};
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

    public int getWinnerPlayer() {
        return winnerPlayer;
    }

    public Boolean getGameEnd() {
        return gameEnd; 
    }

    public void reset() {
        scoreP1 = 0;
        scoreP2 = 0;
        gameStart = false;
        waitP1 = true;
        waitP2 = true;
        winnerPlayer = 0;
        gameEnd = false;
    }
}
