package framework;

import java.awt.event.*;

public class KeyListener extends KeyAdapter {
    public static boolean right = false, left = false, up = false, down = false;
    public GameCanvas gCanvas;

    public KeyListener(GameCanvas gc) {
        this.gCanvas = gc;
        gc.setFocusable(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.printf("up: %b down: %b, left: %b, right: %b%n", up, down, left, right);

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
        System.out.printf("up: %b down: %b, left: %b, right: %b%n", up, down, left, right);

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
