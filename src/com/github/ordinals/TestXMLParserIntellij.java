package com.github.ordinals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 * For Intellij
 * Simple class to test how xml parser is working
 */
public final class TestXMLParserIntellij {
    private static final Locale LOCALE = Locale.US;

    public static void main(final String[] args) {
        final String resourceFile = ResourceReader.getResourcePath(LOCALE);
        try (final InputStream source = new FileInputStream(new File(resourceFile))) {
            XMLParser.parse(source).forEach(System.out::println);
        } catch (final IOException e) {
            throw new OrdinalsException("Could not access resource " + resourceFile, e);
        }
    }
}
