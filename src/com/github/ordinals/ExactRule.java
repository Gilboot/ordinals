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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ExactRule exactRule = (ExactRule) o;

        return ordinal == exactRule.ordinal;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ordinal;
        return result;
    }
}
