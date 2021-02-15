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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        if (precedence != rule.precedence) return false;
        if (!Objects.equals(shortSuffix, rule.shortSuffix)) return false;
        if (!Objects.equals(longSuffix, rule.longSuffix)) return false;
        return gender == rule.gender;
    }

    @Override
    public int hashCode() {
        int result = precedence;
        result = 31 * result + (shortSuffix != null ? shortSuffix.hashCode() : 0);
        result = 31 * result + (longSuffix != null ? longSuffix.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }
}

