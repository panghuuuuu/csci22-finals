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
    private ArrayList<Socket> sockets;
    private int clientNum = 1;
    public GameServer() {
        sockets = new ArrayList<>();
        try {
            ss = new ServerSocket(60001);
        } catch(IOException ex) {
            System.out.println("IOException from GameServer constructor.");
        }
        System.out.println("THE CHAT SERVER HAS BEEN CREATED.");
    }
    // ensure that all sockets in ArrayList are closed when program exits
    // (either normally or some user interrupt)
    public void closeSocketsOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try {
            for(Socket skt : sockets) {
            skt.close();
            }
        } catch (IOException e) {
            System.out.println("IOException from closeSocketsOnShutdown() method.");
        }
        }));
    }
    public void waitForConnections() {
        try {
        System.out.println("NOW ACCEPTING CONNECTIONS...");
        // loop to allow mutliple clients to connect
        while(true) {
            Socket sock = ss.accept();
            sockets.add(sock); // add socket to ArrayList
            // create runnable because server needs a thread for each client
            ClientRunnable cr = new ClientRunnable(sock, clientNum);
            clientNum++; // Use this integer to keep track of each client.
                        // We're not really making use of this in this demo,
                        // but this can be useful to id each client if you
                        // want to add some logic that targets a specific client.
            cr.startThread();
        }
        } catch(IOException ex) {
            System.out.println("IOException from waitForConnections() method.");
        }
    }
    private class ClientRunnable implements Runnable {
        private Socket clientSocket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int cid;
        private String name;
        public ClientRunnable(Socket sck, int n) {
        clientSocket = sck;
        cid = n;
        try {
            dataIn = new DataInputStream(clientSocket.getInputStream());
            dataOut = new DataOutputStream(clientSocket.getOutputStream());
        } catch(IOException ex) {
            System.out.println("IOException from ChatServer constructor");
        }
        }
        public void startThread() {
        Thread t = new Thread(this);
        t.start();
        }
        public void run() {
        try {
            dataOut.writeUTF("HELLO! WELCOME TO THE CHAT ROOM! WHAT IS YOUR NAME?");
            name = dataIn.readUTF(); // read client's name
            System.out.println("[" + name + "]" + " HAS ENTERED THE ROOM...");
            // loop to continuously accept and print messages from the client
            while(true) {
            String messageFromClient = dataIn.readUTF();
            System.out.println("[" + name + "]: " + messageFromClient);
            if(messageFromClient.equals("exit")) {
                sockets.remove(clientSocket);
                clientSocket.close();
                System.out.println("[" + name + "] HAS LEFT THE CHAT... :(");
                break;
            }
            }
        } catch(IOException ex) {
            // Note that if client exits improperly (e.g. just closes the console),
            // then this will lead to an exception.
            // You can attempt to handle that somehow in here.
            // Or maybe just say "Oops. Something happened to [name]. RIP"
            System.out.println("IOException from ClientRunnable's run() method.");
        }
        }
    }
    /*public static void main(String[] args) {
        ChatServer cs = new ChatServer();
        cs.closeSocketsOnShutdown();
        cs.waitForConnections();
    }*/
}
