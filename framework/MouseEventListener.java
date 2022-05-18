package framework;

import java.awt.event.*;

public class MouseEventListener extends MouseAdapter {
    private GameCanvas gCanvas;
    public static int mode = 0;
    public static int landscape = 0;

    public MouseEventListener(GameCanvas gc) {
        gCanvas = gc;
        gc.setFocusable(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (mode == 0) {
            if (450 <= e.getX() && e.getX() <= 700 && 390 <= e.getY() && e.getY() <= 540) {
                mode = 1;
            }    
        }   
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }
}