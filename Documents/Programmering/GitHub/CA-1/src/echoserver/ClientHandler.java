/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoserver;

import echoserver.EchoServer;
import echoserver.Log;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author kristoffernoga
 */
public class ClientHandler extends Thread {

    Scanner input;
    PrintWriter writer;
    protected Socket socket;
    String user;

    EchoServer es;
    
    int count;

    public ClientHandler(Socket socket, EchoServer es) throws IOException, RuntimeException {
        this.es = es;
        this.socket = socket;
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String message) {
        writer.println(message);
    }
    
    public void currentUsers(ArrayList<String> clients, String user){
        writer.print("USERS# ");
        for (int index = 0; index < clients.size(); index++) {
            String currElement = clients.get(index);
            if (index == clients.size() -1) {
                writer.print(currElement + ".");
            } else {
                writer.print(currElement + ", ");
            }
        }
        writer.println("\n");
    }

    public void conInfo(ArrayList<String> clients, String user) {
        writer.println(user + " has connected to the server.");
        writer.print("USERS# ");
        for (int index = 0; index < clients.size(); index++) {
            String currElement = clients.get(index);
            if (index == clients.size() -1) {
                writer.print(currElement + ".");
            } else {
                writer.print(currElement + ", ");
            }
        }
        writer.println("\n");

    }

    public void disconInfo(ArrayList<String> clients, String user) {
        writer.println(user + " has disconnected from the server.");
        writer.print("USERS# ");
        for (int index = 0; index < clients.size(); index++) {
            String currElement = clients.get(index);
            if (index == clients.size() -1) {
                writer.print(currElement + ".");
            } else {
                writer.print(currElement + ", ");
            }
        }
        writer.println("\n");

    }

    @Override
    public void run() {

        try {
            writer.println("Please login by typing 'user#yourname'");
            String message = input.nextLine(); //IMPORTANT blocking call
            if (message.length() < 5) {
                run();
            }
            if (message.substring(0, 5).equalsIgnoreCase("user#")) {
                user = message.substring(5, message.length());
                es.addUser(user, this);

            } else {               
                run();
            }
            message = "";
            System.out.println(String.format("Received the message: %1$S ", message));
            while (!message.equals(ProtocolStrings.STOP)) {
                
                System.out.println(String.format("Received the message: %1$S ", message.toUpperCase()));
                
                message = input.nextLine(); //IMPORTANT blocking call
                for (int i = 0; i < message.length(); i++) {
                    if (message.charAt(i) == '#') {
                        count++;
                    }
                }
                if (message.equalsIgnoreCase(ProtocolStrings.LOGOUT) && message.length() >=7) {
                    es.removeUser(user, this);
                    socket.close();  
                } else if (message.length() >= 5 && message.substring(0, 5).equalsIgnoreCase(ProtocolStrings.SEND) && count ==2) {
                    // message = message.substring(5, message.length());
                    es.send(user, message);
                } else if(message.length() >= 6 && message.equalsIgnoreCase(ProtocolStrings.USERS)){
                    es.currentUsers(user, this);
                } else {
                    writer.println("You must enter a keyword before typing: SEND#, USERS#, LOGOUT#");
                }
                count = 0;
                
            }
            writer.println(ProtocolStrings.STOP);//Echo the stop message back to the client for a nice closedown
            es.removeUser(user, this);
            socket.close();
            System.out.println("Closed a Connection");
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Closed a Connection");
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
