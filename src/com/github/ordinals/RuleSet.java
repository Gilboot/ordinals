package com.github.ordinals;

import java.util.*;


/**
 * Data structure for rules obtained from a single xml file
 */
public class RuleSet {
    private Gender          gender;
    private Join            join;
    private Locale          locale;
    private String          longSuffix;
    private Plural          plural;
    private String          shortSuffix;
    final private Set<Rule> rules = new HashSet<>();



    /**
     * Default constructor. Takes no argument
     */
    public RuleSet() {
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
        this.join        = Join.getJoinOf(join);
        this.shortSuffix = shortSuffix;

        // Wrong method for finding locale, works only for locales with dashes not underscore ie. en-US not en_US
        this.locale      = Locale.forLanguageTag(locale);
        this.longSuffix  = longSuffix;
        this.gender      = Gender.getGenderOf(gender);
        this.plural      = Plural.getPluralOf(plural);
    }

    /**
     * Method for adding an ends with rule to the RuleSet. 
     * All parameters are @{@code String} objects organized in alphabetical order.
     * @param end         Property for {@code EndsWithRule}
     * @param gender      denotes {@code Gender}
     * @param join        denotes {@code Join}
     * @param longSuffix  value of longSuffix
     * @param plural      denotes for {@code Plural}
     * @param precedence  denotes precedence
     * @param shortSuffix denotes shortSuffix
     */
    public void addEndsWithRule(String end, String gender, String join, String longSuffix, String plural, String precedence, String shortSuffix) {
        testNotBlank(end,         "End");
        testNotNull (gender,      "Gender");
        testNotNull (join,        "Join");
        testNotBlank(longSuffix,  "Long Suffix");
        testNotNull (plural,      "Plural");
        testNotBlank(precedence,  "Precedence");
        testNotNull (shortSuffix, "Short Suffix");

        rules.add(new EndsWithRule(
                toInt(end), calculateGender(gender), calculateJoin(join), calculateLongSuffix(longSuffix),  calculatePlural(plural), toInt(precedence), calculateShortSuffix(shortSuffix)));
    }

    /**
     * Method for adding an exact rule to the RuleSet. 
     * All parameters are @{@code String} objects organized in alphabetical order.
     * @param gender      denotes {@code Gender}
     * @param join        denotes {@code Join}
     * @param longSuffix  value of longSuffix
     * @param plural      denotes for {@code Plural}
     * @param precedence  denotes precedence
     * @param shortSuffix denotes shortSuffix
     * @param value       denotes value
     */
    public void addExactRule(String gender, String join, String longSuffix, String plural, String precedence, String shortSuffix, String value) {
        testNotNull (longSuffix,  "Long Suffix");
        testNotBlank(precedence,  "Precedence");
        testNotNull (shortSuffix, "Short Suffix");
        testNotBlank(value,       "Value");
        testNotNull (gender,      "Gender");
        testNotNull (plural,      "Plural");
        testNotNull (join,        "Join");

        
        rules.add(new ExactRule(
                calculateGender(gender), calculateJoin(join), calculateLongSuffix(longSuffix),  calculatePlural(plural), toInt(precedence), calculateShortSuffix(shortSuffix), toInt(value)));
    }

      /**
     * Method for adding an inequality rule to the RuleSet. 
     * All parameters are @{@code String} objects organized in alphabetical order.
     * @param gender      denotes {@code Gender}.
     * @param join        denotes {@code Join}.
     * @param longSuffix  value of longSuffix.
     * @param plural      denotes for {@code Plural}.
     * @param precedence  denotes precedence.
     * @param shortSuffix denotes shortSuffix.
     */
    public void addInequalityRule(String gender, String join, String less, String longSuffix, String more, String plural, String precedence, String shortSuffix) {
        testNotNull (gender,      "Gender");
        testNotNull (join,        "Join");
        testNotNull(less,         "Less");
        testNotNull(longSuffix,   "Long Suffix");
        testNotNull(more,         "More");
        testNotNull (plural,      "Plural");
        testNotBlank(precedence,  "Precedence");
        testNotNull (shortSuffix, "Short Suffix");

        rules.add(new InequalityRule(
                calculateGender(gender), calculateJoin(join), toInt(less), calculateLongSuffix(longSuffix), toInt(more), calculatePlural(plural), toInt(precedence), calculateShortSuffix(shortSuffix)));
    }

