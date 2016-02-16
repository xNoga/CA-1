package echoserver;

import Presentation.ChatGUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class EchoServer extends Thread{

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private Scanner input;
    private PrintWriter output;
    private String ip;
    private int port;
    HashMap <String, ClientHandler> clients = new HashMap<>();
    ArrayList<String> connectedClients = new ArrayList();

    public static void stopServer() {
        keepRunning = false;
    }

    private static void handleClient(Socket socket) throws IOException {
        Scanner input = new Scanner(socket.getInputStream());
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        String message = input.nextLine(); //IMPORTANT blocking call
        System.out.println(String.format("Received the message: %1$S ", message));
        while (!message.equals(ProtocolStrings.STOP)) {
            writer.println(message.toUpperCase());
            System.out.println(String.format("Received the message: %1$S ", message.toUpperCase()));
            message = input.nextLine(); //IMPORTANT blocking call
        }
        writer.println(ProtocolStrings.STOP);//Echo the stop message back to the client for a nice closedown
        socket.close();
        System.out.println("Closed a Connection");
        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Closed a Connection");
    }

    private void runServer(String ip, int port) {
        this.port = port;
        this.ip = ip;

        System.out.println("Sever started. Listening on: " + port + ", bound to: " + ip);
        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Server started. Listening on: " + port + ", bound to " + ip);
        
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            
            do {
                Socket socket = serverSocket.accept(); //Important Blocking call - venter på en client tilkobler
                System.out.println("Connected to a client");
                Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Connected to a client");
                //handleClient(socket);
                new ClientHandler(socket, this).start();
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
        }
    }
    
    public void removeUser(String username, ClientHandler handler){
        clients.remove(username, handler);
        
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet() ) {
            connectedClients.add(entry.getKey());
        }
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet() ) {
            entry.getValue().disconInfo(connectedClients, username);
        }
        connectedClients.removeAll(connectedClients); 
    }
    
    public void addUser(String username, ClientHandler handler){
        clients.put(username, handler);
        
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet() ) {
            connectedClients.add(entry.getKey());
        }
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet() ) {
            entry.getValue().conInfo(connectedClients, username);
        }
         connectedClients.removeAll(connectedClients);
    }
    
    public void send(String user, String msg){
        
        String message = "MESSAGE"+"#" + user + "#"  + msg;
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet() ) {
            entry.getValue().send(message);
        }    
    }
    
    
    
    @Override
     public void run(){
         try {
            Log.setLogFile("logFile.txt", "ServerLog");
            //String ip = "localhost"; // ændret her -----------------!!!!!!!!!!!!!!!!!!!!!
            //int port = 9999;
            new EchoServer().runServer(ChatGUI.allIp, ChatGUI.allPort);
            
           
        } finally {
            Log.closeLogger();
        }
     }

    public static void main(String[] args) {
        try {
            Log.setLogFile("logFile.txt", "ServerLog");
            //String ip = args[0];
            //int port = Integer.parseInt(args[1]);
            String test = "send#hej";
            String ip = "localhost"; // skal ændres til 10.0.0.4
            System.out.println(test.substring(0, 4));
            int port = 9999;
            new EchoServer().runServer(ip, port);
            System.out.println(ip.length());
        } finally {
            Log.closeLogger();
        }
    } 
}
