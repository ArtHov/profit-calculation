package ru.fundcount.pc.profitcalculation.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";


    /**
     * Return date in YYYY-MM-DD format as String
     */
    public static String getCurrentDateAsString() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return currentDate.format(formatter);
    }
}
