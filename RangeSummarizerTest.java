package numberrangesummarizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

public class RangeSummarizerTest {

    private final NumberRangeSummarizer summarizer = new RangeSummarizer();

    @Test
    public void summarizesTheExampleFromTheBrief() {
        Collection<Integer> numbers = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");

        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", summarizer.summarizeCollection(numbers));
    }

    // ---- collect(): reading the input ----

    @Test
    public void collectReadsCommaSeparatedNumbersInTheOrderGiven() {
        assertEquals(Arrays.asList(5, 1, 3), summarizer.collect("5,1,3"));
    }

    @Test
    public void assumption_spacesAroundNumbersAreIgnored() {
        assertEquals(Arrays.asList(1, 2, 3), summarizer.collect(" 1 , 2,3 "));
    }

    @Test
    public void assumption_nullOrBlankInputGivesAnEmptyCollection() {
        assertTrue(summarizer.collect(null).isEmpty());
        assertTrue(summarizer.collect("").isEmpty());
        assertTrue(summarizer.collect("   ").isEmpty());
    }

    @Test
    public void assumption_negativeNumbersAreAccepted() {
        assertEquals(Arrays.asList(-2, -1, 0), summarizer.collect("-2,-1,0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void assumption_nonNumericItemIsRejected() {
        summarizer.collect("1,two,3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assumption_emptyItemBetweenCommasIsRejected() {
        summarizer.collect("1,,2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assumption_trailingCommaIsRejected() {
        summarizer.collect("1,2,");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assumption_decimalNumbersAreRejected() {
        summarizer.collect("1.5,2");
    }

    // ---- summarizeCollection(): building the output ----

    @Test
    public void singleNumberIsWrittenOnItsOwn() {
        assertEquals("7", summarizer.summarizeCollection(Collections.singletonList(7)));
    }

    @Test
    public void numbersWithGapsAreNotGrouped() {
        assertEquals("1, 3, 5", summarizer.summarizeCollection(Arrays.asList(1, 3, 5)));
    }

    @Test
    public void threeSequentialNumbersBecomeARange() {
        assertEquals("4-6", summarizer.summarizeCollection(Arrays.asList(4, 5, 6)));
    }

    @Test
    public void wholeInputCanBeOneRange() {
        assertEquals("1-5", summarizer.summarizeCollection(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void assumption_twoSequentialNumbersAreListedSeparately() {
        assertEquals("1, 2, 5", summarizer.summarizeCollection(Arrays.asList(1, 2, 5)));
    }

    @Test
    public void assumption_outputIsSortedEvenWhenInputIsNot() {
        assertEquals("1-3, 9", summarizer.summarizeCollection(Arrays.asList(9, 3, 1, 2)));
    }

    @Test
    public void assumption_duplicatesAreRemoved() {
        assertEquals("1-3, 5", summarizer.summarizeCollection(Arrays.asList(1, 2, 2, 3, 3, 5, 5)));
    }

    @Test
    public void assumption_negativeNumbersFormRanges() {
        assertEquals("-3--1, 4", summarizer.summarizeCollection(Arrays.asList(-3, -2, -1, 4)));
    }

    @Test
    public void rangeCanCrossZero() {
        assertEquals("-1-1", summarizer.summarizeCollection(Arrays.asList(-1, 0, 1)));
    }

    @Test
    public void assumption_emptyOrNullCollectionGivesEmptyString() {
        assertEquals("", summarizer.summarizeCollection(Collections.<Integer>emptyList()));
        assertEquals("", summarizer.summarizeCollection(null));
    }

    @Test
    public void assumption_nullItemsInsideTheCollectionAreIgnored() {
        assertEquals("1-3", summarizer.summarizeCollection(Arrays.asList(1, null, 2, 3)));
    }

    @Test
    public void handlesIntegerLimitsWithoutOverflow() {
        Collection<Integer> numbers = Arrays.asList(Integer.MAX_VALUE - 2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE, Integer.MIN_VALUE);

        assertEquals(Integer.MIN_VALUE + ", " + (Integer.MAX_VALUE - 2) + "-" + Integer.MAX_VALUE,
                summarizer.summarizeCollection(numbers));
    }
}
