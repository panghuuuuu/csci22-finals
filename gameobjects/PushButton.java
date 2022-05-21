package gameobjects;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

import framework.*;

public class PushButton extends GameObject {
    private Player player;
    private BufferedImage[] pushed, available, coolDown;
    private int spriteCounter = 0, spriteNum = 0;


    public PushButton(double xPos, double yPos, double w, double h, GameObjectID objectID, Player p) {
        super(xPos, yPos, w, h, objectID);
        this.player = p;
        getButtonImages();
    }
       

    @Override
    public void update(ArrayList<GameObject> gameObject) {
        spriteCounter++;
        if (spriteCounter > 35) {
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
        BufferedImage image = null;

        if(player.getCoolDown()) image = coolDown[spriteNum]; else {image = available[0]; spriteNum = 0;}
        g2d.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }

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
