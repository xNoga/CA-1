/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerTest;

import echoclient.EchoClient;
import echoserver.EchoServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kristoffernoga
 */
public class TestEcho {

    public TestEcho() {
    }

    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EchoServer.main(null);
            }
        }).start();
    }

    @AfterClass
    public static void tearDownClass() {
        EchoServer.stopServer();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void send() throws IOException {
        try {
            Thread.sleep(1000);
            EchoClient client = new EchoClient();
            client.connect("localhost", 9999);
            client.send("Hello");
            assertEquals("HELLO", client.receive());
        } catch (InterruptedException ex) {
            Logger.getLogger(TestEcho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
