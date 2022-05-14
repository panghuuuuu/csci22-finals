/**
    A GameFrame class that extends the Canvas class and Implements
    the Runnable interface to instantialize a working JFrame display
    and a Thread that handles the animation and non-animation updates
    of the game.
    @author Angelo Joaquin B. Alvarez (210295)
    @author 
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

package framework;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;


public class GameFrame extends Canvas implements Runnable{
    
    //Declaration of Variables
    private JFrame gameFrame = new JFrame();
    public final static int SCREEN_WIDTH = 1080, SCREEN_HEIGHT = 720;
    private final double UPDATE_RATE = 1.0d/60.0d;
    private int FPS, UPS;
    private long nextTime;
    private boolean running;
    private Thread thread;
    private GameCanvas GC;
    
    //Constructor method for GameFrame class
    public GameFrame() {
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("Tentative Games");
        gameFrame.setVisible(true);  
    }

    /** Setups the Frame by adding JComponents and Listeners */
    public void SetupGUI() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        gameFrame.add(this);
        gameFrame.pack();
    }

    /** Starts a new {@code Thread} thread */
    public synchronized void start() {
        if(running) return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Overrides the run method of Runnable. Also called the Gameloop that
     * calls for updates per second [UPS] for non-animation updates and 
     * calls for different frames per second [FPS] for animation updates.
     */
    @Override
    public void run() {
        GC = new GameCanvas();
        double secs = 0;
        long current, lastUpdate = System.currentTimeMillis();
        nextTime = System.currentTimeMillis() + 1000;

        while(running) {
            current = System.currentTimeMillis();
            double lastRenderedTime = (current - lastUpdate)/1000d;
            secs += lastRenderedTime;
            lastUpdate = current;

            while (secs > UPDATE_RATE) {
                update();
                secs -= UPDATE_RATE;
            }
            draw();
            stats();
        }
        
    }

    /** Calls for non-animation updates for every instance of GameObject */
    public void update() {
        GC.update();
        UPS++;
    }
    
    /** Calls for animation updates for every instance of GameObject */
    public  void draw() {
        FPS++;
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        g2d.setColor(new Color(0,0,0));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        GC.draw(g2d);
        g2d.dispose();
        bs.show();
    }

    /** Gets the Statistics for the current UPS and FPS of the program */
    public void stats() {
        if(System.currentTimeMillis() > nextTime) {
            System.out.println(String.format("FPS: %d, UPS: %d", FPS, UPS));
            FPS = 0;
            UPS = 0;
            nextTime = System.currentTimeMillis() + 1000;
        }
    }
}
