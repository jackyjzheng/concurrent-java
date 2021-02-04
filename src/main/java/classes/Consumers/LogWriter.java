package classes.Consumers;

import classes.Models.Database;

import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
//CONSUMER
public class LogWriter<V> implements Runnable {
    private Database<String> db;
    private FileWriter writer;

    public LogWriter(Database<String> db, FileWriter writer) {
        this.db = db;
        this.writer = writer;
    }

    public void run() {
        try {
            writer.write(db.getValueToLog() + String.format("%n")); // String not parameterized
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
