import java.util.Calendar;
import java.util.Date;

public final class SuffixGenerator {
    /**
     * Get the ordinal suffix for the day of the month.
     *
     * @param dayOfTheMonth the  day of the month
     *
     * @see https://stackoverflow.com/a/4011232/10030693
     *
     * @throws IllegalArgumentException if the value of {@code dayOfTheMonth} is not a valid day of a month.
     */
    public static String getDayOfMonthSuffix(final int dayOfTheMonth) {
        if (dayOfTheMonth < 1 || dayOfTheMonth > 31) {
            throw new IllegalArgumentException("day of month must be between 1 and 31 inclusive: " + dayOfTheMonth);
        }

        if (dayOfTheMonth >= 11 && dayOfTheMonth <= 13) {
            return "th";
        }

        switch (dayOfTheMonth % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

    /**
     * Get day of month suffix from a {@code Date}.
     *
     * @return suffix representing day of month.
     */
    public static String getDayOfMonthSuffix(final Date date) {
        return getDayOfMonthSuffix(getDayOfMonth(date));
    }

    /**
     * Produces the integer representing the day of the month, e.g., 17 in: January 17 2020.
     *
     * @param date The {@code Date} from which the day of the month is extracted.
     *
     * @return the day of the month for {@code date}.
     */
    public static int getDayOfMonth(final Date date) {
        if (date == null) {
            throw new NullPointerException("null: date");
        }

        var cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
}
