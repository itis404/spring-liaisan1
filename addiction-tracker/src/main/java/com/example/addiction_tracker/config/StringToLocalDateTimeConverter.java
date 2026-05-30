package com.example.addiction_tracker.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return LocalDateTime.now();
        }
        try {
            return LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }
}