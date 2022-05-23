/**
    A Score class that displays the scores of the two players on their 
    screens. It also contains score animations which work through 
    changing the score sprite depending on the scores of the players.
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

import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.*;

import framework.*;

public class Score extends GameObject {

    private Player player1;
    private GameCanvas GC;
    private BufferedImage[] activeP1, activeP2, points, p1, p2;
    private int p1Score, p2Score;

    public Score(double xPos, double yPos, double w, double h, GameObjectID objectID, Player p, GameCanvas g) {
        super(xPos, yPos, w, h, objectID);
        this.player1 = p;
        this.GC = g;
        getScoreImages();
    }

    @Override
    public void update(ArrayList<GameObject> gameObject) {
        //Retrieves the score of both players from Game Canvas
        p1Score = GC.getPoints()[0];
        p2Score = GC.getPoints()[1];
    }

    @Override
    public void draw(Graphics2D g2d) {
        //Score animations
        BufferedImage image = null;
        BufferedImage score = null;

        // For Player 1
        if (this.id == GameObjectID.PlayerOneScore) {

            if(player1.getPlayerID() == 1) image = activeP1[0]; else if (player1.getPlayerID() == 2) image = p1[0];
            score = points[p1Score]; 
            g2d.drawImage(score, (int) (x+width), (int) y, 90, 90, null);

        } 
        
        // For Player 2
        else if (this.id == GameObjectID.PlayerTwoScore) {

            if(player1.getPlayerID() == 1) image = p2[0]; else if (player1.getPlayerID() == 2) image = activeP2[0]; 
            score = points[p2Score];
            g2d.drawImage(score, (int) (x-90), (int) y, 90, 90, null);

        }

        // Display score
        g2d.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
        
    }
    /////////////
    //ANIMATION//
    /////////////

    public void getScoreImages() {
        try {
            activeP1 = new BufferedImage[1];
            activeP1[0] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/playerOneScoreP1Active.png"));

            p1 = new BufferedImage[1];
            p1[0] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/playerOneScoreP1.png"));

            activeP2 = new BufferedImage[1];
            activeP2[0] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/playerOneScoreP2Active.png"));

            p2 = new BufferedImage[1];
            p2[0] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/playerOneScoreP2.png"));
            
            points = new BufferedImage[6];
            points[0] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/scoreNumbers/playerOneScoreNumbers1.png"));
            points[1] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/scoreNumbers/playerOneScoreNumbers2.png"));
            points[2] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/scoreNumbers/playerOneScoreNumbers3.png"));
            points[3] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/scoreNumbers/playerOneScoreNumbers4.png"));
            points[4] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/scoreNumbers/playerOneScoreNumbers5.png"));
            points[5] = ImageIO.read(getClass().getResourceAsStream("/res/score-sprites/scoreNumbers/playerOneScoreNumbers6.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
