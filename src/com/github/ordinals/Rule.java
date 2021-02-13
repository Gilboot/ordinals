package com.github.ordinals;

abstract class Rule implements Comparable<Rule> {
    private final int precedence;
    private final String suffix;
    private final String fullName;
    private final Gender gender;

    Rule(final int precedence, final String suffix, final String fullName, final Gender gender) {
        this.precedence = precedence;
        this.suffix     = suffix;
        this.fullName   = fullName;
        this.gender     = gender;
    }

    abstract boolean matches(int i);

    abstract String ruleToString();

    public int getPrecedence() {
        return precedence;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getFullName() {
        return fullName;
    }

    public Gender getGender() {
        return gender;
    }

    @Override public final int compareTo(final Rule other) {
        if (this.getPrecedence() == other.getPrecedence()) {
            throw new OrdinalsException("2 rules with equal precedence detected in the same locale: " + this + ", " + other);
        }

        return this.getPrecedence() < other.getPrecedence() ? 1 : -1;
    }

    @Override public final String toString() {
        return "[rule(" + precedence + "): " + ruleToString() + ", suffix: " + suffix + ", fullName: " + fullName + ", gender: " + gender + "]";
    }
}
