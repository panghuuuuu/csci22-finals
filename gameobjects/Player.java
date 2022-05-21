package gameobjects;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

import javax.imageio.ImageIO;

import framework.*;

public class Player extends GameObject {
    private double movementSpeed;
    private boolean activePush = false, slideX = false, slideY = false, coolDown = false, blink = false,
        moveRight = false, moveLeft = false, moveDown = false, pushingAnimH = false, pushingAnimV = false, compAnim = false;
    private BufferedImage[] idleR, idleL, idleU, idleD, moveR, moveL, moveD, pushR, pushL, pushD, pushU;
    private int[] spawnProps;
    private int blinkCoolDown;
    private String direction = "Right";
    private int pushSpeed;
    private int spriteCounter, spriteNum;
    private int coolDownCounter = 125;
    private int playerID;

    public Player(double xPos, double yPos, double w, double h, GameObjectID objectID, int pid) {
        super(xPos, yPos, w, h, objectID);
        movementSpeed = 2;
        pushSpeed = 10;
        playerID = pid;
        getPlayerImages();
        setSpawnProps(pid);
        if (pid == 1) direction = "Right"; else direction = "Left";
    }

    @Override
    public void update(ArrayList<GameObject> gameObject) {
        if (this.id == GameObjectID.PlayerOne) {
            xSpeed = 0;
            ySpeed = 0;
            
            if (KeyListener.right) {
                setXSpeed(movementSpeed);
                moveX(xSpeed);
                direction = "Right";
                moveRight = true;
            } else moveRight = false;
            if (KeyListener.left) {
                setXSpeed(-movementSpeed); 
                moveX(xSpeed);
                direction = "Left";
                moveLeft = true;
            } else moveLeft = false;
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
            } else moveDown = false;
            if (KeyListener.push && !pushCoolDown()) {
                //activePush = true;
                switch(direction) {
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


            if (slideX) {
               if(pushSpeed > 0) {
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
                if(pushSpeed > 0) {
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
            switch(tempObject.getID()) {
                case PlayerTwo:
                    //Collision
                    HorizontalCollision(tempObject);
                    VerticalCollision(tempObject);

                    //Push Mechanic
                    if(((Player) tempObject).getHRange().intersects(getHBounds())) {
                        HPushable(tempObject);
                    }
                    if(((Player) tempObject).getVRange().intersects(getVBounds())) {
                        VPushable(tempObject);
                    }

                    break;
                default:
                    break;
                }
            } 
        }

        spriteCounter++;
        if (spriteCounter > 5) {

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
                    spriteNum = 0;
                    break;
                default:
                    break;
            }
            spriteCounter = 0;
        }

        blinkCoolDown++;
        if (blinkCoolDown > 300 && spriteNum == 0) {
            blink = true;
            blinkCoolDown = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {

        BufferedImage image = null;
        //Idle Animation
        switch(direction) {
            case "Right":
                if (blink && !moveRight && !pushingAnimH) {
                    image = idleR[spriteNum];
                    if(spriteNum == 3) blink = false;
                } else if (moveRight && !pushingAnimH) {
                    image = moveR[spriteNum];
                } else if (pushingAnimH) {
                    if(spriteNum != 0 && compAnim == false) {spriteNum = 0; compAnim = true;}
                    image = pushR[spriteNum];
                    if(spriteNum == 3) {pushingAnimH = false;  activePush = true;}
                } else image = idleR[0];
                break;
            case "Left":
                if (blink && !moveLeft) {
                    image = idleL[spriteNum];
                    if(spriteNum == 3) blink = false;
                } else if (moveLeft  && !pushingAnimH) {
                    image = moveL[spriteNum];
                } else if (pushingAnimH) {
                    if(spriteNum != 0 && compAnim == false) {spriteNum = 0; compAnim = true;}
                    image = pushL[spriteNum];
                    if(spriteNum == 3) {pushingAnimH = false;  activePush = true;}
                } else image = idleL[0];
                break;
            case "Down":
                if (blink && !moveDown) {
                    image = idleD[spriteNum];
                    if(spriteNum == 3) blink = false;
                } else if (moveDown  && !pushingAnimV) {
                    image = moveD[spriteNum];
                } else if (pushingAnimV) {
                    if(spriteNum != 0 && compAnim == false) {spriteNum = 0; compAnim = true;}
                    image = pushD[spriteNum];
                    if(spriteNum == 3) {pushingAnimV = false;  activePush = true;}
                } else image = idleD[0];
                break;
            case "Up":
                if (pushingAnimV) {
                    if(spriteNum != 0 && compAnim == false) {spriteNum = 0; compAnim = true;}
                    image = pushU[spriteNum];
                    if(spriteNum == 3) {pushingAnimV = false; activePush = true;}
                } else 
                    image = idleU[0];
            default:
                break;
        }
        //g2d.setColor(Color.BLACK);
        //g2d.fill(getVRange());
        //g2d.setColor(Color.BLUE);
        //g2d.fill(getHRange());
        if(!pushingAnimH)
            g2d.drawImage(image, (int) x-15, (int) y-15, (int) width+30, (int) height+30, null);
        else
            g2d.drawImage(image, (int) x-65, (int) y-15, (int) width+130, (int) height+30, null);

        //if(this.playerID == 1) g2d.setColor(new Color(255,0,0)); else g2d.setColor(new Color(0,0,255));
        //g2d.fillRect((int) x,(int) y, (int) width, (int) height); // Creates a Rectangle

    }

    public boolean getPush() {
        return activePush;
    }
    public void setPush(boolean push) {
        this.activePush = push;
    }

    public String getDir() {
        return direction;
    }
    public void setDir(String dir) {
        this.direction = dir;
    }

    //Pushing Range
    public Rectangle getHRange() {
        double vx = this.x - this.width;
        double vy = this.y + 4;
        double vw = this.width*3;
        double vh = this.height - 8;

        return new Rectangle((int) vx, (int) vy, (int) vw, (int) vh);
    }

    public Rectangle getVRange() {
        double vx = this.x + 4;
        double vy = this.y - this.height;
        double vw = this.width - 8;
        double vh = this.height*3;

        return new Rectangle((int) vx, (int) vy, (int) vw, (int) vh);
    }

    //Collision
    public void HorizontalCollision(GameObject p2) {
        if(getHBounds().intersects(p2.getHBounds())) { 
            if(p2.getXSpeed() < 0) {
                x = p2.getX() - p2.getWidth();
            } else if (p2.getXSpeed() > 0) {
                x = p2.getX() + p2.getWidth();
            }
            if(xSpeed > 0) {
                x = p2.getX() - p2.getWidth() + xSpeed;
            } else if (xSpeed < 0) {
                x = p2.getX() + p2.getWidth() + xSpeed;
            }
        }
    }
    public void VerticalCollision(GameObject p2) {
        if(getVBounds().intersects(p2.getVBounds())) {
            if(p2.getYSpeed() < 0) {
                y = p2.getY() - p2.getHeight();
            } else if (p2.getYSpeed() > 0) {
                y = p2.getY() + p2.getHeight();
            }                    
            if(ySpeed > 0) {
               y = p2.getY() - p2.getHeight() + ySpeed;
            } else if (ySpeed < 0) {
               y = p2.getY() + p2.getHeight() + ySpeed;
            }
        }
    }

    //Push Mechanic
    public void HPushable(GameObject p2) {
            if(((Player) p2).getPush()) {
                switch(((Player) p2).getDir()) {
                    case "Right":
                        if(this.x > ((Player) p2).getX()) {
                            System.out.println("PUSHED RIGHT");
                            pushSpeed = 20;
                            slideX = true;
                        }
                        break;
                    case "Left":
                        if(this.x < ((Player) p2).getX()) {
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
    public void VPushable(GameObject p2) {
            if(((Player) p2).getPush()) {
                switch(((Player) p2).getDir()) {
                    case "Down":
                        if(((Player) p2).getY() < this.y) {
                            System.out.println("Down Range");
                            pushSpeed = 20;
                            slideY = true;
                        }
                        break;
                    case "Up":
                        if(((Player) p2).getY() > this. y) {
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
            coolDownCounter = 125; //2 seconds for 60 UPS
            coolDown = false;
        }
        return coolDown;
    }

    public boolean getCoolDown() {
        return this.coolDown;
    }
    public int getPlayerID() {
        return this.playerID;
    }

    public void setSpawnProps(int n) {
        if (n == 1) {
            spawnProps = new int[] {248, 282, 1};
        } else {
            spawnProps = new int[] {856, 282, 0};
        }
    }
    public int[] getSpawnProps() {
        return this.spawnProps;
    }
    public void setPushSpeed(int s) {
        this.pushSpeed = s;
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
                pushR[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushR1.png"));
                pushR[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushR2.png"));
                pushR[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushR3.png"));
                pushR[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushR4.png"));

                pushL = new BufferedImage[4];
                pushL[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushL1.png"));
                pushL[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushL2.png"));
                pushL[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushL3.png"));
                pushL[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushL4.png"));

                pushD = new BufferedImage[4];
                pushD[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushD1.png"));
                pushD[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushD2.png"));
                pushD[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushD3.png"));
                pushD[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushD4.png"));

                pushU = new BufferedImage[4];
                pushU[0] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushU1.png"));
                pushU[1] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushU2.png"));
                pushU[2] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushU3.png"));
                pushU[3] = ImageIO.read(getClass().getResourceAsStream("/res/playerOne-sprite/pushing/playerOnePushU4.png"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
