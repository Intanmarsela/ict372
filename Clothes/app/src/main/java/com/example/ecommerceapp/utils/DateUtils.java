package com.example.ecommerceapp.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Date utility class for filtering orders by date
 * Provides method to check if a date is within last 6 months
 */
public class DateUtils {
    /**
     * Check if a given date is within the last 6 months from now
     * @param date The date to check
     * @return true if date is within last 6 months, false otherwise
     */
    public static boolean isWithinLast6Months(Date date) {
        if (date == null) {
            return false;
        }

        Calendar sixMonthsAgo = Calendar.getInstance();
        sixMonthsAgo.add(Calendar.MONTH, -6);
        Date sixMonthsAgoDate = sixMonthsAgo.getTime();

        return date.after(sixMonthsAgoDate) || date.equals(sixMonthsAgoDate);
    }

    /**
     * Get the date 6 months ago from now
     * @return Date object representing 6 months ago
     */
    public static Date getSixMonthsAgo() {
        Calendar sixMonthsAgo = Calendar.getInstance();
        sixMonthsAgo.add(Calendar.MONTH, -6);
        return sixMonthsAgo.getTime();
    }
}
