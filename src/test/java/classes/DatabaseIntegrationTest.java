package classes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DatabaseIntegrationTest {
    private static Database<String> db;

    @BeforeClass
    public static void setUp() { db = new Database<>(); }

    @Test
    public void test() throws InterruptedException {
        int numThreads = 5;
        int target = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; ++i) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    int id = (int) Thread.currentThread().getId() % numThreads;
                    for (int i = id+1; i <= target; i+=numThreads) {
                        db.addValue(String.format("%09d", i));
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        DatabaseInfo res = db.update();
        System.out.println(res.totalUnique);
        System.out.println(res.newDuplicate);
    }
}