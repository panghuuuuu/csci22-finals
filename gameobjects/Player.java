package gameobjects;

import java.awt.*;
import java.util.*;

import framework.*;

public class Player extends GameObject {
    private double movementSpeed;
    private boolean activePush = false, slideX = false, slideY = false;
    private String direction = "Right";
    private int pushSpeed;

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
            if (KeyListener.push) {
                activePush = true;
            } else {
                activePush = false;
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
                    if(getHBounds().intersects(tempObject.getHBounds())) { 
                        if(tempObject.getXSpeed() < 0) {
                            x = tempObject.getX() - tempObject.getWidth();
                        } else if (tempObject.getXSpeed() > 0) {
                            x = tempObject.getX() + tempObject.getWidth();
                        }
                        if(xSpeed > 0) {
                            x = tempObject.getX() - tempObject.getWidth() + xSpeed;
                        } else if (xSpeed < 0) {
                            x = tempObject.getX() + tempObject.getWidth() + xSpeed;
                        }
                    }

                    if(getVBounds().intersects(tempObject.getVBounds())) {
                        if(tempObject.getYSpeed() < 0) {
                            y = tempObject.getY() - tempObject.getHeight();
                        } else if (tempObject.getYSpeed() > 0) {
                            y = tempObject.getY() + tempObject.getHeight();
                        }                    
                        if(ySpeed > 0) {
                           y = tempObject.getY() - tempObject.getHeight() + ySpeed;
                        } else if (ySpeed < 0) {
                           y = tempObject.getY() + tempObject.getHeight() + ySpeed;
                        }
                    }

                    if(((Player) tempObject).getHRange().intersects(getHBounds())) {
                        if(((Player) tempObject).getPush()) {
                            System.out.println(((Player) tempObject).getDir());
                            switch(((Player) tempObject).getDir()) {
                                case "Right":
                                    pushSpeed = 10;
                                    slideX = true;
                                    break;
                                case "Left":
                                    pushSpeed = -10;
                                    slideX = true;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    if(((Player) tempObject).getVRange().intersects(getVBounds())) {
                        if(((Player) tempObject).getPush()) {
                            System.out.println(((Player) tempObject).getDir());
                            switch(((Player) tempObject).getDir()) {
                                case "Down":
                                    pushSpeed = 10;
                                    slideY = true;
                                    break;
                                case "Up":
                                    pushSpeed = -10;
                                    slideY = true;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
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
}
