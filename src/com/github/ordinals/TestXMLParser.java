package com.github.ordinals;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 * Simple class to test how xml parser is working
 */
public final class TestXMLParser {
    public static void main(String[] args) {
        Locale locale = Locale.US;
        final String resourceName = ResourceReader.getResourceName(Locale.US);
        final InputStream source = ResourceReader.readResourceAsStream(resourceName);

        // changed from source to locale
        final List<Rule> rules = XMLParser.parse(locale);
        rules.forEach(System.out::println);
    }
}
