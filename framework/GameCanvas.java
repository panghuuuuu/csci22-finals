/**
    A GameCanvas class that handles all the game's animation
    and non-animation updates by constantly calling their
    draw and update methods. It also handles the scores, the Players,
    and the reset and respawn mechanics of the game.
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

    /**
     * Adds New Players and other Instances of GameObject to the
     * GameCanvas class needed for the Game to run.
     * @param n {@code int} PlayerId of Current Players
     */
    public void newPlayer(int n) {
        //Creates Two Players depending on their PlayerIds (n)
        if (n == 1) {
            addGameObject(new Player(248, 282, 50, 50, GameObjectID.PlayerOne, n));
            addGameObject(new Player(856, 282, 50, 50, GameObjectID.PlayerTwo, 2));
            playerID = n;
        } else if (n == 2) {
            addGameObject(new Player(248, 282, 50, 50, GameObjectID.PlayerTwo, 1));
            addGameObject(new Player(856, 282, 50, 50, GameObjectID.PlayerOne, n));
        }
        playerID = n;

        //Creates a New Boundary
        w = new Wall(171, 79, 815, 407, GameObjectID.Wall);

        //Creates the Push Button Cool down Graphic
        addGameObject(new PushButton(15, 642, 120, 60, GameObjectID.Button, (Player) getP1()));

        //Creates Graphics for the Score of the Players.
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

    /**
     * Adds a GameObject Instance to the GameObject ArrayList
     * @param object {@code GameObject} instance to be added
     */
    public void addGameObject(GameObject object) {
        this.gameObject.add(object);
    }

    /**
     * Removes a GameObject Instance to the GameObject ArrayList
     * @param object {@code GameObject} instance to be removed
     */
    public void removeGameObject(GameObject object) {
        this.gameObject.remove(object);
    }

    /**
     * Gets the PlayerOne instance from the ArrayList Object
     * @return PlayerOne instance
     */
    public GameObject getP1() {
        for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            if (temp.getID() == GameObjectID.PlayerOne) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Gets the PlayerTwo instance from the ArrayList Object
     * @return PlayerTwo instance
     */
    public GameObject getP2() {
        for (int i = 0; i < gameObject.size(); i++) {
            temp = gameObject.get(i);
            if (temp.getID() == GameObjectID.PlayerTwo) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Gets the points of Players depending on their PlayerId
     * to pass it on to the server.
     * @param p {@code Player} instance
     * @return Scores of Players
     */
    public int getServerPoint(Player p) {
        if (p.getPlayerID() == 1) {
            return this.scoreP2;
        } else if (p.getPlayerID() == 2) {
            return this.scoreP1;
        }
        return 0;
    }

    /**
     * Sets the local points of the Player 2 instance
     * (Player 2 is dependent to the PlayerId of Player 1)
     * @param p {@code Player} instance
     * @param i {@code Int} Score of the Local Player 2
     */
    public void setLocalP2Points(Player p, int i) {
        if (p.getPlayerID() == 1) {
            this.scoreP2 = i;
        } else if (p.getPlayerID() == 2) {
            this.scoreP1 = i;
        }
    }
    
    /**
     * Respawns the Player's position when they are out of bounds.
     */
    public void respawn() {
        Player P1 = ((Player) getP1());
        P1.setX(P1.getSpawnProps()[0]);
        P1.setY(P1.getSpawnProps()[1]);
        if (P1.getSpawnProps()[2] == 1) P1.setDir("Right"); else P1.setDir("Left");
        P1.setPushSpeed(0);
    }

    /**
     * Sets the value of GameStart to true or false.
     * Determining whether the Game starts or not.
     * @param start {@code Boolean} True or False 
     */
    public void gameStart(Boolean start) {
        gameStart = start;
    }

    /**
     * Returns an array of the Scores of Local Players 1 and 2
     * @return {@code int array} containing the values of {Player 1 Score, Player 2 Score}
     */
    public int[] getPoints() {
        return new int[] {scoreP1, scoreP2};
    }

    /**
     * Returns whether Player 1 is Waiting for the Loading Screen
     * @return {@code waitP1} Whether Player 1 is waiting in the Loading Screen
     */
    public Boolean getP1Wait() {
        return waitP1;
    }
    /**
     * Returns whether Player 2 is Waiting for the Loading Screen
     * @return {@code waitP2} Whether Player 2 is waiting in the Loading Screen
     */
    public Boolean getP2Wait() {
        return waitP2;
    }

    /**
     * Returns the PlayerID of the winner
     * @return {@code waitP2} Whether Player 2 is waiting in the Loading Screen
     */
    public int getWinnerPlayer() {
        return winnerPlayer;
    }

    /**
     * Returns whether the Game has ended or not
     * @return {@code gameEnd} game has ended or not
     */
    public Boolean getGameEnd() {
        return gameEnd; 
    }

    /**
     * Resets all the values of the game
     */
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
