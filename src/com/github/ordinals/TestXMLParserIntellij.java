package com.github.ordinals;

import com.github.ordinals.utils.Utils;

import java.io.*;
import java.util.List;
import java.util.Locale;

/**
 * For Intellij
 * Simple class to test how xml parser is working
 */
public final class TestXMLParserIntellij {
    public static void main(String[] args) {
        Locale locale = Locale.US;
        final File f = new File(Utils.getResourcePath(locale));
        try {
            final InputStream source = new FileInputStream(f);
            final List<Rule> rules = XMLParser.parse(source);
            rules.forEach(System.out::println);
        } catch (IOException e) {
            throw new OrdinalsException("Could not access resource " + Utils.getResourceName(locale));
        }
    }


}
