package classes.Producers;

import classes.Models.Database;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseWriter implements Runnable {
    private Database<String> db;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private AtomicBoolean serverExit;

    public DatabaseWriter(Database<String> db, ServerSocket serverSocket, Socket clientSocket, AtomicBoolean serverExit) {
        this.db = db;
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
        this.serverExit = serverExit;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //getInputStream fail
            String line = in.readLine();
            while (!serverExit.get() && line != null && !line.equals("terminate")) {
                db.addValue(line);
                line = in.readLine();
            }
            in.close();
            clientSocket.close(); //wait for timeout if no user input?
            if (line != null && line.equals("terminate")) {
                serverExit.set(true);
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
