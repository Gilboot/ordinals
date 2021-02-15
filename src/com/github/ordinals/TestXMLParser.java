package com.github.ordinals;

import java.util.Locale;

/**
 * Simple class to test how xml parser is working
 */
public final class TestXMLParser {
    public static void main(final String... args) {
        new XMLParser().parse(Locale.US).forEach(System.out::println);
    }
}
