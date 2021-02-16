package com.github.ordinals;

import java.util.*;


/**
 * Contains two pairs of rules. Rules defined by the country and rules defined by the language
 * as obtained from the locale.
 */
public class RuleSetOwner {

    private final RuleSet languageRuleSet;
    private final RuleSet countryRuleSet;


    public RuleSetOwner(final Locale locale) {
        String country  = locale.getCountry();
        String language = locale.getLanguage();

        languageRuleSet = "".equals(language) ? new RuleSet() : new XMLParser().parse(language);
        countryRuleSet  = "".equals(country) ? new RuleSet() : new XMLParser().parse(locale.toString());
    }

    Rule getMatchingRule(int value) {
        Optional<Rule> match = languageRuleSet.getMatchingRule(value);
        if(! match.isPresent()) {
            match = countryRuleSet.getMatchingRule(value);
        }
        return match.orElse(null);
    }

    /**
     * Returns a list of all rules defined for the locale
     * @return rules
     */
    List<Rule> getAllRules() {
        Set<Rule> allRules = new HashSet<>();
        allRules.addAll(languageRuleSet.getRules());
        allRules.addAll(countryRuleSet.getRules());
        return new ArrayList<>(allRules);
    }
    
}
