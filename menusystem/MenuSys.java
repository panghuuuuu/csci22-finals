/**
    A GameCanvas class that handles all the game's animation
    and non-animation updates by constantly calling their
    draw and update methods.
    @author Angelo Joaquin B. Alvarez (210295)
    @author Ysabella B. Panghulan (214521)
    @version May 14, 2022
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

package menusystem;

import javax.swing.*;
import framework.*;
import java.awt.*;
import java.util.ArrayList;
import gameobjects.*;

public class MenuSys extends JComponent{
    private double scoreP1 = 0;
    private double scoreP2 = 0;
    private int playerID;
    private int menuMode1, menuMode2;

    public MenuSys(int w, int h) {

    }

    /** Iterates through every gameObject and calls its update method */
    public void update() {

    }

    /** Iterates through every gameObject and calls its draw method */
    public void draw(Graphics2D g2d) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        if (MouseEventListener.mode == 0) {
            menuMode1 = 0;
            if(menuMode2 > 0){
                System.out.println("OTHER PLAYER PROCEEDED");
            }
            g2d.setColor(new Color(255, 0, 0));
            g2d.fillRect(450, 390, 250, 150);
        } else if (MouseEventListener.mode == 1) {
            menuMode1 = 1;
            MouseEventListener.mode = 2;
        }
        if (MouseEventListener.mode == 2) {
            menuMode1 = 2;
            g2d.setColor(new Color(0, 255, 0));
            g2d.fillRect(100, 195, 250, 250);
            g2d.setColor(new Color(255, 0, 0));
            g2d.fillRect(450, 195, 250, 250);
            g2d.setColor(new Color(0, 0, 255));
            g2d.fillRect(775, 195, 250, 250);
        }
 
        //if (MouseEventListener.mode == 3) {
        //    if (MouseEventListener.landscape == 1) {
        //        Landscape l1 = new Landscape(150, 150, 500, 500, GameObjectID.LandscapeOne);
        //        l1.draw(g2d);
        //        if (playerID == 1) {
        //            if (l1.isOut(getP1())) {
        //                scoreP2 += 0.001;
        //            }
        //            if (l1.isOut(getP2())) {
        //                scoreP1 += 0.001;
        //            }
        //        }
        //    } else if (MouseEventListener.landscape == 2) {
        //        Landscape l2 = new Landscape(150, 150, 500, 500, GameObjectID.LandscapeTwo);
        //        l2.draw(g2d);
        //        if (playerID == 1) {
        //            if (l2.isOut(getP1())) {
        //                scoreP2 += 0.001;
        //            }
        //            if (l2.isOut(getP2())) {
        //                scoreP1 += 0.001;
        //            }
        //        }
        //    } else if (MouseEventListener.landscape == 3) {
        //        Landscape l3 = new Landscape(150, 150, 500, 500, GameObjectID.LandscapeThree);
        //        l3.draw(g2d);
        //        if (playerID == 1) {
        //            if (l3.isOut(getP1())) {
        //                scoreP2 += 0.001;
        //            }
        //            if (l3.isOut(getP2())) {
        //                scoreP1 += 0.001;
        //            }
        //        }
        //    }
        //    }
        }

        public int getP1MenuMode() {
            return menuMode1;
        }
        public int getP2MenuMode() {
            return menuMode2;
        }
        public void setP2MenuMode(int mm2) {
            this.menuMode2 =  mm2;
        }

}
