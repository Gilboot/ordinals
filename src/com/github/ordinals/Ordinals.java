package com.github.ordinals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Represents a date with an ordinal indicator.
 *
 * @see https://stackoverflow.com/q/4011075/10030693
 * @see https://en.m.wikipedia.org/wiki/Ordinal_indicator
 */
public final class Ordinals {
    /**
     * Simple entry point that creates a {@code Date} and represents it as a {@code String} together with an ordinal suffix.
     */
    public static void main(final String... args) {
        try {
            var osf = OrdinalsFactory.getInstance(Locale.US);
            Stream.of(IntStream.range(1, 10),
                      IntStream.range(18, 24),
                      IntStream.range(28, 34))
                .flatMapToInt(i -> i).forEach(i -> {
                        System.out.println("with suffix: " + osf.getOrdinalWithSuffix(i));
                        System.out.println("full name: " + osf.getOrdinalFullName(i));
                    });
        } catch (Exception e) {
            System.err.println("Failed to find required file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Format a date including a suffix as an ordinal indicator.
     *
     * @param date The {@code Date} to format.
     *
     * @return the date formatted.
     */
    public static String getFormattedDate(final Date date) {
        var dayNumberSuffix = OrdinalsFactory.getDayOfMonthSuffix(date);
        var dateFormat = new SimpleDateFormat(String.format("E',' dd'%1s' MMM yyyy 'at' hh:mm a", dayNumberSuffix), Locale.US);
        return dateFormat.format(date);
    }
}
