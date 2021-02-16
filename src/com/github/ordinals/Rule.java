package com.github.ordinals;

import java.util.Objects;

abstract class Rule implements Comparable<Rule> {
    private final int    precedence;
    private final String shortSuffix;
    private final String longSuffix;
    private final Gender gender;

    Rule(final int precedence, final String shortSuffix, final String longSuffix, final Gender gender) {
        this.precedence  = precedence;
        this.shortSuffix = shortSuffix;
        this.longSuffix  = longSuffix;
        this.gender      = gender;
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
        return  (precedence == other.precedence) &&
                (shortSuffix == null ||  shortSuffix.equals(other.shortSuffix)) &&
                (longSuffix == null || longSuffix.equals(other.longSuffix)) &&
                (gender == null || gender.equals(other.gender));
    }

    @Override
    public int hashCode() {
        return Objects.hash(precedence, shortSuffix, longSuffix, gender);
    }
}

