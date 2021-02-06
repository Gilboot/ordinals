package com.github.ordinals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.github.ordinals.data.RuleXML;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import static com.github.ordinals.utils.Utils.getResourcePath;

/**
 * This class contains methods for parsing xml files into XMLRule objects
 */
public class XMLParser {

    /**
     * Looks up an xml file using the given locale and parses it into a list of XMLRules
     *
     * @param locale
     * @return a list of XMLRule objects
     * @see https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
     */
    public static List<RuleXML> getRules(Locale locale) {
        List<RuleXML> rules = new ArrayList<>();
        try {
            File inputFile = new File(getResourcePath(locale));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("rule");

            for (int nodePosition = 0; nodePosition < nodeList.getLength(); nodePosition++) {
                int precedence;
                String type;
                int value;
                String fullname;
                int remainder;
                int modulus;

                Node ruleNode = nodeList.item(nodePosition);
                if (ruleNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element ruleElement = (Element) ruleNode;
                    precedence = getAttributeAsInteger(ruleElement, "precedence");
                    type = getAttribute(ruleElement, "type");
                    value = getAttributeAsInteger(ruleElement, "value");
                    fullname = getAttribute(ruleElement, "fullname");
                    remainder = getAttributeAsInteger(ruleElement, "remainder");
                    modulus = getAttributeAsInteger(ruleElement, "modulus");
                    RuleXML ruleXML = new RuleXML(
                            precedence,
                            type,
                            value,
                            fullname,
                            modulus,
                            remainder
                    );
                    rules.add(ruleXML);
                }
            }
        } catch (Exception e) {
            throw new OrdinalsException("Failed to parse XML using locale " + locale, e);
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
        String attr = getAttribute(element, attribute);
        if ("".equals(attr)) return 0;
        return Integer.parseInt(getAttribute(element, attribute));
    }
}
