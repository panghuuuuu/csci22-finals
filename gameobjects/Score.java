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
        p1Score = GC.getPoints()[0];
        p2Score = GC.getPoints()[1];
    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage image = null;
        BufferedImage score = null;
        if (this.id == GameObjectID.PlayerOneScore) {

            if(player1.getPlayerID() == 1) image = activeP1[0]; else if (player1.getPlayerID() == 2) image = p1[0];
            score = points[p1Score]; 
            g2d.drawImage(score, (int) (x+width), (int) y, 90, 90, null);

        } else if (this.id == GameObjectID.PlayerTwoScore) {

            if(player1.getPlayerID() == 1) image = p2[0]; else if (player1.getPlayerID() == 2) image = activeP2[0]; 
            score = points[p2Score];
            g2d.drawImage(score, (int) (x-90), (int) y, 90, 90, null);

        }

        g2d.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
        
    }

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
