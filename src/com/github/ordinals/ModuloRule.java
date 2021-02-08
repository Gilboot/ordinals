package com.github.ordinals;

final class ModuloRule extends Rule {
    final int remainder;
    final int modulus;

    ModuloRule(final int precedence, final int remainder, final int modulus, final String suffix, final String fullName, final Gender gender) {
        super(precedence, suffix, fullName, gender);
        this.remainder = remainder;
        this.modulus = modulus;
    }

    @Override boolean matches(final int i) {
        return remainder == i % modulus;
    }

    public int getRemainder() {
        return remainder;
    }

    public int getModulus() {
        return modulus;
    }

    @Override String ruleToString() {
        return "congruent to " + remainder + " modulo " + modulus;
    }
}
