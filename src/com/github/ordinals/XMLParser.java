package com.github.ordinals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
     * Looks up an xml file using the given locale and parses it into a list of XMLRules
     *
     * @param inputStream An {@code InputStream} that accesses the ordinal rules encoded in XML.
     * @return a list of XMLRule objects
     * @see https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
     */
    public static List<Rule> parse(final InputStream inputStream) {
        final List<Rule> rules = new ArrayList<>();
        try {
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            final NodeList nodeList = doc.getElementsByTagName("rule");

            for (int ruleIndex = 0; ruleIndex < nodeList.getLength(); ruleIndex++) {
                final Node ruleNode = nodeList.item(ruleIndex);
                if (ruleNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element ruleElement = Element.class.cast(ruleNode);

                    final int    precedence      = getAttributeAsInteger(ruleElement, "precedence");
                    final String type            = getAttribute         (ruleElement, "type");
                    final int    value           = getAttributeAsInteger(ruleElement, "value");
                    final String suffix          = getAttribute         (ruleElement, "suffix");
                    final String fullName        = getAttribute         (ruleElement, "fullname");
                    final String genderAttribute = getAttribute         (ruleElement, "gender");
                    final int    remainder       = getAttributeAsInteger(ruleElement, "remainder");
                    final int    modulus         = getAttributeAsInteger(ruleElement, "modulus");
                    final int    less            = getAttributeAsInteger(ruleElement, "less");
                    final int    more            = getAttributeAsInteger(ruleElement,"more");
                    final int    end             = getAttributeAsInteger(ruleElement, "end");

                    final Gender gender = "".equals(genderAttribute) ? Gender.NEUTRAL : Gender.getGenderOf(genderAttribute);
                    switch (type) {
                        case "exact":
                            rules.add(new ExactRule(precedence, value, suffix, fullName, gender));
                            break;
                        case "modulo":
                            rules.add(new ModuloRule(precedence, remainder, modulus, suffix, fullName, gender));
                            break;
                        case "inequality":
                            rules.add(new InequalityRule(precedence, suffix, fullName, gender, less, more));
                            break;
                        case "ends_with":
                            rules.add(new EndsWithRule(precedence, suffix, fullName, gender, end));
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
}
