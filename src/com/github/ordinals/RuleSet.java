package com.github.ordinals;

import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Data structure for rules obtained from a single xml file
 */
public class RuleSet {
    private final Locale locale;
    private final Join join;
    private final String shortSuffix;
    private final String longSuffix;
    private final Gender gender;
    private final Plural plural;
    private Set<Rule> rules;


    /**
     * Constructor for creating RuleSet
     * @param locale a string to denote the locale
     * @param join a string to denote the join policy
     * @param shortSuffix denotes short suffix eg "st"
     * @param longSuffix denotes long suffix eg "first"
     * @param gender a string to denote gender
     * @param plural a string to denote plurality
     */
    public RuleSet(String locale, String join, String shortSuffix, String longSuffix, String gender, String plural, List<Rule> rules) {
        this.locale = Locale.forLanguageTag(locale);
        this.join = Join.getJoinOf(join);
        this.shortSuffix = shortSuffix;
        this.longSuffix = longSuffix;
        this.gender = Gender.getGenderOf(gender);
        this.plural = Plural.getPluralOf(plural);

        // Add rules
        this.rules.addAll(rules);
    }
}
