package com.github.ordinals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML {@link Rule} parser.
 */
final class XMLParser {
    private static final String
        TOKEN_END             = "end",
        TOKEN_GENDER          = "gender",
        TOKEN_JOIN            = "join",
        TOKEN_LESS            = "less",
        TOKEN_LOCALE          = "locale",
        TOKEN_LONG_SUFFIX     = "long_suffix",
        TOKEN_MODULUS         = "modulus",
        TOKEN_MORE            = "more",
        TOKEN_PLURAL          = "plural",
        TOKEN_PRECEDENCE      = "precedence",
        TOKEN_REMAINDER       = "remainder",
        TOKEN_RULE            = "rule",
        TOKEN_RULES           = "rules",
        TOKEN_SHORT_SUFFIX    = "short_suffix",
        TOKEN_TYPE            = "type",
        TOKEN_VALUE           = "value";

    /**
     * Creates a parser for reading {@code Rules} encoded in XML.
     */
    XMLParser() {
    }

    /**
     * Produces a {@code RuleSet}
     * @throws NullPointerException if {@code locale} is {@code null}.
     *
     * @throws OrdinalsException if an error occurs while parsing {@code
     *                           Rules} XML.
     *
     * @see https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
     * @see https://stackoverflow.com/a/15690414/10030693 to get element of root node
     */
    RuleSet parse(final String locale) {
        if (locale == null) {
            throw new NullPointerException("locale: null");
        }
        if("".equals(locale)) {
            throw new OrdinalsException("Locale: blank");
        }

        // Initialize ruleSet
        // We shall first set the attribute on rules
        // And then add the set of rules
        RuleSet ruleSet = new RuleSet();

        try (final InputStream xml = ResourceReader.readResourceAsStream(ResourceReader.getResourceName(locale))) {
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();

            // inspect the rules element
            final Element rulesElement = doc.getDocumentElement();
            final String documentElementName = rulesElement.getTagName();
            if (! TOKEN_RULES.equals(documentElementName)) {
                throw new OrdinalsException("document element must be " + TOKEN_RULES + ", not " + documentElementName + " for locale " + locale);
            }

            final String localeAttribute = getAttribute(rulesElement, "locale");
            if ("".equals(localeAttribute) || localeAttribute == null) {
                throw new OrdinalsException(TOKEN_LOCALE + " attribute missing in " + TOKEN_RULES + " element for " + locale);
            }
            if(! locale.equals(localeAttribute)) {
                throw new OrdinalsException(localeAttribute + " in file does not match specified locale " + locale);
            }

            // set attribute value defaults for each individual rule from the rules element
            String shortSuffix     = getAttribute         (rulesElement, TOKEN_SHORT_SUFFIX);
            String longSuffix      = getAttribute         (rulesElement, TOKEN_LONG_SUFFIX);
            String gender          = getAttribute         (rulesElement, TOKEN_GENDER);
            String join            = getAttribute         (rulesElement, TOKEN_JOIN);
            String plural          = getAttribute         (rulesElement, TOKEN_PLURAL);

            ruleSet.setProperties(locale, join, shortSuffix, longSuffix, gender, plural);

            // read all of the rules, rules can appear in any order
            final NodeList nodeList = doc.getElementsByTagName(TOKEN_RULE);
            for (int ruleIndex = 0; ruleIndex < nodeList.getLength(); ruleIndex++) {
                final Node ruleNode = nodeList.item(ruleIndex);
                if (ruleNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element ruleElement = Element.class.cast(ruleNode);
                    final String ruleElementName = ruleElement.getTagName();
                    if (! TOKEN_RULE.equals(ruleElementName)) {
                        throw new OrdinalsException("expected " + TOKEN_RULE + " element, not " + ruleElementName + " for locale " + locale);
                    }

                    shortSuffix             = getAttribute(ruleElement, TOKEN_SHORT_SUFFIX);
                    longSuffix              = getAttribute(ruleElement, TOKEN_LONG_SUFFIX);
                    gender                  = getAttribute(ruleElement, TOKEN_GENDER);
                    plural                  = getAttribute(ruleElement, TOKEN_PLURAL);
                    join                    = getAttribute(ruleElement, TOKEN_JOIN);

                    final String precedence = getAttribute(ruleElement, TOKEN_PRECEDENCE); // precedence must be explicitly written, no default
                    final String type       = getAttribute(ruleElement, TOKEN_TYPE);
                    final String value      = getAttribute(ruleElement, TOKEN_VALUE);
                    final String remainder  = getAttribute(ruleElement, TOKEN_REMAINDER);
                    final String modulus    = getAttribute(ruleElement, TOKEN_MODULUS);
                    final String less       = getAttribute(ruleElement, TOKEN_LESS);
                    final String more       = getAttribute(ruleElement, TOKEN_MORE);
                    final String end        = getAttribute(ruleElement, TOKEN_END);

                    // All parameters are String objects organized in alphabetical order.
                    ruleSet.addRule(end, gender, join, longSuffix, less, modulus, more, plural, precedence, remainder, shortSuffix, type, value);
                } else {
                    throw new OrdinalsException("unexpected node type: " + ruleNode.getNodeType());
                }
            }

            return ruleSet;
        } catch (final Exception e) {
            throw new OrdinalsException("XML parse error " + locale, e);
        }
    }

