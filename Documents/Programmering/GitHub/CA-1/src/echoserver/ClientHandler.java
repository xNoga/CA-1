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

    public ClientHandler(Socket socket, EchoServer es) throws IOException {
        this.es = es;
        this.socket = socket;
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String message){
        writer.println(message);
    }
    
    public void conInfo(ArrayList<String> clients, String user){
        writer.println(user + " has connected to the server.");
        writer.print("USERS# ");
        for (String f : clients) {
            writer.print(f + ", ");
        }
        writer.println("\n");
 
    }

    @Override
    public void run() {
        
        try {
            writer.println("Please login by typing 'user#yourname'");
            String message = input.nextLine(); //IMPORTANT blocking call
            if (message.length() < 5) {
                //writer.println("You must login by typing 'user#yourname'");
                run();
            } 
            if (message.substring(0, 5).equalsIgnoreCase("user#")) {
                user = message.substring(5, message.length());
                es.addUser(user, this);
                
            } else{
                // writer.println("You must login by typing 'user#yourname'");
                run();
            }
            message = "";
            System.out.println(String.format("Received the message: %1$S ", message));
            while (!message.equals(ProtocolStrings.STOP)) {
                // es.send(user, message);
                System.out.println(String.format("Received the message: %1$S ", message.toUpperCase()));
                message = input.nextLine(); //IMPORTANT blocking call
                es.send(user, message);
               
                
            }
            writer.println(ProtocolStrings.STOP);//Echo the stop message back to the client for a nice closedown
            socket.close();
            System.out.println("Closed a Connection");
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Closed a Connection");
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
