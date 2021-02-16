package com.github.ordinals;

import java.util.Objects;

final class ExactRule extends Rule {
    final int value;

    /**
     * Constructor for ExactRule
     * All parameters organized alphabetically
     */
    ExactRule(
            final Gender gender,
            final String longSuffix,
            final Plural plural,
            final int    precedence,
            final String shortSuffix,
            final int    value
    ) {
        super(gender, longSuffix, plural, precedence, shortSuffix);
        this.value = value;
    }

    @Override boolean matches(final int i) {
        return value == i;
    }

    public int getValue() {
        return value;
    }

    @Override String ruleToString() {
        return "equals to " + value;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && value == ((ExactRule) o).value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
