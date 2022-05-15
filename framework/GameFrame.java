/**
    A GameFrame class that extends the Canvas class and Implements
    the Runnable interface to instantialize a working JFrame display
    and a Thread that handles the animation and non-animation updates
    of the game.
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

package framework;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class GameFrame extends Canvas implements Runnable {
    
    //Declaration of Variables
    private JFrame gameFrame;
    public final static int SCREEN_WIDTH = 1080, SCREEN_HEIGHT = 720;
    private final double UPDATE_RATE = 1.0d/60.0d;
    private int FPS, UPS;
    private long nextTime;
    private boolean running;
    private Thread thread;
    private GameCanvas GC;
    private Timer timer;
    private boolean up, down, left, right, push;
    private Socket socket;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private int playerID;

    //Constructor method for GameFrame class
    public GameFrame() {
        gameFrame = new JFrame();
        GC = new GameCanvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        up = down = left = right = push = false;
    }

    /** Setups the Frame by adding JComponents and Listeners */
    public void setUpGUI() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("Player " + playerID);
        gameFrame.setVisible(true);  
        gameFrame.add(this);
        gameFrame.pack();
        GC.newPlayer(playerID);
        setUpAnimationTimer();
        setUpKeyListener();
    }

    public void setUpAnimationTimer() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                double speed = 5;
                if (up) {
                    GC.getP1().moveY(-speed); 
                } else if(down) {
                    GC.getP1().moveY(speed);
                } else if(left) {
                    GC.getP1().moveX(-speed);    
                } else if(right) {
                    GC.getP1().moveX(speed);
                }
                GC.repaint();    
            }
        };
        timer = new Timer(10, al);
        timer.start();
    }

    public void setUpKeyListener() {
        KeyListener kl = new KeyListener() {
            public void keyTyped(KeyEvent ke) {

            }

            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        up = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        down = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        left = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        right = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        push = true;
                        break;
                }
            }

            public void keyReleased(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        up = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        down = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        left = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        right = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        push = false;
                        break;
                }
            }
        };
        this.addKeyListener(kl);
        this.setFocusable(true);
    }

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 45371);
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
            playerID = dataIn.readInt();
            System.out.println("You are Player# " + playerID);
            if (playerID == 1) {
                System.out.println("Waiting for player 2...");
            }
            rfsRunnable = new ReadFromServer(dataIn);
            wtsRunnable = new WriteToServer(dataOut);
            rfsRunnable.waitForStart();
        } catch(IOException ex) {
            System.out.println("IOException from connectToServer() method.");
        }
    }

    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created.");
        }

        public void run() {
            try {
                while(true) {
                    if (GC.getP2() != null) {
                        GC.getP2().setX(dataIn.readDouble());
                        GC.getP2().setY(dataIn.readDouble()); 
                    }
                }
            } catch(IOException ex) {
                System.out.println("IOException from RFS run()");
            }
        }

        public void waitForStart() {
            try {
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: " + startMsg);
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            } catch(IOException ex) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }
    }

    private class WriteToServer implements Runnable {
        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created.");
        }

        public void run() {
            try {
                while(true) {
                    if (GC.getP1() != null) {
                        dataOut.writeDouble(GC.getP1().getX());
                        dataOut.writeDouble(GC.getP1().getY());
                        dataOut.flush();    
                        try {
                            Thread.sleep(25);
                        } catch(InterruptedException ex) {
                            System.out.println("InterruptedException from WTS run().");
                        }
                    }
                }
            } catch(IOException ex) {
                    System.out.println("IOException from WTS run()");
                }        
        }
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
        //GC.update();
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
        //GC.draw(g2d);
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
