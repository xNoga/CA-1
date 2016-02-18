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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class EchoServer extends Thread {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private Scanner input;
    private PrintWriter output;
    private String ip;
    private int port;
    HashMap<String, ClientHandlerGUI> clients = new HashMap<>();
    ArrayList<String> connectedClients = new ArrayList();

    public static void stopServer() {
        keepRunning = false;
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
                new ClientHandlerGUI(socket, this).start();
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
        }
    }

    public void removeUser(String username, ClientHandlerGUI handler) {
        clients.remove(username, handler);

        for (Map.Entry<String, ClientHandlerGUI> entry : clients.entrySet()) {
            connectedClients.add(entry.getKey());
        }
        for (Map.Entry<String, ClientHandlerGUI> entry : clients.entrySet()) {
            entry.getValue().conInfo(connectedClients, username);
        }
        connectedClients.removeAll(connectedClients);
    }

    public void addUser(String username, ClientHandlerGUI handler) {
        String user = username.substring(5,username.length());
        clients.put(user, handler);

        for (Map.Entry<String, ClientHandlerGUI> entry : clients.entrySet()) {
            connectedClients.add(entry.getKey());
        }
        for (Map.Entry<String, ClientHandlerGUI> entry : clients.entrySet()) {
            entry.getValue().conInfo(connectedClients, user);
        }
        connectedClients.removeAll(connectedClients);
        // System.out.println("Succes adding client.");
        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Succes adding client.");
    }

    public void currentUsers(String username, ClientHandlerGUI handler) {
        for (Map.Entry<String, ClientHandlerGUI> entry : clients.entrySet()) {
            connectedClients.add(entry.getKey());
        }
        for (Map.Entry<String, ClientHandlerGUI> entry : clients.entrySet()) {
            entry.getValue().currentUsers(connectedClients, username);
        }
    }

    public void send(String user, String msg) {

        String[] msgArray = msg.trim().split("#");
        String receivers = msgArray[1];
        String message = msgArray[2];
        String[] receiverList = receivers.trim().split(",");
        
        if (receivers.equalsIgnoreCase("*")) {
            String sendMessage = "MESSAGE" + "#" + user + "#" + message;
            for (Map.Entry<String, ClientHandlerGUI> entry : clients.entrySet()) {
                entry.getValue().send(sendMessage);
            }
            
        } 
        
        if (receiverList.length >= 1 && !receiverList[0].equalsIgnoreCase("*")) {
            String sendMessage = "MESSAGE" + "#" + user + "#" + message;
            for (Map.Entry<String, ClientHandlerGUI> entry : clients.entrySet()) {
                for (int i = 0; i < receiverList.length; i++) {
                    if (entry.getKey().equalsIgnoreCase(receiverList[i])) {
                        entry.getValue().send(sendMessage);
                    }
                } 
            }
        }
    }

    @Override
    public void run() {
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
            String ip = args[0];
            int port = Integer.parseInt(args[1]);

//            String ip = "localhost"; // skal ændres til 10.0.0.4           
            //int port = 9999;
            new EchoServer().runServer(ip, port);
            System.out.println(ip.length());
        } finally {
            Log.closeLogger();
        }
    }
}
