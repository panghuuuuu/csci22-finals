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
import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
    private ServerSocket ss;
    // (optional) private ArrayList<Socket> sockets;
    private int numPlayer;
    private int maxPlayers;

    private Socket p1Socket;
    private Socket p2Socket;
    private ReadFromClient p1ReadRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private WriteToClient p2WriteRunnable;

    private double p1x, p1y, p2x, p2y;

    public GameServer() {
        System.out.println("=== Game Server ===");
        numPlayer = 0;
        maxPlayers = 2;
        p1x = 10; 
        p1y = 250; 
        p2x =  300;
        p2y = 500;
        try {
            ss = new ServerSocket(45371);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer constructor.");
        }
    }

    public void waitForConnections() {
        try {
            System.out.println("NOW ACCEPTING CONNECTIONS...");
            while (numPlayer < maxPlayers) {
                Socket sock = ss.accept();
                DataInputStream dataIn = new DataInputStream(sock.getInputStream());
                DataOutputStream dataOut = new DataOutputStream(sock.getOutputStream());
                
                numPlayer++;
                dataOut.writeInt(numPlayer);
                System.out.println("Player #" + numPlayer + " has connected.");

                ReadFromClient rfc = new ReadFromClient(numPlayer, dataIn);
                WriteToClient wtc = new WriteToClient(numPlayer, dataOut);

                if (numPlayer == 1) {
                    p1Socket = sock;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = sock;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;
                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();
                    Thread readThreadP1 = new Thread(p1ReadRunnable);
                    Thread readThreadP2 = new Thread(p2ReadRunnable);
                    readThreadP1.start();
                    readThreadP2.start();
                    Thread writeThreadP1 = new Thread(p1WriteRunnable);
                    Thread writeThreadP2 = new Thread(p2WriteRunnable);
                    writeThreadP1.start();
                    writeThreadP2.start();
                }
            }
        } catch (IOException ex) {
            System.out.println("IOException from waitForConnections() method.");
        }
    }

    private class ReadFromClient implements Runnable {
        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;
            System.out.println("RFC" + playerID + " Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        p1x = dataIn.readDouble();
                        p1y = dataIn.readDouble();
                    } else {
                        p2x = dataIn.readDouble();
                        p2y = dataIn.readDouble();
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFC run()");
            }
        }
    }

    private class WriteToClient implements Runnable {
        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;
            System.out.println("WTC" + playerID + " Runnable created");
        }

        public void run() {
            try {
                while(true) {
                    if (playerID == 1) {
                        dataOut.writeDouble(p2x);
                        dataOut.writeDouble(p2y);
                        dataOut.flush();
                    } else {
                        dataOut.writeDouble(p1x);
                        dataOut.writeDouble(p1y);
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTC run()");
            }
        }

        public void sendStartMsg() {
            try {
                dataOut.writeUTF("We now have 2 players.");
            } catch(IOException ex) {
                System.out.println("IOException from sendStartMsg()");
            }
        }
    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.waitForConnections();
    }
}