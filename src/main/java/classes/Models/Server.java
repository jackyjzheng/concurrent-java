package classes.Models;

import classes.Consumers.LogWriter;
import classes.Producers.DatabaseWriter;

import java.io.FileWriter;
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
    private final LogWriter logger;
    private final AtomicBoolean shouldExit = new AtomicBoolean(false);
    private final Database db = new Database<String>( );

    public Server(int port, int poolSize, int maxConnections, String outputFileName) throws IOException {
        serverSocket = new ServerSocket(port, maxConnections);
        pool = Executors.newFixedThreadPool(poolSize);
        logger = new LogWriter(db.getValuesToLog(), new FileWriter(outputFileName), shouldExit);
    }

    public void run() {
        try {
            Thread backgroundLogger = new Thread(logger);
            backgroundLogger.start();
            while (!shouldExit.get()) {  // maybe look at thoroughly
                try {
                    Socket clientSocket = serverSocket.accept();
                    pool.execute(new DatabaseWriter(db, serverSocket, clientSocket, shouldExit));
                } catch (SocketException e) {
                    if (shouldExit.get()) break;
                }
            }
            shutdownAndAwaitTermination();
            while (!db.getValuesToLog().isEmpty())
                continue;
            backgroundLogger.join();
        } catch (IOException | InterruptedException ex) {
            pool.shutdown();
        }
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
