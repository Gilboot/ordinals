package com.github.ordinals;

abstract class Rule {
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

    @Override public final String toString() {
        return "[rule(" + precedence + "): " + ruleToString() + ", suffix: " + suffix + ", fullName: " + fullName + ", gender: " + gender + "]";
    }
}
