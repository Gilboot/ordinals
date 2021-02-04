import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public final class ResourceReader {
    /**
     * Produces the path of a resource file.
     *
     * @param resourceName resource file name.
     *
     * @return the Path object representing the file path
     *
     * @throws URISyntaxException if the resource file called {@code resourceName}.
     *
     * @see https://stackoverflow.com/a/59256704/10030693
     * @see https://stackoverflow.com/a/43973911/10030693
     */
    private static Path getFilePath(final String resourceName) throws URISyntaxException {
        URI uri = Objects.requireNonNull(ClassLoader.getSystemResource(resourceName).toURI());
        String resourcePath = Paths.get(uri).toString();
        return Paths.get(resourcePath);
    }

    /**
     * Get a list of all the lines in a resource file
     *
     * @param resourceName the  name of the resource file whose contents to get
     *
     * @return a list of the lines in the resource file
     *
     * @throws IOException if the resource file called {@code resourceName} does not exist or cannot be accessed.
     *
     * @see https://stackoverflow.com/a/58230499/10030693
     * @see https://stackoverflow.com/a/59256704/10030693
     */
    static List<String> readAllLinesInResourceFile(final String resourceName) throws IOException {
        try {
            Path resourcePath = getFilePath(resourceName);
            return Files.readAllLines(resourcePath);
        } catch (URISyntaxException uriSyntaxException) {
            throw new IOException("Failed to process uri from resource name");
        }
    }
}
