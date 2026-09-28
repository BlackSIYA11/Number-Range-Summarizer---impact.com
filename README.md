# Number-Range-Summarizer

Turns a list of numbers into a compact, comma delimited summary:

    "1,3,6,7,8,12,13,14,15,21,22,23,24,31"  ->  "1, 3, 6-8, 12-15, 21-24, 31"

## Design

- `NumberRangeSummarizer` - the interface provided with the exercise.
- `RangeSummarizer` - implements the interface. Reads the input, sorts and de-duplicates, then groups numbers.
- `Range` - a small immutable class that represents a run of sequential numbers and knows how to print itself.

## Assumptions

These are listed in the `RangeSummarizer` Javadoc and each one has a test whose name starts with `assumption_`.

1. Input is comma separated; spaces around numbers are ignored.
2. Null or blank input gives an empty collection.
3. Non-numeric or empty items (e.g. `1,,2`, `1,two`) throw `IllegalArgumentException`.
4. Output is sorted low to high with duplicates removed.
5. Only runs of 3 or more become a range; `1,2` stays `1, 2`.
6. Null or empty collection gives `""`; null items are ignored.
7. Negative numbers are supported (`-3,-2,-1` -> `-3--1`).

## Running the tests

Requires Java 8+ and Maven:

    mvn test
