/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerTest;

import echoclient.ClientObserver;
import echoclient.EchoClient;
import echoserver.EchoServer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Eske Wolff
 */
public class ServerTest implements ClientObserver{

    public ServerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] args = {"Localhost", "9999"};
                EchoServer.main(args);
            }
        }).start();
    }

    @AfterClass
    public static void tearDownClass() {
        EchoServer.stopServer();
    }

//    @Test
//    public void connect() throws InterruptedException {
//        try {
//            Thread.sleep(1000);
//            EchoClient client = new EchoClient();
//            client.connect("localhost", 9999);
//            client.send("user#Test");
//            assertEquals("USERS#Test,Test2,", client.receive());
//
//        } catch (IOException ex) {
//            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    @Test
//    public void send() throws InterruptedException {
//        try {
//            Thread.sleep(1000);
//            EchoClient client = new EchoClient();
//            client.connect("localhost", 9999);
//               client.send("user#Test2");
//            assertEquals("USERS#Test2,", client.receive());
//            client.send("send#*#Hello!");
//            assertEquals("MESSAGE#Test2#Hello!", client.receive());
//        } catch (IOException ex) {
//            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    private CountDownLatch lock = new CountDownLatch(1);
    String result = "";
   @Test
    public void sendMessage() throws InterruptedException {
        try {
            EchoClient client = new EchoClient();
            client.connect("localhost", 9999);
            client.registerClientObserver(this);
            new Thread(client).start();
            Thread.sleep(2000);
            client.send("user#Test");     
            lock.await(2000, TimeUnit.MILLISECONDS);
            assertNotNull(result);
            assertEquals("USERS#Test,", result);
            
        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Override
    public void sendMessage(String message) {
                    
    }

    @Override
    public void updateList(String users) {
        result = users;
        lock.countDown();
    }
}
