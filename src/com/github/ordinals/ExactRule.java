package com.github.ordinals;

final class ExactRule extends Rule {
    final int ordinal;

    ExactRule(final int precedence, final int ordinal, final String suffix, final String fullName, final Gender gender) {
        super(precedence, suffix, fullName, gender);
        this.ordinal = ordinal;
    }

    @Override boolean matches(final int i) {
        return ordinal == i;
    }

    public int getValue() {
        return ordinal;
    }

    @Override String ruleToString() {
        return "equals to " + ordinal;
    }
}
