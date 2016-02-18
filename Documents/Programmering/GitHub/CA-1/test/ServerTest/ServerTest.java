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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Eske Wolff
 */
public class ServerTest implements ClientObserver {

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

    private CountDownLatch lock = new CountDownLatch(1);
    private CountDownLatch lock2 = new CountDownLatch(1);
    String userResult = "";
    String sendResult = "";

    @Test
    public void send() throws InterruptedException {
        try {
            EchoClient client = new EchoClient();
            
            client.connect("localhost", 9999);
            client.registerClientObserver(this);
            new Thread(client).start();
            //Thread.sleep(2000);
            client.send("user#Test");
            lock.await(2000, TimeUnit.MILLISECONDS);
            assertNotNull(userResult);
            assertEquals("USERS#Test,", userResult);
            
            // Thread.sleep(5000);
            client.send("SEND#*#Hej med dig");
            lock2.await(2000, TimeUnit.MILLISECONDS);
            assertNotNull(sendResult);
            assertEquals("MESSAGE#Test#Hej med dig", sendResult);
//            EchoClient client2 = new EchoClient();
//            client2.connect("localhost", 9999);
//            client2.send("user#Test2");
//            assertEquals("USERS#Test,Test2,", userResult);
            

        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @Test
    public void test2() throws InterruptedException{
        EchoClient client2 = new EchoClient();
        
    }

    @Override
    public void sendMessage(String message) {
        sendResult = message;
        lock2.countDown();
    }

    @Override
    public void updateList(String users) {
        userResult = users;
        lock.countDown();
    }
}
