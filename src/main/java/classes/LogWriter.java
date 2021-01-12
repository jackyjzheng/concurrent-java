package classes;

import java.io.FileWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
//CONSUMER
public class LogWriter<V> implements Runnable {
    protected BlockingQueue<V> valuesToWrite;
    private FileWriter writer;

    public LogWriter(BlockingQueue<V> valuesToWrite, FileWriter writer) {
        this.valuesToWrite = valuesToWrite;
        this.writer = writer;
    }

    public void run() {
        try {
            writer.write(valuesToWrite.take() + String.format("%n")); // String not parameterized
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
