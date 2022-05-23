package framework;

import java.awt.event.*;
import java.awt.*;

public class MouseEventListener extends MouseAdapter {
    private GameCanvas gCanvas;
    private Rectangle start;
    public static int mode = 0;
    public static int landscape = 0;

    public MouseEventListener(GameCanvas gc) {
        gCanvas = gc;
        gc.setFocusable(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (mode == 0) {
            start = new Rectangle(568, 494, 273, 128);
            if(start.contains(e.getPoint())) mode = 1;
        }   
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }
}