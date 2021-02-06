package com.github.ordinals;

import com.github.ordinals.data.RuleXML;

import java.util.List;
import java.util.Locale;

/**
 * Simple class to test how xml parser is working
 */
public class TestXMLParser {
    public static void main(String[] args) {
        List<RuleXML> rules = XMLParser.getRules(Locale.US);
        System.out.println(rules);
    }
}
