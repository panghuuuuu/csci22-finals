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
        
        if (mode == 2) {
            if (100 <= e.getX() && e.getX() <= 350 && 195 <= e.getY() && e.getY() <= 445) {
                landscape = 1; 
                mode = 3;
            } else if (450 <= e.getX() && e.getX() <= 700 && 195 <= e.getY() && e.getY() <= 445) {
                landscape = 2;
                mode = 3;
            } else if (775 <= e.getX() && e.getX() <= 1025 && 195 <= e.getY() && e.getY() <= 445) {
                landscape = 3;
                mode = 3;
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
