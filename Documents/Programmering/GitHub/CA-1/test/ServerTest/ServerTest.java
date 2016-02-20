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
public class ServerTest {

    private CountDownLatch lock = new CountDownLatch(5);
    String usersResult = "";
    String sendResult = "";

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

    @Test
    public void testEchoClient() throws InterruptedException {
        try {
            EchoClient client = new EchoClient();
            client.connect("localhost", 9999);
            client.registerClientObserver(new ClientObserver() {
                @Override
                public void sendMessage(String message) {
                    sendResult = message;
                    lock.countDown();
                }

                @Override
                public void updateList(String users) {
                    usersResult = users;
                    lock.countDown();
                }
            });
            new Thread(client).start();
            Thread.sleep(1000);
            client.send("USER#Test");
            lock.await(2000, TimeUnit.MILLISECONDS);
            assertNotNull(usersResult);
            assertEquals("USERS#Test", usersResult);
            client.send("SEND#*#Hej med dig");
            lock.await(2000, TimeUnit.MILLISECONDS);
            assertNotNull(sendResult);
            assertEquals("MESSAGE#Test#Hej med dig", sendResult);
            client.send("LOGOUT#");
        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

       @Test
    public void testServerTwoClients() throws InterruptedException {
        try {
            EchoClient client = new EchoClient();
            EchoClient client2 = new EchoClient();
            client.connect("localhost", 9999);
            client.registerClientObserver(new ClientObserver() {
                @Override
                public void sendMessage(String message) {
                    sendResult = message;
                    lock.countDown();
                }

                @Override
                public void updateList(String users) {
                    usersResult = users;
                    lock.countDown();
                }
            });
            new Thread(client).start();
            Thread.sleep(1000);
            client.send("USER#Test");
            lock.await(2000, TimeUnit.MILLISECONDS);
            assertNotNull(usersResult);
            assertEquals("USERS#Test", usersResult);
            client2.connect("localhost", 9999);
            client2.send("USER#Test2");
             lock.await(2000, TimeUnit.MILLISECONDS);
            Thread.sleep(1000);
            assertNotNull(usersResult);
            assertEquals("USERS#Test,Test2", usersResult);
            client2.send("LOGOUT#");
            lock.await(2000, TimeUnit.MILLISECONDS);
            assertNotNull(usersResult);
            assertEquals("USERS#Test", usersResult);
            client.send("LOGOUT#");
        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
