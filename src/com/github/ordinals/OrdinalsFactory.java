package com.github.ordinals;

import com.github.ordinals.utils.Utils;
import java.io.InputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class OrdinalsFactory {
    private static final ConcurrentMap<Locale, OrdinalsFactory> factories = new ConcurrentHashMap<>();

    private final List<Rule> rules = new CopyOnWriteArrayList<>();

    public static OrdinalsFactory getInstance(final Locale locale) {
        return factories.computeIfAbsent(locale, OrdinalsFactory::new);
    }

    private OrdinalsFactory(final Locale locale) {
        final String resourceName = Utils.getResourceName(locale);
        final InputStream source = ResourceReader.readResourceAsStream(resourceName);
        this.rules.addAll(XMLParser.parse(source).stream().sorted().collect(Collectors.toList()));
    }

    public String getOrdinalWithSuffix(final int i) {
        return i + rules.stream().filter(r -> r.matches(i)).findFirst().get().getSuffix();
    }

    public String getOrdinalWithSuffix(final int i, final Gender g) {
        throw new RuntimeException("unimplemented");
    }

    public String getOrdinalFullName(final int i) {
        return rules.stream().filter(r -> r.matches(i)).findFirst().get().getFullName();
    }

    public String getOrdinalFullName(final int i, final Gender g) {
        throw new RuntimeException("unimplemented");
    }

    /**
     * Get the ordinal suffix for the day of the month.
     *
     * @param dayOfTheMonth the day of the month.
     *
     * @throws IllegalArgumentException if the value of {@code dayOfTheMonth} is not a valid day of a month.
     *
     * @see https://stackoverflow.com/a/4011232/10030693
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
     * Produces the day of month suffix from a {@code Date}.
     *
     * @param date The {@code Date} of which the day of the month suffix is produced.
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

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
}
