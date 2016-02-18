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
public class ServerTest {

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
      private CountDownLatch lock3 = new CountDownLatch(1);
    private CountDownLatch lock4 = new CountDownLatch(1);
    String usersResult = "";
    String sendResult = "";

    @Test
    public void test() throws InterruptedException {

        try {
            EchoClient client = new EchoClient();
            client.connect("localhost", 9999);
            client.registerClientObserver(new ClientObserver() {
                @Override
                public void sendMessage(String message) {
                    sendResult = message;
                    lock2.countDown();
                }

                @Override
                public void updateList(String users) {
                    usersResult = users;
                    lock.countDown();
                }
            });
            new Thread(client).start();
            Thread.sleep(2000);
            client.send("user#Test");
            lock.await(2000, TimeUnit.MILLISECONDS);
            assertNotNull(usersResult);
            assertEquals("USERS#Test", usersResult);
            client.send("SEND#*#Hej med dig");
            lock2.await(2000, TimeUnit.MILLISECONDS);
            assertNotNull(sendResult);
            assertEquals("MESSAGE#Test#Hej med dig", sendResult);
            client2();
            Thread.sleep(2000);
           assertEquals("USERS#Test", usersResult);
        } catch (IOException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void client2() throws IOException {
        try {
            EchoClient client2 = new EchoClient();
            client2.connect("localhost", 9999);
            client2.registerClientObserver(new ClientObserver() {
                @Override
                public void sendMessage(String message) {
                    sendResult = message;
                    lock4.countDown();
                }

                @Override
                public void updateList(String users) {
                    usersResult = users;
                    lock3.countDown();
                }
            });
            new Thread(client2).start();
            Thread.sleep(2000);
            client2.send("USER#Test2");
            lock3.await(2000, TimeUnit.MILLISECONDS);
            assertEquals("USERS#Test,Test2", usersResult);
            client2.send("logout#");
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
