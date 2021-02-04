import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a date with an ordinal indicator.
 *
 * @see https://stackoverflow.com/q/4011075/10030693
 * @see https://en.m.wikipedia.org/wiki/Ordinal_indicator
 */
public final class OrdinalIndicator {
    /**
     * Simple entry point that creates a {@code Date} and represents it as a {@code String} together with an ordinal suffix.
     */
    public static void main(final String... args) {
        // For dates. Commenting out to test ordinals from resources.
        // We need a better testing workflow
        // var date = new Date();
        // var dateWithOrdinal = getFormattedDate(date);
        // System.out.println(dateWithOrdinal);

        // Show ordinals using resource files
        try {
            var englishOrdinals = ResourceReader.readAllLinesInResourceFile("resources/en.txt");
            // Represent 1 (one) in short ordinal format
            var line = englishOrdinals.get(1);
            var shortOrdinal = line.substring(2, 4);
            System.out.println(shortOrdinal);
        } catch (IOException e) {
            System.err.println("Failed to find required file: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Format a date including a suffix as an ordinal indicator.
     *
     * @param date The {@code Date} to format.
     *
     * @return the date formatted.
     */
    public static String getFormattedDate(final Date date) {
        var dayNumberSuffix = SuffixGenerator.getDayOfMonthSuffix(date);
        var dateFormat = new SimpleDateFormat(String.format("E',' dd'%1s' MMM yyyy 'at' hh:mm a", dayNumberSuffix), Locale.US);
        return dateFormat.format(date);
    }
}
