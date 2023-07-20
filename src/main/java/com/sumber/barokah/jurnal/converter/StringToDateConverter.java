package com.sumber.barokah.jurnal.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Slf4j
@Component
public class StringToDateConverter implements Converter<String, LocalDateTime> {

    //private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // use Date
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ROOT); // use package java.time
    LocalDateTime dateTime;

    @Override
    public LocalDateTime convert(String source) {

        try {

           // return dateFormat.parse(source); // use Date
            return dateTime = LocalDateTime.parse(source, formatter);

        } catch (Exception e) {
            log.warn("Error convert data from string {}", source, e);
            return null;
        }
    }
}
