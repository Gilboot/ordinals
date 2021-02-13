package com.github.ordinals;


/**
 * A class that states the plurality of a noun ie Singular or Plural
 */
public enum Plural {
        SINGULAR, PLURAL;

        static Plural getPluralOf(final String p) {
            switch (p) {
                case "s":
                    return SINGULAR;
                case "p":
                    return PLURAL;
                default:
                    throw new RuntimeException("unexpected plurality: " + p);
            }
        }
}
