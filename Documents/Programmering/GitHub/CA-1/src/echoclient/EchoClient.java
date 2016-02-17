package echoclient;

import Presentation.ChatGUI;
import echoserver.EchoServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class EchoClient implements Runnable {

    Socket socket;
    private int port;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    List<ClientObserver> observers = new ArrayList();
    

    public void connect(String address, int port) throws UnknownHostException, IOException {
        try {
            Thread.sleep(500);
            this.port = port;
            serverAddress = InetAddress.getByName(address);
            socket = new Socket(serverAddress, port);
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
        } catch (InterruptedException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void registerClientObserver(ClientObserver o) {
        observers.add(o);
    }

    public void send(String msg) {
        System.out.println(msg);
        output.println(msg);
    }
    
    public void sendUser(String user){
        output.println("USER#"+user);
    }
    
    
    public void notifyObserversUsers(String msg){
        System.out.println(msg + "hej fra notifyObserversUsers");
        for (ClientObserver observer : observers) {
            observer.updateList(msg);
        }
    }
    public void notifyObserversMessage(String msg){
        System.out.println(msg + "hej fra observerMessage");
        for (ClientObserver observer : observers) {
            observer.sendMessage(msg);
        }
    }

    public void stop() throws IOException {
        output.println(ProtocolStrings.STOP);
    }

    public String receive() {
        String msg = input.nextLine();
        if (msg.equals(ProtocolStrings.STOP)) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return msg;
    }

//    public static void main(String[] args) {
//        int port;
//        String ip;
//        if (args.length == 2) {
//            ip = args[0];
//            port = Integer.parseInt(args[1]);
//        } else {
//            port = 9999;
//            ip = "localhost";
//            //ip = "10.0.0.4";
////       port = ChatGUI.allPort;
////       ip = ChatGUI.allIp;
//        }
//        try {
//            EchoClient tester = new EchoClient();
//            tester.connect(ip, port);
//            System.out.println("Sending 'Hello world'");
//            tester.send("Hello World");
//            System.out.println("Waiting for a reply");
//            System.out.println("Received: " + tester.receive()); //Important Blocking call         
//            tester.stop();
//            //System.in.read();      
//        } catch (UnknownHostException ex) {
//            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @Override
    public void run() {
        System.out.println("hej fra run");
        while (true) {
            String msg;     
            msg = input.nextLine();
            System.out.println(msg + "hej fra run message");
            String check = msg.substring(0, 6);
            if (check.equalsIgnoreCase("users#")) {
                notifyObserversUsers(msg);
            } else  {
                notifyObserversMessage(msg); 
            }
            //setChanged();
            //notifyObservers(msg);
        }

    }
}
