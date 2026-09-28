package numberrangesummarizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RangeTest {

    @Test
    public void canOnlyExtendToTheNextNumber() {
        Range range = new Range(5);

        assertTrue(range.canExtendTo(6));
        assertFalse(range.canExtendTo(5));
        assertFalse(range.canExtendTo(7));
    }

    @Test
    public void extendingDoesNotChangeTheOriginalRange() {
        Range original = new Range(1);

        original.extendTo(2);

        assertEquals("1", original.toString());
    }

    @Test
    public void describesItselfBasedOnItsLength() {
        Range one = new Range(1);
        Range two = one.extendTo(2);
        Range three = two.extendTo(3);

        assertEquals("1", one.toString());
        assertEquals("1, 2", two.toString());
        assertEquals("1-3", three.toString());
    }
}
