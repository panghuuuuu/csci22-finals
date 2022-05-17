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
        if (450 <= e.getX() && e.getX() <= 700 && 390 <= e.getY() && e.getY() <= 540) {
            mode = 1;
        } 
        if (mode == 1) {
            if (100 <= e.getX() && e.getX() <= 350 && 195 <= e.getY() && e.getY() <= 445 ||
            450 <= e.getX() && e.getX() <= 700 && 195 <= e.getY() && e.getY() <= 445 ||
            775 <= e.getX() && e.getX() <= 1025 && 195 <= e.getY() && e.getY() <= 445) {
                mode = 2;
                if (100 <= e.getX() && e.getX() <= 350 && 195 <= e.getY() && e.getY() <= 445) {
                    landscape = 1;
                } else if (450 <= e.getX() && e.getX() <= 700 && 195 <= e.getY() && e.getY() <= 445) {
                    landscape = 2;
                } else if (775 <= e.getX() && e.getX() <= 1025 && 195 <= e.getY() && e.getY() <= 445) {
                    landscape = 3;
                }
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
