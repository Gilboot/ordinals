package com.github.ordinals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public final class RuleTest {
    @Test public void equalityTest() {
        final Rule a = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
        final Rule b = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
        assertTrue(a.equals(b));
    }

    @Test public void inequalityTest() {
//        final Rule a = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        final Rule b = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        assertFalse(a.equals(b));
//        Rule a = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "fook", 10, Plural.SINGULAR, 5, 1, "bar");
//        Rule b = new ModuloRule(Gender.MASCULINE, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        assertFalse(a.equals(b));

//        a = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        b = new ModuloRule(Gender.NEUTRAL, Join.SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        assertFalse(a.equals(b));
//
//        a = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        b = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "boom", 10, Plural.SINGULAR, 5, 1, "bar");
//        assertFalse(a.equals(b));
//
//        a = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        b = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 5, Plural.SINGULAR, 5, 1, "bar");
//        assertFalse(a.equals(b));
//
//        a = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        b = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.PLURAL, 5, 1, "bar");
//        assertFalse(a.equals(b));
//
//        a = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        b = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 1, 4, "bar");
//        assertFalse(a.equals(b));
//
//        a = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "bar");
//        b = new ModuloRule(Gender.NEUTRAL, Join.NO_SPACE, "foo", 10, Plural.SINGULAR, 5, 1, "gee");
//        assertFalse(a.equals(b));
    }
}
