package com.github.ordinals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public final class RuleTest {
    @Test public void equalityTest() {
        final Rule a = new ModuloRule(42, 1, 10, "foo", "bar", Gender.NEUTRAL);
        final Rule b = new ModuloRule(42, 1, 10, "foo", "bar", Gender.NEUTRAL);
        assertTrue(a.equals(b));
    }

    @Test public void inequalityTest() {
        final Rule a0 = new ModuloRule(42, 1, 10, "foo", "bar", Gender.NEUTRAL);
        final Rule b0 = new ModuloRule(73, 1, 10, "foo", "bar", Gender.NEUTRAL);
        assertFalse(a0.equals(b0));

        final Rule a1 = new ModuloRule(42, 1, 10, "foo", "bar", Gender.NEUTRAL);
        final Rule b1 = new ModuloRule(42, 2, 10, "foo", "bar", Gender.NEUTRAL);
        assertFalse(a1.equals(b1));

        final Rule a2 = new ModuloRule(42, 1,  4, "foo", "bar", Gender.NEUTRAL);
        final Rule b2 = new ModuloRule(42, 1, 10, "foo", "bar", Gender.NEUTRAL);
        assertFalse(a2.equals(b2));

        final Rule a3 = new ModuloRule(42, 1, 10, "foo", "bar", Gender.NEUTRAL);
        final Rule b3 = new ModuloRule(42, 1, 10, "baz", "bar", Gender.NEUTRAL);
        assertFalse(a3.equals(b3));

        final Rule a4 = new ModuloRule(42, 1, 10, "foo", "bar", Gender.NEUTRAL);
        final Rule b4 = new ModuloRule(42, 1, 10, "foo", "boz", Gender.NEUTRAL);
        assertFalse(a4.equals(b4));

        final Rule a5 = new ModuloRule(42, 1, 10, "foo", "bar", Gender.NEUTRAL);
        final Rule b5 = new ModuloRule(42, 1, 10, "foo", "bar", Gender.FEMININE);
        assertFalse(a5.equals(b5));
    }
}
