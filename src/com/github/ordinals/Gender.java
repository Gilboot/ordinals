package com.github.ordinals;

import java.util.Objects;

public enum Gender {
    NEUTRAL, MASCULINE, FEMININE;

    static Gender getGenderOf(final String g) {
        switch (g) {
            case "n": return Gender.NEUTRAL;
            case "m": return Gender.MASCULINE;
            case "f": return Gender.FEMININE;
            default: throw new RuntimeException("unexpected gender: " + g);
        }
    }
}
