package com.github.ordinals;


/**
 * Rule that tests whether a given number is less than what the rule species as less or more that what
 * the rule specifies as more.
 */
public class InequalityRule extends Rule{
    final int less;
    final int more;

    InequalityRule(final int precedence, final String suffix, final String fullName, final Gender gender, final int less, final int more) {
        super(precedence, suffix, fullName, gender);
        this.less = less;
        this.more = more;
    }

    @Override boolean matches(final int i) {
        if (less != 0 && more != 0) return i < less && i > more;
        if (less != 0) return i < less;
        return i > more;
    }

    public int getLess() {
        return less;
    }

    public int getMore() {
        return more;
    }

    @Override String ruleToString() {
        return "less than " + less + " or more than " + more;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        InequalityRule that = (InequalityRule) o;

        if (less != that.less) return false;
        return more == that.more;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + less;
        result = 31 * result + more;
        return result;
    }
}
