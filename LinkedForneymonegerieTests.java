package linked_forneymonegerie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LinkedForneymonegerieTests {

    // =================================================
    // Test Configuration
    // =================================================

    // Used as the basic empty menagerie to test; the @Before
    // method is run before every @Test
    LinkedForneymonegerie fm;

    @Before
    public void init() {
        fm = new LinkedForneymonegerie();
    }

    // =================================================
    // Unit Tests
    // =================================================

    @Test
    public void testSize() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
    }

    @Test
    public void testTypeSize() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(1, fm.typeSize());
        fm.collect("Burnymon");
        assertEquals(2, fm.typeSize());
    }

    @Test
    public void testCollect() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Burnymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        fm1.collect("Leafymon");
        fm1.collect("Zappymon");
        fm1.collect("Leafymon");
        assertTrue(fm1.contains("Dampymon"));
        assertTrue(fm1.contains("Burnymon"));
        assertTrue(fm1.contains("Leafymon"));
        assertTrue(fm1.contains("Zappymon"));
        assertEquals(6, fm1.size());
        assertEquals(4, fm1.typeSize());

    }

    @Test
    public void testRelease() {

        fm.collect("Dampymon");
        fm.release("Dampymon");
        assertEquals(0, fm.size());
        assertEquals(0, fm.typeSize());
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.release("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        assertEquals(2, fm1.size());
        assertEquals(1, fm1.typeSize());
        fm1.release("Dampymon");
        assertEquals(1, fm1.size());
        assertEquals(1, fm1.typeSize());
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Andrewmon");
        fm2.collect("Andrewmon");
        fm2.collect("Andrewmon");
        fm2.collect("Baddymon");
        LinkedForneymonegerie dolly = fm.clone();
        dolly.release("Andrewmon");
        dolly.release("Andrewmon");
        dolly.release("Andrewmon");
        dolly.release("Baddymon");

    }

    @Test
    public void testReleaseType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
        assertEquals(2, fm.typeSize());
        fm.releaseType("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        fm.releaseType("Burnymon");
        assertEquals(0, fm.size());
        assertEquals(0, fm.typeSize());

    }

    @Test
    public void testCountType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(2, fm.countType("Dampymon"));
        assertEquals(1, fm.countType("Burnymon"));
        assertEquals(0, fm.countType("forneymon"));
    }

    @Test
    public void testContains() {
        assertFalse(fm.contains("forneymon"));
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        assertFalse(fm.contains("forneymon"));
        fm.collect("forneymon");
        assertTrue(fm.contains("forneymon"));
        fm.collect("1");
        fm.collect("2");
        fm.collect("3");
        fm.collect("4");
        fm.collect("5");
        fm.collect("6");
        fm.collect("7");
        fm.collect("8");
        fm.collect("8");
        assertTrue(fm.contains("1"));
        assertTrue(fm.contains("2"));
        assertTrue(fm.contains("3"));
        assertTrue(fm.contains("4"));
        assertTrue(fm.contains("5"));
        assertTrue(fm.contains("6"));
        assertTrue(fm.contains("7"));
        assertTrue(fm.contains("8"));

    }

    @Test
    public void testRarestType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
    }

    @Test
    public void testClone() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        LinkedForneymonegerie dolly = fm.clone();
        assertEquals(2, dolly.countType("Dampymon"));
        assertEquals(dolly.countType("Burnymon"), 1);
        dolly.collect("Zappymon");
        assertFalse(fm.contains("Zappymon"));

        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Andrewmon");
        fm1.collect("Andrewmon");
        fm1.collect("Andrewmon");
        fm1.collect("Baddymon");
        LinkedForneymonegerie dolly1 = fm1.clone();
        assertEquals(dolly1.countType("Andrewmon"), 3);
        assertEquals(dolly1.countType("Baddymon"), 1);
    }

    @Test
    public void testTrade() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Zappymon");
        fm2.collect("Leafymon");
        fm1.trade(fm2);
        assertTrue(fm1.contains("Zappymon"));
        assertTrue(fm1.contains("Leafymon"));
        assertTrue(fm2.contains("Dampymon"));
        assertTrue(fm2.contains("Burnymon"));
        assertFalse(fm1.contains("Dampymon"));
    }

    @Test
    public void testDiffMon() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Dampymon");
        fm2.collect("Zappymon");
        LinkedForneymonegerie fm3 = LinkedForneymonegerie.diffMon(fm1, fm2);
        assertEquals(fm3.countType("Dampymon"), 1);
        assertEquals(fm3.countType("Burnymon"), 1);
        assertFalse(fm3.contains("Zappymon"));
        fm3.collect("Leafymon");
        assertFalse(fm1.contains("Leafymon"));
        assertFalse(fm2.contains("Leafymon"));
    }

    @Test
    public void testSameForneymonegerie() {
        LinkedForneymonegerie fm1 = new LinkedForneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        LinkedForneymonegerie fm2 = new LinkedForneymonegerie();
        fm2.collect("Burnymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        assertTrue(LinkedForneymonegerie.sameCollection(fm1, fm2));
        assertTrue(LinkedForneymonegerie.sameCollection(fm2, fm1));
        fm2.collect("Leafymon");
        assertFalse(LinkedForneymonegerie.sameCollection(fm1, fm2));
    }

    @Test
    public void testToString() {
        fm.collect("Burnymon");
        assertEquals("[ \"Burnymon\": 1 ]", fm.toString());
    }

    @Test
    public void testToString_t1() {
        fm.collect("Burnymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Leafymon");
        fm.collect("Zappymon");
        fm.collect("Leafymon");
        assertEquals("[ \"Burnymon\": 2, \"Dampymon\": 1, \"Leafymon\": 2, \"Zappymon\": 1 ]", fm.toString());
    }

    @Test
    public void testIteratorBasics() {
        fm.collect("Andrewmon");
        fm.collect("Andrewmon");
        fm.collect("Andrewmon");
        fm.collect("Baddymon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();

        // Test next()
        LinkedForneymonegerie dolly = fm.clone();
        while (true) {
            String gotten = it.getType();
            assertTrue(dolly.contains(gotten));
            dolly.release(gotten);
            if (it.hasNext()) {
                it.next();
            } else {
                break;
            }
        }
        assertTrue(dolly.empty());
        assertFalse(it.hasNext());

        // Test prev()
        dolly = fm.clone();
        while (true) {
            String gotten = it.getType();
            assertTrue(dolly.contains(gotten));
            dolly.release(gotten);
            if (it.hasPrev()) {
                it.prev();
            } else {
                break;
            }
        }
        // If we've seen every Forneymon that was in fm, and removed
        // that from dolly (a copy), then dolly should be empty by now
        assertTrue(dolly.empty());
        assertFalse(it.hasPrev());

        int countOfReplaced = fm.countType(it.getType());
        it.replaceAll("Mimicmon");
        assertEquals(countOfReplaced, fm.countType("Mimicmon"));
        assertTrue(it.isValid());
        

        fm.collect("Cooliomon");
        assertFalse(it.isValid());
    }

    public void testIteratorBasics2() {
        fm.collect("1mon");
        fm.collect("2mon");
        fm.collect("3mon");
        fm.collect("3mon");
        fm.collect("3mon");
        LinkedForneymonegerie.Iterator it = fm.getIterator();
        String gotten = it.getType();
        assertEquals(gotten, "1mon");
        it.next();
        gotten = it.getType();
        assertEquals(gotten, "2mon");
        it.prev();
        gotten = it.getType();
        assertEquals(gotten, "1mon");
        it.next();
        it.next();
        it.next();
        gotten = it.getType();
        assertEquals(gotten, "3mon");
        it.prev();
        gotten = it.getType();
        assertEquals(gotten, "3mon");
        it.replaceAll("Baddiemon");
        gotten = it.getType();
        assertEquals(gotten, "Baddiemon");
        it.next();
        gotten = it.getType();
        assertEquals(gotten, "Baddiemon");
        it.next();
        gotten = it.getType();
        assertEquals(gotten, "Baddiemon");
        fm.collect("breakIterator");
        assertFalse(it.hasPrev());
        assertFalse(it.hasNext());
    }

}
