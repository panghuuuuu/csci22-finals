/**
    This is a GameStarter program that contains the main method
    and instantializes the GameFrame class. It also calls its methods
    to setup the overall Game.
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

import framework.GameFrame;

public class GameStarter {

    /** The Main Program which Creates a New Instance of GameFrame */
    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.setFocusable(true);
        gf.closeSocketOnShutdown();
        gf.connectToServer();
        gf.setUpGUI();
        gf.start();
    }

}