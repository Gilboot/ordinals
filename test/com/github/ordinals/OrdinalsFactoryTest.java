package com.github.ordinals;

import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public final class OrdinalsFactoryTest {
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
