package classes;

import java.util.concurrent.BlockingQueue;

public class LogWriter<V> {
    private BlockingQueue<V> valuesToWrite;

    public LogWriter(BlockingQueue<V> queue) {
        this.valuesToWrite = queue;
    }

    public void run() {
        try {
            while (!valuesToWrite.isEmpty()) {
                Thread.sleep(10);
                System.out.println("Running");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
