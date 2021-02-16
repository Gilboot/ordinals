package com.github.ordinals;

import java.util.*;
import java.util.function.Supplier;


/**
 * Data structure for rules obtained from a single xml file
 */
public class RuleSet {
    private Locale          locale;
    private Join            join;
    private String          shortSuffix;
    private String          longSuffix;
    private Gender          gender;
    private Plural          plural;
    final private Set<Rule> rules = new HashSet<>();

    private static final String
        TOKEN_TYPE_ENDS_WITH  = "ends_with",
        TOKEN_TYPE_EXACT      = "exact",
        TOKEN_TYPE_INEQUALITY = "inequality",
        TOKEN_TYPE_MODULO     = "modulo";


    /**
     * Default constructor. Takes no argument
     */
    public RuleSet() {
    }


    /**
     * Constructor for creating RuleSet that takes arguments.
     * @param locale a string to denote the locale
     * @param join a string to denote the join policy
     * @param shortSuffix denotes short suffix eg "st"
     * @param longSuffix denotes long suffix eg "first"
     * @param gender a string to denote gender
     * @param plural a string to denote plurality
     */
    public RuleSet(String locale, String join, String shortSuffix, String longSuffix, String gender, String plural, List<Rule> rules) {
        this.locale      = Locale.forLanguageTag(locale);
        this.join        = Join.getJoinOf(join);
        this.shortSuffix = shortSuffix;
        this.longSuffix  = longSuffix;
        this.gender      = Gender.getGenderOf(gender);
        this.plural      = Plural.getPluralOf(plural);

        // Add rules
        this.rules.addAll(rules);
    }


    /**
     * Method for adding properties of a RuleSet. All parameters are @{@code String} objects organized in alphabetical order.
     * @param gender denotes {@Code Gender}
     * @param join denotes {@code Join}
     * @param locale denotes {@code Locale}
     * @param longSuffix value of longSuffix
     * @param plural denotes {@code Plural}
     * @param shortSuffix value of shortSuffix
     */
    public void setProperties(String gender, String join, String locale, String longSuffix, String plural, String shortSuffix) {
        this.locale      = Locale.forLanguageTag(locale);
        this.join        = Join.getJoinOf(join);
        this.shortSuffix = shortSuffix;
        this.longSuffix  = longSuffix;
        this.gender      = Gender.getGenderOf(gender);
        this.plural      = Plural.getPluralOf(plural);
    }

    /**
     * Method for adding a rule. All parameters are @{@code String} objects organized in alphabetical order.
     * @param precedence
     * @param value
     * @param shortSuffix
     * @param gender
     * @param plural
     */

    /**
     * Method for adding a single rule to a RuleSet. All parameters are @{@code String} objects organized in alphabetical order.
     * @param end Property for {@code EndsWithRule}
     * @param gender denotes {@code Gender}
     * @param join denotes {@code Join}
     * @param longSuffix value of longSuffix
     * @param less Property for {@code InequalityRule}
     * @param modulus Property for {@code ModulusRule}
     * @param more Property for {@code InequalityRule}
     * @param plural denotes for {@code Plural}
     * @param precedence denotes precedence
     * @param remainder property for {@code ModulusRule}
     * @param shortSuffix denotes shortSuffix
     * @param type denotes type
     * @param value denotes value
     */
    public void addRule(String end, String gender, String join, String longSuffix, String less, String modulus, String more, String plural, String precedence, String remainder, String shortSuffix, String type, String value) {
        /*String finalType = type;
        final Supplier<Rule> ruleGenerator = () -> {
            switch (finalType) {
                case TOKEN_TYPE_EXACT:      return new ExactRule(precedence, value, shortSuffix, longSuffix, gender);
                case TOKEN_TYPE_MODULO:     return new ModuloRule(precedence, remainder, modulus, shortSuffix, longSuffix, gender);
                case TOKEN_TYPE_INEQUALITY: return new InequalityRule(precedence, shortSuffix, longSuffix, gender, less, more);
                case TOKEN_TYPE_ENDS_WITH:  return new EndsWithRule(precedence, shortSuffix, longSuffix, gender, end);
                default: throw new OrdinalsException("parse error: unrecognized type \"" + type + "\" for rule with precedence " + precedence);
            }
        };

         */
    }
}
