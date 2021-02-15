package com.github.ordinals;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && remainder == ((ModuloRule) o).remainder && modulus == ((ModuloRule) o).modulus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), remainder, modulus);
    }
}
