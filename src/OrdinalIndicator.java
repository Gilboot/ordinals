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
    public static void main(String... args) {
        var date = new Date();
        var dateWithOrdinal = getFormattedDate(date);
        System.out.println(dateWithOrdinal);
    }

    /**
     * Format a date including a suffix as an ordinal indicator.
     *
     * @param date The {@code Date} to format.
     *
     * @return the date formatted.
     */
    public static String getFormattedDate(Date date) {
        var dayNumberSuffix = SuffixGenerator.getDayOfMonthSuffix(date);
        var dateFormat = new SimpleDateFormat(String.format("E',' dd'%1s' MMM yyyy 'at' hh:mm a", dayNumberSuffix), Locale.US);
        return dateFormat.format(date);
    }
}
