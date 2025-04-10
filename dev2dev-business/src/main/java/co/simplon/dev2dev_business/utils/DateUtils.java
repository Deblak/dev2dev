package co.simplon.dev2dev_business.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {
    public static OffsetDateTime convertStringToOffsetDateTime(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date, dateTimeFormatter);
        return offsetDateTime;
        //valider m apres conversion programmatic validation
    }
}
