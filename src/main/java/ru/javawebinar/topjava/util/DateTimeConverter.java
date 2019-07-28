package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class DateTimeConverter implements Formatter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public LocalDateTime parse(@Nullable String text, Locale locale) throws ParseException {
        return LocalDateTime.of(Objects.requireNonNull(DateTimeUtil.parseLocalDate(text)), Objects.requireNonNull(DateTimeUtil.parseLocalTime(text)));
    }

    @Override
    public String print(Object object, Locale locale) {
        return null;
    }
}
