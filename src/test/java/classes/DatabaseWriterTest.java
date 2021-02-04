package classes;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DatabaseWriterTest {
    private int testPort = 4000;
    private ServerSocket testServer;

    @BeforeClass
    public void setUp() {
        try {
            testServer = new ServerSocket(testPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void end() {
        try {
            testServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        try {
            int attempts = 10;
            Socket clientSocket = new Socket("localhost", testPort);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            for (int i = 0; i <  attempts; ++i) {
                out.println(String.format("%09d", i));
            }
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
