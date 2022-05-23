/**
    A PushButton class that displays the push button on the players' 
    screens. It also updates the player's sprite animation depending
    on the push function. It also contains button animations 
    which work through changing the button sprite depending on the 
    cooldown time of the push function.
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
import java.awt.image.*;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

import framework.*;

public class PushButton extends GameObject {
    private Player player;
    private BufferedImage[] available, coolDown;
    private int spriteCounter = 0, spriteNum = 0;


    public PushButton(double xPos, double yPos, double w, double h, GameObjectID objectID, Player p) {
        super(xPos, yPos, w, h, objectID);
        this.player = p;
        getButtonImages();
    }
       

    @Override
    public void update(ArrayList<GameObject> gameObject) {
        // For player's sprite animations 
        spriteCounter++;
        if (spriteCounter > 40) {
            switch(spriteNum) {
                case 0:
                    spriteNum = 1;
                    break;
                case 1:
                    spriteNum = 2;
                    break;
                case 2:
                    spriteNum = 3;
                    break;
                case 3:
                    spriteNum = 4;
                case 4:
                    spriteNum = 0;
                    break;
                default:
                    break;
            }
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Display push button
        BufferedImage image = null;
        if(player.getCoolDown()) image = coolDown[spriteNum]; else {image = available[0]; spriteNum = 0;}
        g2d.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }

    /////////////
    //ANIMATION//
    /////////////    
    
    public void getButtonImages() {
        try {
            available = new BufferedImage[1];
            available[0] = ImageIO.read(getClass().getResourceAsStream("/res/button/buttonAvailable.png"));

            coolDown = new BufferedImage[5];
            coolDown[0] = ImageIO.read(getClass().getResourceAsStream("/res/button/buttonPushed.png"));
            coolDown[1] = ImageIO.read(getClass().getResourceAsStream("/res/button/coolDown/buttonCoolDown1.png"));
            coolDown[2] = ImageIO.read(getClass().getResourceAsStream("/res/button/coolDown/buttonCoolDown2.png"));
            coolDown[3] = ImageIO.read(getClass().getResourceAsStream("/res/button/coolDown/buttonCoolDown3.png"));
            coolDown[4] = ImageIO.read(getClass().getResourceAsStream("/res/button/coolDown/buttonCoolDown4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
