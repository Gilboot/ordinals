package com.github.ordinals;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @see https://stackoverflow.com/q/4011075/10030693
 * @see https://en.m.wikipedia.org/wiki/Ordinal_indicator
 */
public final class OrdinalsFactory {
    private static final ConcurrentMap<Locale, OrdinalsFactory> factories = new ConcurrentHashMap<>();

    private final List<Rule> rules = new CopyOnWriteArrayList<>();

    public static OrdinalsFactory getInstance(final Locale locale) {
        return factories.computeIfAbsent(locale, OrdinalsFactory::new);
    }

    private OrdinalsFactory(final Locale locale) {
        // this.rules.addAll(new XMLParser().parse(locale));
        this.rules.addAll(new RuleSetOwner(locale).getAllRules());
    }

    public List<Rule> getRules() {
        return Collections.unmodifiableList(rules);
    }

    public String getOrdinalSuffix(final int i) {
        return rules.stream().filter(r -> r.matches(i)).findFirst().get().getShortSuffix();
    }

    public String getOrdinalWithSuffix(final int i) {
        return i + getOrdinalSuffix(i);
    }

    public String getOrdinalWithSuffix(final int i, final Gender g) {
        throw new RuntimeException("unimplemented");
    }

    public String getOrdinalFullName(final int i) {
        return rules.stream().filter(r -> r.matches(i)).findFirst().get().getLongSuffix();
    }

    public String getOrdinalFullName(final int i, final Gender g) {
        throw new RuntimeException("unimplemented");
    }

    /**
     * Produces the day of month suffix from a {@code Date}.
     *
     * @param date The {@code Date} of which the day of the month suffix is produced.
     *
     * @return suffix representing day of month.
     *
     * @see https://stackoverflow.com/a/4011232/10030693
     */
    public String getDayOfMonthSuffix(final Date date) {
        return getOrdinalSuffix(getDayOfMonth(date));
    }

    /**
     * Produces the integer representing the day of the month, e.g., 17 in: January 17 2020.
     *
     * @param date The {@code Date} from which the day of the month is extracted.
     *
     * @return the day of the month for {@code date}.
     */
    public int getDayOfMonth(final Date date) {
        if (date == null) {
            throw new NullPointerException("null: date");
        }

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * Format a date including a suffix as an ordinal indicator.
     *
     * @param date The {@code Date} to format.
     *
     * @return the date formatted.
     */
    public String getFormattedDate(final Date date) {
        final String dayNumberSuffix = getDayOfMonthSuffix(date);
        final DateFormat dateFormat = new SimpleDateFormat(String.format("E',' dd'%1s' MMM yyyy 'at' hh:mm a", dayNumberSuffix), Locale.US);
        return dateFormat.format(date);
    }
}
