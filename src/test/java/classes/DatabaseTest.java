package classes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {
    private Database<Integer> db;

    @Before
    public void setUp() {
        db = new Database<>();
    }

    @Test
    public void testAddValue() {
        db.addValue(1);
        db.addValue(2);
        db.addValue(1);
        assertTrue(db.contains(1));
        assertTrue(db.contains(2));
        assertFalse(db.contains(3));
    }

    @Test
    public void testUpdate() {
        for (int i = 0; i < 10; ++i) {
            db.addValue(i);
            if (i < 5)
                db.addValue(i);
        }
        DatabaseInfo res = db.update();
        assertEquals(5, res.newDuplicate);
        assertEquals(10, res.newUnique);
        assertEquals(10, res.totalUnique);
    }
}