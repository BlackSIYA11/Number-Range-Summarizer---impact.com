package numberrangesummarizer;

/**
 * An immutable run of sequential numbers, e.g. 6, 7, 8.
 *
 * A range knows how to grow and how to describe itself. Keeping this logic
 * here means the summarizer only has to decide which numbers belong together.
 */
final class Range {

    /** Runs shorter than this are written out number by number, e.g. "1, 2". */
    static final int MIN_LENGTH_TO_COMPRESS = 3;

    private final int start;
    private final int end;

    Range(int singleNumber) {
        this(singleNumber, singleNumber);
    }

    private Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /** True if the given number comes directly after the end of this range. */
    boolean canExtendTo(int number) {
        // Using long avoids an overflow when the end is Integer.MAX_VALUE.
        return (long) number == (long) end + 1;
    }

    /** Returns a new, longer range that finishes on the given number. */
    Range extendTo(int number) {
        return new Range(start, number);
    }

    @Override
    public String toString() {
        long length = (long) end - start + 1;

        if (length >= MIN_LENGTH_TO_COMPRESS) {
            return start + "-" + end;
        }
        if (length == 2) {
            return start + ", " + end;
        }
        return String.valueOf(start);
    }
}
