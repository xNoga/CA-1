/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoserver;

import echoclient.EchoClient;
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
public class ClientHandlerGUI extends Thread {

    Scanner input;
    PrintWriter writer;
    protected Socket socket;
    String user;

    EchoServer es;
    EchoClient ec = new EchoClient();

    int count;

    public ClientHandlerGUI(Socket socket, EchoServer es) throws IOException, RuntimeException {
        this.es = es;
        this.socket = socket;
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String message) {
        writer.println(message);
    }

    public void currentUsers(ArrayList<String> clients, String user) {
        for (int index = 0; index < clients.size(); index++) {
            String currElement = clients.get(index);
            if (index == clients.size() - 1) {
                writer.print(currElement + ".");
            } else {
                writer.print(currElement + ", ");
            }
        }
        writer.println("\n");
    }

    public void conInfo(ArrayList<String> clients, String user) {
        String users = "USERS#";
        //writer.println("USERS#");
        for (String f : clients) {
            users = users + f +",";
        }
        System.out.println(users);
          // writer.println("\n");
          writer.println(users);
    }

    public void disconInfo(ArrayList<String> clients, String user) {
        for (int index = 0; index < clients.size(); index++) {
            String currElement = clients.get(index);
            if (index == clients.size() - 1) {
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
            String message = input.nextLine(); //IMPORTANT blocking call
            user = message.substring(5, message.length());
            if (message.length() >= 5 && message.substring(0, 5).equalsIgnoreCase(ProtocolStrings.USER)) {
                es.addUser(message, this);
            } else {
                socket.close();
            }
            // message = "";
            //System.out.println(String.format("Received the message: %1$S ", message));

            while (!message.equals(ProtocolStrings.STOP)) {

                //System.out.println(String.format("Received the message: %1$S ", message.toUpperCase()));

                message = input.nextLine(); //IMPORTANT blocking call

                if (message.length() >= 7 && message.equalsIgnoreCase(ProtocolStrings.LOGOUT)) {
                    es.removeUser(user, this);
                    socket.close();
                } else if (message.length() >= 5 && message.substring(0, 5).equalsIgnoreCase(ProtocolStrings.SEND)) {
                    // message = message.substring(5, message.length());
                    es.send(user, message);
                }

            }
            writer.println(ProtocolStrings.STOP);//Echo the stop message back to the client for a nice closedown
            es.removeUser(user, this);
            socket.close();
            System.out.println("Closed a Connection");
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, "Closed a Connection");
        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
