package gameobjects;

import java.awt.*;
import java.util.*;

import framework.*;

public class Player extends GameObject {
    private double movementSpeed;
    private boolean activePush = false, slideX = false, slideY = false, coolDown = false;
    private String direction = "Right";
    private int pushSpeed;
    private int coolDownCounter = 0;

    public Player(double xPos, double yPos, double w, double h, GameObjectID objectID) {
        super(xPos, yPos, w, h, objectID);
        movementSpeed = 2;
        pushSpeed = 10;
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
            }
            if (KeyListener.left) {
                setXSpeed(-movementSpeed); 
                moveX(xSpeed);
                direction = "Left";
            }
            if (KeyListener.up) {
                setYSpeed(-movementSpeed);
                moveY(ySpeed);
                direction = "Up";
            }
            if (KeyListener.down) {
                setYSpeed(movementSpeed);
                moveY(ySpeed);
                direction = "Down";
            }
            if (KeyListener.push && !pushCoolDown()) {
                activePush = true;
            } else {
                activePush = false;
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
                    Pushable(tempObject);

                    break;
                default:
                    break;
                }
            } 
        }
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(new Color(255,0,0));
        g2d.fillRect((int) x,(int) y, (int) width, (int) height); // Creates a Rectangle
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
    public void Pushable(GameObject p2) {
        if(((Player) p2).getHRange().intersects(getHBounds())) {
            if(((Player) p2).getPush()) {
                System.out.println(((Player) p2).getDir());
                switch(((Player) p2).getDir()) {
                    case "Right":
                        pushSpeed = 20;
                        slideX = true;
                        break;
                    case "Left":
                        pushSpeed = -20;
                        slideX = true;
                        break;
                    default:
                        break;
                }
            }
        }

        if(((Player) p2).getVRange().intersects(getVBounds())) {
            if(((Player) p2).getPush()) {
                System.out.println(((Player) p2).getDir());
                switch(((Player) p2).getDir()) {
                    case "Down":
                        pushSpeed = 20;
                        slideY = true;
                        break;
                    case "Up":
                        pushSpeed = -20;
                        slideY = true;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private boolean pushCoolDown() {
        if (activePush) {
            coolDownCounter = 120; //3 seconds for 60 UPS
            coolDown = true;
        } 
        if (coolDown) {
            coolDownCounter--;
        }
        if (coolDownCounter <= 0) {
            coolDown = false;
        }
        return coolDown;
    }
}
