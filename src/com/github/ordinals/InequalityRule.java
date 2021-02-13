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
        return (less != 0 && i < less) || (more != 0 && i > more);
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
}
