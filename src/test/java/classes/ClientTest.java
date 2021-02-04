package classes;

import classes.Models.Client;
import org.junit.Test;

public class ClientTest {
    private static int testPort = 4000;
    private static String serverAddress = "localhost";

    @Test
    public void test() throws InterruptedException {
        Client client1 = new Client(serverAddress, testPort, 1000, 1000);
        client1.run();
        Client client2 = new Client(serverAddress, testPort, 5000, 1000, true);
        client2.run();
    }
}
