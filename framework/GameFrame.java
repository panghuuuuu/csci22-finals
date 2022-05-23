/**
    A GameFrame class that extends the Canvas class and Implements
    the Runnable interface to instantialize a working JFrame display
    and a Thread that handles the animation and non-animation updates
    of the game. It also handles the Local connection to the Server and
    Reading and writing of variables within said server.
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
import java.io.*;
import java.net.*;
import gameobjects.*;
import javax.imageio.*;
import javax.sound.sampled.*;
import java.util.*;

public class GameFrame extends Canvas implements Runnable {

    // Declaration of Variables
    private JFrame gameFrame;
    public final static int SCREEN_WIDTH = 1080, SCREEN_HEIGHT = 720;
    private final double UPDATE_RATE = 1.0d / 60.0d;
    private int FPS, UPS;
    private long nextTime;
    private Clip clip = null;
    private boolean running;
    private Boolean waitP1 = true;
    private Boolean waitP2 = true;
    private Boolean reset = true;
    private Boolean music1 = false, music2 = false, music3 = false;
    private Thread thread;
    private GameCanvas GC;
    private Socket socket;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private int playerID;
    private BufferedImage[] image;

    /**
     * Creates a New instance of a GameFrame
     * creating a new JFrame, GameCanvas and getting the backgrounds
     * for the Menu System.
     */
    public GameFrame() {
        gameFrame = new JFrame();
        GC = new GameCanvas();
        getBackgrounds();
    }

    /** Setups the Frame by adding JComponents and Listeners */
    public void setUpGUI() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("Player " + playerID);
        gameFrame.setVisible(true);
        this.addKeyListener(new KeyListener());
        this.addMouseListener(new MouseEventListener());
        gameFrame.add(this);
        gameFrame.pack();
        gameFrame.setFocusable(true);
        GC.constructObjects(playerID);
    }

    /** Starts a new {@code Thread} thread */
    public synchronized void start() {
        if (running)
            return;

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

        while (running) {
            current = System.currentTimeMillis();
            double lastRenderedTime = (current - lastUpdate) / 1000d;
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
        musicHandler();
        UPS++;

        if (MouseEventListener.mode == 0) {
            reset = true;
            GC.gameStart(false);
        }

        if (playerID == 1) {
            waitP1 = GC.getP1Wait();
        } else {
            waitP2 = GC.getP2Wait();
        }

    
        if (waitP1 == false && waitP2 == false) {
            MouseEventListener.mode = 2;
            GC.gameStart(true);
        }
        
        if (reset == false && KeyListener.reset == true) {
            MouseEventListener.mode = 0;
            GC.reset();
            GC.respawn();
        }
    }

    /** Calls for animation updates for every instance of GameObject */
    public void draw() {
        FPS++;

        //Creates a Buffer Strategy to buffer Frames per Second.
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        //Creates the Backgrounds
        BufferedImage bg = image[MouseEventListener.mode];
        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        g2d.drawImage(bg, (int) 0, (int) 0, getWidth(), getHeight(), null);

        
        //if (MouseEventListener.mode == 0) {
        //    reset = true;
        //    GC.gameStart(false);
        //}

        //if (playerID == 1) {
        //    waitP1 = GC.getP1Wait();
        //} else {
        //    waitP2 = GC.getP2Wait();
        //}

    
        //if (waitP1 == false && waitP2 == false) {
        //    MouseEventListener.mode = 2;
        //    GC.gameStart(true);
        //}

        //Handles the Game Over Screens
        if (GC.getGameEnd() == true) {
            BufferedImage win = null;
            BufferedImage lose = null;

            try {
                win = image[3];
                lose = image[4];
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            if (GC.getWinnerPlayer() == playerID && playerID == 1 || playerID == 2 && GC.getWinnerPlayer() != playerID)  
                g2d.drawImage(win, (int) 0, (int) 0, getWidth(), getHeight(), null); //Win Screen
            else  
                g2d.drawImage(lose, (int) 0, (int) 0, getWidth(), getHeight(), null); //Lose Screen
            
            reset = false;
            GC.gameStart(false);
        }

        //Draws GameCanvas Objects
        GC.draw(g2d);
        g2d.dispose(); // Disposes the Graphics
        bs.show();
    }

    /** Gets the Statistics for the current UPS and FPS of the program */
    public void stats() {
        if (System.currentTimeMillis() > nextTime) {
            System.out.println(String.format("FPS: %d, UPS: %d", FPS, UPS));
            FPS = 0;
            UPS = 0;
            nextTime = System.currentTimeMillis() + 1000;
        }
    }

    /** Gets all the images for the Game's Backgrounds */
    public void getBackgrounds () {
        try {
            image = new BufferedImage[5];
            image[0] = ImageIO.read(getClass().getResourceAsStream("/res/landscape/start.png"));
            image[1] = ImageIO.read(getClass().getResourceAsStream("/res/landscape/loading.png"));
            image[2] = ImageIO.read(getClass().getResourceAsStream("/res/landscape/sample.png"));
            image[3] = ImageIO.read(getClass().getResourceAsStream("/res/win-lose/win.png"));
            image[4] = ImageIO.read(getClass().getResourceAsStream("/res/win-lose/lose.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Plays Music from a specified file path
     *  @param filepath {@code String} file path of a .wav file
     */
    public void playMusic(String filepath) {
        try {
            File musicPath = new File(filepath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("No File Found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Handles the Background music of the game */
    public void musicHandler() {
        if(MouseEventListener.mode == 0) {
            if(music1) return;
            if(music3 || music2) clip.close();
            playMusic("res/music/pvzMainMenu.wav");
            music1 = true;
            music2 = music3 = false;
        } else if (MouseEventListener.mode == 2 && !GC.getGameEnd()){
            if(music2) return;
            clip.close();
            playMusic("res/music/pvzStage.wav");
            music2 = true;
            music1 = music3 = false;
        } else if (GC.getGameEnd()) {
            if(music3) return;
            clip.close();
            playMusic("res/music/pvzGameOver.wav");
            music3 = true;
            music1 = music2 = false;
        }
    }

    //////////////////
    //SERVER METHODS//
    //////////////////

    /** Connects to a local or online Server  */
    public void connectToServer() {
        try {
            Scanner console = new Scanner(System.in);
            System.out.print("IP Address: ");
            String ipAddress = console.nextLine();
            System.out.print("Port number: ");
            int portNumber = Integer.parseInt(console.nextLine());
            socket = new Socket(ipAddress, portNumber);
            console.close();
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
            playerID = dataIn.readInt();
            System.out.println("You are Player #" + playerID);
            if (playerID == 1) {
                System.out.println("Waiting for Player 2...");
            }
            rfsRunnable = new ReadFromServer(dataIn);
            wtsRunnable = new WriteToServer(dataOut);
            rfsRunnable.waitForStart();
        } catch (IOException ex) {
            System.out.println("IOException from connectToServer() method.");
        }
    }
    public void closeSocketOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
          try {
            socket.close();
          } catch (IOException e) {
            System.out.println("IOException from closeSocketOnShutdown() method.");
          }
        }));
    }

    /** Class that reads the values from the server and puts them into the values of the local Player 2 */
    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created.");
        }

        public void run() {
            try {
                while (true) {
                    GameObject P2 = GC.getP2();
                    if (P2 == null) {
                        P2 = GC.getP2();
                        //System.out.println("PLAYER TWO NOT INITIALIZED");
                    }
                    if (P2 != null) {
                        P2 = GC.getP2();
                        P2.setX(dataIn.readDouble());
                        P2.setY(dataIn.readDouble());
                        P2.setXSpeed(dataIn.readDouble());
                        P2.setYSpeed(dataIn.readDouble());
                        ((Player) P2).setPush(dataIn.readBoolean());
                        ((Player) P2).setDir(dataIn.readUTF());
                        if (playerID == 1) {
                            waitP2 = dataIn.readBoolean();
                        } else { 
                            waitP1 = dataIn.readBoolean();
                        }
                        GC.setLocalP2Points((Player) P2, dataIn.readInt());
                    }
                }
            } catch (IOException ex) {
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
            } catch (IOException ex) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }
    }

    /** Class that Writes the Values of Player 1 to the Server */
    private class WriteToServer implements Runnable {
        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created.");
        }

        public void run() {
            try {
                while (true) {
                    GameObject P1 = GC.getP1();
                    if (P1 == null) {
                        P1 = GC.getP1();
                        //System.out.println("PLAYER ONE NOT INITIALIZED");
                    }
                    if (P1 != null) {
                        P1 = GC.getP1();
                        dataOut.writeDouble(P1.getX());
                        dataOut.writeDouble(P1.getY());
                        dataOut.writeDouble(P1.getXSpeed());
                        dataOut.writeDouble(P1.getYSpeed());
                        dataOut.writeBoolean(((Player) P1).getPush());
                        dataOut.writeUTF(((Player) P1).getDir());
                        if (playerID == 1) {
                            dataOut.writeBoolean(waitP1);
                        } else { 
                           dataOut.writeBoolean(waitP2);
                        }
                        dataOut.writeInt(GC.getServerPoint((Player) P1));
                        dataOut.flush();
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException ex) {
                            System.out.println("InterruptedException from WTS run().");
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTS run()");
            }
        }
    }
}
