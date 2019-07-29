package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeConverter implements Converter<LocalDate, LocalDateTime> {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate localDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalTime localTime;

    @Override
    public LocalDateTime convert(LocalDate source) {
        return null;
    }

    public LocalDate convertDate(String source){
        localDate = LocalDate.parse(source);
        LocalDate ld = localDate;
        return ld;
    }

    public LocalTime convertTime(String source){
        localTime = LocalTime.parse(source);
        LocalTime lt = localTime;
        return lt;
    }

}
