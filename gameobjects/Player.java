package gameobjects;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

import javax.imageio.ImageIO;

import framework.*;

public class Player extends GameObject {
    private double movementSpeed;
    private boolean activePush = false, slideX = false, slideY = false, coolDown = false, blink = false,
        moveRight = false, moveLeft = false, moveDown = false;
    private BufferedImage[] idleR, idleL, idleU, idleD, moveR, moveL, moveD;
    private int blinkCoolDown;
    private String direction = "Right";
    private int pushSpeed;
    private int spriteCounter, spriteNum;
    private int coolDownCounter = 0;

    public Player(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        super(xPos, yPos, w, h, objectID);
        movementSpeed = 2;
        pushSpeed = 10;
        getPlayerImages();
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
                activePush = true;
                System.out.println("PUSH");
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
                    HPushable(tempObject);
                    VPushable(tempObject);

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

        //g2d.setColor(new Color(255,0,0));
        //g2d.fillRect((int) x + 30,(int) y + 30, (int) width, (int) height); // Creates a Rectangle
        

        BufferedImage image = null;
        //Idle Animation
        switch(direction) {
            case "Right":
                if (blink && !moveRight) {
                    image = idleR[spriteNum];
                    if(spriteNum == 3) blink = false;
                } else if (moveRight) {
                    image = moveR[spriteNum];
                } else image = idleR[0];
                break;
            case "Left":
                if (blink && !moveLeft) {
                    image = idleL[spriteNum];
                    if(spriteNum == 3) blink = false;
                } else if (moveLeft) {
                    image = moveL[spriteNum];
                } else image = idleL[0];
                break;
            case "Down":
                if (blink && !moveDown) {
                    image = idleD[spriteNum];
                    if(spriteNum == 3) blink = false;
                } else if (moveDown) {
                    image = moveD[spriteNum];
                } else image = idleD[0];
                break;
            case "Up":
                image = idleU[0];
            default:
                break;
        }

        g2d.drawImage(image, (int) x, (int) y, (int) width+30, (int) height+30, null);
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
        if(((Player) p2).getHRange().intersects(getHBounds())) {
            if(((Player) p2).getPush()) {
                switch(((Player) p2).getDir()) {
                    case "Right":
                        if(this.x + this.width > ((Player) p2).getX()) {
                            pushSpeed = 20;
                            slideX = true;
                        }
                        break;
                    case "Left":
                        if(this.x < ((Player) p2).getX() + ((Player) p2).getWidth()) {
                            pushSpeed = -20;
                            slideX = true;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void VPushable(GameObject p2) {
        if(((Player) p2).getVRange().intersects(getVBounds())) {
            if(((Player) p2).getPush()) {
                switch(((Player) p2).getDir()) {
                    case "Down":
                        if(this.y > ((Player) p2).getY() + ((Player) p2).getWidth()) {
                            pushSpeed = 20;
                            slideY = true;
                        }
                        break;
                    case "Up":
                        if(this.y + this.height < ((Player) p2).getY()) {
                            pushSpeed = -20;
                            slideY = true;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private boolean pushCoolDown() {
        if (activePush) {
            coolDownCounter = 120; //2 seconds for 60 UPS
            coolDown = true;
            activePush = false;
        } 
        if (coolDown) {
            coolDownCounter--;
        }
        if (coolDownCounter <= 0) {
            coolDown = false;
        }
        return coolDown;
    }

    /////////////
    //ANIMATION//
    /////////////

    public void getPlayerImages() {
        try {
                idleR = new BufferedImage[4];
                idleR[0] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleR1.png"));
                idleR[1] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleR2.png"));
                idleR[2] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleR3.png"));
                idleR[3] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleR4.png"));

                idleL = new BufferedImage[4];
                idleL[0] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleL1.png"));
                idleL[1] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleL2.png"));
                idleL[2] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleL3.png"));
                idleL[3] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleL4.png"));

                idleD = new BufferedImage[4];
                idleD[0] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleD1.png"));
                idleD[1] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleD2.png"));
                idleD[2] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleD3.png"));
                idleD[3] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleD4.png"));

                idleU = new BufferedImage[1];
                idleU[0] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/idle/playerOneidleU.png"));

                moveR = new BufferedImage[4];
                moveR[0] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveR1.png"));
                moveR[1] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveR2.png"));
                moveR[2] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveR3.png"));
                moveR[3] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveR4.png"));

                moveL = new BufferedImage[4];
                moveL[0] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveL1.png"));
                moveL[1] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveL2.png"));
                moveL[2] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveL3.png"));
                moveL[3] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveL4.png"));

                moveD = new BufferedImage[4];
                moveD[0] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveD1.png"));
                moveD[1] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveD2.png"));
                moveD[2] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveD3.png"));
                moveD[3] = ImageIO.read(getClass().getResourceAsStream("/playerOne-sprite/moving/playerOneMoveD4.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
