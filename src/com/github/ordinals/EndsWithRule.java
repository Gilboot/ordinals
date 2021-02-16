package com.github.ordinals;


import java.util.Objects;

/**
 * Matches based on how a number ends
 */
final class EndsWithRule extends Rule {
    final int endsWith;

    EndsWithRule(final int precedence, final String suffix, final String fullName, final Gender gender, final int endsWith) {
        super(precedence, suffix, fullName, gender);
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
