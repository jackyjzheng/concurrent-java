package classes.Models;

import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private String serverAddress;
    private int targetPort;
    private int start;
    private int numberOfSends;
    private boolean terminateClient;

    public Client(String serverAddress, int targetPort, int start, int numberOfSends, boolean terminateClient) {
        this.serverAddress = serverAddress;
        this.targetPort = targetPort;
        this.start = start;
        this.numberOfSends = numberOfSends;
        this.terminateClient = terminateClient;
    }

    public Client(String serverAddress, int targetPort, int start, int numberOfSends) {
        this(serverAddress, targetPort, start, numberOfSends, false);
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(this.serverAddress, this.targetPort);
            PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            for (int i = start; i < start+numberOfSends; ++i) {
                outputStream.println(String.format("%09d", i));
            }
            if (terminateClient) {
                outputStream.println("terminate");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
