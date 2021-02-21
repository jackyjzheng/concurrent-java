package classes;

import classes.Models.Client;
import org.junit.Test;

public class ClientTest {
    private static int testPort = 4000;
    private static String serverAddress = "localhost";

    @Test
    public void test() throws InterruptedException {
        Client client1 = new Client(serverAddress, testPort, 0, 400000);
        Client client2 = new Client(serverAddress, testPort, 400000, 400000);
        Client client3 = new Client(serverAddress, testPort, 800000, 400000);
        Client client4 = new Client(serverAddress, testPort, 1200000, 400000);
        Client client5 = new Client(serverAddress, testPort, 1600000, 400000, true);
        client1.run();
        client2.run();
        client3.run();
        client4.run();
        client5.run();
    }
}
