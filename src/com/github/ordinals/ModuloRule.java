package com.github.ordinals;

import java.util.Objects;

final class ModuloRule extends Rule {
    final int remainder;
    final int modulus;

    ModuloRule(
        final Gender gender,
        final String longSuffix,
        final int    modulus,
        final Plural plural,
        final int    precedence,
        final int    remainder,
        final String shortSuffix
    ) {
        super(gender, longSuffix, plural, precedence, shortSuffix);
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

    @Override
    public boolean equals(Object o) {
        final ModuloRule other = ModuloRule.class.cast(o);
        return super.equals(o) && remainder == other.remainder && modulus == other.modulus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), remainder, modulus);
    }
}