    /**
     * Produces the value of the specified XML attribute of the speified
     * XML {@link Element}.
     *
     * @param element The {@code Element} for which the value of {@code
     *                attribute} is produced.
     *
     * @param attribute The value of this attribute is produced for the
     *                  {@code Element} {@code element}.
     *
     * @return the value of the {@code attribute} of the {@code Element}
     *         {@code element}.
     *
     * @throws NullPointerException if either {@code attribute}, or {@code
     *                              element} is {@code null}.
     */
    private String getAttribute(final Element element, final String attribute) {
        return element.getAttribute(attribute);
    }

    /**
     * Produces the value of the specified XML attribute of the speified
     * XML {@link Element}, or a default value specified in an identically
     * named attribute of the {@value #TOKEN_RULES} {@code Element}. The
     * empty string is produced if a value is specified for neither this
     * attribute, nor the default attribute.
     *
     * @param element The {@code Element} for which the value of {@code
     *                attribute} is produced.
     *
     * @param attribute The value of this attribute is produced for the
     *                  {@code Element} {@code element}.
     *
     * @param defaultValue The default value of this attribute if no value
     *                     is explicitly specified for {@code element}.
     *
     * @return the value of the {@code attribute} of the {@code Element}
     *         {@code element}, or {@code defaultValue} if no value is
     *         explicitly specified for {@code element}.
     *
     * @throws NullPointerException if any parameter is {@code null}.
     */
    private String getAttribute(final Element element, final String attribute, final String defaultValue) {
        if (defaultValue == null) {
            throw new NullPointerException("defaultValue is null for element " + element + " attribute " + attribute);
        }
        final String attr = getAttribute(element, attribute);
        return "".equals(attr) ? defaultValue : attr;
    }

    /**
     * Produces the value of the specified XML attribute of the speified
     * XML {@link Element} as an {@code int}, or zero if no value is
     * specified.
     *
     * @param element The {@code Element} for which the {@code int} value
     *                of {@code attribute} is produced.
     *
     * @param attribute The {@code int} value of this attribute is produced
     *                  for the {@code Element} {@code element}.
     *
     * @return the {@code int} value of the {@code attribute} of the {@code
     *         Element} {@code element}, or zero if no value is specified.
     *
     * @throws NullPointerException if either {@code attribute}, or {@code
     *                              element} is {@code null}.
     *
     * @throws NumberFormatException if the value of {@code attribute}
     *                               cannot be parsed as an {@code int}.
     */
    private int getAttributeAsInteger(final Element element, final String attribute) {
        final String attr = getAttribute(element, attribute);
        return "".equals(attr) ? 0 : Integer.parseInt(attr); // TODO: produce 0 or throw?
    }

    /**
     * Produces the value of the specified XML attribute of the speified
     * XML {@link Element} as an {@code int}, or a default value specified
     * in an identically named attribute of the {@value #TOKEN_RULES}
     * {@code Element}. Zero is produced if a value is specified for
     * neither this attribute, nor the default attribute.
     *
     * @param element The {@code Element} for which the {@code int} value
     *                of {@code attribute} is produced.
     *
     * @param attribute The {@code int} value of this attribute is produced
     *                  for the {@code Element} {@code element}.
     *
     * @param defaultValue The default value of this attribute if no value
     *                     is explicitly specified for {@code element}.
     *
     * @return the {@code int} value of the {@code attribute} of the {@code
     *         Element} {@code element}, or {@code defaultValue} if no
     *         value is explicitly specified for {@code element}.
     *
     * @throws NullPointerException if either {@code attribute}, or {@code
     *                              element} is {@code null}.
     *
     * @throws NumberFormatException if the value of {@code attribute}
     *                               cannot be parsed as an {@code int}.
     */
    private int getAttributeAsInteger(final Element element, final String attribute, final int defaultValue) {
        final boolean has = element.hasAttribute​(attribute);
        return has ? getAttributeAsInteger(element, attribute) : defaultValue;
    }

    /**
     * Produces the "parent" of the specified {@code Locale}. An empty
     * {@code Optional} is produced if the specified {@code Locale} is a
     * "root" {@code Locale}.
     *
     * @param locale The {@code Locale} for which the "parent" {@code
     *               Locale} is produced.
     *
     * @return An {@code Optional} either containing the "parent" {@code
     *         Locale} of the specified {@code Locale}, or is empty if the
     *         specified {@code Locale} is a "root" {@code Locale}.
     *
     * @throws NullPointerException if {@code locale} is {@code null}.
     *
     * @throws OrdinalsException if the specified {@code Locale} has an
     *                           unexpected name.
     *
     * @see https://stackoverflow.com/a/15238594/10030693
     */
    private Optional<Locale> getParentLocale(final Locale locale) {
        final String localeString = locale.toString();
        if ("".equals(localeString)) {
            throw new OrdinalsException("locale has no name");
        }
        final String[] parts = localeString.split("_");
        switch (parts.length) {
            case 1: return Optional.empty();
            case 2: return Optional.of(new Locale(parts[0]));
            case 3: return Optional.of(new Locale(parts[0], parts[1]));
            default: throw new OrdinalsException("locale has unexpectedly long name: " + localeString);
        }
    }
}
