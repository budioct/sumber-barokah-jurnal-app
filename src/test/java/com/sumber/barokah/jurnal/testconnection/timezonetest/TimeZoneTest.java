package com.sumber.barokah.jurnal.testconnection.timezonetest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@SpringBootTest
public class TimeZoneTest {

    @Test
    void testTimeZoneDifferent(){

        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("LocalDateTime: {}", localDateTime); // LocalDateTime: 2023-07-14T14:02:06.170617600

        Instant instant = Instant.now();
        long epochMilli = instant.toEpochMilli();
        long epochSecond = instant.getEpochSecond();
        int nano = instant.getNano();

        // konversi ke localtime dan timezone
        LocalDateTime ofInstant = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Jakarta"));
        ZonedDateTime ofInstant1 = ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Jakarta"));

        log.info("Instant: {}", instant); // Instant: 2023-07-14T07:02:06.170617600Z
        log.info("epochMilli: {}", epochMilli); // Instant: 2023-07-14T07:02:06.170617600Z
        log.info("epochSecond: {}", epochSecond); // Instant: 2023-07-14T07:02:06.170617600Z
        log.info("nano: {}", nano); // Instant: 2023-07-14T07:02:06.170617600Z

        log.info("konversi ke LocalDateTime: {}", ofInstant); // konversi ke LocalDateTime: 2023-07-14T14:24:59.342938800
        log.info("konversi ke ZonedDateTime: {}", ofInstant1); // konversi ke ZonedDateTime: 2023-07-14T14:24:59.342938800+07:00[Asia/Jakarta]

    }



}
