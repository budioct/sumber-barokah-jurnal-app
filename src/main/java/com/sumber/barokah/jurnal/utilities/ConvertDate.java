package com.sumber.barokah.jurnal.utilities;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

@Component
public class ConvertDate {

    public static LocalDateTime convertToLocalDateTime(Instant instant) {

        if (Objects.isNull(instant)) {
            return null;
        }

        // return LocalDateTime.ofInstant(instant, ZoneOffset.of("+07:00")); // indonesian zone offset +07:00
        return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());

    }

    public static Instant convertToInstant(LocalDateTime localDateTime) {

        LocalDateTime ldt = LocalDateTime.now();

        return ldt.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"))
                .toInstant();

    }

}
