package com.github.ordinals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class contains methods for parsing xml files into XMLRule objects
 */
public final class XMLParser {

    /**
     * Parses xml files to generate rules matching a given {@code locale}
     * @param locale the locale whose rules are required
     * @return a list of rules
     */
    public static List<Rule> parse(final Locale locale) { return parse(locale, new ArrayList<>()); }

    /**
      * Recursive.
      * Parses xml file with rules and adds them to existing rules in array list
      *
      * @param locale An {@code Locale} that is used to decide which ordinal rules to access that are encoded in XML.
      * @param existingRules An {@code ArrayList} that holds the rules we currently have and wish to add to
      * @return a list of rule objects
      * @see https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
      * @see https://stackoverflow.com/a/15690414/10030693 to get element of root node:
      */
    private static List<Rule> parse(final Locale locale, List<Rule> existingRules) {
        List<Rule> rules = new ArrayList<>(existingRules);
        final String resourceName = ResourceReader.getResourceName(locale);
        final InputStream source = ResourceReader.readResourceAsStream(resourceName);
        try {
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(source);
            doc.getDocumentElement().normalize();
            final NodeList nodeList = doc.getElementsByTagName("rule");

            // get rules from parent locale
            final Element rulesElement = doc.getDocumentElement();
            String localeName = getAttribute(rulesElement, "locale");
            if (!localeName.equals("")) {
                Locale parentLocale = localeFromString(localeName);

                // recursively call parse function
                rules = parse(parentLocale, rules);
            }

            // Set defaults
            final String defaultType            = getAttribute         (rulesElement, "type");
            final int    defaultValue           = getAttributeAsInteger(rulesElement, "value");
            final String defaultShortSuffix     = getAttribute         (rulesElement, "short_suffix");
            final String defaultLongSuffix      = getAttribute         (rulesElement, "long_suffix");
            final String defaultGender          = getAttribute         (rulesElement, "gender");
            final int    defaultRemainder       = getAttributeAsInteger(rulesElement, "remainder");
            final int    defaultModulus         = getAttributeAsInteger(rulesElement, "modulus");
            final int    defaultLess            = getAttributeAsInteger(rulesElement, "less");
            final int    defaultMore            = getAttributeAsInteger(rulesElement,"more");
            final int    defaultEnd             = getAttributeAsInteger(rulesElement, "end");


            // deal with individual rules
            for (int ruleIndex = 0; ruleIndex < nodeList.getLength(); ruleIndex++) {
                final Node ruleNode = nodeList.item(ruleIndex);
                if (ruleNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element ruleElement = Element.class.cast(ruleNode);

                    final int    precedence      = getAttributeAsInteger(ruleElement, "precedence");

                    final String type            = getAttribute         (ruleElement, "type", defaultType);
                    final int    value           = getAttributeAsInteger(ruleElement, "value", defaultValue);
                    final String shortSuffix     = getAttribute         (ruleElement, "short_suffix", defaultShortSuffix);
                    final String longSuffix      = getAttribute         (ruleElement, "long_suffix", defaultLongSuffix);
                    final String genderAttribute = getAttribute         (ruleElement, "gender", defaultGender);
                    final int    remainder       = getAttributeAsInteger(ruleElement, "remainder", defaultRemainder);
                    final int    modulus         = getAttributeAsInteger(ruleElement, "modulus", defaultModulus);
                    final int    less            = getAttributeAsInteger(ruleElement, "less", defaultLess);
                    final int    more            = getAttributeAsInteger(ruleElement,"more", defaultMore);
                    final int    end             = getAttributeAsInteger(ruleElement, "end", defaultEnd);
                    final Gender gender          = Gender.getGenderOf(genderAttribute);

                    switch (type) {
                        case "exact":
                            rules.add(new ExactRule(precedence, value, shortSuffix, longSuffix, gender));
                            break;
                        case "modulo":
                            rules.add(new ModuloRule(precedence, remainder, modulus, shortSuffix, longSuffix, gender));
                            break;
                        case "inequality":
                            rules.add(new InequalityRule(precedence, shortSuffix, longSuffix, gender, less, more));
                            break;
                        case "ends_with":
                            rules.add(new EndsWithRule(precedence, shortSuffix, longSuffix, gender, end));

                            break;

                        default:
                            throw new OrdinalsException("parse error: unrecognized type \"" + type + "\" for rule " + (ruleIndex + 1) + ", precedence(" + precedence + ")");
                    }
                }
            }
        } catch (final Exception e) {
            throw new OrdinalsException("XML parse error", e);
        }
        return rules;
    }

    /**
     * Gets an attribute from an element
     *
     * @param element
     * @param attribute
     * @return a string value of that attribute
     */
    private static String getAttribute(Element element, String attribute) {
        return element.getAttribute(attribute);
    }

    /**
     * Gets an attribute from an element
     *
     * @param element
     * @param attribute
     * @return a string value of that attribute
     */
    private static String getAttribute(Element element, String attribute, String defaultAttribute) {
        final String attr = getAttribute(element, attribute);
        return "".equals(attr) ? defaultAttribute : attr;
    }


    /**
     * Gets an integer attribute from an element
     *
     * @param element
     * @param attribute
     * @return 0 if the the attribute does not exist or an integer value of the attribute
     */
    private static int getAttributeAsInteger(Element element, String attribute) {
        final String attr = getAttribute(element, attribute);
        return ("".equals(attr)) ? 0 : Integer.parseInt(getAttribute(element, attribute));
    }

    /**
     * Gets an integer attribute from an element.
     * Returns default attribute if none is specified
     *
     * @param element
     * @param attribute
     * @param defaultAttribute The default attribute to set if no attribute was obtained
     * @return 0 if the the attribute does not exist or an integer value of the attribute
     */
    private static int getAttributeAsInteger(Element element, String attribute, int defaultAttribute) {
        final int attr = getAttributeAsInteger(element, attribute);
        return (attr == 0) ? defaultAttribute : Integer.parseInt(getAttribute(element, attribute));
    }





    /**
     * Gets the equivalent locale matching a string
     *
     * @see https://stackoverflow.com/a/15238594/10030693
     *
     * @param localeAsString String representation of locale
     * @return Locale Locale matching a given String
     */
    public static Locale localeFromString(String localeAsString) {
        String[] parts = localeAsString.split("_", -1);
        if (parts.length == 1) return new Locale(parts[0]);
        else if (parts.length == 2
                || (parts.length == 3 && parts[2].startsWith("#")))
            return new Locale(parts[0], parts[1]);
        else return new Locale(parts[0], parts[1], parts[2]);
    }
}
