package framework;

import java.awt.event.*;

public class KeyListener extends KeyAdapter {
    public boolean right = false, left = false, up = false, down = false;


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
        if(key == KeyEvent.VK_D) {
            right = true; // Move right
        }
        if(key == KeyEvent.VK_A) {
            left = true; // Move left
        }
        if(key == KeyEvent.VK_W) {
            up = true; // Move Up
        }
        if(key == KeyEvent.VK_S) {
            down = true; // Move down
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_D) { 
            right = false;
        }
        if(key == KeyEvent.VK_A) { 
            left = false;
        }
        if(key == KeyEvent.VK_W) { 
            up = false;
        }
        if(key == KeyEvent.VK_S) { 
            down = false;
        }
    }
}
