package com.github.ordinals;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public final class OrdinalsFactoryTest {
    private final OrdinalsFactory osf = OrdinalsFactory.getInstance(Locale.US);

    private final String[] ordinals = new String[] {
        "0th",   "1st",  "2nd",  "3rd",  "4th",  "5th",  "6th",  "7th",  "8th",  "9th",
        "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th"
    };

    @Disabled("new rule interpreter strategy in flux")
    @Test public void zeroThroughTwentyOrdinals() {
        for (int i = 0; i < ordinals.length; ++i) {
            final String o = ordinals[i];
            final String s = osf.getOrdinalWithSuffix(i);
            assertTrue(o.equals(s), "expected \"" + ordinals[i] + "\" for " + i + " but got \"" + s + "\"");
        }
    }

    @Test public void aTest() {
        assertTrue(true);

        // TODO
        // final OrdinalsFactory osf = OrdinalsFactory.getInstance(Locale.US);
        // Stream.of(IntStream.range(1, 10),
        //           IntStream.range(18, 24),
        //           IntStream.range(28, 34),
        //           IntStream.range(99, 101),
        //           IntStream.range(999, 1001),
        //           IntStream.range(999999, 1000001))
        //     .flatMapToInt(i -> i).forEach(i -> {
        //             System.out.println("with suffix: " + osf.getOrdinalWithSuffix(i));
        //             System.out.println("full name: " + osf.getOrdinalFullName(i));
        //         });
    }
}
