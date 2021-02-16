package com.github.ordinals;

public enum Gender {
    NEUTRAL, MASCULINE, FEMININE;

    static Gender getGenderOf(final String g) {
        switch (g) {
            case "n": return Gender.NEUTRAL;
            case "m": return Gender.MASCULINE;
            case "f": return Gender.FEMININE;
            case "": return null;
            default: throw new RuntimeException("unexpected gender: " + g);
        }
    }
}