    /**
     * Method for adding an modulo rule to the RuleSet. 
     * All parameters are @{@code String} objects organized in alphabetical order.
     * @param gender      denotes {@code Gender}.
     * @param join        denotes {@code Join}.
     * @param longSuffix  value of longSuffix.
     * @param modulus     Property for {@code ModuloRule}
     * @param plural      denotes for {@code Plural}.
     * @param precedence  denotes precedence.
     * @param remainder   Property for {@code ModuloRule}
     * @param shortSuffix denotes shortSuffix.
     */
    public void addModuloRule(String gender, String join, String longSuffix, String modulus, String plural, String precedence, String remainder, String shortSuffix) {
        testNotNull (gender,      "Gender");
        testNotNull (join,        "Join");
        testNotNull(longSuffix,  "Long Suffix");
        testNotBlank(modulus,     "Modulus");
        testNotNull (plural,      "Plural");
        testNotBlank(precedence,  "Precedence");
        testNotBlank(remainder,   "Remainder");
        testNotNull (shortSuffix, "Short Suffix");

        rules.add(new ModuloRule(
                calculateGender(gender), calculateJoin(join), calculateLongSuffix(longSuffix), toInt(modulus), calculatePlural(plural), toInt(precedence), toInt(remainder), calculateShortSuffix(shortSuffix)));
    }

    /**
     * Gets the rule matching the value and of smallest precedence
     * @param value
     */
    public Optional<Rule> getMatchingRule(int value) {
        return rules.stream()
            .filter(rule -> rule.matches(value))
            .max(Comparator.comparing(Rule::getPrecedence));
    }


    /**
     * Computes the equivalent Gender from the gender string else returns the Gender defined on the RuleSet object
     * @param gender
     * @return Gender
     */
    public Gender calculateGender(String gender) {
        return (Gender.getGenderOf(gender) == null) ? this.gender : Gender.getGenderOf(gender);
    }

    /**
     * Computes the equivalent Join from the join string else returns the Join defined on the RuleSet object
     * @param join
     * @return Join
     */
    public Join calculateJoin(String join) {
        return (Join.getJoinOf(join) == null) ? this.join : Join.getJoinOf(join);
    }

    /**
     * Calculate longSuffix. If longSuffix is blank, return the longSuffix defined on the RuleSet 
     * @param longSuffix
     * @return longSuffix
     */
    public String calculateLongSuffix(String longSuffix) {
        return ("".equals(longSuffix)) ? this.longSuffix : longSuffix;
    }
    
    /**
     * Computes the equivalent Plural from the plural string else returns the Plural defined on the RuleSet object
     * @param plural
     * @return Plural
     */
    public Plural calculatePlural(String plural) {
        return (Plural.getPluralOf(plural) == null) ? this.plural : Plural.getPluralOf(plural);
    }

    /**
     * Calculate shortSuffix. If shortSuffix is blank, return the shortSuffix defined on the RuleSet 
     * @param shortSuffix
     * @return shortSuffix
     */
    public String calculateShortSuffix(String shortSuffix) {
        return ("".equals(shortSuffix)) ? this.shortSuffix : shortSuffix;
    }



    /**
     * Converts a string to an integer
     * @param number The string representation of a number 
     * @return integer representation of the number
     */
    static int toInt(String number) {
        testNotNull(number, "Number as string"); 
        return ("".equals(number) ? 0 : Integer.parseInt(number));
    }

    /**
     * Check whether a given object is not null
     * If its null, throw an error
     * @param o the object to check
     * @param field the field we are validating
     * @throws OrdinalsException
     */
    static void testNotNull(Object o, String field) throws OrdinalsException {
        if(o == null) {
            throw new OrdinalsException(field + " can not be null");
        }
    }

    /**
     * Check whether a given String is not blank.
     * Use it for fields that can not be blank 
     * If tje field is blank an error is thrown
     * @param s, the String to check
     * @param field the field we are validating
     * @throws OrdinalsException
     */
    static void testNotBlank(String s, String field) throws OrdinalsException {
        testNotNull(s, field);
        if("".equals(s)) {
            throw new OrdinalsException(field + " can not be blank");
        }
    }

    public Set<Rule> getRules() {
        return rules;
    }
}
