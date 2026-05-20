package com.soutenance.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {

    public static final DateTimeFormatter DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeUtil() {
    }

    public static LocalDate parseDate(String value) {
        return LocalDate.parse(value, DATE);
    }

    public static LocalTime parseTime(String value) {
        return LocalTime.parse(value, TIME);
    }

    public static String date(LocalDate value) {
        return value == null ? null : DATE.format(value);
    }

    public static String time(LocalTime value) {
        return value == null ? null : TIME.format(value);
    }

    public static String offset(OffsetDateTime value) {
        return value == null ? null : value.toString();
    }
}
