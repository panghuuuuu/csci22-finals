/**
    This is a template for a Java file.
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

    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.connectToServer();
        gf.setUpGUI();
        gf.start();
    }

}