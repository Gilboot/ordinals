package com.github.ordinals;

import java.util.Objects;

abstract class Rule implements Comparable<Rule> {
    private final Gender gender;
    private final Join join;
    private final String longSuffix;
    private final Plural plural;
    private final int    precedence;
    private final String shortSuffix;

    /**
     * Constructor for rule objects
     * All parameters organized alphabetically
     */
    Rule(final Gender gender, final Join join, final String longSuffix, final Plural plural, final int precedence, final String shortSuffix) {
        this.gender      = gender;
        this.join        = join;
        this.longSuffix  = longSuffix;
        this.plural      = plural;
        this.precedence  = precedence;
        this.shortSuffix = shortSuffix;
    }

    abstract boolean matches(int i);

    abstract String ruleToString();

    public int getPrecedence() {
        return precedence;
    }

    public String getShortSuffix() {
        return shortSuffix;
    }

    public String getLongSuffix() {
        return longSuffix;
    }

    public Gender getGender() {
        return gender;
    }

    @Override public final int compareTo(final Rule other) {
        if (this.getPrecedence() == other.getPrecedence()) {
            throw new OrdinalsException("2 rules with equal precedence detected in the same locale: " + this + ", " + other);
        }

        return this.getPrecedence() > other.getPrecedence() ? 1 : -1;
    }

    @Override public final String toString() {
        return "[rule(" + precedence + "): " + ruleToString() + ", suffix: " + shortSuffix + ", fullName: " + longSuffix + ", gender: " + gender + "]";
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Rule)) return false;
        Rule other = Rule.class.cast(o);
        return  (gender      == null || gender.equals(other.gender)) &&
                (join        == null || join.equals(other.join)) &&
                (longSuffix  == null || longSuffix.equals(other.longSuffix)) &&
                (plural      == null || plural.equals(other.plural)) &&
                (precedence  == other.precedence) &&
                (shortSuffix == null || shortSuffix.equals(other.shortSuffix));
    }

    @Override
    public int hashCode() {
        return Objects.hash(precedence, shortSuffix, longSuffix, gender);
    }

    public Plural getPlural() {
        return plural;
    }

    public Join getJoin() {
        return join;
    }
}

