/**
    A MouseListener class that extends the abstract class
    MouseAdapter to handle Mouseclicks during the Main
    Menu sequences of the Game.
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
import java.awt.*;

public class MouseEventListener extends MouseAdapter {
    private Rectangle start;
    public static int mode = 0;
    public static int landscape = 0;

    public MouseEventListener() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (mode == 0) {
            start = new Rectangle(568, 494, 273, 128); // Area of the Start Button
            if(start.contains(e.getPoint())) mode = 1; 
        }   
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }
}