package numberrangesummarizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Turns "1,3,6,7,8" into "1, 3, 6-8".
 *
 * Assumptions (each one is pinned down by a unit test):
 *  1. Input numbers are separated by commas; spaces around a number are ignored.
 *  2. Null or blank input gives an empty collection.
 *  3. Anything that is not a whole number (or an empty item such as "1,,2")
 *     is an error, reported with an IllegalArgumentException.
 *  4. The summary is sorted from low to high and duplicates are removed.
 *  5. Only runs of three or more numbers become a range. Two neighbours
 *     such as 1 and 2 are listed as "1, 2", because "1-2" saves nothing.
 *  6. A null or empty collection gives an empty string, and null items
 *     inside a collection are ignored.
 *  7. Negative numbers are supported, so -3,-2,-1 is written "-3--1".
 */
public class RangeSummarizer implements NumberRangeSummarizer {

    private static final String INPUT_SEPARATOR = ",";
    private static final String OUTPUT_SEPARATOR = ", ";

    @Override
    public Collection<Integer> collect(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // The -1 keeps trailing empty items, so "1,2," is reported as an error.
        String[] items = input.split(INPUT_SEPARATOR, -1);

        List<Integer> numbers = new ArrayList<>();
        for (String item : items) {
            numbers.add(parse(item));
        }
        return numbers;
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null) {
            return "";
        }

        List<Integer> sortedNumbers = input.stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        return groupIntoRanges(sortedNumbers).stream()
                .map(Range::toString)
                .collect(Collectors.joining(OUTPUT_SEPARATOR));
    }

    private int parse(String item) {
        try {
            return Integer.parseInt(item.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("'" + item + "' is not a valid whole number", e);
        }
    }

    /** Walks through sorted, unique numbers and keeps growing a range while they are sequential. */
    private List<Range> groupIntoRanges(List<Integer> sortedNumbers) {
        List<Range> ranges = new ArrayList<>();
        Range current = null;

        for (int number : sortedNumbers) {
            if (current != null && current.canExtendTo(number)) {
                current = current.extendTo(number);
            } else {
                if (current != null) {
                    ranges.add(current);
                }
                current = new Range(number);
            }
        }

        if (current != null) {
            ranges.add(current);
        }
        return ranges;
    }
}
