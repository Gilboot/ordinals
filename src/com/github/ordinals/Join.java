package com.github.ordinals;


/**
 * Join policy
 */

public enum Join {
    NO_SPACE, MINUS_ONE_CHAR, MINUS_ONE_WORD, SPACE;

    static Join getJoinOf(final String j) {
        switch (j) {
            case "minus_word": return Join.MINUS_ONE_WORD;
            case "minus_one": return Join.MINUS_ONE_CHAR;
            case "no_space": return Join.NO_SPACE;
            case "space": return Join.SPACE;
            default: throw new RuntimeException("unexpected join policy: " + j);
        }
    }
}
