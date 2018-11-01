//Bridget O'Connor

package forneymonegerie;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;

public class ForneymonegerieTests {

    // =================================================
    // Test Configuration
    // =================================================

    // Used as the basic empty menagerie to test; the @Before
    // method is run before every @Test
    Forneymonegerie fm;

    @Before
    public void init() {
        fm = new Forneymonegerie();
    }

    // =================================================
    // Unit Tests
    // =================================================

    @Test
    public void testempty() {
        assertEquals(true, fm.empty());
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(false, fm.empty());
    }

    @Test
    public void testSize() {
        assertEquals(0, fm.size());
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
    }

    @Test
    public void testTypeSize() {
        assertEquals(0, fm.typeSize());
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(1, fm.typeSize());
        fm.collect("Burnymon");
        assertEquals(2, fm.typeSize());
        fm.collect("Zappymon");
        fm.collect("Yeetmon");
        assertEquals(4, fm.typeSize());
    }

    @Test
    public void testCollect() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        assertTrue(fm.collect("Yeetmon"));
        assertFalse(fm.collect("Yeetmon"));
    }

    @Test
    public void testRelease() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        assertEquals(1, fm.typeSize());
        fm.release("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        fm.release("Dampymon");
        assertEquals(0, fm.countType("Dampymon"));
        assertEquals(0, fm.size());
        assertEquals(0, fm.typeSize());
        assertFalse(fm.release("Dampymon"));
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
        for (int i = 0; i < 20; i++) {
            fm.collect("Angrymon");
        }
        assertEquals(2, fm.countType("Dampymon"));
        assertEquals(1, fm.countType("Burnymon"));
        assertEquals(0, fm.countType("forneymon"));
        assertEquals(20, fm.countType("Angrymon"));
    }

    @Test
    public void testContains() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        assertFalse(fm.contains("forneymon"));
        assertFalse(fm.contains("Angrymon"));
        fm.collect("Angrymon");
        assertTrue(fm.contains("Angrymon"));
    }

    @Test
    public void testNth() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        assertEquals("Dampymon", fm.nth(0));
        assertEquals("Dampymon", fm.nth(1));
        assertEquals("Burnymon", fm.nth(2));
        assertEquals("Zappymon", fm.nth(3));
        fm.releaseType("Dampymon");
        assertEquals("Burnymon", fm.nth(0));

        fm.collect("Pikachu");
        fm.collect("Pikachu");
        fm.collect("Pikachu");
        fm.collect("Pikachu");
        fm.collect("Pikachu");
        fm.collect("Weakachu");
        fm.collect("Weakachu");
        fm.collect("Weakachu");
        assertEquals("Weakachu", fm.nth(9));
        assertEquals("Weakachu", fm.nth(8));
        assertEquals("Weakachu", fm.nth(7));
        assertEquals("Pikachu", fm.nth(2));
    }

    @Test
    public void testThrowIllegalArgsNth() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        assertThrows(IllegalArgumentException.class, () -> {
            assertEquals("Dampymon", fm.nth(8));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            assertEquals("Dampymon", fm.nth(-1));
        });
    }

    @Test
    public void testRarestType() {
        assertEquals(null, fm.rarestType());
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
        fm.collect("Pikachu");
        assertEquals("Pikachu", fm.rarestType());
    }

    @Test
    public void testClone() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        Forneymonegerie dolly = fm.clone();
        assertEquals(dolly.countType("Dampymon"), 2);
        assertEquals(dolly.countType("Burnymon"), 1);
        dolly.collect("Zappymon");
        assertFalse(fm.contains("Zappymon"));
        fm.collect("Pikachu");
        assertFalse(dolly.contains("Pikachu"));
    }

    @Test
    public void testTrade() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Zappymon");
        fm2.collect("Leafymon");
        fm1.trade(fm2);
        assertTrue(fm1.contains("Zappymon"));
        assertTrue(fm1.contains("Leafymon"));
        assertTrue(fm2.contains("Dampymon"));
        assertTrue(fm2.contains("Burnymon"));
        assertFalse(fm1.contains("Dampymon"));
        fm1.trade(fm2);
        assertEquals(fm1.countType("Dampymon"), 2);
        assertEquals(fm1.countType("Burnymon"), 1);
        assertEquals(fm2.countType("Zappymon"), 1);
        assertEquals(fm2.countType("Leafymon"), 1);
    }

    @Test
    public void testDiffMon() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Dampymon");
        fm2.collect("Zappymon");
        Forneymonegerie fm3 = Forneymonegerie.diffMon(fm1, fm2);
        assertEquals(fm3.countType("Dampymon"), 1);
        assertEquals(fm3.countType("Burnymon"), 1);
        assertFalse(fm3.contains("Zappymon"));
        fm3.collect("Leafymon");
        assertFalse(fm1.contains("Leafymon"));
        assertFalse(fm2.contains("Leafymon"));

        Forneymonegerie fm4 = new Forneymonegerie();
        fm4.collect("Dampymon");
        fm4.collect("Dampymon");
        fm4.collect("Burnymon");
        Forneymonegerie fm5 = new Forneymonegerie();
        fm5.collect("Dampymon");
        fm5.collect("Dampymon");
        fm5.collect("Burnymon");
        Forneymonegerie fm6 = Forneymonegerie.diffMon(fm4, fm5);
        assertEquals(0, fm6.size());
        assertEquals(0, fm6.typeSize());
    }

    @Test
    public void testSameForneymonegerie() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Burnymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        assertTrue(Forneymonegerie.sameCollection(fm1, fm2));
        assertTrue(Forneymonegerie.sameCollection(fm2, fm1));
        fm2.collect("Leafymon");
        assertFalse(Forneymonegerie.sameCollection(fm1, fm2));

        Forneymonegerie fm3 = new Forneymonegerie();
        Forneymonegerie fm4 = new Forneymonegerie();
        assertTrue(Forneymonegerie.sameCollection(fm3, fm4));
        fm3.collect("Littymon");
        fm3.collect("Bittymon");
        fm4.collect("Littymon");
        fm4.collect("Pikachu");
        assertFalse(Forneymonegerie.sameCollection(fm3, fm4));
    }

    @Test
    public void testCheckAndGrow() {
        for (int i = 0; i < 20; i++) {
            fm.collect("Dampymon");
        }
        assertEquals(20, fm.size());
        assertEquals(fm.countType("Dampymon"), 20);

    }

    @Test
    public void testToString() {
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Burnymon");
        assertEquals("[ \"Dampymon\": 1, \"Burnymon\": 2 ]", fm.toString());
    }

}
