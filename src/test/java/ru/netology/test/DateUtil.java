package ru.netology.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    static String getDate(Integer plusDays, String formatArg) {
        LocalDate date = LocalDate.now().plusDays(plusDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatArg);
        return date.format(formatter);
    }
}
