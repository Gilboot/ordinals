package com.github.ordinals;


import java.util.Objects;

/**
 * Matches based on how a number ends
 */
final class EndsWithRule extends Rule {
    final int endsWith;

    EndsWithRule(
        final int endsWith,
        final Gender gender,
        final String longSuffix,
        final Plural plural,
        final int    precedence,
        final String shortSuffix
    ) {
        super(gender, longSuffix, plural, precedence, shortSuffix);
        this.endsWith = endsWith;
    }

    @Override boolean matches(final int i) {
        return Integer.toString(i).endsWith(Integer.toString(endsWith));
    }


    @Override String ruleToString() {
        return "ends with " + endsWith;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && endsWith == ((EndsWithRule) o).endsWith;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), endsWith);
    }
}
