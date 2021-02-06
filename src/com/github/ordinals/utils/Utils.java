package com.github.ordinals.utils;

import java.util.Locale;


/**
 * This class contains utility functions that maybe used in multiple places
 */
public class Utils {
    /**
     * Gets the name of the resource file based on the locale provide
     * @param locale
     * @return name of the resource file
     */
    public static String getResourceName(Locale locale) {
        return "ordinals-" + locale + ".xml";
    }

    /**
     * Gets the resource file path based on the locale provided
     * @param locale
     * @return file path to the resource file
     */
    public static String getResourcePath(Locale locale) {
        return "resources/com/github/ordinals/" + getResourceName(locale);
    }
}
