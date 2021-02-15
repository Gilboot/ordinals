package com.github.ordinals;


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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EndsWithRule that = (EndsWithRule) o;

        return endsWith == that.endsWith;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + endsWith;
        return result;
    }
}
