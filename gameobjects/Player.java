/**
    A Players class that controls the players' movements. The program 
    allows the player to move using arrow keys. It also allows the player
    to push the opponent using the push function, which can be triggered by 
    spacebar, and collision detection. It also contains player animations 
    which work through changing the player's sprite depending on the movements
    made by the user. 
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
import java.util.*;
import javax.imageio.ImageIO;
import framework.*;

public class Player extends GameObject {
    private double movementSpeed;
    private boolean activePush = false, slideX = false, slideY = false, coolDown = false, blink = false,
            moveRight = false, moveLeft = false, moveDown = false, pushingAnimH = false, pushingAnimV = false,
            compAnim = false;
    private BufferedImage[] idleR, idleL, idleU, idleD, moveR, moveL, moveD, pushR, pushL, pushD, pushU;
    private int[] spawnProps;
    private int blinkCoolDown;
    private String direction = "Right";
    private int pushSpeed;
    private int spriteCounter, spriteNum;
    private int coolDownCounter = 130;
    private int playerID;

    /**
     * Constructor method of the Player Class
     * instantiaing the movementSpeed, pushSpeed,
     * playerID and running the getPlayerImages and
     * setSpawnProps methods.
     */
    public Player(double xPos, double yPos, double w, double h, GameObjectID objectID, int pid) {
        super(xPos, yPos, w, h, objectID);
        movementSpeed = 2;
        pushSpeed = 10;
        playerID = pid;
        getPlayerImages();
        setSpawnProps(pid);
        if (pid == 1)
            direction = "Right";
        else
            direction = "Left";
    }

    /**
     * Overrides the update method of GameObject.
     * @param gameObject {@code ArrayList<GameObject>} ArrayList of GameObjects
     */
    @Override
    public void update(ArrayList<GameObject> gameObject) {
        // For Player 1
        if (this.id == GameObjectID.PlayerOne) {
            xSpeed = 0;
            ySpeed = 0;

            // Movement controls
            if (KeyListener.right) {
                setXSpeed(movementSpeed);
                moveX(xSpeed);
                direction = "Right";
                moveRight = true;
            } else
                moveRight = false;
            if (KeyListener.left) {
                setXSpeed(-movementSpeed);
                moveX(xSpeed);
                direction = "Left";
                moveLeft = true;
            } else
                moveLeft = false;
            if (KeyListener.up) {
                setYSpeed(-movementSpeed);
                moveY(ySpeed);
                direction = "Up";
            }
            if (KeyListener.down) {
                setYSpeed(movementSpeed);
                moveY(ySpeed);
                direction = "Down";
                moveDown = true;
            } else
                moveDown = false;

            // Push mechanic
            if (KeyListener.push && !pushCoolDown()) {
                switch (direction) {
                    case "Right":
                    case "Left":
                        pushingAnimH = true;
                        break;
                    case "Up":
                    case "Down":
                        pushingAnimV = true;
                        break;
                    default:
                        break;
                }
            } else {
                pushCoolDown();
            }

            // Slide mechanic when player is pushed
            if (slideX) {
                if (pushSpeed > 0) {
                    x += pushSpeed;
                    pushSpeed -= 0.5;
                } else if (pushSpeed < 0) {
                    x += pushSpeed;
                    pushSpeed += 0.5;
                }
                if (pushSpeed == 0) {
                    slideX = false;
                }
            }

            if (slideY) {
                if (pushSpeed > 0) {
                    y += pushSpeed;
                    pushSpeed -= 0.5;
                } else if (pushSpeed < 0) {
                    y += pushSpeed;
                    pushSpeed += 0.5;
                }
                if (pushSpeed == 0) {
                    slideY = false;
                }
            }

            for (int i = 0; i < gameObject.size(); i++) {
                GameObject tempObject = gameObject.get(i);
                switch (tempObject.getID()) {
                    case PlayerTwo:
                        // Collision
                        HorizontalCollision(tempObject);
                        VerticalCollision(tempObject);

                        // Push Mechanic
                        if (((Player) tempObject).getHRange().intersects(getHBounds())) {
                            HPushable(tempObject);
                        }
                        if (((Player) tempObject).getVRange().intersects(getVBounds())) {
                            VPushable(tempObject);
                        }

                        break;
                    default:
                        break;
                }
            }
        }

        // For sprite animations
        spriteCounter++;
        if (spriteCounter > 5) {

            switch (spriteNum) {
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
                    spriteNum = 0;
                    break;
                default:
                    break;
            }
            spriteCounter = 0;
        }

        // For sprite blink animation
        blinkCoolDown++;
        if (blinkCoolDown > 300 && spriteNum == 0) {
            blink = true;
            blinkCoolDown = 0;
        }
    }

    /**
     * Overrides the draw method of GameObject.
     * @param g2d {@code Graphics2D} Graphics2D object
     */
    @Override
    public void draw(Graphics2D g2d) {

        BufferedImage image = null;
        // Idle Animation
        switch (direction) {
            // Player sprite when it moves to the right
            case "Right":
                if (blink && !moveRight && !pushingAnimH) {
                    image = idleR[spriteNum];
                    if (spriteNum == 3)
                        blink = false;
                } else if (moveRight && !pushingAnimH) { // Player is moving to the right but not pushing
                    image = moveR[spriteNum];
                } else if (pushingAnimH) { // Player is moving to the right and pushing
                    if (spriteNum != 0 && compAnim == false) {
                        spriteNum = 0;
                        compAnim = true;
                    }
                    image = pushR[spriteNum];
                    if (spriteNum == 3) {
                        pushingAnimH = false;
                        activePush = true;
                    }
                } else
                    image = idleR[0]; // Player is idle
                break;

            // Player sprite when it moves to the left
            case "Left":
                if (blink && !moveLeft) {
                    image = idleL[spriteNum];
                    if (spriteNum == 3)
                        blink = false;
                } else if (moveLeft && !pushingAnimH) { // Player is moving to the left but not pushing
                    image = moveL[spriteNum];
                } else if (pushingAnimH) { // Player is moving to the right and pushing
                    if (spriteNum != 0 && compAnim == false) {
                        spriteNum = 0;
                        compAnim = true;
                    }
                    image = pushL[spriteNum];
                    if (spriteNum == 3) {
                        pushingAnimH = false;
                        activePush = true;
                    }
                } else
                    image = idleL[0]; // Player is idle
                break;

            // Player sprite when it moves downwards
            case "Down":
                if (blink && !moveDown) {
                    image = idleD[spriteNum];
                    if (spriteNum == 3)
                        blink = false;
                } else if (moveDown && !pushingAnimV) { // Player is moving downwards but not pushing
                    image = moveD[spriteNum];
                } else if (pushingAnimV) { // Player is moving downwards and pushing
                    if (spriteNum != 0 && compAnim == false) {
                        spriteNum = 0;
                        compAnim = true;
                    }
                    image = pushD[spriteNum];
                    if (spriteNum == 3) {
                        pushingAnimV = false;
                        activePush = true;
                    }
                } else
                    image = idleD[0]; // Player is idle
                break;

            // Player sprite when it moves upwards
            case "Up":
                if (pushingAnimV) { // Player is moving upwards and pushing
                    if (spriteNum != 0 && compAnim == false) {
                        spriteNum = 0;
                        compAnim = true;
                    }
                    image = pushU[spriteNum];
                    if (spriteNum == 3) {
                        pushingAnimV = false;
                        activePush = true;
                    }
                } else
                    image = idleU[0]; // Player is idle
            default:
                break;
        }

        if (!pushingAnimH) // Player sprite if it is not pushing
            g2d.drawImage(image, (int) x - 15, (int) y - 15, (int) width + 30, (int) height + 30, null);
        else
            g2d.drawImage(image, (int) x - 65, (int) y - 15, (int) width + 130, (int) height + 30, null);
    }

    /**
     * Adjusts the horizontal position when there's horizontal collision
     * @param p2 {@code GameObject} GameObject of Opposing Player
     */
    public void HorizontalCollision(GameObject p2) {
        if (getHBounds().intersects(p2.getHBounds())) {
            if (p2.getXSpeed() < 0) {
                x = p2.getX() - p2.getWidth();
            } else if (p2.getXSpeed() > 0) {
                x = p2.getX() + p2.getWidth();
            }
            if (xSpeed > 0) {
                x = p2.getX() - p2.getWidth() + xSpeed;
            } else if (xSpeed < 0) {
                x = p2.getX() + p2.getWidth() + xSpeed;
            }
        }
    }

    /**
     * Adjusts the vertical position when there's vertical collision
     * @param p2 {@code GameObject} GameObject of Opposing Player
     */
    public void VerticalCollision(GameObject p2) {
        if (getVBounds().intersects(p2.getVBounds())) {
            if (p2.getYSpeed() < 0) {
                y = p2.getY() - p2.getHeight();
            } else if (p2.getYSpeed() > 0) {
                y = p2.getY() + p2.getHeight();
            }
            if (ySpeed > 0) {
                y = p2.getY() - p2.getHeight() + ySpeed;
            } else if (ySpeed < 0) {
                y = p2.getY() + p2.getHeight() + ySpeed;
            }
        }
    }

    /**
     * Handles horizontal push mechanic
     * @param p2 {@code GameObject} GameObject of Opposing Player
     */
    public void HPushable(GameObject p2) {
        if (((Player) p2).getPush()) {
            switch (((Player) p2).getDir()) {
                case "Right":
                    if (this.x > ((Player) p2).getX()) {
                        System.out.println("PUSHED RIGHT");
                        pushSpeed = 20;
                        slideX = true;
                    }
                    break;
                case "Left":
                    if (this.x < ((Player) p2).getX()) {
                        System.out.println("PUSHED LEFT");
                        pushSpeed = -20;
                        slideX = true;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles vertical push mechanic
     * @param p2 {@code GameObject} GameObject of Opposing Player
     */
    public void VPushable(GameObject p2) {
        if (((Player) p2).getPush()) {
            switch (((Player) p2).getDir()) {
                case "Down":
                    if (((Player) p2).getY() < this.y) {
                        System.out.println("Down Range");
                        pushSpeed = 20;
                        slideY = true;
                    }
                    break;
                case "Up":
                    if (((Player) p2).getY() > this.y) {
                        System.out.println("Down Range");
                        pushSpeed = -20;
                        slideY = true;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles the position when the player gets respawned
     * @param n {@code int} Player number
     */
    public void setSpawnProps(int n) {
        if (n == 1) {
            spawnProps = new int[] { 248, 282, 1 };
        } else {
            spawnProps = new int[] { 856, 282, 0 };
        }
    }

    /**
     * Sets the current push speed of the player
     * @param s {@code int} Player's push speed
     */
    public void setPushSpeed(int s) {
        this.pushSpeed = s;
    }

    /**
     * Sets the direction of movement of the player
     * @param dir {@code String} Player's direction of movement
     */

    public void setDir(String dir) {
        this.direction = dir;
    }

    /**
     * Sets the push boolean
     * @param push {@code boolean} If the player is pushing or not
     */
    public void setPush(boolean push) {
        this.activePush = push;
    }

    /**
     * Gets the Player's direction of movement
     * @return direction
     */
    public String getDir() {
        return direction;
    }

    /**
     * Gets the Player activePush
     * @return activePush
     */
    public boolean getPush() {
        return activePush;
    }

    /**
     * Gets the horizontal pushing range of the player
     * @return Rectangle of horizontal pushing range
     */
    public Rectangle getHRange() {
        double vx = this.x - this.width;
        double vy = this.y + 4;
        double vw = this.width * 3;
        double vh = this.height - 8;

        return new Rectangle((int) vx, (int) vy, (int) vw, (int) vh);
    }

    /**
     * Gets the vertical pushing range of the player
     * @return Rectangle of vertical pushing range
     */
    public Rectangle getVRange() {
        double vx = this.x + 4;
        double vy = this.y - this.height;
        double vw = this.width - 8;
        double vh = this.height * 3;

        return new Rectangle((int) vx, (int) vy, (int) vw, (int) vh);
    }

    /**
     * Gets the push cool down of the player
     * @return Push cooldown
     */
    private boolean pushCoolDown() {
        if (activePush) {
            coolDown = true;
            if (coolDownCounter <= 120) {
                activePush = false;
            }
        }
        if (coolDown) {
            coolDownCounter--;
        }
        if (coolDownCounter <= 0) {
            coolDownCounter = 130; // 2 seconds for 60 UPS + 10 Frames for lag allowance
            coolDown = false;
        }
        return coolDown;
    }

    /**
     * Returns the current push cooldown
     * @return Current cooldown Value
     */
    public boolean getCoolDown() {
        return this.coolDown;
    }

    /**
     * Returns the playerID
     * @return Current playerID
     */
    public int getPlayerID() {
        return this.playerID;
    }

    /**
     * Returns an ArrayList of integers
     * @return Current ArrayList of spawnProps integers
     */
    public int[] getSpawnProps() {
        return this.spawnProps;
    }

    /////////////
    //ANIMATION//
    /////////////

    public void getPlayerImages() {
        try {
            idleR = new BufferedImage[4];
            idleR[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleR1.png"));
            idleR[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleR2.png"));
            idleR[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleR3.png"));
            idleR[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleR4.png"));

            idleL = new BufferedImage[4];
            idleL[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleL1.png"));
            idleL[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleL2.png"));
            idleL[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleL3.png"));
            idleL[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleL4.png"));

            idleD = new BufferedImage[4];
            idleD[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleD1.png"));
            idleD[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleD2.png"));
            idleD[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleD3.png"));
            idleD[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleD4.png"));

            idleU = new BufferedImage[1];
            idleU[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/idle/playerOneidleU.png"));

            moveR = new BufferedImage[4];
            moveR[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveR1.png"));
            moveR[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveR2.png"));
            moveR[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveR3.png"));
            moveR[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveR4.png"));

            moveL = new BufferedImage[4];
            moveL[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveL1.png"));
            moveL[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveL2.png"));
            moveL[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveL3.png"));
            moveL[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveL4.png"));

            moveD = new BufferedImage[4];
            moveD[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveD1.png"));
            moveD[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveD2.png"));
            moveD[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveD3.png"));
            moveD[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/moving/playerOneMoveD4.png"));

            pushR = new BufferedImage[4];
            pushR[0] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushR1.png"));
            pushR[1] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushR2.png"));
            pushR[2] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushR3.png"));
            pushR[3] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushR4.png"));

            pushL = new BufferedImage[4];
            pushL[0] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushL1.png"));
            pushL[1] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushL2.png"));
            pushL[2] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushL3.png"));
            pushL[3] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushL4.png"));

            pushD = new BufferedImage[4];
            pushD[0] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushD1.png"));
            pushD[1] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushD2.png"));
            pushD[2] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushD3.png"));
            pushD[3] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushD4.png"));

            pushU = new BufferedImage[4];
            pushU[0] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushU1.png"));
            pushU[1] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushU2.png"));
            pushU[2] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushU3.png"));
            pushU[3] = ImageIO
                    .read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushU4.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
