import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Main Class
 * Represents a date with an ordinal indicator (https://en.m.wikipedia.org/wiki/Ordinal_indicator)
 * https://stackoverflow.com/q/4011075/10030693
 */
public class OrdinalIndicator {

    public static void main(String[] args) {
        // We create a date and represent it as a string together with the suffix
        Date date = new Date();
        String dateWithOrdinal = getFormattedDate(date);
        System.out.println(dateWithOrdinal);

    }

    /**
     * Format a date using SimpleDateFormat and include a suffix as an ordinal indicator
     *
     * @param date
     * @return
     */
    static String getFormattedDate(Date date) {
        String dayNumberSuffix = SuffixGenerator.getDayOfMonthSuffix(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(String.format("E',' dd'%1s' MMM yyyy 'at' hh:mm a", dayNumberSuffix), Locale.US);
        return dateFormat.format(date);
    }
}
