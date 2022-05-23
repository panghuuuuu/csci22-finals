/**
    A KeyListener Class that extends the abstract class
    KeyAdapter to handle the KeyBindings and KeyControls
    of the Game. Booleans are publicly available so that
    any class can access the key bindings.
    @author Angelo Joaquin B. Alvarez (210295)
    @author Ysabella B. Panghulan (214521)
    @version May 24, 2022
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


package framework;

import java.awt.event.*;

public class KeyListener extends KeyAdapter {
    public static boolean right = false, left = false, up = false, down = false, push = false, reset = false;

    /** Constructs a New KeyListener Instance */
    public KeyListener() {
    }

    @Override
    /** Invoked when a key has been pressed
     * @param e The event to be processed.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1); //Exit Prorgam
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
    /** Invoked when a key has been released
     * @param e The event to be processed.
     */
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
