package classes.Consumers;

import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

//CONSUMER
public class LogWriter<V> implements Runnable {
    private BlockingQueue<V> valuesToLog;
    private FileWriter writer;
    private AtomicBoolean shouldExit;

    public LogWriter(BlockingQueue<V> valuesToLog, FileWriter writer, AtomicBoolean shouldExit) {
        this.valuesToLog = valuesToLog;
        this.writer = writer;
        this.shouldExit = shouldExit;
    }

    public void run() {
        try {
            while (!shouldExit.get() || !valuesToLog.isEmpty())
                writer.write(valuesToLog.take() + String.format("%n"));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
