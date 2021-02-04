import classes.Models.Server;
import java.io.IOException;

public class Main {
    static private int portNumber = 4000;
    static private int poolSize = 5;
    private static int maxConnections = 5;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting up server...");
        Server server = new Server(portNumber, poolSize, maxConnections);
        server.run();
    }
}
