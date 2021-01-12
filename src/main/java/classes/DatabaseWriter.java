package classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
//PRODUCER

public class DatabaseWriter implements Runnable {
    private Database<String> db;
    private Socket clientSocket;
    private PrintWriter out;

    public DatabaseWriter(Database<String> db, Socket clientSocket) {
        this.db = db;
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //getInputStream fail
            String line = in.readLine();
            while (line != null) {
                db.addValue(line);
                line = in.readLine();
            }
            in.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
