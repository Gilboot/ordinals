package com.github.ordinals;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;

final class ResourceReader {
    private static final String RESOURCES_REPOSITORY_DIRECTORY = "resources";
    private static final String RESOURCE_NAME_PREFIX = "ordinals-";
    private static final String RESOURCE_NAME_SUFFIX = ".xml";

    /**
     * Produces an {@code InputStream} for the named resource.
     *
     * @param resourceName the resource name.
     *
     * @return the named resource as an {@code InputStream}.
     *
     * @throws OrdinalsException if the resource called {@code resourceName} does not exist or cannot be accessed.
     */
    static InputStream readResourceAsStream(final String resourceName) {
        final InputStream inputStream = ResourceReader.class.getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new OrdinalsException("could not access resource \"" + resourceName + "\" relative to " + ResourceReader.class);
        }
        return inputStream;
    }

    /**
     * Produces resource name as a function of {@code Locale}.
     *
     * @param locale The {@code Locale} of the resource.
     *
     * @return resource name.
     *
     * @throws NullPointerException if locale is {@code null}.
     */
    static String getResourceName(final Locale locale) {
        if (locale == null) {
            throw new NullPointerException("locale: null");
        }
        return RESOURCE_NAME_PREFIX + locale + RESOURCE_NAME_SUFFIX;
    }

    /**
     * Produces the repository (not jar) file path to the resource as a function of {@code Locale}.
     *
     * @param locale The {@code Locale} of the resource.
     *
     * @return the path to the repository resource file.
     */
    static String getResourcePath(final Locale locale) {
        return String.join(File.separator, RESOURCES_REPOSITORY_DIRECTORY, ResourceReader.class.getPackage().getName().replace('.', '/'), getResourceName(locale));
    }
}
