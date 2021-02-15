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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ModuloRule that = (ModuloRule) o;

        if (remainder != that.remainder) return false;
        return modulus == that.modulus;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + remainder;
        result = 31 * result + modulus;
        return result;
    }
}
