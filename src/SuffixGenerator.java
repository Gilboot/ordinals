import java.util.Calendar;
import java.util.Date;

public class SuffixGenerator {
    /**
     * Get suffix to indicate the day of month.
     * https://stackoverflow.com/a/4011232/10030693
     * @param n , the  day of the month
     */
    public static String getDayOfMonthSuffix(final int n) {
        // Requires third party library. Commenting out
        // checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);

        if (n >= 11 && n <= 13) {
            return "th";
        }

        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

    /**
     * Get day of month suffix from a Date
     * @return suffix representing day of month
     */
    public static String getDayOfMonthSuffix(Date date) {
        return getDayOfMonthSuffix(getDayOfMonth(date));
    }

    /**
     * Get the integer representing the day of the month eg 17 in January 17 2020
     * @param date
     * @return dayOfMonth
     */
    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }



}
