package ru.fundcount.pc.profitcalculation.util;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


    private static final String REGEXP = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";

    /**
     * Compere received date with current date if it already passed or current date
     * @param date received date
     * @return     boolean
     */
    public static boolean isValidDate(String date) {
        if (isValidDateFormat(date)) {
            return LocalDate.parse(DateUtil.getCurrentDateAsString()).compareTo(LocalDate.parse(date)) >= 0;
        }
        return false;
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    private static boolean isValidDateFormat(String date) {

        Pattern pattern = Pattern.compile(REGEXP);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

}
