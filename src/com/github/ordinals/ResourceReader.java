package com.github.ordinals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class ResourceReader {
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
}
