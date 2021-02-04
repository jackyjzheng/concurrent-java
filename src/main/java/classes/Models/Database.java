package classes.Models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Database<V> {
    private Set<V> uniqueValues = Collections.synchronizedSet(new HashSet<>());
    private BlockingQueue<V> valuesToLog = new LinkedBlockingQueue<>();
    private volatile int unique = 0;
    private volatile int totalUnique = 0;
    private volatile int duplicate = 0;

    public synchronized void addValue(V value) {
        if (!uniqueValues.contains(value)) {
            try {
                uniqueValues.add(value);
                valuesToLog.put(value);
                unique += 1;
                totalUnique += 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            duplicate += 1;
        }
    }

    public synchronized DatabaseInfo update() {
        DatabaseInfo dbInfo = new DatabaseInfo(unique,totalUnique,duplicate);
        this.unique = 0;
        this.duplicate = 0;
        return dbInfo;
    }

    public V getValueToLog() throws InterruptedException {
        return valuesToLog.take();
    }

    public synchronized boolean contains(V value) {
        return uniqueValues.contains(value);
    }
}
