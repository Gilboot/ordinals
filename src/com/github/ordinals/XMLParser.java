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
        TOKEN_LESS            = "less",
        TOKEN_LOCALE          = "locale",
        TOKEN_LONG_SUFFIX     = "long_suffix",
        TOKEN_MODULUS         = "modulus",
        TOKEN_MORE            = "more",
        TOKEN_PRECEDENCE      = "precedence",
        TOKEN_REMAINDER       = "remainder",
        TOKEN_RULE            = "rule",
        TOKEN_RULES           = "rules",
        TOKEN_SHORT_SUFFIX    = "short_suffix",
        TOKEN_TYPE            = "type",
        TOKEN_TYPE_ENDS_WITH  = "ends_with",
        TOKEN_TYPE_EXACT      = "exact",
        TOKEN_TYPE_INEQUALITY = "inequality",
        TOKEN_TYPE_MODULO     = "modulo",
        TOKEN_VALUE           = "value";

    /**
     * Creates a parser for reading {@code Rules} encoded in XML.
     */
    XMLParser() {
    }

    /**
     * Produces an ordered {@code List} (with respect to {@linkplain
     * Rule#getPrecedence()} precedence) of {@link Rule} data, parsed from
     * XML, on the classpath (as per {@link ResourceReader}), for the
     * specified {@code Locale}.
     * <p>
     * A {@code Rule} of a particular precedence value from a "child"
     * {@code Locale} supercedes/overrides any rule of equal precedence
     * from any "parent" {@code Locale}. The "child" {@code Locale} is
     * considered to be "more specific" than the "parent" {@code
     * Locale}. The superceded/overridden {@code Rule} is not included as a
     * member of the collection produced.
     * <p>
     * Example:
     * <p>
     * A {@code Rule} of precedence, say {@code 10}, from {@code Locale}
     * {@code "en_US"} ("child {@code Locale}")
     * supercedes/replaces/overrides/takes-precedence-over another {@code
     * Rule} of precedence {@code 10} from {@code Locale} {@code "en"} (a
     * "parent {@code Locale}" of {@code Locale} {@code "en_US"}).
     *
     * @param locale The {@code Locale} whose {@code Rules} are parsed
     *               along with {@code Rules} of all "parent" {@code
     *               Locales}.
     *
     * @return An ordered {@code List} (with respect to {@linkplain
     *         Rule#getPrecedence() precedence}) of {@code Rules} with no
     *         two {@code Rules} having equal precedence. Each {@code Rule}
     *         of a particular precedence is from "most specific child"
     *         {@code Locale}, overriding any {@code Rule} of equal
     *         precedence from any parent {@code Locale}.
     *
     * @throws NullPointerException if {@code locale} is {@code null}.
     *
     * @throws OrdinalsException if an error occurs while parsing {@code
     *                           Rules} XML.
     *
     * @see https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
     * @see https://stackoverflow.com/a/15690414/10030693 to get element of root node:
     */
    List<Rule> parse(final Locale locale) {
        if (locale == null) {
            throw new NullPointerException("locale: null");
        }
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

            if (! localeAttribute.equals(locale.toString())) {
                throw new OrdinalsException(TOKEN_LOCALE + " attribute in " + TOKEN_RULES + " element " + localeAttribute + " does not match locale " + locale);
            }

            // parent rules are in effect unless they are overridden by child rules at this level.
            final Optional<Locale> parentLocale = getParentLocale(locale);
            final List<Rule> parentRules = parentLocale.isPresent()
                ? parse(parentLocale.get())
                : new ArrayList<>(); // might be a root locale, no parent, recursion base case

            // set attribute value defaults for each individual rule from the rules element
            final String defaultType            = getAttribute         (rulesElement, TOKEN_TYPE);
            final int    defaultValue           = getAttributeAsInteger(rulesElement, TOKEN_VALUE);
            final String defaultShortSuffix     = getAttribute         (rulesElement, TOKEN_SHORT_SUFFIX);
            final String defaultLongSuffix      = getAttribute         (rulesElement, TOKEN_LONG_SUFFIX);
            final String defaultGender          = getAttribute         (rulesElement, TOKEN_GENDER);
            final int    defaultRemainder       = getAttributeAsInteger(rulesElement, TOKEN_REMAINDER);
            final int    defaultModulus         = getAttributeAsInteger(rulesElement, TOKEN_MODULUS);
            final int    defaultLess            = getAttributeAsInteger(rulesElement, TOKEN_LESS);
            final int    defaultMore            = getAttributeAsInteger(rulesElement, TOKEN_MORE);
            final int    defaultEnd             = getAttributeAsInteger(rulesElement, TOKEN_END);

            // read all of the rules, rules can appear in any order with respect to precedence, don't sort them yet
            final List<Rule> rules = new ArrayList<>();
            final NodeList nodeList = doc.getElementsByTagName(TOKEN_RULE);
            for (int ruleIndex = 0; ruleIndex < nodeList.getLength(); ruleIndex++) {
                final Node ruleNode = nodeList.item(ruleIndex);
                if (ruleNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element ruleElement = Element.class.cast(ruleNode);
                    final String ruleElementName = ruleElement.getTagName();
                    if (! TOKEN_RULE.equals(ruleElementName)) {
                        throw new OrdinalsException("expected " + TOKEN_RULE + " element, not " + ruleElementName + " for locale " + locale);
                    }

                    final int precedence = getAttributeAsInteger(ruleElement, TOKEN_PRECEDENCE); // precedence must be explicitly written, no default

                    final String type            = getAttribute         (ruleElement, TOKEN_TYPE,         defaultType);
                    final int    value           = getAttributeAsInteger(ruleElement, TOKEN_VALUE,        defaultValue);
                    final String shortSuffix     = getAttribute         (ruleElement, TOKEN_SHORT_SUFFIX, defaultShortSuffix);
                    final String longSuffix      = getAttribute         (ruleElement, TOKEN_LONG_SUFFIX,  defaultLongSuffix);
                    final String genderAttribute = getAttribute         (ruleElement, TOKEN_GENDER,       defaultGender);
                    final int    remainder       = getAttributeAsInteger(ruleElement, TOKEN_REMAINDER,    defaultRemainder);
                    final int    modulus         = getAttributeAsInteger(ruleElement, TOKEN_MODULUS,      defaultModulus);
                    final int    less            = getAttributeAsInteger(ruleElement, TOKEN_LESS,         defaultLess);
                    final int    more            = getAttributeAsInteger(ruleElement, TOKEN_MORE,         defaultMore);
                    final int    end             = getAttributeAsInteger(ruleElement, TOKEN_END,          defaultEnd);

                    final Gender gender = Gender.getGenderOf(genderAttribute);

                    final Supplier<Rule> ruleGenerator = () -> {
                        switch (type) {
                            case TOKEN_TYPE_EXACT:      return new ExactRule(precedence, value, shortSuffix, longSuffix, gender);
                            case TOKEN_TYPE_MODULO:     return new ModuloRule(precedence, remainder, modulus, shortSuffix, longSuffix, gender);
                            case TOKEN_TYPE_INEQUALITY: return new InequalityRule(precedence, shortSuffix, longSuffix, gender, less, more);
                            case TOKEN_TYPE_ENDS_WITH:  return new EndsWithRule(precedence, shortSuffix, longSuffix, gender, end);
                            default: throw new OrdinalsException("parse error: unrecognized type \"" + type + "\" for rule with precedence " + precedence);
                        }
                    };
                    rules.add(ruleGenerator.get());
                } else {
                    throw new OrdinalsException("unexpected node type: " + ruleNode.getNodeType());
                }
            }

            // override parent rules with child rules producing a sorted list (modified merge sort, choosing child rule if there is a precedence conflict)
            final List<Rule> result = new ArrayList<>();
            int parentRuleIndex = 0;
            for (Rule r : rules) {
                while (parentRuleIndex < parentRules.size() && parentRules.get(parentRuleIndex).getPrecedence() < r.getPrecedence()) { // lower number is higher precedence
                    result.add(parentRules.get(parentRuleIndex++));
                }
                while (parentRuleIndex < parentRules.size() && parentRules.get(parentRuleIndex).getPrecedence() == r.getPrecedence()) { // should be at most 1
                    ++parentRuleIndex;
                }
                result.add(r);
            }
            result.addAll(parentRules.subList(parentRuleIndex, parentRules.size()));
            return result;
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
