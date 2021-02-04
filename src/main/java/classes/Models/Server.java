package classes.Models;

import classes.Producers.DatabaseWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server implements Runnable {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private final AtomicBoolean shouldExit;
    private final Database db = new Database<String>();

    public Server(int port, int poolSize, int maxConnections) throws IOException {
        serverSocket = new ServerSocket(port, maxConnections);
        pool = Executors.newFixedThreadPool(poolSize);
        shouldExit = new AtomicBoolean(false);
    }

    public void run() {
        try {
            while (!shouldExit.get()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    pool.execute(new DatabaseWriter(db, serverSocket, clientSocket, shouldExit));
                } catch (SocketException e) {
                    if (shouldExit.get()) break;
                }
            }
        } catch (IOException ex) {
            pool.shutdown();
        }
        shutdownAndAwaitTermination();
    }

    public void shutdownAndAwaitTermination() {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
