package framework;

import java.awt.event.*;

public class KeyListener extends KeyAdapter {
    public static boolean right = false, left = false, up = false, down = false, push = false, reset = false;
    private GameCanvas gCanvas;

    public KeyListener(GameCanvas gc) {
        gCanvas = gc;
        gc.setFocusable(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }
        if(key == KeyEvent.VK_RIGHT) {
            right = true; // Move right
        }
        if(key == KeyEvent.VK_LEFT) {
            left = true; // Move left
        }
        if(key == KeyEvent.VK_UP) {
            up = true; // Move Up
        }
        if(key == KeyEvent.VK_DOWN) {
            down = true; // Move down
        }
        if(key == KeyEvent.VK_SPACE) {
            push = true; // Push
        }
        if(key == KeyEvent.VK_ENTER) {
            reset = true; // Play again
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT) { 
            right = false;
        }
        if(key == KeyEvent.VK_LEFT) { 
            left = false;
        }
        if(key == KeyEvent.VK_UP) { 
            up = false;
        }
        if(key == KeyEvent.VK_DOWN) { 
            down = false;
        }
        if(key == KeyEvent.VK_SPACE) {
            push = false;
        }
        if(key == KeyEvent.VK_ENTER) {
            reset = false;
        }
    }
}
